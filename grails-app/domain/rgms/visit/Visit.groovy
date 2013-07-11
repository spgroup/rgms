package rgms.visit

import rgms.member.ResearchGroup

class Visit {

  	Date    dataInicio
	Date    dataFim
	Visitor visitor
	
	//#if( $reserchgroupobrigatorio )	
		ResearchGroup researchGroup;
	//#end
	
	//#if( $descricaovisita )	
		String descricao	
	//#end
	
	static constraints = {
		dataInicio(nullable:false,blank:false)
	}
}
