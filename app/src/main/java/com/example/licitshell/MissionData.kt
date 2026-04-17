package com.example.licitshell

data class MissionStoryPage(
    val title: String,
    val story: String,
    val lesson: String
)

data class MissionStory(
    val key: String,
    val title: String,
    val subtitle: String,
    val theme: String,
    val pages: List<MissionStoryPage>
)

object MissionData {

    private val missions = listOf(
        MissionStory(
            key = "road_safety",
            title = "Road Safety Story",
            subtitle = "Learn traffic rules through a simple real-life story.",
            theme = "Safety",
            pages = listOf(
                MissionStoryPage(
                    title = "Aarav borrows a scooter",
                    story = "Aarav wants to ride his cousin's scooter to the market. His cousin asks whether he has a valid licence, helmet, and the vehicle documents.",
                    lesson = "Driving starts with legality. A valid licence, safety gear, and proper vehicle documents matter before the vehicle even moves."
                ),
                MissionStoryPage(
                    title = "The signal turns yellow",
                    story = "Aarav speeds up to cross the signal before it turns red, but a pedestrian steps onto the crossing. He brakes suddenly and realizes he almost caused harm.",
                    lesson = "Signals, crossings, and pedestrian movement are not suggestions. Rash driving on a public way can lead to penalties and criminal consequences."
                ),
                MissionStoryPage(
                    title = "A police checkpoint",
                    story = "At the checkpoint, Aarav is asked for his licence, registration, and insurance. He also notices another rider being stopped for not wearing a helmet.",
                    lesson = "Traffic enforcement checks compliance with the Motor Vehicles Act and road safety rules. Carrying valid documents and following safety norms protects everyone."
                ),
                MissionStoryPage(
                    title = "The lesson home",
                    story = "Aarav reaches home and tells his friends that safe driving is not only about avoiding fines. It is about protecting life, public safety, and legal responsibility.",
                    lesson = "Road safety combines law and responsibility. Safe speed, helmets, seat belts, sober driving, and respect for public roads are essential."
                )
            )
        ),
        MissionStory(
            key = "marriage_law",
            title = "Marriage Law Story",
            subtitle = "Understand marriage, registration, age, consent, and legal protection through a guided story.",
            theme = "Justice",
            pages = listOf(
                MissionStoryPage(
                    title = "Nisha asks about legal marriage",
                    story = "Nisha and Arjun want to understand how marriage works legally. They learn that marriage can involve different laws, such as the Hindu Marriage Act or the Special Marriage Act, depending on the situation.",
                    lesson = "Marriage law in India is not one-size-fits-all. The applicable law depends on the form of marriage and legal background."
                ),
                MissionStoryPage(
                    title = "Why age and consent matter",
                    story = "When they visit a legal aid camp, the counselor explains that age and consent are basic conditions. Child marriage is prohibited, and forced decisions can create serious legal consequences.",
                    lesson = "Marriage law is closely tied to age, consent, dignity, and protection. Child marriage and coercive situations raise legal issues immediately."
                ),
                MissionStoryPage(
                    title = "The value of registration",
                    story = "The couple also learns that registration is important because it helps prove the marriage and supports legal rights related to identity, maintenance, inheritance, and official records.",
                    lesson = "Registration strengthens legal certainty. It can become very important in future disputes, documentation, or legal protection."
                ),
                MissionStoryPage(
                    title = "When disputes happen",
                    story = "The counselor further explains that if cruelty, abandonment, unlawful second marriage, domestic violence, or dowry-related abuse occurs, a person may need both family-law remedies and separate criminal-law protection.",
                    lesson = "Marriage law is connected with broader legal protection. In difficult situations, users may need civil, family, and criminal remedies together."
                )
            )
        )
    )

    fun getMissions(): List<MissionStory> = missions

    fun getMissionByKey(key: String): MissionStory? = missions.firstOrNull { it.key == key }
}
