package rgms.publication

import org.apache.shiro.SecurityUtils
import rgms.authentication.User
import rgms.member.Member
import rgms.member.Orientation
import rgms.researchProject.ResearchProject

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
        String flashMessage = 'default.xml.import.message'
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
            def tools = XMLService.createTools(xmlFile, user.name)*.obj
            XMLService.saveImportedTools(tools)
    }

    def uploadXMLBook() {
        String flashMessage = message(code: 'book.importedMsg.message')

        XMLService.Import(saveBook, returnWithMessage, flashMessage, "Book", request)
        return
    }

    private saveBook = {
        Node xmlFile ->
            Member user = getCurrentUser()
            def books = XMLService.createBooks(xmlFile, user.name)*.obj
            XMLService.saveImportedBooks(books)
    }

    //#if($researchLine)
    def uploadXMLResearchLine() {
        XMLService.Import(saveResearchLine, returnWithMessage, 'default.researchline.import.flashmessage.success', "ResearchLine", request)
    }

    private saveResearchLine = {
        Node xmlFile ->
            Member user = getCurrentUser()
            def researchLines = XMLService.createResearchLines(xmlFile, user.name)*.obj
            XMLService.saveImportedResearchLines(researchLines)
    }
    //#end

    //#if($researchProject)
    def uploadXMLResearchProject() {
        XMLService.Import(saveReseachProject, returnWithMessage, 'default.researchproject.import.flashmessage.success', "ResearchProject", request)
    }

    private saveReseachProject = {
        Node xmlFile ->
            Member user = getCurrentUser()
            def researchProjects = XMLService.createResearchProjects(xmlFile, user.name)*.obj
            XMLService.saveImportedResearchProjects(researchProjects)
    }
    //#end

    def uploadXMLBookChapter() {
        String flashMessage = 'The non existent Book Chapters were successfully imported'

        if (XMLService.Import(saveBookChapters, returnWithMessage, flashMessage, "BookChapter", request))
            return
    }

    public saveBookChapters = {
        Node xmlFile ->
            Member user = getCurrentUser()
            def bookChapters = XMLService.createBooksChapters(xmlFile, user.name)*.obj
            XMLService.saveImportedBookChapters(bookChapters)
    }

    def uploadXMLDissertacao() {
        String flashMessage = 'The non existent dissertations were successfully imported'

        if (!XMLService.Import(saveDissertations, returnWithMessage, flashMessage, "Dissertacao", request))
            return
    }

    private saveDissertations = {
        Node xmlFile ->
            Member user = getCurrentUser()
            def dissertation = XMLService.createMasterDissertation(xmlFile, user.name).obj
            XMLService.saveImportedDissertation(dissertation)
    }

    def enviarConferenciaXML() {
        String flashMessage = message(code: 'default.importedMsg.message')

        if (!XMLService.Import(saveConferencias, returnWithMessage, flashMessage, "Conferencia", request))
            return
    }

    private saveConferencias = {
        Node xmlFile ->
            Member user = getCurrentUser()
            def conferences = XMLService.createConferencias(xmlFile, user.name)*.obj
            XMLService.saveImportedConferences(conferences)
    }

    //#if($Orientation)
    def uploadOrientationXML() {
        String flashMessage = 'default.orientation.imported.message'

        if (!XMLService.Import(saveOrientations, returnWithMessage, flashMessage, "Orientation", request))
            return
    }

    private saveOrientations = {
        Node xmlFile ->
            Member user = getCurrentUser()
            def orientations = XMLService.createOrientations(xmlFile, user)*.obj
            XMLService.saveImportedOrientations(orientations)
    }
    //#end

    //#if($Article)
    def uploadXMLPeriodico() {
        String flashMessage = 'default.article.imported.message'

        if (!XMLService.Import(saveJournals, returnWithMessage, flashMessage, "Periodico", request))
            return
    }

    private saveJournals = {
        Node xmlFile ->
            Member user = getCurrentUser()
            def journals = XMLService.createJournals(xmlFile, user.name)*.obj
            XMLService.saveImportedJournals(journals)
    }
    //#end

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
        //importacao via opcao XMLImport no menu da tela inicial do sistema
        if (controller == "Publication"){
            request.message = message(code: msg)
            render(view:"home", model:[publications:publications])
        }
        //importacao via outras telas (ainda precisa corrigir)
        else{
            flash.message = message(code: msg)
            redirect(controller: controller, action: "list", params: params)
        }
    }

    private def getCurrentUser() {
        User user = User.findByUsername(SecurityUtils.getSubject()?.getPrincipal().toString())
        return user?.author
    }

    def save() {
        Member user = getCurrentUser()
        def msg = XMLService.saveImportedPublications(params, user)
        flash.message = message(code: msg)
        redirect(uri: '/')
    }

}
