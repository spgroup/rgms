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

    Closure returnWithMessage = {
        String msg ->

            redirect(action: "list")
            flash.message = message(code: msg)
    }

    def uploadOrientationXML()
    {
        String flashMessage = 'The non existent orientations were successfully imported'

        XMLService serv = new XMLService()
        Node xmlFile = serv.parseReceivedFile(request)
        if (!serv.Import(saveOrientations, returnWithMessage, xmlFile, flashMessage))
            return
    }

    Closure saveOrientations = {
        Node xmlFile ->

        String loggedUser = session.getAttribute("username").toString()
        Member user = Member.findByUsername(loggedUser)
        def orientations = (Node)xmlFile.children()[3]
        def concludedOrientations = (Node)orientations.children()[0]
        List<Object> values = concludedOrientations.children()
        if(values != null && values.size() > 0)
        {
            for(int i = 0; i < values.size(); i++)
            {
                Node node = (Node)values[i]
                Orientation newOrientation = new Orientation()
                String name = (String)node.name()
                if (name.toLowerCase().contains("mestrado"))
                {
                    fillOrientationData(node, newOrientation, user, "Mestrado")
                }
                else if (name.toLowerCase().contains("doutorado"))
                {
                    fillOrientationData(node, newOrientation, user, "Doutorado")
                }
                else
                {
                    Node children = (Node)(node.children()[0])
                    String natureza = (String)children.attribute("NATUREZA")
                    if (natureza.toLowerCase().contains("iniciacao_cientifica"))
                    {
                        fillOrientationData(node, newOrientation, user, "Iniciação Científica")
                    }
                }
                if (Orientation.findAll().find { it -> newOrientation.Equals(it)} == null)
                    newOrientation.save(flush: false)
            }
        }
    }

    static void fillOrientationData(Node node, Orientation newOrientation, Member user, String tipoOrientacao)
    {
        Node basicData = (Node)(node.children()[0])
        Node specificData = (Node)(node.children()[1])
        newOrientation.tipo = tipoOrientacao
        newOrientation.tituloTese = XMLService.getAttributeValueFromNode(basicData, "TITULO")
        String ano = XMLService.getAttributeValueFromNode(basicData, "ANO")
        newOrientation.anoPublicacao = Integer.parseInt(ano)
        newOrientation.curso = XMLService.getAttributeValueFromNode(specificData, "NOME-DO-CURSO")
        newOrientation.instituicao = XMLService.getAttributeValueFromNode(specificData, "NOME-DA-INSTITUICAO")
        newOrientation.orientador = user
        newOrientation.orientando = XMLService.getAttributeValueFromNode(specificData, "NOME-DO-ORIENTADO")
    }
}