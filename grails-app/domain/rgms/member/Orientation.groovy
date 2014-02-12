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
        boolean compatible = true
        this.each { key, data ->
            compatible = compatible && (other."$key" == data)
        }
        return compatible
    }

    String toString() {
        return "Titulo = " + this.tituloTese + "; Orientador = " + this.orientador + "; Orientando: " + this.orientando
    }
}
//#end
