package com.example.licitshell

data class LibraryTopic(
    val id: Int,
    val title: String,
    val category: String,
    val subtitle: String,
    val content: String
)

data class LibraryVideo(
    val title: String,
    val source: String,
    val description: String,
    val url: String
)

object LibraryData {

    private val topics = listOf(
        LibraryTopic(
            id = 1,
            title = "Preamble and Nature of the State",
            category = "Justice",
            subtitle = "Understand why the Preamble is called the soul of the Constitution.",
            content = """
                Overview
                The Preamble introduces the Constitution and explains the source of authority, the ideals of the Republic, and the kind of State India chooses to be.

                Core knowledge
                The words "We, the People of India" show that sovereignty ultimately rests with the people.
                The Constitution was adopted on November 26, 1949, which is observed as Constitution Day.

                Nature of the Indian State
                Sovereign: India is internally and externally supreme.
                Socialist: Added by the 42nd Amendment in 1976, this reflects democratic socialism and equal opportunity.
                Secular: Added in 1976, the State has no official religion and respects all religions equally.
                Democratic: The government is elected by the people through universal adult franchise.
                Republic: The head of state is elected, not hereditary.

                Academic note
                The Preamble is not just decorative text. It helps explain constitutional philosophy and is often used to understand the spirit behind the Articles.
            """.trimIndent()
        ),
        LibraryTopic(
            id = 2,
            title = "Part I and Part II",
            category = "Justice",
            subtitle = "Union, territory, and citizenship at the start of the constitutional order.",
            content = """
                Part I: The Union and its Territory
                Article 1 declares "India, that is Bharat" and describes India as a Union of States.
                The Constitution uses the word Union to show that the Indian Union is indestructible and that states do not have a right to secede.

                Important powers
                Article 2 allows Parliament to admit new states into the Union.
                Article 3 allows Parliament to create new states, alter boundaries, increase or reduce territory, or change state names.
                The President must refer such a Bill to the concerned State Legislature for its views, but Parliament is not bound by those views.

                Part II: Citizenship
                Articles 5 to 10 were transitional provisions dealing with citizenship at the commencement of the Constitution, especially in the context of Partition.
                Article 11 gives Parliament the power to regulate citizenship by law.
                This power led to the Citizenship Act, 1955, which governs citizenship by birth, descent, registration, naturalization, and other modern questions.

                Key learning point
                India follows single citizenship. A person is a citizen of India, not separately of a state and the Union.
            """.trimIndent()
        ),
        LibraryTopic(
            id = 3,
            title = "Article 12, Article 13, and Constitutional Protection",
            category = "Justice",
            subtitle = "Know who counts as the State and why unconstitutional laws can be struck down.",
            content = """
                Article 12: Meaning of the State
                For the purpose of Fundamental Rights, the State includes:
                1. The Government and Parliament of India.
                2. The Government and Legislatures of the States.
                3. Local authorities such as municipalities and panchayats.
                4. Other authorities such as statutory bodies and public agencies.

                Why this matters
                To understand rights, students must know who rights are protected against.
                If a right is violated by the State, a person may seek constitutional remedies.
                If a private person commits a wrong, the usual route is criminal or civil law.

                Article 13: Laws inconsistent with Fundamental Rights
                Any law that takes away or abridges Fundamental Rights is void to the extent of inconsistency.
                This forms the foundation of judicial review.

                Judicial review
                Courts can examine whether a law passed by Parliament or a State Legislature violates the Constitution.
                If it does, that law can be declared invalid.
            """.trimIndent()
        ),
        LibraryTopic(
            id = 4,
            title = "Fundamental Rights: Articles 14 to 32",
            category = "Justice",
            subtitle = "The Magna Carta of the Indian Constitution.",
            content = """
                Right to Equality
                Article 14: Equality before law and equal protection of laws.
                Article 15: No discrimination only on grounds of religion, race, caste, sex, or place of birth.
                Article 16: Equality of opportunity in public employment, including space for reservations.
                Article 17: Abolition of untouchability.
                Article 18: Abolition of titles except military and academic distinctions.

                Right to Freedom
                Article 19 guarantees key freedoms including speech, peaceful assembly, association, movement, residence, and profession.
                These freedoms are subject to reasonable restrictions.
                Article 20 protects against ex post facto punishment, double jeopardy, and self-incrimination.
                Article 21 protects life and personal liberty according to procedure established by law.
                Article 21A guarantees free and compulsory education for children aged 6 to 14.

                Right Against Exploitation
                Article 23 prohibits trafficking and forced labour.
                Article 24 prohibits child labour below 14 years in hazardous settings.

                Freedom of Religion
                Articles 25 to 28 protect conscience, profession, practice, and management of religion with constitutional limits.

                Cultural and Educational Rights
                Articles 29 and 30 protect the rights of minorities to conserve culture and run educational institutions.

                Right to Constitutional Remedies
                Article 32 allows direct access to the Supreme Court when Fundamental Rights are violated.
                Writs include Habeas Corpus, Mandamus, Prohibition, Certiorari, and Quo Warranto.

                Emergency safeguard
                Articles 20 and 21 cannot be suspended even during an emergency.
            """.trimIndent()
        ),
        LibraryTopic(
            id = 5,
            title = "Directive Principles and Fundamental Duties",
            category = "Justice",
            subtitle = "Part IV and Part IV-A show the welfare goals of the Constitution.",
            content = """
                Nature of DPSP
                Directive Principles of State Policy are contained in Part IV.
                They are non-justiciable, meaning courts cannot enforce them directly.
                Even so, Article 37 says they are fundamental in the governance of the country.

                Broad categories
                Socialistic Principles:
                Articles 38, 39, 39A, 41, and 42 promote welfare, legal aid, just work conditions, and social justice.

                Gandhian Principles:
                Articles 40, 43, 46, 47, and 48 focus on village panchayats, cottage industries, weaker sections, public health, and cattle preservation.

                Liberal-Intellectual Principles:
                Articles 44, 45, 48A, 49, 50, and 51 cover the Uniform Civil Code idea, early childhood care, environment, monuments, separation of judiciary from executive, and international peace.

                Rights versus DPSP
                Fundamental Rights are generally enforceable and normally prevail in conflict.
                But the Constitution aims for harmony between Part III and Part IV.
                In Minerva Mills, the Supreme Court stressed balance between rights and welfare principles.

                Fundamental Duties
                Part IV-A and Article 51A list duties of citizens.
                These include respecting the Constitution, protecting unity, promoting harmony, safeguarding public property, and protecting the environment.
                The 11th duty requires providing educational opportunities to children between 6 and 14 years.
            """.trimIndent()
        ),
        LibraryTopic(
            id = 6,
            title = "Union Government: Part V",
            category = "Justice",
            subtitle = "President, Prime Minister, Parliament, Supreme Court, and CAG.",
            content = """
                Union Executive
                The President is the Head of State and constitutional head of the Union.
                The President is elected by an electoral college and acts largely on the aid and advice of the Council of Ministers.
                The President has executive, legislative, military, and pardoning powers.

                Prime Minister and Council of Ministers
                The Prime Minister is the real executive head.
                Article 74 provides for a Council of Ministers with the Prime Minister at the head.
                The Council is collectively responsible to the Lok Sabha.

                Parliament
                Parliament consists of the President, the Lok Sabha, and the Rajya Sabha.
                Lok Sabha is the House of the People and has stronger control over Money Bills.
                Rajya Sabha is the Council of States and is a permanent body.
                A Bill becomes law after passage through Parliament and Presidential assent.

                Supreme Court
                The Supreme Court is the guardian of the Constitution.
                It exercises judicial review and its judgments are binding on all courts in India.
                The President may seek its advisory opinion under Article 143.

                CAG
                The Comptroller and Auditor General audits public expenditure and protects the integrity of public finance.
            """.trimIndent()
        ),
        LibraryTopic(
            id = 7,
            title = "State Government and Federalism",
            category = "Justice",
            subtitle = "Governor, Chief Minister, State Legislature, High Court, and division of powers.",
            content = """
                State Executive
                The Governor is the constitutional head of the State and is appointed by the President.
                The Chief Minister is the real executive head and leads the Council of Ministers.
                The State Council of Ministers is collectively responsible to the Legislative Assembly.

                State Legislature
                Some states are unicameral with only a Legislative Assembly.
                Some are bicameral with both a Legislative Assembly and Legislative Council.
                The Assembly is the more powerful House.

                High Courts
                Every state has a High Court, though some states may share one.
                Under Article 226, High Courts can issue writs not only for Fundamental Rights but also for other legal rights.

                Federal structure
                Schedule 7 divides powers into:
                Union List: Parliament alone legislates on subjects like defence and foreign affairs.
                State List: States legislate on subjects like police and public order.
                Concurrent List: Both can legislate on subjects such as education and forests.

                Emergency framework
                Article 356 allows President's Rule when constitutional machinery fails in a state.
                Article 352 covers National Emergency.
                Article 360 deals with Financial Emergency.
            """.trimIndent()
        ),
        LibraryTopic(
            id = 8,
            title = "Local Self-Government",
            category = "Education",
            subtitle = "Panchayats and municipalities bring democracy to the grassroots.",
            content = """
                Part IX: Panchayats
                The 73rd Amendment gave constitutional status to Panchayati Raj institutions.
                The usual three-tier system is:
                Gram Panchayat at village level,
                Panchayat Samiti at block level,
                Zila Parishad at district level.

                Gram Sabha
                This is the foundation of local democracy and consists of all registered voters in the village.

                Reservation and term
                Seats are reserved for SCs and STs in proportion to population.
                Not less than one-third of seats are reserved for women, and some states provide higher reservation.
                The term of a Panchayat is five years.

                Part IX-A: Municipalities
                The 74th Amendment created the urban local government structure.
                Nagar Panchayat serves transitional areas.
                Municipal Council serves smaller urban areas.
                Municipal Corporation serves large urban areas.

                Important support bodies
                State Election Commission conducts local body elections.
                State Finance Commission reviews finances of Panchayats and Municipalities.

                Schedules
                The 11th Schedule lists 29 Panchayat subjects.
                The 12th Schedule lists 18 Municipal subjects.
            """.trimIndent()
        ),
        LibraryTopic(
            id = 9,
            title = "Amendments, Languages, and Special Areas",
            category = "Education",
            subtitle = "How the Constitution changes while protecting diversity and federal balance.",
            content = """
                Part XX and Article 368
                The Constitution can be amended through different methods depending on the subject.

                Methods of amendment
                Simple majority is used for some constitutional matters treated like ordinary law.
                Special majority is used for most constitutional amendments.
                Special majority plus ratification by at least half of the states is required for key federal features.

                Basic Structure doctrine
                Parliament can amend the Constitution, but it cannot destroy its basic structure.
                Core features such as democracy, secularism, judicial review, and constitutional supremacy are protected.

                Official language framework
                Article 343 recognises Hindi in Devanagari script as the official language of the Union, with English continuing in practice as an associate official language.
                The Eighth Schedule contains 22 recognised languages.

                Scheduled and Tribal Areas
                The Fifth Schedule applies to Scheduled Areas in several states.
                The Sixth Schedule creates Autonomous District Councils in parts of Assam, Meghalaya, Tripura, and Mizoram.
            """.trimIndent()
        ),
        LibraryTopic(
            id = 10,
            title = "Election Commission and Constitutional Bodies",
            category = "Education",
            subtitle = "Independent institutions that help protect democracy and public trust.",
            content = """
                Election Commission of India
                Article 324 places the superintendence, direction, and control of elections in the Election Commission.
                It conducts elections to Parliament, State Legislatures, and the offices of President and Vice-President.
                Article 326 reflects adult suffrage for citizens aged 18 and above.

                Why independence matters
                The Chief Election Commissioner is protected so that elections remain free and fair.
                The Model Code of Conduct helps maintain fairness during elections.

                UPSC
                The Union Public Service Commission helps preserve merit-based recruitment for public services.

                Finance Commission
                The Finance Commission recommends how tax revenue should be shared between the Union and the States.

                Teaching takeaway
                Constitutional democracy does not depend only on rights and institutions of government.
                It also depends on independent watchdog bodies that keep the system credible.
            """.trimIndent()
        ),
        LibraryTopic(
            id = 11,
            title = "Landmark Judgments and Living Constitutionalism",
            category = "Justice",
            subtitle = "The Constitution evolves through judicial interpretation.",
            content = """
                Kesavananda Bharati v. State of Kerala (1973)
                Parliament can amend the Constitution, but it cannot alter the basic structure.
                This case protects the core identity of the Constitution.

                Maneka Gandhi v. Union of India (1978)
                Article 21 was interpreted broadly.
                Any law depriving liberty must be just, fair, and reasonable.

                S.R. Bommai v. Union of India (1994)
                Secularism was recognised as part of the basic structure.
                The misuse of Article 356 was also checked.

                Justice K.S. Puttaswamy v. Union of India (2017)
                Privacy was recognised as a Fundamental Right under Article 21.
                This is highly important in the digital age.

                Why landmark cases matter
                The Constitution is a living document.
                Judicial interpretation helps apply old constitutional language to modern realities.
            """.trimIndent()
        ),
        LibraryTopic(
            id = 12,
            title = "Recent Amendments and Modern Constitutional Learning",
            category = "Safety",
            subtitle = "Connect the Constitution to present-day law, privacy, reservation, and governance.",
            content = """
                Recent constitutional developments
                The 101st Amendment introduced GST and created the GST Council.
                The 102nd Amendment gave constitutional status to the National Commission for Backward Classes.
                The 103rd Amendment introduced 10 percent reservation for Economically Weaker Sections.
                The 105th Amendment restored the power of States to identify their own socially and educationally backward classes.

                Women's reservation
                The 106th Amendment, also known as the Nari Shakti Vandan framework, provides for reservation for women in the Lok Sabha and State Legislative Assemblies.

                Privacy and data
                Modern constitutional learning must include privacy as part of Article 21.
                Students should understand that consent, dignity, and digital rights are now central to public law discussions.

                Emergency safeguards
                The 44th Amendment made emergency powers harder to misuse by replacing "internal disturbance" with "armed rebellion."
                Articles 20 and 21 remain protected even in emergency conditions.

                Final teaching note
                The Constitution is a living document.
                It protects liberty, structures power, promotes welfare, and adapts to new social and technological realities.
            """.trimIndent()
        ),
        LibraryTopic(
            id = 13,
            title = "Marriage Law Basics in India",
            category = "Justice",
            subtitle = "Marriage, registration, divorce, child marriage, bigamy, maintenance, and key legal routes.",
            content = """
                Overview
                Marriage law in India is not governed by one single law for every person. The applicable law often depends on the form of marriage and the personal law involved.

                Main laws to know
                Hindu Marriage Act, 1955:
                Section 5 deals with conditions for a valid Hindu marriage.
                Section 7 deals with ceremonies for a Hindu marriage.
                Section 8 deals with registration.
                Sections 11 and 12 deal with void and voidable marriages.
                Section 13 deals with divorce.
                Section 13B deals with divorce by mutual consent.
                Section 17 addresses bigamy through the legal framework linked to second marriage during the subsistence of the first.

                Special Marriage Act, 1954:
                Section 4 lays down the conditions for a special marriage.
                Sections 5 to 14 cover notice, publication, objection, solemnization, and certificate.
                Sections 24 and 25 deal with void and voidable marriages.
                Section 27 deals with divorce.
                Section 28 deals with divorce by mutual consent.

                Child marriage
                The Prohibition of Child Marriage Act, 2006 prohibits child marriage.
                A child means a male below 21 years or a female below 18 years.
                The Act also provides punishment for promoting, permitting, or performing child marriage and allows protective intervention.

                Important legal issues students should know
                A marriage can raise questions about validity, registration, age, consent, prohibited relationships, maintenance, domestic violence, custody, and property.
                Cruelty, dowry-related abuse, domestic violence, abandonment, or unlawful second marriage can trigger separate civil and criminal consequences depending on facts.

                Practical note
                Exact remedies and charges depend on the facts and the law being applied. For educational use, start with the correct marriage statute, then look at family court remedies and any connected criminal law issues.
            """.trimIndent()
        ),
        LibraryTopic(
            id = 14,
            title = "Road Safety and Driving Rules",
            category = "Safety",
            subtitle = "Driving licence, age limits, registration, rash driving, and road safety compliance.",
            content = """
                Core law
                The Motor Vehicles Act, 1988 is the central law governing driving, registration, licensing, permits, insurance, offences, and penalties relating to motor vehicles.

                Important provisions to study
                Section 3: Necessity for driving licence.
                Section 4: Age limit in connection with driving motor vehicles.
                Section 5: Responsibility of vehicle owners for contravention of licence and age requirements.
                The Act also covers learner licences, driving licences, registration, permits, insurance, and enforcement.

                Safety issues users should understand
                Driving without a valid licence, allowing an underage or unlicensed person to drive, dangerous driving, drunken driving, lack of registration, and insurance violations can all attract legal action.
                Road safety also includes helmets, seat belts, valid documents, traffic signal compliance, speed regulation, and safe driving behaviour.

                Criminal law connection
                The Bharatiya Nyaya Sanhita, 2023 includes offences such as rash driving or riding on a public way and obstruction or danger on a public way.
                Serious accidents may also lead to additional criminal charges depending on injury, death, negligence, or public endangerment.

                Practical note
                Penalty amounts and enforcement details may change through amendments and notifications. For exact current penalties, users should check the latest official Motor Vehicles law and transport department guidance.
            """.trimIndent()
        ),
        LibraryTopic(
            id = 15,
            title = "Public Places and Public Conduct",
            category = "Safety",
            subtitle = "Understand public nuisance, smoking in public places, safety, and lawful conduct in shared spaces.",
            content = """
                Why this topic matters
                Public places are shared by everyone, so the law focuses on safety, order, convenience, and respect for others.

                Important legal areas
                Bharatiya Nyaya Sanhita, 2023:
                Section 270 deals with public nuisance.
                Section 281 deals with rash driving or riding on a public way.
                Section 285 deals with danger or obstruction in a public way or line of navigation.

                Tobacco control in public places
                The Cigarettes and Other Tobacco Products Act, 2003 is important for public place regulation.
                Section 4 prohibits smoking in a public place.
                Section 6 restricts sale to persons below 18 years and in certain areas near educational institutions.

                What kinds of behaviour may create legal problems
                Obstructing a public way, creating danger, causing public nuisance, smoking in prohibited public spaces, damaging public property, or engaging in violent or abusive conduct can trigger legal consequences.

                Practical note
                The exact charge depends on the behaviour, the place, and the harm caused. Local police laws, municipal rules, and special local regulations may also apply.
            """.trimIndent()
        ),
        LibraryTopic(
            id = 16,
            title = "Public Transport and Passenger Safety",
            category = "Safety",
            subtitle = "Passenger rights, railway law, lawful travel conduct, and transport-related safety.",
            content = """
                Main laws
                Public transport issues can involve the Motor Vehicles Act, 1988, the Railways Act, 1989, service rules of transport agencies, and general criminal law.

                Railways
                The Railways Act, 1989 governs railway administration, carriage of passengers, safety systems, accident-related issues, and offences connected with railway travel.
                It is a key source for understanding lawful travel, passenger responsibilities, and railway-related offences.

                Road-based public transport
                Buses, taxis, and other public transport vehicles are generally regulated through the Motor Vehicles Act framework, permit rules, licensing rules, and state transport enforcement.

                Common safety and legal issues
                Travelling without a valid ticket, misconduct in transport systems, damage to transport property, harassment of passengers, unsafe driving by transport vehicles, overloading, and violation of permit or safety rules may all lead to penalties or prosecution.

                Practical note
                Passenger complaints can also involve consumer rights, police complaints, transport authority complaints, or women and child safety mechanisms depending on the incident.
            """.trimIndent()
        ),
        LibraryTopic(
            id = 17,
            title = "Society and Community Living Rules",
            category = "Education",
            subtitle = "Housing society basics, common area rules, nuisance, and shared responsibilities.",
            content = """
                Overview
                Housing society and residential community rules are often shaped by state-level laws, bye-laws, apartment ownership rules, cooperative society laws, and local authority regulations.

                What users should know first
                Society rules usually cover membership, maintenance charges, use of common areas, noise, meetings, parking, waste disposal, visitors, safety, and community discipline.

                When legal issues arise
                Disputes may involve unpaid dues, nuisance, encroachment, damage to common property, harassment, violence, discrimination, or violation of registered bye-laws.
                Depending on the issue, the remedy may be internal society action, civil proceedings, cooperative authority complaint, municipal complaint, or police complaint.

                Public law overlap
                If conduct becomes dangerous, violent, or obstructive, public law may apply in addition to society rules.
                Public nuisance, trespass, property damage, harassment, and safety violations can move beyond internal society discipline into criminal or municipal action.

                Practical note
                This is a state-sensitive topic. For exact society sections and procedures, users should check the cooperative society or apartment law applicable in their own state along with the bye-laws of the specific society.
            """.trimIndent()
        )
    )

