package rgms

class Tese extends Publication {
	
	String school
	String address
	
    static constraints = {
		school nullable: false, blank: false
		address nullable: false, blank: false
    }
	
	//#if($Bibtex)
//	public String setBib(){
//		super.bibTex = "@phdthesis{" + super.members.get(0) + super.publicationDate.get(Calendar.YEAR)
//		super.bibTex = super.bibTex +",author=\"" + this.retListaAutor()
//		super.bibTex = super.bibTex +"\",\n title=\"" + this.title
//		super.bibTex = super.bibTex +"\",\n school=\"" + this.school
//		super.bibTex = super.bibTex +"\",\n year=\"" + super.publicationDate.get(Calendar.YEAR)
//		super.bibTex = super.bibTex +"\",\n month=\""+ super.publicationDate.get(Calendar.MONTH) +"\"}"
//	}
	//#end
}
