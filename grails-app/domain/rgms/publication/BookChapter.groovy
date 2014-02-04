package rgms.publication


class BookChapter extends Publication {

    String publisher
    int chapter

    static constraints = {
        publisher nullable: false, blank: false, unique: ['title', 'file', 'chapter']
        chapter nullable: false, blank: false, min: 1
    }

    //#if($Bibtex)
    String generateBib() {
        return new BibtexExport().generateBibtexBookChapter(this)
    }
    //#end
}
