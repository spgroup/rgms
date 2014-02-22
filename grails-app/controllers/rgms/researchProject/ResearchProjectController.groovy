package rgms.researchProject

import org.springframework.dao.DataIntegrityViolationException

class ResearchProjectController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [researchProjectInstanceList: ResearchProject.list(params), researchProjectInstanceTotal: ResearchProject.count()]
    }

    def create() {
        [researchProjectInstance: new ResearchProject(params)]
    }

    def save() {
        def researchProjectInstance = new ResearchProject(params)
        if (!researchProjectInstance.save(flush: true)) {
            render(view: "create", model: [researchProjectInstance: researchProjectInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'researchProject.label', default: 'ResearchProject'), researchProjectInstance.id])
        redirect(action: "show", id: researchProjectInstance.id)
    }

    def show(Long id) {
        def researchProjectInstance = ResearchProject.get(id)
        if (!researchProjectInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'researchProject.label', default: 'ResearchProject'), id])
            redirect(action: "list")
            return
        }

        [researchProjectInstance: researchProjectInstance]
    }

    def edit(Long id) {
        def researchProjectInstance = ResearchProject.get(id)
        if (!researchProjectInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'researchProject.label', default: 'ResearchProject'), id])
            redirect(action: "list")
            return
        }

        [researchProjectInstance: researchProjectInstance]
    }

    def update(Long id, Long version) {
        def researchProjectInstance = ResearchProject.get(id)
        if (!researchProjectInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'researchProject.label', default: 'ResearchProject'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (researchProjectInstance.version > version) {
                researchProjectInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'researchProject.label', default: 'ResearchProject')] as Object[],
                        "Another user has updated this ResearchProject while you were editing")
                render(view: "edit", model: [researchProjectInstance: researchProjectInstance])
                return
            }
        }

        researchProjectInstance.properties = params

        if (!researchProjectInstance.save(flush: true)) {
            render(view: "edit", model: [researchProjectInstance: researchProjectInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'researchProject.label', default: 'ResearchProject'), researchProjectInstance.id])
        redirect(action: "show", id: researchProjectInstance.id)
    }

    def delete(Long id) {
        def researchProjectInstance = ResearchProject.get(id)
        if (!researchProjectInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'researchProject.label', default: 'ResearchProject'), id])
            redirect(action: "list")
            return
        }

        try {
            researchProjectInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'researchProject.label', default: 'ResearchProject'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'researchProject.label', default: 'ResearchProject'), id])
            redirect(action: "show", id: id)
        }
    }
}
