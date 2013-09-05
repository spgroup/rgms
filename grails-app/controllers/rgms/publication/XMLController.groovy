package rgms.publication

import org.springframework.web.multipart.MultipartHttpServletRequest
import rgms.XMLService
import rgms.member.Member
import rgms.member.Orientation

import javax.servlet.http.HttpServletRequest

/**
 * Created with IntelliJ IDEA.
 * User: Cynthia
 * Date: 03/09/13
 * Time: 21:55
 * To change this template use File | Settings | File Templates.
 */
class XMLController {


    private Closure returnWithMessage = {
        String msg, String controller ->
            redirectToList(controller)
            flash.message = message(code: msg)
    }

    private def redirectToList(String controllerUsed){
        redirect(controller: controllerUsed, action: "list", params: params)
    }

    def uploadXMLFerramenta()
    {
        String flashMessage = 'The non existent dissertations were successfully imported'
        String controller = "Ferramenta"
        if (!XMLService.Import(saveTools, returnWithMessage, flashMessage, controller, request))
            return
    }

    private Closure saveTools = {
        Node xmlFile ->
            XMLService.createFerramentas(xmlFile)
    }

    def uploadXMLBookChapter()
    {
        String flashMessage = 'The non existent Book Chapters were successfully imported'

        if (XMLService.Import(saveBookChapters, returnWithMessage, flashMessage, "BookChapter", request))
            return
    }

    private Closure saveBookChapters = {
        Node xmlFile ->
            XMLService.createBooksChapters(xmlFile)
    }

    def uploadXMLDissertacao() {
        String flashMessage = 'The non existent dissertations were successfully imported'

        XMLService serv = new XMLService()
        Node xmlFile = serv.parseReceivedFile(request as MultipartHttpServletRequest)
        serv.Import(saveDissertations, returnWithMessage, flashMessage, "Dissertacao", xmlFile as HttpServletRequest)
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

    def uploadOrientationXML() {
        String flashMessage = 'default.orientation.imported.message'

        if (!XMLService.Import(saveOrientations, returnWithMessage, flashMessage, "Orientation", request))
            return
    }

    private Closure saveOrientations = {
        Node xmlFile ->
            Member user = Member.findByUsername(session.getAttribute("username").toString())
            XMLService.createOrientations(xmlFile, user)
    }

    def uploadXMLPeriodico() {
        String flashMessage = 'default.article.imported.message'

        if (!XMLService.Import(saveJournals, returnWithMessage, flashMessage, "Periodico", request))
            return
    }

    private Closure saveJournals = {
        Node xmlFile ->
            XMLService.createJournals(xmlFile)
    }
}
