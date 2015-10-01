//#if($funder)
package rgms.researchProject

class Funder {
    String name
    String code
    String nature


    static constraints = {
        code(nullable: false, blank: true, unique: true)
        nature(nullable: false, blank: false, inList: ["AUXILIO_FINANCEIRO", "BOLSA", "REMUNERACAO", "COOPERACAO", "OUTRA"])
        name(maxSize: 200, nullable: false, blank: false)
    }

    String toString(){
        return "[" + code + "] " + name + " [" + nature + "]"
    }

    boolean equals(Funder other) {
        def compatible = (other != null)
        other?.properties.each {key, value  ->
            compatible = compatible && (this."$key".equals(value))
        }
        return compatible

    }
}
//#end
