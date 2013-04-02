package rgms.publication

import org.springframework.dao.DataIntegrityViolationException

//import Tese;

class TeseController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    public def index() {
        redirect(action: "list", params: params)
    }
	
    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [teseInstanceList: Tese.list(params), teseInstanceTotal: Tese.count()]
    }

    def create() {
        [teseInstance: new Tese(params)]
    }

    def save() {
        def teseInstance = new Tese(params)
		PublicationController pb = new PublicationController()
        if (!pb.upload(teseInstance) || !teseInstance.save(flush: true)) {
            render(view: "create", model: [teseInstance: teseInstance])
            return
        }
		flash.message = message(code: 'default.created.message', args: [message(code: 'tese.label', default: 'Tese'), teseInstance.id])
        redirect(action: "show", id: teseInstance.id)
    }

    def show() {
        ShowOrEdit()
    }

    def edit() {
        ShowOrEdit()
    }

    def update() {
        def teseInstance = Tese.get(params.id)
        if (!teseInstance) {
            getMessage()
            return
        }
        if (params.version) {
            def version = params.version.toLong()
            if (teseInstance.version > version) {
                teseInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'tese.label', default: 'Tese')] as Object[],
                          "Another user has updated this Tese while you were editing")
                render(view: "edit", model: [teseInstance: teseInstance])
                return
            }
        }
        teseInstance.properties = params
        if (!teseInstance.save(flush: true)) {
            render(view: "edit", model: [teseInstance: teseInstance])
            return
        }
		flash.message = message(code: 'default.updated.message', args: [message(code: 'tese.label', default: 'Tese'), teseInstance.id])
        redirect(action: "show", id: teseInstance.id)
    }

    def delete() {
        def teseInstance = Tese.get(params.id)
        if (!teseInstance) {
			getMessage()
            return
        }
        try {
            teseInstance.delete(flush: true)
			getMessage()
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'tese.label', default: 'Tese'), params.id])
            redirect(action: "show", id: params.id)
        }
    }

    def ShowOrEdit(){
        def teseInstance = Tese.get(params.id)
        if (!teseInstance) {
            getMessage()
            return
        }
        [teseInstance: teseInstance]
    }

    def getMessage()
    {
        flash.message = message(code: 'default.not.found.message', args: [message(code: 'tese.label', default: 'Tese'), params.id])
        redirect(action: "list")
    }
}
