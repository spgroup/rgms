package rgms.publication

abstract class TeseOrDissertacao extends Publication{
	
	String school
	String address
	
	static constraints = {
		school nullable: false, blank: false
		address nullable: false, blank: false
	}
}
