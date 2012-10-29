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
		return "@inproceedings{"+ super.members.get(0) + super.publicationDate.getAt(Calendar.YEAR) 
			+ ",author=\"" + BibtexAux.organizeAuthors(super.members) + "\",\n title=\"" + super.title + "\",\n booktitle=\"" 
			+ this.booktitle + "\",\n year=\"" + super.publicationDate.getAt(Calendar.YEAR) + "\",\n pages=\"" 
			+ this.pages + "\",\n month=\"" + super.publicationDate.getAt(Calendar.MONTH) + "\"}"
	}
	//#end
	
}