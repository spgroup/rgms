package rgms.publication

//#if($XMLUpload && $Conferencia)
import rgms.XMLService
//#end


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
        [conferenciaInstance: new Conferencia(params)]
    }

    def save() {
        def conferenciaInstance = new Conferencia(params)
		PublicationController pb = new PublicationController()
        if (!pb.upload(conferenciaInstance) || !conferenciaInstance.save(flush: true)) {
            render(view: "create", model: [conferenciaInstance: conferenciaInstance])
            return
        }
		flash.message = message(code: 'default.created.message', args: [message(code: 'conferencia.label', default: 'Conferencia'), conferenciaInstance.id])
        redirect(action: "show", id: conferenciaInstance.id)
    }

    def show() {
        def conferenciaInstance = Conferencia.get(params.id)
		boolean isReturned = aux.check(params.id, conferenciaInstance, 'conferencia.label', 'Conferencia');
		if(!isReturned){
			[conferenciaInstance: conferenciaInstance]
		}
    }

    def edit() {
        def conferenciaInstance = Conferencia.get(params.id)
        boolean isReturned = aux.check(params.id, conferenciaInstance, 'conferencia.label', 'Conferencia');
		if(!isReturned){
			[conferenciaInstance: conferenciaInstance]
		}
    }

    def update() {
        def conferenciaInstance = Conferencia.get(params.id)
        boolean isReturned = aux.check(params.id, conferenciaInstance, 'conferencia.label', 'Conferencia');
		if(!isReturned) {
			if (params.version) {
				def version = params.version.toLong()
				if (conferenciaInstance.version > version) {
					conferenciaInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
						[message(code: 'conferencia.label', default: 'Conferencia')] as Object[],
						"Another user has updated this Conferencia while you were editing")
					render(view: "edit", model: [conferenciaInstance: conferenciaInstance])
					return
				}
			}
			conferenciaInstance.properties = params
			if (!conferenciaInstance.save(flush: true)) {
				render(view: "edit", model: [conferenciaInstance: conferenciaInstance])
				return
			}
			flash.message = message(code: 'default.updated.message', args: [message(code: 'conferencia.label', default: 'Conferencia'), conferenciaInstance.id])
			redirect(action: "show", id: conferenciaInstance.id)
		}
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
		String flashMessage = 'The non existent conferences were successfully imported'
		XMLService serv = new XMLService()
		Node xmlFile = serv.parseReceivedFile(request)
		if (!serv.Import(saveConferencias, returnWithMessage, xmlFile, flashMessage)) return
	}
	
	Closure saveConferencias = {
		Node xmlFile -> 
		Node trabalhosEmEventos = (Node) ((Node)xmlFile.children()[1]).children()[0]
		List<Object> trabalhosEmEventosChildren = trabalhosEmEventos.children()
		for (int i = 0; i < trabalhosEmEventosChildren.size(); ++i){
			List<Object> primeiroTrabalho = ((Node)trabalhosEmEventosChildren[i]).children()
			Node dadosBasicos = (Node) primeiroTrabalho[0]
			Node detalhamento = (Node) primeiroTrabalho[1]
			String nomeEvento = XMLService.getAttributeValueFromNode(detalhamento, "NOME-DO-EVENTO")
			if(nomeEvento.contains("onferenc")){
				Conferencia novaConferencia = new Conferencia()
				novaConferencia.title = nomeEvento;
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
					novaConferencia.file = 'emptyfile' + i.toString()
					novaConferencia.save(flush: false)
				}
			}
		}
	}
	//#end
}
