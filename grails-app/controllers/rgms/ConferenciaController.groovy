package rgms

import org.springframework.dao.DataIntegrityViolationException
import rgms.PublicacaoController


class ConferenciaController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

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
		
		//params.member.each{conferenciaInstance.author = conferenciaInstance.author + Member.findAllById(it)}
		
		for(i in conferenciaInstance.members){
			conferenciaInstance.author =  i.name+ "," + conferenciaInstance.author
		}
		conferenciaInstance.author = conferenciaInstance.author.replace(']', '').replace('[', ',')
		
		PublicacaoController pb = new PublicacaoController()
		/**Velocity**/
		#if($bibtex)
			String bibTex = pb.bibTex(conferenciaInstance)
			conferenciaInstance.bibTex = bibTex
		#end
		/**Velocity**/
        if (conferenciaInstance.save(flush: true)) {
			pb.upload(conferenciaInstance)
            render(view: "show", model: [conferenciaInstance: conferenciaInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'conferencia.label', default: 'Conferencia'), conferenciaInstance.id])
        redirect(action: "show", id: conferenciaInstance.id)
    }

    def show() {
        def conferenciaInstance = Conferencia.get(params.id)
				
        if (!conferenciaInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'conferencia.label', default: 'Conferencia'), params.id])
            redirect(action: "list")
            return
        }

        [conferenciaInstance: conferenciaInstance]
    }

    def edit() {
        def conferenciaInstance = Conferencia.get(params.id)
        if (!conferenciaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'conferencia.label', default: 'Conferencia'), params.id])
            redirect(action: "list")
            return
        }

        [conferenciaInstance: conferenciaInstance]
    }

    def update() {
        def conferenciaInstance = Conferencia.get(params.id)
        if (!conferenciaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'conferencia.label', default: 'Conferencia'), params.id])
            redirect(action: "list")
            return
        }

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
			//upload(conferenciaInstance)
            render(view: "edit", model: [conferenciaInstance: conferenciaInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'conferencia.label', default: 'Conferencia'), conferenciaInstance.id])
        redirect(action: "show", id: conferenciaInstance.id)
    }

    def delete() {
        def conferenciaInstance = Conferencia.get(params.id)
        if (!conferenciaInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'conferencia.label', default: 'Conferencia'), params.id])
            redirect(action: "list")
            return
        }

        try {
            conferenciaInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'conferencia.label', default: 'Conferencia'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'conferencia.label', default: 'Conferencia'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
