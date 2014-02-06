package steps

import org.apache.shiro.subject.Subject
import org.apache.shiro.util.ThreadContext
import org.codehaus.groovy.tools.GroovyClass
import rgms.authentication.User
import rgms.member.*
import rgms.news.News
import rgms.news.NewsController
import rgms.publication.*
import org.apache.shiro.SecurityUtils
import steps.ThesisOrDissertationTestDataAndOperations

class TestDataAndOperations {

    static members = [
            [name: "Rodolfo Ferraz", username: "usernametest", email: "rodolfofake@gmail.com",
                    status: "Graduate Student", university: "UFPE", enabled: true
            ],
            [name: "Rebeca Souza", username: "rebecasouza", email: "rsa2fake@cin.ufpe.br",
                    status: "Graduate Student", university: "UFPE", enabled: true
            ],
            [name: "Invalid User", username: "userwithinvalidphone", email: "uwif@cin.ufpe.br",
                    status: "Graduate Student", university: "UFPE", enabled: true, phone: "invalidphone"
            ]]

    static researchgroups = [
            [name: "SPG",
                    description: "SW Productivity Research Group",
                    childOf: null]
            ,
            [name: "taes",
                    description: "grupo de estudos",
                    childOf: null]]

    static memberships = [
            [member: (new Member(members[0])),
                    researchGroup: (new ResearchGroup(researchgroups[0])),
                    dateJoined: (new Date(2012, 03, 01)),
                    dateLeft: (new Date(2012, 06, 01))]
            ,
            [member: (new Member(members[1])),
                    researchGroup: (new ResearchGroup(researchgroups[0])),
                    dateJoined: (new Date(2012, 03, 01)),
                    dateLeft: (new Date(2012, 06, 01))]]


    static public def openBibTexFile(String path){
        BibtexFileController bibtexFileController = new BibtexFileController()
        BibtexFile bibtexFile = bibtexFileController.transform(new File(path))
    }

    static public def findMembershipByResearchGroupName(String groupname) {
        memberships.find { membership ->
            membership.researchGroup.name == groupname
        }
    }

    static public def findResearchGroupByGroupName(String groupname) {
        researchgroups.find { group ->
            group.name == groupname
        }
    }



    static public boolean memberCompatibleTo(member, username) {
        def testmember = findByUsername(username)
        def compatible = false
        if (testmember == null && member == null) {
            compatible = true
        } else if (testmember != null && member != null) {
            compatible = true
            testmember.each { key, data ->
                compatible = compatible && (member."$key" == data)
            }
        }
        return compatible
    }

    static public void uploadFerramenta(filepath) {
        def cont = new FerramentaController()
        def xml = new File((String) filepath);
        def records = new XmlParser()
        cont.saveTools(records.parse(xml));
        cont.response.reset()
    }

    static public void createArticle(String title, filename) {
        def cont = new PeriodicoController()
        def date = new Date()
        cont.params << TestDataAndOperations.findArticleByTitle(title) << [file: filename]
        cont.request.setContent(new byte[1000]) // Could also vary the request content.
        cont.create()
        cont.save()
        cont.response.reset()
    }

    static public void createFerramenta(String title, filename) {
        def cont = new FerramentaController()
        def date = new Date()
        cont.params << TestDataAndOperations.findFerramentaByTitle(title) << [file: filename]
        cont.request.setContent(new byte[1000]) // Could also vary the request content.
        cont.create()
        cont.save()
        cont.response.reset()
    }

    static public void createResearchGroup(String groupname) {
        def cont = new ResearchGroupController()
        cont.params << TestDataAndOperations.findResearchGroupByGroupName(groupname)
        cont.request.setContent(new byte[1000]) // Could also vary the request content.
        cont.create()
        cont.save()
        cont.response.reset()
    }


