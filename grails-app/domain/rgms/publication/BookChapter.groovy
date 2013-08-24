package rgms.publication


class BookChapter extends Publication {

    String publisher
	int chapter
	
    static constraints = {
		publisher nullable: false, blank: false
		chapter nullable: false, blank: false, min: 1
    }

    //#if($Bibtex)
    String generateBib() {
        return "@book{"+ "author=\"" + BibtexAux.organizeAuthors(members) + "\",\n title=\"" + title + "\",\n publisher=\""+ this.publisher + "\",\n year=\"" + publicationDate.getAt(Calendar.YEAR) + "\",\n chapter=\"" + this.chapter + "\"}"
    }
    //#end
}
