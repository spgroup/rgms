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
        return (other != null && this.anoPublicacao == other.anoPublicacao
                && this.orientador == other.orientador
                && this.orientando == other.orientando
                && this.tipo == other.tipo
                && this.tituloTese == other.tituloTese)
    }

    String toString() {
        return "Titulo = " + this.tituloTese + "; Orientador = " + this.orientador + "; Orientando: " + this.orientando
    }
}
//#end
