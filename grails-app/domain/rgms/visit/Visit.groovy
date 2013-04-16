package rgms.visit

class Visit {

  	Date    dataInicio
	Date    dataFim
	Visitor visitor
	
	static constraints = {
		dataInicio(nullable:false,blank:false)
	}
}
