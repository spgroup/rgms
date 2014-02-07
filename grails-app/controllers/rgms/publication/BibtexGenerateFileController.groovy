package rgms.publication

import rgms.authentication.User
import rgms.member.Member
import rgms.member.Membership
import rgms.member.ResearchGroup

/**
 * Created with IntelliJ IDEA.
 * User: Diogo Falcão
 * Date: 02/09/13
 * Time: 03:24
 * To change this template use File | Settings | File Templates.
 */
class BibtexGenerateFileController {

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
    }

    def home = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        def userMemberList = []
        def members = Member.list(params)
        for(i in members){
            def user = User.findByAuthor(i)
            if(user)
                userMemberList.add([user: user, member:i])
            else
                userMemberList.add([member:i])
        }
        [researchGroupInstanceList: ResearchGroup.list(params), researchGroupInstanceTotal: ResearchGroup.count(), userMemberInstance: userMemberList, userMemberInstanceTotal: userMemberList.size() ]
    }

    def generateBibTex = {
        int numero = (params.id).toInteger()
        render(memberPublications(numero))
    }

    private String memberPublications(int numero) {
        String bibtex = ""
        for (publication in Publication.getAll()) {
            for (member in publication.getMembers()) {
                if (member.getId() == numero) {
                    bibtex = bibtex + publication.generateBib() + "<br>"
                    break
                }

            }
        }
        return bibtex
    }

    def generateBibTexGroup  = {
        String bibtex = ""
        int numero = (params.id).toInteger()
        for(group in ResearchGroup.getAll())
        {
            if(group.getId() == numero)
            {
                for(member in Membership.getAllMembers(group))
                {
                   bibtex = bibtex + memberPublications(member.getId())
                }
            }
        }

        render(bibtex)
    }

}
