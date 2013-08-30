//#if($Orientation)
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

        if(orientationInstance.orientador.name .equalsIgnoreCase(orientationInstance.orientando))
        {
            render(view: "create", model: [orientationInstance: orientationInstance])
            flash.message = message(code: 'orientation.same.members', args: [message(code: 'orientation.label', default: 'Orientation'), orientationInstance.id])
            return
        }
        if (!orientationInstance.save(flush: true)) {
            render(view: "create", model: [orientationInstance: orientationInstance])
            return
        }

        showFlashMessage(orientationInstance.id, "show", 'default.created.message')

    }

    def show = {
        _processOrientation()
    }

    def edit = {
        _processOrientation()
    }

    def showFlashMessage(Long id, String action, String code){
        flash.message = message(code: code, args: [message(code: 'orientation.label', default: 'Orientation'), id])
        redirect(action: action, id: id)
    }

    def _processOrientation()
    {
        def orientationInstance = Orientation.get(params.id)
        if (!orientationInstance) {
            showFlashMessage(null, "list",'default.not.found.message')
            return
        }

        [orientationInstance: orientationInstance]
    }

    def isOrientationInstance(Long id){

        def orientationInstance = Orientation.get(id)

        if (!orientationInstance) {
            showFlashMessage(id, "list",'default.not.found.message')
            return null
        }

        return orientationInstance
    }

    def update(Long id, Long version) {

        def orientationInstance = isOrientationInstance(id)

        if(orientationInstance != null){
            if(!checkOrientationVersion(orientationInstance, version)) {return }
            orientationInstance.properties = params
            if(!checkOrientationOrientando(orientationInstance)){return }

            if (!orientationInstance.save(flush: true)) {
                render(view: "edit", model: [orientationInstance: orientationInstance])
                return
            }

            showFlashMessage(orientationInstance.id, "show",'default.updated.message')
        }
    }

    def checkOrientationOrientando(Orientation orientationInstance){

        if(orientationInstance.orientador.name.equalsIgnoreCase(orientationInstance.orientando)) {
            render(view: "edit", model: [orientationInstance: orientationInstance])
            flash.message = message(code: 'orientation.same.members', args: [message(code: 'orientation.label', default: 'Orientation'), orientationInstance.id])
            return false
        }

        return true
    }

    def checkOrientationVersion(Orientation orientationInstance, Long version){

        if (version != null) {
            if (orientationInstance.version > version) {
                orientationInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'orientation.label', default: 'Orientation')] as Object[],
                        'default.orientation.checkVersion.message')
                render(view: "edit", model: [orientationInstance: orientationInstance])
                return false
            }
        }
        return true
    }

    def delete(Long id) {
        def orientationInstance = isOrientationInstance(id)

        if(orientationInstance != null){
            try {
                orientationInstance.delete(flush: true)
                showFlashMessage(id,"list",'default.deleted.message')
            }

            catch (DataIntegrityViolationException) {
                showFlashMessage(id, "show", 'default.not.deleted.message')

            }
        }
    }
    //#if($XMLImp)
    Closure returnWithMessage = {
        String msg ->

            redirect(action: "list")
            flash.message = message(code: msg)
    }

    def uploadOrientationXML() {
        String flashMessage = 'default.orientation.imported.message'

        if (!XMLService.Import(saveOrientations, returnWithMessage, flashMessage, request))
            return
    }

    Closure saveOrientations = {
        Node xmlFile ->

        List<Object> completedOrientations = findCompletedOrientations(xmlFile)
        Member user = Member.findByUsername(session.getAttribute("username").toString())

        if (!isNullOrEmpty(completedOrientations))
            for (int i = 0; i < completedOrientations.size(); i++)
                saveNewOrientation(completedOrientations, i, user)
    }

    private void saveNewOrientation(List<Object> completedOrientations, int i, Member user) {
        Node node = (Node) completedOrientations[i]
        Orientation newOrientation = new Orientation()
        String name = (String) node.name()

        fillNewOrientation(name, node, newOrientation)
        saveOrientation(newOrientation)
    }

    private void fillNewOrientation(String name, Node node, Orientation newOrientation){

        if (name.toLowerCase().contains("mestrado")) {
            fillOrientationData(node, newOrientation, user, "Mestrado")
        } else if (name.toLowerCase().contains("doutorado")) {
            fillOrientationData(node, newOrientation, user, "Doutorado")
        } else {

            Node children = (Node) (node.children()[0])
            String natureza = (String) children.attribute("NATUREZA")

            if (isUndergraduateResearch(natureza)) {
                fillOrientationData(node, newOrientation, user, "Iniciação Científica")
            }
        }

    }

    //Only saves if the orientation does not already exist
    private void saveOrientation(Orientation newOrientation) {
        if (Orientation.findAll().find { it -> newOrientation.Equals(it) } == null)
            newOrientation.save(flush: false)
    }

    private boolean isUndergraduateResearch(String natureza) {
        natureza.toLowerCase().contains("iniciacao_cientifica")
    }

    private boolean isNullOrEmpty(List<Object> completedOrientations) {
        completedOrientations == null || completedOrientations.size() > 0
    }

    private List<Object> findCompletedOrientations(Node xmlFile) {
        def orientations = (Node) xmlFile.children()[3]
        def completedOrientations = (Node) orientations.children()[0]
        List<Object> values = completedOrientations.children()
        values
    }

    static void fillOrientationData(Node node, Orientation newOrientation, Member user, String tipoOrientacao) {
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

    //#end
}

//#end