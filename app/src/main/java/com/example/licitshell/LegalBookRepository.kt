package com.example.licitshell

data class LegalBook(
    val id: Int,
    val title: String,
    val category: String,
    val description: String,
    val content: String,
    val sourceName: String,
    val sourceUrl: String
)

object LegalBookRepository {

    private val constitutionUrl = "https://legislative.gov.in/constitution-of-india/"
    private val indiaCodeUrl = "https://www.indiacode.nic.in/"

    private val books = listOf(
        book(1, "The Constitution of India", "Constitution", "The foundation of rights, duties, and government structure in India.", "Legislative Department", constitutionUrl),
        book(2, "Preamble and Fundamental Duties", "Constitution", "A beginner-friendly reading guide to constitutional values and citizen duties.", "Legislative Department", constitutionUrl),
        book(3, "Fundamental Rights Handbook", "Rights", "Learn equality, freedom, constitutional remedies, and protections available to every citizen.", "Legislative Department", constitutionUrl),
        book(4, "Directive Principles Made Simple", "Constitution", "Understand how the state is expected to promote justice, welfare, and opportunity.", "Legislative Department", constitutionUrl),
        book(5, "Right to Education Act, 2009", "Education Law", "Key school rights, enrollment protections, and access to basic education.", "Legislative Department", "https://lddashboard.legislative.gov.in/actsofparliamentfromtheyear/right-children-free-and-compulsory-education-act-2009"),
        book(6, "Juvenile Justice Act Overview", "Child Protection", "Rights, care, protection, and procedures related to children in need and conflict with law.", "Legislative Department", "https://lddashboard.legislative.gov.in/actsofparliamentfromtheyear/juvenile-justice-care-and-protection-children-act-2015"),
        book(7, "POCSO Act Learning Guide", "Child Protection", "Child safety, reporting abuse, legal safeguards, and victim-sensitive procedures.", "Legislative Department", "https://lddashboard.legislative.gov.in/actsofparliamentfromtheyear/protection-children-sexual-offences-act-2012"),
        book(8, "Child Labour Law Basics", "Labour Law", "Rules protecting children from hazardous work and exploitation.", "Ministry of Labour & Employment", "https://labour.gov.in/en/childlabour/child-labour-acts-and-rules"),
        book(9, "Consumer Protection Act Guide", "Consumer Law", "Rights of buyers, unfair practices, complaints, and dispute resolution.", "Department of Consumer Affairs", "https://consumeraffairs.nic.in/theconsumerprotection/consumer-protection-act-2019"),
        book(10, "Right to Information Act", "Transparency", "How citizens can ask for information from public authorities.", "Legislative Department", "https://lddashboard.legislative.gov.in/actsofparliamentfromtheyear/right-information-act-2005"),
        book(11, "Protection of Women from Domestic Violence Act", "Women’s Rights", "Understanding safety orders, residence rights, and legal relief.", "Legislative Department", "https://www.lddashboard.legislative.gov.in/actsofparliamentfromtheyear/protection-women-domestic-violence-act-2005"),
        book(12, "Sexual Harassment at Workplace Law", "Women’s Rights", "Protections, complaint process, and internal complaints committee basics.", "Legislative Department", "https://lddashboard.legislative.gov.in/actsofparliamentfromtheyear/sexual-harassment-women-workplace-prevention-prohibition-and-redressal"),
        book(13, "The Bharatiya Nyaya Sanhita Primer", "Criminal Law", "An introduction to the core structure of offences and punishments.", "India Code", "https://www.indiacode.nic.in/handle/123456789/20062"),
        book(14, "The Bharatiya Nagarik Suraksha Sanhita Primer", "Criminal Procedure", "Understand investigation, arrest, bail, and trial procedure basics.", "India Code", indiaCodeUrl),
        book(15, "The Bharatiya Sakshya Adhiniyam Primer", "Evidence Law", "A simple reading path for evidence, proof, documents, and witness rules.", "India Code", indiaCodeUrl),
        book(16, "Information Technology Act Basics", "Cyber Law", "Digital safety, cyber offences, and electronic record concepts.", "Legislative Department", "https://lddashboard.legislative.gov.in/actsofparliamentfromtheyear/information-technology-act-2000"),
        book(17, "Motor Vehicles Act Road Safety Guide", "Public Safety", "Traffic rules, road safety duties, and key legal responsibilities.", "Legislative Department", "https://lddashboard.legislative.gov.in/actsofparliamentfromtheyear/motor-vehicles-act-1988"),
        book(18, "Legal Services Authorities Act", "Legal Aid", "How free legal aid and Lok Adalats support access to justice.", "Legislative Department", "https://lddashboard.legislative.gov.in/actsofparliamentfromtheyear/legal-services-authorities-act-1987"),
        book(19, "Indian Contract Law Basics", "Civil Law", "Offer, acceptance, consideration, breach, and enforceable agreements.", "Legislative Department", "https://www.lddashboard.legislative.gov.in/actsofparliamentfromtheyear/indian-contract-act-1872"),
        book(20, "Family Law Starter Guide", "Family Law", "Marriage, maintenance, guardianship, and family protection concepts.", "Legislative Department", "https://www.lddashboard.legislative.gov.in/actsofparliamentfromtheyear/hindu-marriage-act-1955"),
        book(21, "Labour Codes and Worker Rights", "Labour Law", "Wages, social security, safety, and fair workplace protections.", "Ministry of Labour & Employment", "https://labour.gov.in/acts-rules/child-and-adolescent-labour-prohibition-and-regulation-act-1986"),
        book(22, "Environmental Protection Law", "Environment", "Public duties and legal tools for protecting air, water, and nature.", "Legislative Department", "https://www.lddashboard.legislative.gov.in/actsofparliamentfromtheyear/environment-protection-act-1986"),
        book(23, "Disaster Management Act Overview", "Public Safety", "Emergency response structures and citizen-facing protections.", "Legislative Department", "https://lddashboard.legislative.gov.in/list-acts-2005"),
        book(24, "Digital Privacy and Data Protection Basics", "Privacy", "A learning-focused overview of privacy, consent, and responsible data use.", "India Code", indiaCodeUrl)
    )

    fun getAllBooks(): List<LegalBook> = books

    fun getBookById(id: Int): LegalBook? = books.firstOrNull { it.id == id }

    private fun book(
        id: Int,
        title: String,
        category: String,
        description: String,
        sourceName: String,
        sourceUrl: String
    ): LegalBook {
        val content = buildString {
            appendLine("Overview")
            appendLine(description)
            appendLine()
            appendLine("Why open this source")
            appendLine("This detail page prepares the learner before opening the official text, PDF, or statute page in the in-app reader.")
            appendLine()
            appendLine("What to focus on")
            appendLine("1. Main definitions and who the law applies to.")
            appendLine("2. Key rights, duties, procedures, or restrictions.")
            appendLine("3. Remedies, penalties, complaint routes, or enforcement powers.")
            appendLine()
            appendLine("Reading note")
            appendLine("The next screen opens an official or public legal source in WebView so the learner can continue reading without leaving the app.")
        }
        return LegalBook(id, title, category, description, content, sourceName, sourceUrl)
    }
}
