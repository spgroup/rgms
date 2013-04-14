package rgms.news

import rgms.member.ResearchGroup;

class News {
	
	String description
	Date date	
	ResearchGroup researchGroup

    static constraints = {
		description(blank:false) 
		date(blank:false)		
    }
	
	
}
