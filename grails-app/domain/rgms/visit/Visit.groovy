package rgms.visit

import rgms.member.ResearchGroup

class Visit {

  	Date    dataInicio
	Date    dataFim
	Visitor visitor
	ResearchGroup researchGroup;
	
	static constraints = {
		dataInicio(nullable:false,blank:false)
	}
}
