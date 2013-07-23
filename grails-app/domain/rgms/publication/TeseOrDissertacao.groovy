package rgms.publication

import rgms.member.Member
import org.apache.shiro.SecurityUtils

abstract class TeseOrDissertacao extends Publication{
	
	String school
	String address
	
	static constraints = {
		school nullable: false, blank: false
		address nullable: false, blank: false
	}

	public String schoolSelected() {
//#if ($Autofill)
		def loggedUsername = SecurityUtils.subject?.principal;
		return school ? school : Member.findByUsername(loggedUsername).university;
//#else
		return school;
//#end
	}
}
