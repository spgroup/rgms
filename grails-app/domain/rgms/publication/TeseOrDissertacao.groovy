package rgms.publication

import rgms.member.Member

abstract class TeseOrDissertacao extends Publication{
	
	String school
	String address
	
	static constraints = {
		school nullable: false, blank: false
		address nullable: false, blank: false
	}

	public Set schoolSelected(loggedUsername) {
//#if ($Autofill)
		return school ? school : [ Member.findByUsername(loggedUsername).university ];
//#else
		return school;
//#end
	}
}
