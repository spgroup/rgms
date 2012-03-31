package rgms

class Ferramenta {

	String author
	String title
	int year
	String link

	String descricao
	String publicacaoAssociada

    static constraints = {
		author(nullable:false, blank:false)
		title(nullable:false, blank:false)
		year(nullable:false, blank:false)
		link(nullable:false, blank:false)
    }
}
