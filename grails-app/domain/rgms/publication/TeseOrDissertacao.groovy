package rgms.publication

import org.apache.shiro.SecurityUtils
import rgms.member.Member

abstract class TeseOrDissertacao extends Publication {

    String school
    String address
	String author

    static constraints = {
        school nullable: false, blank: false
        address nullable: false, blank: false
		author nullable: false, blank: false
    }

}