    static public void createMembership(String username, String rgroup, String date1, String date2) {
        //TODO Deveria pegar os dados dos parametros, mas
        //		esta dando problema na criacao. Entao para
        //		simplificar o entendimento do problema,
        //		os valores est?o fixos. O problema n?o
        //		est? nos par?metros passados.

        def cont = new MembershipController()

        cont.params << [member: (new Member(members[0])),
                researchGroup: (new ResearchGroup(name: "taes", description: "grupo de estudos", childOf: null)),
                dateJoined: (new Date(2012, 03, 01)), dateLeft: (new Date(2012, 06, 01))]
        cont.request.setContent(new byte[1000]) // Could also vary the request content.
        cont.create()
        cont.save()
        cont.response.reset()
    }

    static public void deleteMembership(String username, String rgroup, String date1, String date2) {
        java.text.DateFormat df = new java.text.SimpleDateFormat("dd-MM-yyyy");
        def cont = new MembershipController()
        def identificador = Membership.findByMemberAndResearchGroupAndDateJoinedAndDateLeft(User.findByUsername(username)?.author,
                ResearchGroup.findByName(rgroup), df.parse(date1), df.parse(date2)).id
        cont.params << [id: identificador]
        cont.request.setContent(new byte[1000]) // Could also vary the request content.
        cont.delete()
        //cont.save()
        cont.response.reset()
    }


    static public boolean containsMember(username, members) {
        def testmember = User.findByUsername(username)?.author
        def cont = new MemberController()
        def result = cont.list().memberInstanceList
        return result.contains(testmember)
    }

    static public ResearchGroup createAndGetResearchGroupByName(String name) {
        return createAndGetResearchGroupByNameWithTwitter(name,null)
    }

    static public ResearchGroup createAndGetResearchGroupByNameWithTwitter(String name, String twitter) {
        def researchGroupController = new ResearchGroupController()
        researchGroupController.params << findResearchGroupByGroupName(name)
        if(twitter != null)
            researchGroupController.params << [twitter: twitter]
        researchGroupController.create()
        researchGroupController.save()
        researchGroupController.response.reset()
        return ResearchGroup.findByName(name)
    }

    static public void requestNewsFromTwitter(ResearchGroup group) {
        def cont = new ResearchGroupController()
        cont.params << [id: group.id]
        cont.updateNewsFromTwitter()
        cont.response.reset()
    }

    //não funciona
    static public void editResearchGroupTwitter(researchGroup, String newTwitter) {
        def researchGroupController = new ResearchGroupController()
        researchGroupController.params << [twitter: newTwitter] << [id: researchGroup.getId()]
        researchGroupController.edit()
        researchGroupController.save()
        researchGroupController.response.reset()
    }

    //mapmf_tasj

 static public ResearchGroup editResearchGroupTwitterAcount(researchGroup, String newTwitter){
        def researchGroupController = new ResearchGroupController()
        researchGroupController.params << [twitter: newTwitter] << [id: researchGroup.getId()]
        researchGroupController.update()
        researchGroupController.response.reset()
        return researchGroup
    }

    static public String getTestFilesPath(String filename){
        new File(".").getCanonicalPath() + File.separator + "test" + File.separator + "functional" + File.separator + "steps" + File.separator + filename
    }
    //mapmf_tasj

    //article

    static public def path(){
        return new File(".").getCanonicalPath() + File.separator + "test" + File.separator + "files" + File.separator
      }

    static public void uploadPublications(filename) {
        def cont = new XMLController()
        def xml = new File(filename);
        def records = new XmlParser()
        cont.savePublication(records.parse(xml));
        cont.response.reset()
    }

    static public void loginController(cla){
        def registry = GroovySystem.metaClassRegistry
        cla.oldMetaClass = registry.getMetaClass(SecurityUtils)
        registry.removeMetaClass(SecurityUtils)
        def subject = [getPrincipal: { "admin" },
                isAuthenticated: { true }
        ]as Subject
        ThreadContext.put(ThreadContext.SECURITY_MANAGER_KEY,
                [getSubject: { subject } as SecurityManager])
        SecurityUtils.metaClass.static.getSubject = { subject }
    }

    static public void logoutController(cla){
        GroovySystem.metaClassRegistry.setMetaClass(SecurityUtils, cla.oldMetaClass)
    }
}
