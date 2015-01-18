package rgms.publication

class Book extends Publication {

    String publisher
    int volume
    String pages

    static constraints = {
        publisher nullable: false, blank: false, unique: ['title', 'volume']
        volume nullable: false, blank: false, min: 1
        pages nullable: false, blank: false
    }

    //#if($Bibtex)
    String generateBib() {
        return new BibtexExport().generateBibtexBook(this)
    }
    //#end

    Book findByTitle(){
        return null;
    }

}
