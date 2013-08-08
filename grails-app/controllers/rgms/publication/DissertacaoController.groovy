package rgms.publication

import rgms.XMLService
import org.apache.shiro.SecurityUtils
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.springframework.dao.DataIntegrityViolationException
import rgms.member.Member


class DissertacaoController {

    def grailsApplication
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [dissertacaoInstanceList: Dissertacao.list(params), dissertacaoInstanceTotal: Dissertacao.count()]
    }

    def create() {
        Dissertacao dissertacaoInstance = new Dissertacao(params)
        //#if($publicationContext)
        def publcContextOn = grailsApplication.getConfig().getProperty("publicationContext");
        if(publcContextOn){
            if(SecurityUtils.subject?.principal != null){
                def user = PublicationController.addAuthor(dissertacaoInstance)
                if(!user.university.isEmpty()){
                    dissertacaoInstance.school = user.university
                }
            }
        }
        //#end
        [dissertacaoInstance: dissertacaoInstance]
    }

    def save() {
        def dissertacaoInstance = new Dissertacao(params)

        PublicationController pb = new PublicationController()


        if (Dissertacao.findByTitle(params.title)) {
            handleSavingError(dissertacaoInstance, 'dissertacao.duplicatetitle.failure')
            return
        }
        if (!pb.upload(dissertacaoInstance)) {
            handleSavingError(dissertacaoInstance, 'dissertacao.filesaving.failure')
            return
        }
        if (!dissertacaoInstance.save(flush: true)) {
            handleSavingError(dissertacaoInstance, 'dissertacao.saving.failure')
            return
        }
        //#if($facebook)
        //def user = Member.findByUsername(SecurityUtils.subject?.principal)
		//pb.sendPostFacebook(user, dissertacaoInstance.toString())
        //#end
		flash.message = message(code: 'default.created.message', args: [message(code: 'dissertacao.label', default: 'Dissertacao'), dissertacaoInstance.id])
        redirect(action: "show", id: dissertacaoInstance.id)
    }

    def handleSavingError(Dissertacao dissertacaoInstance, String message) {
        dissertacaoInstance.discardMembers()
        flash.message = message
        render(view: "create", model: [dissertacaoInstance: dissertacaoInstance])
    }

    def show() {
        def dissertacaoInstance = Dissertacao.get(params.id)

        if (!dissertacaoInstance) {
            flash.message = messageGenerator('default.not.found.message', params.id)
            redirect(action: "list")
            return
        }

        [dissertacaoInstance: dissertacaoInstance]
    }

    def edit() {
        def dissertacaoInstance = Dissertacao.get(params.id)
        if (!dissertacaoInstance) {
            flash.message = messageGenerator('default.not.found.message', params.id)
            redirect(action: "list")
            return
        }

        [dissertacaoInstance: dissertacaoInstance]
    }

    def update() {
        def dissertacaoInstance = Dissertacao.get(params.id)
        if (!dissertacaoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'dissertacao.label', default: 'Dissertacao'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (dissertacaoInstance.version > version) {
                dissertacaoInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'dissertacao.label', default: 'Dissertacao')] as Object[],
                        "Another user has updated this Dissertacao while you were editing")
                render(view: "edit", model: [dissertacaoInstance: dissertacaoInstance])
                return
            }
        }

        dissertacaoInstance.properties = params

        if (!dissertacaoInstance.save(flush: true)) {
            render(view: "edit", model: [dissertacaoInstance: dissertacaoInstance])
            return
        }

        flash.message = messageGenerator('default.updated.message', dissertacaoInstance.id)
        redirect(action: "show", id: dissertacaoInstance.id)
    }

    def delete() {
        def dissertacaoInstance = Dissertacao.get(params.id)
        if (!dissertacaoInstance) {
            flash.message = messageGenerator('default.not.found.message', params.id)
            redirect(action: "list")
            return
        }

        try {
            dissertacaoInstance.delete(flush: true)
            flash.message = messageGenerator('default.deleted.message', params.id)
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = messageGenerator('default.not.deleted.message' + ' Erro: ' + e.message, params.id)
            redirect(action: "show", id: params.id)
        }
    }

    def messageGenerator(String code, def id) {
        return message(code: code, args: [message(code: 'dissertacao.label', default: 'Dissertacao'), id])
    }
//#if($upXMLDissertacao)
    def uploadXMLDissertacao() {
        String flashMessage = 'The non existent dissertations were successfully imported'

        XMLService serv = new XMLService()
        Node xmlFile = serv.parseReceivedFile(request)
        if (!serv.Import(saveDissertations, returnWithMessage, flashMessage, xmlFile))
            return
    }

    Closure returnWithMessage = {
        String msg ->

            redirect(action: "list")
            flash.message = message(code: msg)
    }

    def createDissertation(Node xmlNode) {

        Dissertacao newDissertation = new Dissertacao()
        newDissertation.title = XMLService.getAttributeValueFromNode(xmlNode, "TITULO-DA-DISSERTACAO-TESE")

        newDissertation.publicationDate = new Date()

        String tryingToParse = XMLService.getAttributeValueFromNode(xmlNode, "ANO-DE-OBTENCAO-DO-TITULO")
        if (tryingToParse.isInteger())
            newDissertation.publicationDate.set(year: tryingToParse.toInteger())

        newDissertation.school = XMLService.getAttributeValueFromNode(xmlNode, "NOME-INSTITUICAO")
        newDissertation.file = 'no File'
        newDissertation.address = 'no Address'
        newDissertation.save(flush: false)

    }

    Closure saveDissertations = {
        Node xmlFile ->
            Node dadosGerais = (Node) xmlFile.children()[0]
            Node mestrado = (Node) ((Node) dadosGerais.children()[3]).children()[1]
            Node doutorado = (Node) ((Node) dadosGerais.children()[3]).children()[2]

            createDissertation(mestrado)
            createDissertation(doutorado)
    }
//#end
}
