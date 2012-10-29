package rgms.publication


class Ferramenta extends Publication {

	String website
	String description

	static constraints = {
		website nullable: false, blank: false
		description nullable: false, blank: false
	}

	//#if($Bibtex)
	String generateBib() {
		return "@misc{"+ super.members.get(0) + super.publicationDate.getAt(Calendar.YEAR)
		+ ",author=\"" + BibtexAux.organizeAuthors(super.members) + "\",\n title=\"" + super.title + "\",\n website=\""
		+ this.website + "\",\n year=\"" + super.publicationDate.getAt(Calendar.YEAR) + "\",\n description=\""
		+ this.description + "\",\n month=\"" + super.publicationDate.getAt(Calendar.MONTH) + "\"}"
	}
	//#end
}
