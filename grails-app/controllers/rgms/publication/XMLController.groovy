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

        if (!XMLService.Import(saveTools, returnWithMessage, flashMessage, "Ferramenta", request))
            return
    }

    private Closure saveTools = {
        Node xmlFile ->
            Node producaoTecnica = (Node)xmlFile.children()[2]

            for (Node currentNode : producaoTecnica.children()){
                if (currentNode.name().equals("SOFTWARE")){
                    XMLService.saveNewTool(currentNode)
                }
            }
    }

    def uploadXMLBookChapter()
    {
        String flashMessage = 'The non existent Book Chapters were successfully imported'

        if (XMLService.Import(saveBookChapters, returnWithMessage, flashMessage, "BookChapter", request))
            return
    }

    private Closure saveBookChapters = {
        Node xmlFile ->
            Node bookChapters = (Node)((Node)((Node)xmlFile.children()[1]).children()[2]).children()[1]
            List<Object> bookChaptersChildren = bookChapters.children()
            for (int i = 0; i < bookChaptersChildren.size(); ++i){
                List<Object> bookChapter = ((Node)bookChaptersChildren[i]).children()
                Node dadosBasicos = (Node) bookChapter[0]
                Node detalhamentoCapitulo = (Node) bookChapter[1]
                XMLService.createNewBookChapter(dadosBasicos,detalhamentoCapitulo, i)
            }
    }

    def uploadXMLDissertacao() {
        String flashMessage = 'The non existent dissertations were successfully imported'

        XMLService serv = new XMLService()
        Node xmlFile = serv.parseReceivedFile(request as MultipartHttpServletRequest)
        serv.Import(saveDissertations, returnWithMessage, flashMessage, "Dissertacao", xmlFile as HttpServletRequest)
    }

    private Closure saveDissertations = {
        Node xmlFile ->
            Node dadosGerais = (Node) xmlFile.children()[0]
            Node mestrado = (Node) ((Node) dadosGerais.children()[3]).children()[1]
            Node doutorado = (Node) ((Node) dadosGerais.children()[3]).children()[2]

            XMLService.createDissertation(mestrado)
            XMLService.createDissertation(doutorado)
    }

    def enviarConferenciaXML(){
        String flashMessage = message(code: 'default.importedMsg.message')

        if (!XMLService.Import(saveConferencias, returnWithMessage, flashMessage, "Conferencia", request))
            return
    }

    private Closure saveConferencias = {
        Node xmlFile ->
            Node trabalhosEmEventos = (Node) ((Node)xmlFile.children()[1]).children()[0]

            for (Node currentNode : trabalhosEmEventos.children()){
                List<Object> nodeConferencia = currentNode.children()
                XMLService.saveNewConferencia (nodeConferencia);
            }
    }

    def uploadOrientationXML() {
        String flashMessage = 'default.orientation.imported.message'

        if (!XMLService.Import(saveOrientations, returnWithMessage, flashMessage, "Orientation", request))
            return
    }

    private Closure saveOrientations = {
        Node xmlFile ->

            List<Object> completedOrientations = XMLService.findCompletedOrientations(xmlFile)
            Member user = Member.findByUsername(session.getAttribute("username").toString())

            if (!XMLService.isNullOrEmpty(completedOrientations))
                for (int i = 0; i < completedOrientations.size(); i++)
                    XMLService.saveNewOrientation(completedOrientations, i, user)
    }

    def uploadXMLPeriodico() {
        String flashMessage = 'default.article.imported.message'

        if (!XMLService.Import(saveJournals, returnWithMessage, flashMessage, "Periodico", request))
            return
    }

    private Closure saveJournals = {
        Node xmlFile ->

            Node artigosPublicados = (Node) ((Node) xmlFile.children()[1]).children()[1]
            List<Object> artigosPublicadosChildren = artigosPublicados.children()

            for (int i = 0; i < artigosPublicadosChildren.size(); ++i)
                XMLService.saveNewJournal(artigosPublicadosChildren, i)
    }

}
