package rgms.publication



class TechnicalReport extends Publication {

	String institution
	
    static constraints = {
		institution nullable: false, blank: false
    }
	
	//#if($Bibtex)
	String generateBib() {
		return "@techreport{"+ super.members.get(0) + super.publicationDate.getAt(Calendar.YEAR)
		+ ",author=\"" + BibtexAux.organizeAuthors(super.members) + "\",\n title=\"" + super.title + "\",\n institution=\""
		+ this.institution + "\",\n year=\"" + super.publicationDate.getAt(Calendar.YEAR) + "\"}"
	}
	//#end
}