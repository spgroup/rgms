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
		return "@book{"+ super.members.get(0) + super.publicationDate.getAt(Calendar.YEAR)
		+ ",author=\"" + BibtexAux.organizeAuthors(super.members) + "\",\n title=\"" + super.title + "\",\n publisher=\""
		+ this.publisher + "\",\n year=\"" + super.publicationDate.getAt(Calendar.YEAR) + "\",\n chapter=\""
		+ this.chapter + "\"}"
	}
	//#end
}
