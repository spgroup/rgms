package rgms

class Ferramenta {

	String author
	String title
	int year
	String link
	/**Velocity**/
	#if($bibtex)
		String bibTex
	#end
	/**Velocity**/
	String descricao
	String publicacaoAssociada
	
	static hasMany = [members : Member]

    static constraints = {
		author(nullable:false, blank:false)
		title(nullable:false, blank:false)
		year(nullable:false, blank:false)
		link(nullable:false, blank:false)
    }
}
