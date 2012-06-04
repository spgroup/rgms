package rgms

class Tese {
	String author
	String title
	String school
	int year
	String bibTex
	int month
	String arquivo
	
    static constraints = {
		author(nullable:false, blank:false)
		title(nullable:false, blank:false)
		school(nullable:false, blank:false)
		year(nullable:false, blank:false)
		month(maxSize:12)
		arquivo(blank:true, maxSize:100000000)
    }
}
