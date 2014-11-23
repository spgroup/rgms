//#if($Orientation)
package rgms.member

class Orientation {
    String tipo
    String orientando
    Member orientador
    String tituloTese
    int anoPublicacao
    String instituicao
    String curso


    static constraints = {
        tipo(nullable: false, blank: false, inList: ["Mestrado", "Doutorado", "Iniciação Científica"])
        orientando(nullable: false)
        orientador(nullable: false)
        tituloTese(nullable: false, blank: false, unique: true)
        anoPublicacao(nullable: false, min: 0)
        instituicao(nullable: false, blank: false)
        curso(nullable: true)
    }

    boolean equals(Orientation other) {
        def compatible = (other != null)
        other?.properties.each {key, value  ->
            compatible = compatible && (this."$key" == value)
        }
        return compatible
        
    }

    static public def isFiltered(orientations,typeof) {
        for (orientation in orientations) {
            if(!(orientation.tipo).contains(typeof))
                return false
        }
        return true
    }

    static public def isFiltered(orientations,supervised, tipo) {
        for (orientation in orientations) {
            if(!(orientation.orientador).contains(supervised))
                return false
        }
        return true
    }

    String toString() {
        return "Titulo = " + this.tituloTese + "; Orientador = " + this.orientador + "; Orientando: " + this.orientando
    }
}
//#end
