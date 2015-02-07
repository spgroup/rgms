package rgms.publication

class Book extends Publication {

    String publisher
    int volume
    String pages
    String autores

    static constraints = {
        publisher nullable: false, blank: false, unique: ['title', 'volume']
        volume nullable: false, blank: false, min: 1
        pages nullable: false, blank: false
        autores nullable:false, blank: false
    }

    //#if($Bibtex)
    String generateBib() {
        return new BibtexExport().generateBibtexBook(this)
    }
    //#end
}
