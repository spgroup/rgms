package rgms.publication

import rgms.XMLService
import org.apache.shiro.SecurityUtils
import org.springframework.dao.DataIntegrityViolationException
import rgms.member.Member
import org.apache.shiro.SecurityUtils
import rgms.member.Member;
//import Tese;

class TeseController {
    def grailsApplication
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [teseInstanceList: Tese.list(params), teseInstanceTotal: Tese.count()]
    }

    def create() {
        Tese teseInstance = new Tese(params)
        //#if($publicationContext)
        def publcContextOn = grailsApplication.getConfig().getProperty("publicationContext")
        if(publcContextOn){
            if(SecurityUtils.subject?.principal != null){
                def user = PublicationController.addAuthor(teseInstance)
                if(!user.university.isEmpty()){
                    teseInstance.school = user.university
                }
            }
        }
        //#end
        [teseInstance: teseInstance]
    }

    def save() {
        def teseInstance = new Tese(params)
        PublicationController pb = new PublicationController()
        
        if (Tese.findByTitle(params.title)) {
            handleSavingError(teseInstance, 'tese.duplicatetitle.failure')
            return
        }
        
        if (!pb.upload(teseInstance)) {
            handleSavingError(teseInstance, 'tese.filesaving.failure')
            return
        }

        if (!teseInstance.save(flush: true)) {
            handleSavingError(teseInstance, 'tese.saving.failure')
            return
        }
       
        //#if($facebook)
        //def user = Member.findByUsername(SecurityUtils.subject?.principal)
        //pb.sendPostFacebook(user, teseInstance.toString())
        //#end

        flash.message = message(code: 'default.created.message', args: [message(code: 'tese.label', default: 'Tese'), teseInstance.id])
        redirect(controller: "tese", action: "show", id: teseInstance.id)
    }
    
    def handleSavingError(Tese teseInstance, String message) {
        teseInstance.discardMembers()
        flash.message = message
        render(view: "create", model: [teseInstance: teseInstance])
    }

    def show() {
        def teseInstance = Tese.get(params.id)
        
        if (!teseInstance) {
            flash.message = messageGenerator('default.not.found.message',  params.id)
            redirect(action: "list")
            return
        }

        [teseInstance: teseInstance]
    }

    def edit() {
        def teseInstance = Tese.get(params.id)
        if (!teseInstance) {
            flash.message = messageGenerator('default.not.found.message',  params.id)
            redirect(action: "list")
            return
        }

        [teseInstance: teseInstance]
    }

    def update() {
        def teseInstance = Tese.get(params.id)
        if (!teseInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tese.label', default: 'Tese'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (teseInstance.version > version) {
                teseInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'tese.label', default: 'Tese')] as Object[],
                          "Another user has updated this Tese while you were editing")
                render(view: "edit", model: [teseInstance: teseInstance])
                return
            }
        }

        teseInstance.properties = params

        if (!teseInstance.save(flush: true)) {
            render(view: "edit", model: [teseInstance: teseInstance])
            return
        }

        flash.message = messageGenerator('default.updated.message',  teseInstance.id)
        redirect(action: "show", id: teseInstance.id)
    }

    def delete() {
        def teseInstance = Tese.get(params.id)
        if (!teseInstance) {
            flash.message = messageGenerator('default.not.found.message', params.id)
            redirect(action: "list")
            return
        }

        try {
            teseInstance.delete(flush: true)
            flash.message = messageGenerator('default.deleted.message', params.id)
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = messageGenerator('default.not.deleted.message' + ' Erro: '+e.message, params.id)
            redirect(action: "show", id: params.id)
        }
    }
    
    def messageGenerator (String code, def id)
    {
        return message(code: code, args: [message(code: 'tese.label', default: 'Tese'), id])
    }
//#if($upXMLTese)
    def uploadXMLTese()
    {
        String flashMessage = 'The non existent theses were successfully imported'
        XMLService serv = new XMLService()
        Node xmlFile = serv.parseReceivedFile(request)
        if (!serv.Import(saveTheses, returnWithMessage, xmlFile, flashMessage))
            return
    }

    Closure returnWithMessage = {
        String msg ->

            redirect(action: "list")
            flash.message = message(code: msg)
    }

    def createThesis(Node xmlNode) {

            Tese newThesis = new Tese()
            newThesis.title =  XMLService.getAttributeValueFromNode(xmlNode, "TITULO-DA-DISSERTACAO-TESE")

            newThesis.publicationDate = new Date()

            String tryingToParse = XMLService.getAttributeValueFromNode(xmlNode, "ANO-DE-OBTENCAO-DO-TITULO")
            if (tryingToParse.isInteger())
                newThesis.publicationDate.set(year: tryingToParse.toInteger())

            newThesis.school = XMLService.getAttributeValueFromNode(xmlNode, "NOME-INSTITUICAO")
            newThesis.file = 'no File'
            newThesis.address = 'no Address'
            newThesis.save(flush: false)

    }

    Closure saveTheses = {
        Node xmlFile ->
            Node dadosGerais = (Node)xmlFile.children()[0]
            Node mestrado = (Node) ((Node) dadosGerais.children()[3]).children()[1]
            Node doutorado = (Node) ((Node) dadosGerais.children()[3]).children()[2]

            createThesis(mestrado)
            createThesis(doutorado)
    }
//#end
}
