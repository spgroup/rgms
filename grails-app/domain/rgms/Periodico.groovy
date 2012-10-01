package rgms

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
//	public String setBib(){
//		super.bibTex = "@article{" + super.members.get(0) + super.publicationDate.get(Calendar.YEAR)
//		super.bibTex = super.bibTex +",author=\"" + this.retListaAutor()
//		super.bibTex = super.bibTex +"\",\n title=\"" + this.title
//		super.bibTex = super.bibTex +"\",\n journal=\"" + this.journal
//		super.bibTex = super.bibTex +"\",\n year=\"" + super.publicationDate.get(Calendar.YEAR)
//		super.bibTex = super.bibTex +"\",\n volume=\"" + this.volume
//		super.bibTex = super.bibTex +"\",\n number=\"" + this.number
//		super.bibTex = super.bibTex +"\",\n pages=\""+ this.pages +"\"}"
//	}
	//#end
}
