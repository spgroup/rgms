package rgms.publication

import org.apache.shiro.SecurityUtils
import org.springframework.web.multipart.MultipartHttpServletRequest
import rgms.XMLService
import rgms.authentication.User
import rgms.member.Member
import rgms.member.Orientation

/**
 * Created with IntelliJ IDEA.
 * User: Cynthia
 * Date: 03/09/13
 * Time: 21:55
 * To change this template use File | Settings | File Templates.
 */
class XMLController {

    def home(){}

    def upload(){
        String flashMessage = 'Publications imported!'
        String controller = "Publication"
        if (!XMLService.Import(savePublication, returnWithMessage, flashMessage, controller, request))
            return
    }

    private Closure savePublication = {
        Node xmlFile ->
            Member user = getCurrentUser()
            XMLService.createPublications(xmlFile, user)
    }

    def uploadXMLFerramenta(){
        String flashMessage = 'The non existent dissertations were successfully imported'

        if (!XMLService.Import(saveTools, returnWithMessage, flashMessage, "Ferramenta", request))
            return
    }

    private Closure saveTools = {
        Node xmlFile ->
            XMLService.createFerramentas(xmlFile)
    }

    def uploadXMLBookChapter(){
        String flashMessage = 'The non existent Book Chapters were successfully imported'

        if (XMLService.Import(saveBookChapters, returnWithMessage, flashMessage, "BookChapter", request))
            return
    }

    public Closure saveBookChapters = {
        Node xmlFile ->
            XMLService.createBooksChapters(xmlFile)
    }

    def uploadXMLDissertacao(){
        String flashMessage = 'The non existent dissertations were successfully imported'

        if (!XMLService.Import(saveDissertations, returnWithMessage, flashMessage, "Dissertacao", request))
            return
    }

    private Closure saveDissertations = {
        Node xmlFile ->
            XMLService.createDissertations(xmlFile)
    }

    def enviarConferenciaXML(){
        String flashMessage = message(code: 'default.importedMsg.message')

        if (!XMLService.Import(saveConferencias, returnWithMessage, flashMessage, "Conferencia", request))
            return
    }

    private Closure saveConferencias = {
        Node xmlFile ->
            XMLService.createConferencias(xmlFile)
    }

    def uploadOrientationXML(){
        String flashMessage = 'default.orientation.imported.message'

        if (!XMLService.Import(saveOrientations, returnWithMessage, flashMessage, "Orientation", request))
            return
    }

    private Closure saveOrientations = {
        Node xmlFile ->
            Member user = getCurrentUser()

            XMLService.createOrientations(xmlFile, user)
    }

    def uploadXMLPeriodico(){
        String flashMessage = 'default.article.imported.message'

        if (!XMLService.Import(saveJournals, returnWithMessage, flashMessage, "Periodico", request))
            return
    }

    private Closure saveJournals = {
        Node xmlFile ->
            XMLService.createJournals(xmlFile)
    }

    def uploadMemberXML() {
        String flashMessage = 'XML data extracted. Complete the remaining fields'

        if (!XMLService.Import(saveMember, returnWithMessage, flashMessage, "Member", request))
            return
    }

    private Closure saveMember = {
        Node xmlFile ->
            Member newMember = new Member(params)
            XMLService.createMember(xmlFile, newMember)
    }

    private Closure returnWithMessage = {
        String msg, String controller ->
            redirectToList(controller)
            flash.message = message(code: msg)
    }

    private def redirectToList(String controllerUsed){
        if(controllerUsed == "Publication")
            redirect (uri: '/')
        else
            redirect(controller: controllerUsed, action: "list", params: params)
    }

    private def getCurrentUser(){
        User user = User.findByUsername(SecurityUtils.getSubject()?.getPrincipal().toString())
        return user?.author
    }
}
