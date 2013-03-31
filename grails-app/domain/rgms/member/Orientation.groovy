package rgms.member

import rgms.publication.Periodico

class Orientation {
    String tipo
    String orientando
    Member orientador
    String tituloTese
    int anoPublicacao
    String instituicao
    String curso

    static constraints = {
        tipo(nullable: false, blank: false, inList: ["Mestrado","Doutorado","Iniciação Científica"])
        orientando(nullable: false)
        orientador(nullable: false)
        tituloTese(nullable: false, blank: false)
        anoPublicacao(nullable: false)
        instituicao(nullable: false, blank: false)
    }
}
