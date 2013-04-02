package rgms.publication


class ConferenciaController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	AuxiliarController aux = new AuxiliarController();

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

    def delete() {
		def conferenciaInstance = Conferencia.get(params.id)
		aux.delete(params.id, conferenciaInstance, 'conferencia.label', 'Conferencia');
	}
	
    def update() {
        def conferenciaInstance = Conferencia.get(params.id)
		boolean isReturned = aux.check(params.id, conferenciaInstance, 'conferencia.label', 'Conferencia');
		if(!isReturned){
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

}