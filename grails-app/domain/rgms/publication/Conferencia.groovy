package rgms.publication

class Conferencia extends Publication {

    String booktitle
    String pages

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