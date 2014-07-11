package rgms.publication


class Ferramenta extends Publication {

    String website
    String description

    static constraints = {
        website nullable: true, blank: true
        description nullable: false, blank: false
    }

    //#if($Bibtex)
    String generateBib() {
        return new BibtexExport().generateBibtexFerramenta(this)
    }
    //#end
}
