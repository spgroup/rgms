package rgms.publication

//#if($XMLUpload && $Conferencia)
import rgms.XMLService
//#end
import org.apache.shiro.SecurityUtils
import org.springframework.web.multipart.MultipartHttpServletRequest

class ConferenciaController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    AuxiliarController aux = new AuxiliarController()

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [conferenciaInstanceList: Conferencia.list(params), conferenciaInstanceTotal: Conferencia.count()]
    }

    def create() {
        def conferenciaInstance = new Conferencia(params)
        //#if($contextualInformation)
        PublicationController.addAuthor(conferenciaInstance)
        //#end
        [conferenciaInstance: conferenciaInstance]
    }

    private alertMessage(String typeMessage, Conferencia conferenciaInstance){
        flash.message = message(code: 'default.'+ typeMessage +'.message', args: [message(code: 'conferencia.label', default: 'Conferencia'), conferenciaInstance.id])
        redirect(action: "show", id: conferenciaInstance.id)
    }

    def save() {
        def conferenciaInstance = new Conferencia(params)
        PublicationController pb = new PublicationController()
        if (!pb.upload(conferenciaInstance) || !conferenciaInstance.save(flush: true)) {
            render(view: "create", model: [conferenciaInstance: conferenciaInstance])
            return
        }
        alertMessage('created',conferenciaInstance)
    }

    private returnConferenciaInstance(){
        def conferenciaInstance = Conferencia.get(params.id)
        boolean isReturned = aux.check(params.id, conferenciaInstance, 'conferencia.label', 'Conferencia');
        if(!isReturned){
            [conferenciaInstance: conferenciaInstance]
        }

    }

    private returnConferenciaActualVersion()
    {
        def conferenciaInstance = Conferencia.get(params.id)
        boolean isReturned = aux.check(params.id, conferenciaInstance, 'conferencia.label', 'Conferencia');
        if(!isReturned)
        {
            if (params.version) {
                def version = params.version.toLong()
                if (conferenciaInstance.version > version) {
                    conferenciaInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                            [message(code: 'conferencia.label', default: 'Conferencia')] as Object[],
                            message(code: 'default.updateError.message'))
                    render(view: "edit", model: [conferenciaInstance: conferenciaInstance])
                    return  }  }
            conferenciaInstance.properties = params
            if (!conferenciaInstance.save(flush: true)) {
                render(view: "edit", model: [conferenciaInstance: conferenciaInstance])
                return
            }
            alertMessage('updated',conferenciaInstance)
        }
    }

    def show() {
        returnConferenciaInstance()
    }

    def edit() {
        returnConferenciaInstance()
    }

    def update() {
        returnConferenciaActualVersion();
    }

    def delete() {
		def conferenciaInstance = Conferencia.get(params.id)
		aux.delete(params.id, conferenciaInstance, 'conferencia.label', 'Conferencia');
	}

    //#if($XMLUpload && $Conferencia)
    Closure returnWithMessage = {
        String msg ->
            redirect(action: "list")
            flash.message = message(code: msg)
    }

    def enviarConferenciaXML(){
        String flashMessage = message(code: 'default.importedMsg.message')

        if (!XMLService.Import(saveConferencias, returnWithMessage, flashMessage, request))
            return
    }

    Closure saveConferencias = {
        Node xmlFile ->
            Node trabalhosEmEventos = (Node) ((Node)xmlFile.children()[1]).children()[0]

            for (Node currentNode : trabalhosEmEventos.children()){
                List<Object> nodeConferencia = currentNode.children()
                saveNewConferencia (nodeConferencia);
            }
    }

    private void saveNewConferencia (List nodeConferencia){
        Node dadosBasicos = (Node) nodeConferencia[0]
        Node detalhamento = (Node) nodeConferencia[1]
        String nomeEvento = XMLService.getAttributeValueFromNode(detalhamento, "NOME-DO-EVENTO")

        if(nomeEvento.contains("onferenc")){
            Conferencia novaConferencia = new Conferencia()
            novaConferencia.title = nomeEvento

            if (Publication.findByTitle(novaConferencia.title) == null){
                novaConferencia.publicationDate = new Date()
                String tryingToParse = XMLService.getAttributeValueFromNode(dadosBasicos, "ANO-DO-TRABALHO")

                if (tryingToParse.isInteger())
                    novaConferencia.publicationDate.set(year: tryingToParse.toInteger())

                tryingToParse = XMLService.getAttributeValueFromNode(dadosBasicos, "TITULO-DO-TRABALHO")
                novaConferencia.booktitle = tryingToParse;
                tryingToParse =  XMLService.getAttributeValueFromNode(detalhamento, "PAGINA-INICIAL")
                String tryingToParse2 = XMLService.getAttributeValueFromNode(detalhamento, "PAGINA-FINAL")
                novaConferencia.pages = tryingToParse + " - " + tryingToParse2
                novaConferencia.file = 'emptyfile'
                novaConferencia.save(flush: false)
            }
        }
    }
    //#end
}
