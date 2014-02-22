package rgms.researchProject

class Funder {
    String name
    long code
    String nature


    static constraints = {
        code(nullable: true, blank: true)
        nature(nullable: false, blank: false, inList: ["AUXIO FINANCEIRO", "BOLSA", "REMUNERAÇÃO", "COOPERAÇÃO", "OUTRA"])
        name(maxSize: 100, nullable: false, blank: false)
    }
}
