package rgms

class Conferencia {

	String author
	String title
	String conference
	int year

	int pages
	int month

	static constraints = {
		author(nullable:false, blank:false)
		title(nullable:false, blank:false)
		conference(nullable:false, blank:false)
		year(nullable:false, blank:false)
		pages()
		month(maxSize:12)
	}
}
