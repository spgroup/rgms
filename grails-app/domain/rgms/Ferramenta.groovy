package rgms

import groovy.lang.GroovyObjectSupport;

class Ferramenta extends Publication {

	String website
	String description
	
    static constraints = {
		website nullable: false, blank: false
		description nullable: false, blank: false
    }
	
//	#if($Bibtex)
//	public String setBib(){
//		this.bibTex = "@misc{" + super.members.get(0) + super.publicationDate.get(Calendar.YEAR)
//		this.bibTex = this.bibTex +",author=\""+this.retListaAutor()
//		this.bibTex = this.bibTex +"\",\n title=\"" + this.title
//		this.bibTex = this.bibTex +"\",\n year=\"" + super.publicationDate.get(Calendar.YEAR)
//		this.bibTex = this.bibTex +"\",\n note=\"" + this.description + "\"}"
//	}
//	#end
}
