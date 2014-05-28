package rgms.publication

import rgms.member.Member


class Conferencia extends Publication {

    String booktitle
    String pages
    Set<Member> authors

    static constraints = {
        booktitle nullable: false, blank: false
        pages nullable: false, blank: false
    }

    //#if($Bibtex)
    String generateBib() {
        return new BibtexExport().generateBibtexConferencia(this)
    }
    //#end
}