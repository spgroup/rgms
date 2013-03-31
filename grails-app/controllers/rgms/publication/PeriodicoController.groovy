package rgms.publication

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.web.multipart.MultipartHttpServletRequest
import org.springframework.web.multipart.commons.CommonsMultipartFile
import rgms.publication.Periodico;
import org.xml.sax.SAXParseException
import rgms.XMLService

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
        [periodicoInstance: new Periodico(params)]
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
        if (!periodicoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'periodico.label', default: 'Periodico'), params.id])
            redirect(action: "list")
            return
        }

        [periodicoInstance: periodicoInstance]
    }

    def edit() {
        def periodicoInstance = Periodico.get(params.id)
        if (!periodicoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'periodico.label', default: 'Periodico'), params.id])
            redirect(action: "list")
            return
        }

        [periodicoInstance: periodicoInstance]
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

    def returnWithMessage (String msg)
    {
        redirect(action: "list")
        flash.message = message(code: msg)
    }

    def uploadXMLPeriodico()
    {
        String flashMessage = 'The non existent articles were successfullyimported'
        boolean errorFound = false

        try
        {
            XMLService serv = new XMLService()
            Node xmlFile = serv.parseReceivedFile(request)
            serv.saveJournals(xmlFile)
        }
        catch (SAXParseException) { //Se o arquivo nÃ£o for XML ou nÃ£o passaram nenhum
            flashMessage = 'default.xml.parserror.message'
            errorFound = true
        }
        catch (NullPointerException) //Se a estrutura do XML estÃ¡ errada (cast em NÃ³ nulo)
        {
            flashMessage = 'default.xml.structure.message'
            errorFound = true
        }
        catch (Exception e)
        {
            flashMessage = 'default.xml.unknownerror.message'
            errorFound = true
        }
        returnWithMessage(flashMessage)
        if (errorFound) return
    }
}
