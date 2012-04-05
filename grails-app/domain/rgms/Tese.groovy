package rgms

class Tese {
	String author
	String title
	String school
	int year
	
	int month
	
    static constraints = {
		author(nullable:false, blank:false)
		title(nullable:false, blank:false)
		school(nullable:false, blank:false)
		year(nullable:false, blank:false)
		month(maxSize:12)
    }
}
