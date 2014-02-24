package rgms.researchProject

class ResearchProject {
    String projectName
    String description
    String status
    String responsible

    int startYear
    int endYear

    static hasMany = [funders:Funder, members:String]

    static constraints = {
        projectName(maxSize: 300, nullable: false, blank: false, unique: true)
        description(maxSize: 3000, nullable: false, blank: false)
        status(nullable: false, blank: false, inList: ["ENCERRADO","EM_ANDAMENTO","CONCLUIDO"])
        startYear(nullable: false, blank: false)
        endYear(nullable: true, blank: true)
        responsible(nullable: true, blank: true, maxSize: 300)
    }
}
