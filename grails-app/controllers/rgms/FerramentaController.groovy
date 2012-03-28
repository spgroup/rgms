package rgms

import org.springframework.dao.DataIntegrityViolationException

class FerramentaController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [ferramentaInstanceList: Ferramenta.list(params), ferramentaInstanceTotal: Ferramenta.count()]
    }

    def create() {
        [ferramentaInstance: new Ferramenta(params)]
    }

    def save() {
        def ferramentaInstance = new Ferramenta(params)
		
		ferramentaInstance.link = "http://"+ferramentaInstance.link
		
        if (!ferramentaInstance.save(flush: true)) {
            render(view: "create", model: [ferramentaInstance: ferramentaInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'ferramenta.label', default: 'Ferramenta'), ferramentaInstance.id])
        redirect(action: "show", id: ferramentaInstance.id)
    }

    def show() {
        def ferramentaInstance = Ferramenta.get(params.id)
        if (!ferramentaInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'ferramenta.label', default: 'Ferramenta'), params.id])
            redirect(action: "list")
            return
        }

        [ferramentaInstance: ferramentaInstance]
    }

    def edit() {
        def ferramentaInstance = Ferramenta.get(params.id)
		
		ferramentaInstance.link = "http://"+ferramentaInstance.link
		
        if (!ferramentaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'ferramenta.label', default: 'Ferramenta'), params.id])
            redirect(action: "list")
            return
        }

        [ferramentaInstance: ferramentaInstance]
    }

    def update() {
        def ferramentaInstance = Ferramenta.get(params.id)
        if (!ferramentaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'ferramenta.label', default: 'Ferramenta'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (ferramentaInstance.version > version) {
                ferramentaInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'ferramenta.label', default: 'Ferramenta')] as Object[],
                          "Another user has updated this Ferramenta while you were editing")
                render(view: "edit", model: [ferramentaInstance: ferramentaInstance])
                return
            }
        }

        ferramentaInstance.properties = params

        if (!ferramentaInstance.save(flush: true)) {
            render(view: "edit", model: [ferramentaInstance: ferramentaInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'ferramenta.label', default: 'Ferramenta'), ferramentaInstance.id])
        redirect(action: "show", id: ferramentaInstance.id)
    }

    def delete() {
        def ferramentaInstance = Ferramenta.get(params.id)
        if (!ferramentaInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'ferramenta.label', default: 'Ferramenta'), params.id])
            redirect(action: "list")
            return
        }

        try {
            ferramentaInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'ferramenta.label', default: 'Ferramenta'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'ferramenta.label', default: 'Ferramenta'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
