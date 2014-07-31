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
        String flashMessage = 'default.xml.import.message'
        String controller = "Publication"
        if (!XMLService.Import(createPublication, returnWithMessage, flashMessage, controller, request))
            return
    }

    private createPublication = {
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
            XMLService.saveImportedPubsOfType(tools,"tools")
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
            XMLService.saveImportedPubsOfType(books, "books")
    }

    //#if($researchLine)
    def uploadXMLResearchLine() {
        XMLService.Import(saveResearchLine, returnWithMessage, 'default.researchline.import.flashmessage.success', "ResearchLine", request)
    }

    private saveResearchLine = {
        Node xmlFile ->
            Member user = getCurrentUser()
            def researchLines = XMLService.createResearchLines(xmlFile, user.name)*.obj
            XMLService.saveImportedPubsOfType(researchLines, "researchLines")
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
            XMLService.saveImportedPubsOfType(researchProjects, "researchProjects")
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
            XMLService.saveImportedPubsOfType(bookChapters, "bookChapters")
    }

    def uploadXMLDissertacao() {
        String flashMessage = 'The non existent dissertations were successfully imported'

        if (!XMLService.Import(saveDissertations, returnWithMessage, flashMessage, "Dissertacao", request))
            return
    }

    private saveDissertations = {
        Node xmlFile ->
            Member user = getCurrentUser()
            def dissertation = XMLService.createDissertation(xmlFile, user.name).obj
            XMLService.saveImportedPubsOfType([dissertation], "masterDissertation")
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
            XMLService.saveImportedPubsOfType(conferences, "conferences")
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
            XMLService.saveImportedPubsOfType(orientations, "orientations")
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
            XMLService.saveImportedPubsOfType(journals, "journals")
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
            XMLService.saveMember(xmlFile, newMember)
    }

    private returnWithMessage = { String msg, String controller, publications ->
        //importacao via opcao XMLImport no menu da tela inicial do sistema
        if (controller == "Publication"){
            if(publications.isEmpty()) request.message = message(code: "xml.import.empty.message")
            else request.message = message(code: msg)
            render(view:"home", model:[publications:publications])
        }
        //importacao via outras telas (ainda precisa corrigir)
        else{
            flash.message = message(code: msg)
            redirect(controller: controller, action: "list")
        }
    }

    private def getCurrentUser() {
        User user = User.findByUsername(SecurityUtils.getSubject()?.getPrincipal().toString())
        return user?.author
    }

    def save() {
        def msg = 'default.xml.saveerror.message'
        Member user = getCurrentUser()
        msg = XMLService.saveImportedPublications(params, user)
        flash.message = message(code: msg)
        redirect(uri: '/')
    }

}
