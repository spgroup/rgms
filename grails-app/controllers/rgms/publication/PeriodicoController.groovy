package rgms.publication

import org.apache.shiro.SecurityUtils
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.web.multipart.MultipartHttpServletRequest
import org.springframework.web.multipart.commons.CommonsMultipartFile
import org.xml.sax.SAXParseException
import rgms.XMLService
import rgms.member.Member

class PeriodicoController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [periodicoInstanceList: Periodico.list(params), periodicoInstanceTotal: Periodico.count()]
    }

    def create() {
        def periodicoInstance = new Periodico(params)
        //#if($publicationContext)
        def publcContextOn = grailsApplication.getConfig().getProperty("publicationContext");
        if(publcContextOn){
            if(SecurityUtils.subject?.principal != null){
                PublicationController.addAuthor(periodicoInstance)
            }
        }
        //#end
        [periodicoInstance: periodicoInstance]
    }

    def save () {
        def periodicoInstance = new Periodico(params)
        PublicationController pb = new PublicationController()

        if (!pb.upload(periodicoInstance) || !periodicoInstance.save(flush: true)) {
            render(view: "create", model: [periodicoInstance: periodicoInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'periodico.label', default: 'Periodico'), periodicoInstance.id])
        redirect(action: "show", id: periodicoInstance.id)
    }

    def show() {
        def periodicoInstance = Periodico.get(params.id)
    }

    def edit() {
        def periodicoInstance = Periodico.get(params.id)
    }

    def update() {
        def periodicoInstance = Periodico.get(params.id)
        if (!periodicoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'periodico.label', default: 'Periodico'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (periodicoInstance.version > version) {
                periodicoInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'periodico.label', default: 'Periodico')] as Object[],
                        "Another user has updated this Periodico while you were editing")
                render(view: "edit", model: [periodicoInstance: periodicoInstance])
                return
            }
        }

        periodicoInstance.properties = params

        if (!periodicoInstance.save(flush: true)) {
            upload(periodicoInstance)
            render(view: "edit", model: [periodicoInstance: periodicoInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'periodico.label', default: 'Periodico'), periodicoInstance.id])
        redirect(action: "show", id: periodicoInstance.id)
    }

    def delete() {
        def periodicoInstance = Periodico.get(params.id)
        if (!periodicoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'periodico.label', default: 'Periodico'), params.id])
            redirect(action: "list")
            return
        }

        try {
            periodicoInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'periodico.label', default: 'Periodico'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'periodico.label', default: 'Periodico'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
	
	private checkPeriodicoInstance(Map flash, Map params, Periodico periodicoInstance) {
		if (!periodicoInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'periodico.label', default: 'Periodico'), params.id])
			redirect(action: "list")
			return
		}

		[periodicoInstance: periodicoInstance]
	}

    Closure returnWithMessage = {
        String msg ->

        redirect(action: "list")
        flash.message = message(code: msg)
    }

    def uploadXMLPeriodico()
    {
        String flashMessage = 'The non existent articles were successfully imported'

        XMLService serv = new XMLService()
        Node xmlFile = serv.parseReceivedFile(request)
        if (!serv.Import(saveJournals, returnWithMessage, xmlFile, flashMessage))
            return
    }

    Closure saveJournals = {
        Node xmlFile ->

        Node artigosPublicados = (Node) ((Node)xmlFile.children()[1]).children()[1]
        List<Object> artigosPublicadosChildren = artigosPublicados.children()

        for (int i = 0; i < artigosPublicadosChildren.size(); ++i)
        {
            List<Object> firstArticle = ((Node)artigosPublicadosChildren[i]).children()
            Node dadosBasicos = (Node) firstArticle[0]
            Node detalhamentoArtigo = (Node) firstArticle[1]
            Periodico newJournal = new Periodico()
            newJournal.title = XMLService.getAttributeValueFromNode(dadosBasicos, "TITULO-DO-ARTIGO")

            if (Publication.findByTitle(newJournal.title) == null)
            {
                newJournal.publicationDate = new Date()

                String tryingToParse = XMLService.getAttributeValueFromNode(dadosBasicos, "ANO-DO-ARTIGO")
                if (tryingToParse.isInteger())
                    newJournal.publicationDate.set(year: tryingToParse.toInteger()- 1900)

                tryingToParse = XMLService.getAttributeValueFromNode(detalhamentoArtigo, "VOLUME")
                if (tryingToParse.isInteger())
                    newJournal.volume = tryingToParse.toInteger()
                else
                    newJournal.volume = 1

                tryingToParse = XMLService.getAttributeValueFromNode(detalhamentoArtigo, "FASCICULO")
                if (tryingToParse.isInteger())
                    newJournal.number = tryingToParse.toInteger()
                else
                    newJournal.number = 1

                tryingToParse =  XMLService.getAttributeValueFromNode(detalhamentoArtigo, "PAGINA-FINAL")
                String tryingToParse2 = XMLService.getAttributeValueFromNode(detalhamentoArtigo, "PAGINA-INICIAL")
                if (tryingToParse.isInteger() && tryingToParse2.isInteger())
                    newJournal.pages = tryingToParse.toInteger() - tryingToParse2.toInteger() + 1
                else
                    newJournal.pages = 1

                newJournal.journal = XMLService.getAttributeValueFromNode(detalhamentoArtigo, "TITULO-DO-PERIODICO-OU-REVISTA")
                newJournal.file = 'emptyfile' + i.toString()

                newJournal.save(flush: false)
            }
        }
    }
}
