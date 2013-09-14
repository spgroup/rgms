package rgms.publication

import rgms.authentication.User
import rgms.member.Member
import rgms.member.Membership
import rgms.member.ResearchGroup
import rgms.news.News

import static java.lang.Math.*
import static java.lang.Math.min as min


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
        String bibtex = ""
        int numero = (params.id).toInteger()
        for(publication in Publication.getAll())
        {
            for(member in publication.getMembers())
            {
                if(member.getId() == numero)
                {
                    bibtex = bibtex + publication.generateBib()  + "<br>"
                    break
                }

            }
        }

        render(bibtex)
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
                   bibtex = bibtex + teste(member.getId())
                }
            }
        }

        render(bibtex)
    }

    String teste(numero)
    {
        String bibtex = ""
        for(publication in Publication.getAll())
        {
            for(member in publication.getMembers())
            {
                if(member.getId() == numero)
                {
                    bibtex = bibtex + publication.generateBib()  + "<br>"
                    break
                }
            }
        }
        return bibtex
    }
}