    private val videos = listOf(
        LibraryVideo(
            title = "Video Library on the Indian Constitution",
            source = "ConstitutionofIndia.net",
            description = "A curated constitutional video library with explainers, talks, and learning resources.",
            url = "https://www.constitutionofindia.net/videos/"
        ),
        LibraryVideo(
            title = "The Making of India’s Constitution",
            source = "ConstitutionofIndia.net",
            description = "An animated explainer about how India’s Constitution was drafted and shaped.",
            url = "https://www.constitutionofindia.net/video/the-making-of-indias-constitution/"
        ),
        LibraryVideo(
            title = "Department of Justice Video Resources",
            source = "Department of Justice, Government of India",
            description = "Legal literacy and legal awareness videos on rights, education, and law-related topics.",
            url = "https://www.doj.gov.in/videos"
        ),
        LibraryVideo(
            title = "NALSA Awareness Videos",
            source = "National Legal Services Authority",
            description = "Official legal awareness videos on rights, legal aid, workplace safety, and justice access.",
            url = "https://nalsa.gov.in/video-category/awareness-video/"
        ),
        LibraryVideo(
            title = "NCPCR IEC Video Gallery",
            source = "National Commission for Protection of Child Rights",
            description = "Official child-rights video resources, including POCSO and Right to Education awareness material.",
            url = "https://ncpcr.gov.in/iec-videos"
        )
    )

    fun getTopics(): List<LibraryTopic> = topics

    fun getTopicById(id: Int): LibraryTopic? = topics.firstOrNull { it.id == id }

    fun getVideos(): List<LibraryVideo> = videos
}
