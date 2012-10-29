package rgms.publication


class Periodico extends Publication {

	String journal
	int volume
	int number
	String pages
	
    static constraints = {
		journal nullable: false, blank: false
		volume nullable: false, blank: false, min: 1
		number nullable: false, blank: false, min: 1
		pages nullable: false, blank: false
    }

	//#if($Bibtex)
	String generateBib() {
		return "@article{"+ super.members.get(0) + super.publicationDate.getAt(Calendar.YEAR)
		+ ",author=\"" + BibtexAux.organizeAuthors(super.members) + "\",\n title=\"" + super.title + "\",\n journal=\""
		+ this.journal + "\",\n year=\"" + super.publicationDate.getAt(Calendar.YEAR) + "\",\n volume=\""
		+ this.volume + "\",\n month=\"" + super.publicationDate.getAt(Calendar.MONTH) 
		+ "\",\n number=\"" + this.number + "\",\n pages=\"" + this.pages + "\"}"
	}
	//#end
}
