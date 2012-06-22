package rgms

import groovy.lang.GroovyObjectSupport;

class Ferramenta extends Publicacao{

	String link
	String descricao
	String publicacaoAssociada
	
	static hasMany = [members : Member]

    static constraints = {
		author(nullable:false, blank:false)
		title(nullable:false, blank:false)
		year(nullable:false, blank:false)
		link(nullable:false, blank:false)
    }
	
	#if($bibtex)
	public String setBib(){
		this.bibTex = "@misc{"+this.retPrimeiroAutor()+this.year
		this.bibTex = this.bibTex +",author=\""+this.retListaAutor()
		this.bibTex = this.bibTex +"\",\n title=\""+this.title
		this.bibTex = this.bibTex +"\",\n year=\""+this.year
		this.bibTex = this.bibTex +"\",\n note=\""+this.descricao+"\"}"
	}
	#end
}
