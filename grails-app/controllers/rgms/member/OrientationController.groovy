package rgms.member

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.web.multipart.MultipartHttpServletRequest
import org.springframework.web.multipart.commons.CommonsMultipartFile
import rgms.XMLService

class OrientationController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [orientationInstanceList: Orientation.list(params), orientationInstanceTotal: Orientation.count()]
    }

    def create() {
        [orientationInstance: new Orientation(params)]
    }

    def save() {
        def orientationInstance = new Orientation(params)
        if(orientationInstance.orientador.id == orientationInstance.orientando.id)
        {
            render(view: "create", model: [orientationInstance: orientationInstance])
            flash.message = message(code: 'orientation.same.members', args: [message(code: 'orientation.label', default: 'Orientation'), orientationInstance.id])
            return
        }
        if (!orientationInstance.save(flush: true)) {
            render(view: "create", model: [orientationInstance: orientationInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'orientation.label', default: 'Orientation'), orientationInstance.id])
        redirect(action: "show", id: orientationInstance.id)
    }

    def show = {
        _processOrientation()
    }

    def edit = {
        _processOrientation()
    }

    def _processOrientation()
    {
        def orientationInstance = Orientation.get(params.id)
        if (!orientationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'orientation.label', default: 'Orientation'), id])
            redirect(action: "list")
            return
        }

        [orientationInstance: orientationInstance]
    }

    def update(Long id, Long version) {
        def orientationInstance = Orientation.get(id)
        if (!orientationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'orientation.label', default: 'Orientation'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (orientationInstance.version > version) {
                orientationInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'orientation.label', default: 'Orientation')] as Object[],
                        "Another user has updated this Orientation while you were editing")
                render(view: "edit", model: [orientationInstance: orientationInstance])
                return
            }
        }

        orientationInstance.properties = params
        if(orientationInstance.orientador.id == orientationInstance.orientando.id)
        {
            render(view: "edit", model: [orientationInstance: orientationInstance])
            flash.message = message(code: 'orientation.same.members', args: [message(code: 'orientation.label', default: 'Orientation'), orientationInstance.id])
            return
        }
        if (!orientationInstance.save(flush: true)) {
            render(view: "edit", model: [orientationInstance: orientationInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'orientation.label', default: 'Orientation'), orientationInstance.id])
        redirect(action: "show", id: orientationInstance.id)
    }

    def delete(Long id) {
        def orientationInstance = Orientation.get(id)
        if (!orientationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'orientation.label', default: 'Orientation'), id])
            redirect(action: "list")
            return
        }

        try {
            orientationInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'orientation.label', default: 'Orientation'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'orientation.label', default: 'Orientation'), id])
            redirect(action: "show", id: id)
        }
    }

    def uploadOrientationXML()
    {
        try
        {
            XMLService serv = new XMLService()
            Node xmlFile = serv.parseReceivedFile(request)
            serv.importOrientations(xmlFile, session.getAttribute("username").toString())
        } catch (Exception) {
            //SAXParseException se o arquivo não for XML
            //NullPointerException se a estrutura do XML está errada (cast em Nó nulo)
            redirect([action: "list"])
            flash.message = 'Insira um arquivo XML válido'
            return
        }
        redirect([action: "list"])
        flash.message = 'Dados do XML extraídos.'
    }
}