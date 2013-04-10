package rgms.publication


class Tese extends TeseOrDissertacao {

	//#if($Bibtex)
	String generateBib() {
		return "@phdthesis{"+ super.members.get(1) + super.publicationDate.getAt(Calendar.YEAR) + 
		",author=\"" + BibtexAux.organizeAuthors(super.members) + "\",\n title=\"" + super.title + "\",\n school=\"" + 
		this.school + "\",\n year=\"" + super.publicationDate.getAt(Calendar.YEAR) + "\",\n address=\"" + 
		this.address + "\"}"
	}
	//#end
}
