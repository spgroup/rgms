package rgms

class Conferencia {

	String author
	String title
	String conference
	int year
	String bibTex
	int pageInitial
	int pageFinal
	int month
	String arquivo

	static hasMany = [members:Member]
	
	static constraints = {
		author(nullable:false, blank:false)
		title(nullable:false, blank:false)
		conference(nullable:false, blank:false)
		year(nullable:false, blank:false)
		month(maxSize:12)
		arquivo(blank:true, maxSize:100000000)
	}
}
