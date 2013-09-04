package rgms.publication

import org.apache.shiro.SecurityUtils
import rgms.member.Member

abstract class TeseOrDissertacao extends Publication {

    String school
    String address

    static constraints = {
        school nullable: false, blank: false
        address nullable: false, blank: false
    }

}
