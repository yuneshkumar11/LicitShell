package com.example.licitshell

import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

object GeminiService {

    private const val MODEL_NAME = "gemini-2.5-flash"

    fun hasApiKey(): Boolean = BuildConfig.GEMINI_API_KEY.isNotBlank()

    fun generateReply(userPrompt: String): Result<String> {
        if (!hasApiKey()) {
            return Result.failure(IllegalStateException("Gemini API key is missing"))
        }

        return runCatching {
            val url = URL(
                "https://generativelanguage.googleapis.com/v1beta/models/$MODEL_NAME:generateContent"
            )
            val connection = (url.openConnection() as HttpURLConnection).apply {
                requestMethod = "POST"
                doOutput = true
                connectTimeout = 15000
                readTimeout = 30000
                setRequestProperty("Content-Type", "application/json")
                setRequestProperty("x-goog-api-key", BuildConfig.GEMINI_API_KEY)
            }

            val prompt = """
                You are LICIT Bot inside an Indian legal learning app for students.
                Explain clearly in simple language.
                Focus on Indian rights, constitution, road safety, marriage law, public safety, and educational guidance.
                Keep answers practical, easy to read, and not overly long.
                If the user asks for legal help, give educational information and suggest official sources for exact legal action.

                User question: $userPrompt
            """.trimIndent()

            val requestJson = JSONObject().apply {
                put(
                    "contents",
                    JSONArray().put(
                        JSONObject().put(
                            "parts",
                            JSONArray().put(
                                JSONObject().put("text", prompt)
                            )
                        )
                    )
                )
            }

            OutputStreamWriter(connection.outputStream).use { writer ->
                writer.write(requestJson.toString())
                writer.flush()
            }

            val responseCode = connection.responseCode
            val responseText = try {
                val stream = if (responseCode in 200..299) connection.inputStream else connection.errorStream
                stream?.bufferedReader()?.use(BufferedReader::readText).orEmpty()
            } finally {
                connection.disconnect()
            }

            if (responseCode !in 200..299) {
                val message = responseText.ifBlank { "Gemini request failed with code $responseCode" }
                throw IllegalStateException(message)
            }

            extractReply(responseText)
        }
    }

    private fun extractReply(responseText: String): String {
        val root = JSONObject(responseText)
        val candidates = root.optJSONArray("candidates")
        if (candidates == null || candidates.length() == 0) {
            throw IllegalStateException("No response candidates returned")
        }

        val parts = candidates
            .optJSONObject(0)
            ?.optJSONObject("content")
            ?.optJSONArray("parts")
            ?: throw IllegalStateException("No response parts returned")

        val builder = StringBuilder()
        for (index in 0 until parts.length()) {
            val text = parts.optJSONObject(index)?.optString("text").orEmpty()
            if (text.isNotBlank()) {
                if (builder.isNotEmpty()) builder.append("\n\n")
                builder.append(text.trim())
            }
        }

        val reply = builder.toString().trim()
        if (reply.isBlank()) {
            throw IllegalStateException("Gemini returned an empty reply")
        }
        return reply
    }
}
