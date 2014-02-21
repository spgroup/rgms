package rgms.publication

class Book extends Publication {

    String publisher
    int volume
    String pages

    static constraints = {
        publisher nullable: false, blank: false
        volume nullable: false, blank: false, min: 1
        pages nullable: false, blank: false
    }

    //#if($Bibtex)
    String generateBib() {
        return null  //To change body of implemented methods use File | Settings | File Templates.
    }
    //#end
}
