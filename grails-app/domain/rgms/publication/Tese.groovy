package rgms.publication


class Tese extends Publication {
	
	String school
	String address
	
    static constraints = {
		school nullable: false, blank: false
		address nullable: false, blank: false
    }
	
	//#if($Bibtex)
	String generateBib() {
		return "@phdthesis{"+ super.members.get(0) + super.publicationDate.getAt(Calendar.YEAR)
		+ ",author=\"" + BibtexAux.organizeAuthors(super.members) + "\",\n title=\"" + super.title + "\",\n school=\""
		+ this.school + "\",\n year=\"" + super.publicationDate.getAt(Calendar.YEAR) + "\",\n address=\""
		+ this.address + "\"}"
	}
	//#end
}
