package rgms.publication


class Dissertacao extends Publication {
	
	String school
	String address
	
	static constraints = {
		school nullable: false, blank: false
		address nullable: false, blank: false
	}
	
	//#if($Bibtex)
	String generateBib() {
		return "@masterthesis{"+ super.members.get(0) + super.publicationDate.getAt(Calendar.YEAR) 
			+ ",author=\"" + BibtexAux.organizeAuthors(super.members) + "\",\n title=\"" + super.title + "\",\n school=\"" 
			+ this.school + "\",\n year=\"" + super.publicationDate.getAt(Calendar.YEAR) + "\",\n address=\"" 
			+ this.address + "\",\n month=\"" + super.publicationDate.getAt(Calendar.MONTH) + "\"}"
	}
	//#end
}