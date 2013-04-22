package rgms.news

import java.util.List;

import rgms.member.ResearchGroup;

class News {
	
	String description
	Date date	
	
	static belongsTo = [researchGroup:ResearchGroup]

    static constraints = {
		description(blank:false) 
		date(blank:false)	
		researchGroup nullable:false 	
    }
	
	
	static List getCurrentNews(researchGroup){
		def list = News.findAllByResearchGroup(researchGroup)
		return list
	}
	
}
