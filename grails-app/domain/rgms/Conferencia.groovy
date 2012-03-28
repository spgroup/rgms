package rgms

class Conferencia {

	String author
	String title
	String conference
	int year

	int pageInitial
	int pageFinal
	int month

	static constraints = {
		author(nullable:false, blank:false)
		title(nullable:false, blank:false)
		conference(nullable:false, blank:false)
		year(nullable:false, blank:false)
		month(maxSize:12)
	}
}
