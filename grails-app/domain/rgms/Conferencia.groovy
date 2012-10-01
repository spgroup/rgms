package rgms

class Conferencia extends Publication {

	String booktitle
	String pages
	
    static constraints = {
		booktitle nullable: false, blank: false
		pages nullable: false, blank: false
    }
	
	//#if($Bibtex)
	public String setBib(){
		super.bibTex = "@inproceedings{"+ super.members.get(0) + super.publicationDate.get(Calendar.YEAR)
		super.bibTex = super.bibTex +",author=\"" + this.retListaAutor()
		super.bibTex = super.bibTex +"\",\n title=\"" + this.title
		super.bibTex = super.bibTex +"\",\n booktitle=\"" + this.booktitle
		super.bibTex = super.bibTex +"\",\n year=\"" + super.publicationDate.get(Calendar.YEAR)
		super.bibTex = super.bibTex +"\",\n pages=\"" + this.pages
		super.bibTex = super.bibTex +"\",\n month=\"" + super.publicationDate.get(Calendar.MONTH) + "\"}"
	}
	//#end
}
