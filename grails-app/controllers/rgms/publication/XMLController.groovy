package rgms.publication

import org.apache.shiro.SecurityUtils
import rgms.authentication.User
import rgms.member.Member

/**
 * Created with IntelliJ IDEA.
 * User: Cynthia
 * Date: 03/09/13
 * Time: 21:55
 * To change this template use File | Settings | File Templates.
 */
class XMLController {

    def XMLService

    def home() {}

    def upload() {
        String flashMessage = 'Publications imported!'
        String controller = "Publication"
        if (!XMLService.Import(savePublication, returnWithMessage, flashMessage, controller, request))
            return
    }

    private savePublication = {
        Node xmlFile ->
            Member user = getCurrentUser()
            XMLService.createPublications(xmlFile, user)
    }

    def uploadXMLFerramenta() {
        String flashMessage = 'The non existent dissertations were successfully imported'

        if (!XMLService.Import(saveTools, returnWithMessage, flashMessage, "Ferramenta", request))
            return
    }

    private saveTools = {
        Node xmlFile ->
            Member user = getCurrentUser()
            XMLService.createTools(xmlFile, user.name)
    }

    def uploadXMLBook() {
        String flashMessage = message(code: 'book.importedMsg.message')

        XMLService.Import(saveBook, returnWithMessage, flashMessage, "Book", request)
        return
    }

    private saveBook = {
        Node xmlFile ->
            Member user = getCurrentUser()
            XMLService.createBooks(xmlFile, user.name)
    }

    def uploadXMLResearchLine() {
        XMLService.Import(saveResearchLine, returnWithMessage, 'default.researchline.import.flashmessage.success', "ResearchLine", request)
    }

    private saveResearchLine = {
        Node xmlFile ->
            XMLService.createResearchLines(xmlFile)
    }

    def uploadXMLResearchProject() {
        XMLService.Import(saveReseachProject, returnWithMessage, 'default.researchproject.import.flashmessage.success', "ResearchProject", request)
    }

    private saveReseachProject = {
        Node xmlFile ->
            XMLService.createResearchProjects(xmlFile)
    }

    def uploadXMLBookChapter() {
        String flashMessage = 'The non existent Book Chapters were successfully imported'

        if (XMLService.Import(saveBookChapters, returnWithMessage, flashMessage, "BookChapter", request))
            return
    }

    public saveBookChapters = {
        Node xmlFile ->
            Member user = getCurrentUser()
            XMLService.createBooksChapters(xmlFile, user.name)
    }

    def uploadXMLDissertacao() {
        String flashMessage = 'The non existent dissertations were successfully imported'

        if (!XMLService.Import(saveDissertations, returnWithMessage, flashMessage, "Dissertacao", request))
            return
    }

    private saveDissertations = {
        Node xmlFile ->
            Member user = getCurrentUser()
            XMLService.createMasterDissertation(xmlFile, user.name)
    }

    def enviarConferenciaXML() {
        String flashMessage = message(code: 'default.importedMsg.message')

        if (!XMLService.Import(saveConferencias, returnWithMessage, flashMessage, "Conferencia", request))
            return
    }

    private saveConferencias = {
        Node xmlFile ->
            Member user = getCurrentUser()
            XMLService.createConferencias(xmlFile, user.name)
    }

    def uploadOrientationXML() {
        String flashMessage = 'default.orientation.imported.message'

        if (!XMLService.Import(saveOrientations, returnWithMessage, flashMessage, "Orientation", request))
            return
    }

    private saveOrientations = {
        Node xmlFile ->
            Member user = getCurrentUser()

            XMLService.createOrientations(xmlFile, user)
    }

    def uploadXMLPeriodico() {
        String flashMessage = 'default.article.imported.message'

        if (!XMLService.Import(saveJournals, returnWithMessage, flashMessage, "Periodico", request))
            return
    }

    private saveJournals = {
        Node xmlFile ->
            Member user = getCurrentUser()
            XMLService.createJournals(xmlFile, user.name)
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

    private returnWithMessage = { String msg, String controller, publications ->
        if (controller == "Publication"){ //importacao via opcao XMLImport no menu da tela inicial do sistema
            request.message = message(code: msg)
            render(view:"home", model:[publications:publications])
        }
        else{ //importacao via outras telas (ainda precisa corrigir)
            flash.message = message(code: msg)
            redirect(controller: controller, action: "list", params: params)
        }
    }

    private def getCurrentUser() {
        User user = User.findByUsername(SecurityUtils.getSubject()?.getPrincipal().toString())
        return user?.author
    }
}
