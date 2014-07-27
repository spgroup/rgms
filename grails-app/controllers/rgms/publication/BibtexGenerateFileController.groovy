package rgms.publication

import rgms.authentication.User
import rgms.member.Member
import rgms.member.MemberController
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

    def show = {
    }

    def home = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        def userMemberList = MemberController.GetUserMemberList(params)
        [researchGroupInstanceList: ResearchGroup.list(params), researchGroupInstanceTotal: ResearchGroup.count(), userMemberInstance: userMemberList, userMemberInstanceTotal: userMemberList.size() ]
    }

    def exportToResearchGate = {
        if(PublicationController.getLoggedMember().researchGate_username != null)
            redirect(url: "https://www.researchgate.net/home.Home.html")
        else
            redirect(url: "https://www.researchgate.net")
    }

    def generateBibTex = {
        int numero = (params.id).toInteger()
        render(view: "show", model: [bibtexContent: memberPublications(numero)])
    }

    def saveBibtexInFile = {
        String bibtex = params.bibtexContent
        def tempfile = new File("My bibtex.bibtex")
        tempfile.write(bibtex)
        response.setContentType("application/octet-stream")
        response.setHeader("Content-disposition", "attachment;filename=${tempfile.getName()}")
        tempfile.withInputStream { response.outputStream << it }
        tempfile.delete()
    }

    public String memberPublications(int numero) {
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
