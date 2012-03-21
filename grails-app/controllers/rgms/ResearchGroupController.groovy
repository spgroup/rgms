package rgms

import org.springframework.dao.DataIntegrityViolationException

class ResearchGroupController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [researchGroupInstanceList: ResearchGroup.list(params), researchGroupInstanceTotal: ResearchGroup.count()]
    }

    def create() {
        [researchGroupInstance: new ResearchGroup(params)]
    }

    def save() {
        def researchGroupInstance = new ResearchGroup(params)
        if (!researchGroupInstance.save(flush: true)) {
            render(view: "create", model: [researchGroupInstance: researchGroupInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'researchGroup.label', default: 'Research Group'), researchGroupInstance.id])
        redirect(action: "show", id: researchGroupInstance.id)
    }

    def show() {
        def researchGroupInstance = ResearchGroup.get(params.id)
        if (!researchGroupInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'researchGroup.label', default: 'Research Group'), params.id])
            redirect(action: "list")
            return
        }

        [researchGroupInstance: researchGroupInstance]
    }

    def edit() {
        def researchGroupInstance = ResearchGroup.get(params.id)
        if (!researchGroupInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'researchGroup.label', default: 'Research Group'), params.id])
            redirect(action: "list")
            return
        }

        [researchGroupInstance: researchGroupInstance]
    }

    def update() {
        def researchGroupInstance = ResearchGroup.get(params.id)
        if (!researchGroupInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'researchGroup.label', default: 'Research Group'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (researchGroupInstance.version > version) {
                researchGroupInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'researchGroup.label', default: 'Research Group')] as Object[],
                          "Another user has updated this ResearchGroup while you were editing")
                render(view: "edit", model: [researchGroupInstance: researchGroupInstance])
                return
            }
        }

        researchGroupInstance.properties = params

        if (!researchGroupInstance.save(flush: true)) {
            render(view: "edit", model: [researchGroupInstance: researchGroupInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'researchGroup.label', default: 'Research Group'), researchGroupInstance.id])
        redirect(action: "show", id: researchGroupInstance.id)
    }

    def delete() {
        def researchGroupInstance = ResearchGroup.get(params.id)
        if (!researchGroupInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'researchGroup.label', default: 'Research Group'), params.id])
            redirect(action: "list")
            return
        }

        try {
            researchGroupInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'researchGroup.label', default: 'Research Group'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'researchGroup.label', default: 'Research Group'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
