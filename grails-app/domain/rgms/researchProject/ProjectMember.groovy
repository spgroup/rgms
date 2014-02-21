package rgms.researchProject

import rgms.member.Member

class ProjectMember {
    String name
    boolean responsable
    long cnpqId

    //@TODO Adicionar relacao com o member cadastrado no sistema
    static constraints = {
        name(maxSize: 100, nullable: false, blank: false)
        responsable(nullable: false, blank: false)
        cnpqId(nullable: true, blank: true)
    }
}
