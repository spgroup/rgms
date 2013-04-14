package rgms.publication

import org.apache.shiro.SecurityUtils
import rgms.member.Member

/**
 * Created with IntelliJ IDEA.
 * User: Flavio
 * Date: 14/04/13
 * Time: 01:03
 * To change this template use File | Settings | File Templates.
 */
class PublicationControllerUtils {
    static Member addAuthor(Publication publication){
        Member user = Member.findByUsername(SecurityUtils.subject.principal)
        if(!publication.members){
            publication.members = new LinkedHashSet<Member>()
        }
        publication.members.add(user);
        return user
    }
}
