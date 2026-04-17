package com.example.licitshell

data class QuizQuestion(
    val question: String,
    val options: List<String>,
    val correctIndex: Int
)

data class QuizSet(
    val key: String,
    val title: String,
    val description: String,
    val questions: List<QuizQuestion>
)

object QuizData {

    private val quizzes = listOf(
        QuizSet(
            key = "marriage",
            title = "Marriage Law Quiz",
            description = "10 questions on marriage law basics, registration, child marriage, and related legal issues.",
            questions = listOf(
                QuizQuestion(
                    "Which law is commonly used for civil marriage across religions in India?",
                    listOf("Motor Vehicles Act", "Special Marriage Act, 1954", "Railways Act, 1989", "Right to Information Act"),
                    1
                ),
                QuizQuestion(
                    "Under the Prohibition of Child Marriage Act, a female below what age is treated as a child?",
                    listOf("16", "17", "18", "21"),
                    2
                ),
                QuizQuestion(
                    "Which section of the Hindu Marriage Act deals with divorce?",
                    listOf("Section 5", "Section 8", "Section 13", "Section 17"),
                    2
                ),
                QuizQuestion(
                    "Which section of the Hindu Marriage Act deals with registration of marriage?",
                    listOf("Section 8", "Section 11", "Section 13B", "Section 24"),
                    0
                ),
                QuizQuestion(
                    "The Prohibition of Child Marriage Act, 2006 mainly aims to:",
                    listOf("Regulate traffic", "Prevent child marriage", "Register companies", "Grant pensions"),
                    1
                ),
                QuizQuestion(
                    "Which of the following can be a connected legal issue in a marriage dispute?",
                    listOf("Maintenance", "Driving licence renewal", "Passport stamping only", "Land measurement only"),
                    0
                ),
                QuizQuestion(
                    "Which section of the Special Marriage Act is commonly associated with divorce?",
                    listOf("Section 4", "Section 14", "Section 27", "Section 5"),
                    2
                ),
                QuizQuestion(
                    "A male below what age is treated as a child under the child marriage law?",
                    listOf("18", "19", "20", "21"),
                    3
                ),
                QuizQuestion(
                    "Divorce by mutual consent under the Hindu Marriage Act is linked with:",
                    listOf("Section 13B", "Section 5", "Section 7", "Section 17"),
                    0
                ),
                QuizQuestion(
                    "For exact marriage remedies and charges, the safest approach is to:",
                    listOf("Rely only on rumors", "Ignore the applicable statute", "Check the correct marriage law and case facts", "Assume one rule fits every religion"),
                    2
                )
            )
        ),
        QuizSet(
            key = "road_safety",
            title = "Road Safety Quiz",
            description = "10 questions on driving licence rules, road safety, rash driving, and transport law basics.",
            questions = listOf(
                QuizQuestion(
                    "Which Act is the main central law for driving, licences, and vehicle regulation?",
                    listOf("Special Marriage Act", "Motor Vehicles Act, 1988", "Citizenship Act, 1955", "Consumer Protection Act"),
                    1
                ),
                QuizQuestion(
                    "Which section of the Motor Vehicles Act deals with the need for a driving licence?",
                    listOf("Section 3", "Section 4", "Section 5", "Section 10"),
                    0
                ),
                QuizQuestion(
                    "Which section deals with age limits in connection with driving motor vehicles?",
                    listOf("Section 3", "Section 4", "Section 24", "Section 32"),
                    1
                ),
                QuizQuestion(
                    "Allowing an underage or unlicensed person to drive can create legal responsibility for:",
                    listOf("Only the weather department", "The vehicle owner or person in charge", "Only the passenger", "No one"),
                    1
                ),
                QuizQuestion(
                    "Which of the following is a core road safety requirement?",
                    listOf("Ignoring signals", "No documents needed", "Helmet and seat belt compliance", "Driving without registration"),
                    2
                ),
                QuizQuestion(
                    "Rash driving on a public way can also connect with which criminal law topic?",
                    listOf("Public way danger and rash driving offences", "Marriage registration only", "Citizenship by descent only", "Temple property law only"),
                    0
                ),
                QuizQuestion(
                    "Which of these can attract legal action on the road?",
                    listOf("Dangerous driving", "Maintaining lane discipline", "Using valid insurance", "Carrying documents"),
                    0
                ),
                QuizQuestion(
                    "Penalty details for traffic offences should be checked from:",
                    listOf("Random social media posts", "Latest official law and transport guidance", "Neighbour opinions only", "Unofficial posters only"),
                    1
                ),
                QuizQuestion(
                    "Which is NOT a safe driving behaviour?",
                    listOf("Following speed rules", "Using seat belts", "Driving after alcohol consumption", "Obeying signals"),
                    2
                ),
                QuizQuestion(
                    "The best summary of road safety law is:",
                    listOf("It only concerns vehicle colour", "It covers licences, documents, safety behaviour, and offences", "It applies only in villages", "It has no criminal link"),
                    1
                )
            )
        )
    )

    fun getQuizzes(): List<QuizSet> = quizzes

    fun getQuizByKey(key: String): QuizSet? = quizzes.firstOrNull { it.key == key }
}
