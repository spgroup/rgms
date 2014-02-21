package rgms.researchProject

import rgms.publication.Publication

class ResearchProject {
    String description
    String projectName
    String status

    int startYear
    int endYear

    static hasMany = [publications:Publication, projectMembers:ProjectMember, funders:Funder]

    static constraints = {
        projectName(maxSize: 300, nullable: false, blank: false)
        description(maxSize: 1000, nullable: false, blank: false)
        status(nullable: false, blank: false, inList: ["ENCERRADO","EM ANDAMENTO","CONCLUIDO"])
        startYear(nullable: false, blank: false)
        endYear(nullable: true, blank: true)
    }
}
