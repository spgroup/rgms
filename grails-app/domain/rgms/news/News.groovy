package rgms.news

class News {
	
	String description
	Date date

    static constraints = {
		description blank:false 
		date blank:false		
    }
	
	
}
