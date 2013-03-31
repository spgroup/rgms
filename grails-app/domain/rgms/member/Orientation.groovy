package rgms.member

import rgms.publication.Periodico

class Orientation {
    String tipo
    Member orientando
    Member orientador
    String descricao
    Periodico periodico

    static belongsTo = Member

    static constraints = {
        tipo(nullable: false, blank: false, inList: ["Mestrado","Doutorado","Iniciação Científica"])
        orientando(nullable: false)
        orientador(nullable: false)
        periodico(nullable: false)
    }

    public String toString()
    {
        return this.tipo + "- Aluno: " + orientando.name + "; Orientador: " + this.orientador.name + "; Periodico: " +
                this.periodico.title + ". " + this.descricao
    }
}
