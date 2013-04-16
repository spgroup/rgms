package rgms.publication

import org.apache.shiro.SecurityUtils
import org.springframework.dao.DataIntegrityViolationException
import rgms.member.Member
import rgms.publication.Conferencia;

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
        //#if($publicationContext)
        def publcContextOn = grailsApplication.getConfig().getProperty("publicationContext");
        if(publcContextOn){
            if(SecurityUtils.subject?.principal != null){
                PublicationController.addAuthor(conferenciaInstance)
            }
        }
        //#end
        [conferenciaInstance: conferenciaInstance]
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
        if (!conferenciaInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'conferencia.label', default: 'Conferencia'), params.id])
            redirect(action: "list")
            return
        }

        [conferenciaInstance: conferenciaInstance]
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
}
