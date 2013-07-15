package rgms.visit

import org.springframework.dao.DataIntegrityViolationException

class VisitController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [visitorInstanceList: Visitor.list(params), visitorInstanceTotal: Visitor.count()]
    }

    def create() {
        [visitorInstance: new Visitor(params)]
    }

    def save() {
        def visitorInstance = new Visitor(params)
        if (!visitorInstance.save(flush: true)) {
            render(view: "create", model: [visitorInstance: visitorInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'visitor.label', default: 'Visitor'), visitorInstance.id])
        redirect(action: "show", id: visitorInstance.id)
    }

    def show(Long id) {
        def visitorInstance = Visitor.get(id)
        if (!visitorInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'visitor.label', default: 'Visitor'), id])
            redirect(action: "list")
            return
        }

        [visitorInstance: visitorInstance]
    }

    def edit(Long id) {
        def visitorInstance = Visitor.get(id)
        if (!visitorInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'visitor.label', default: 'Visitor'), id])
            redirect(action: "list")
            return
        }

        [visitorInstance: visitorInstance]
    }

    def update(Long id, Long version) {
        def visitorInstance = Visitor.get(id)
        if (!visitorInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'visitor.label', default: 'Visitor'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (visitorInstance.version > version) {
                visitorInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'visitor.label', default: 'Visitor')] as Object[],
                        "Another user has updated this Visitor while you were editing")
                render(view: "edit", model: [visitorInstance: visitorInstance])
                return
            }
        }

        visitorInstance.properties = params

        if (!visitorInstance.save(flush: true)) {
            render(view: "edit", model: [visitorInstance: visitorInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'visitor.label', default: 'Visitor'), visitorInstance.id])
        redirect(action: "show", id: visitorInstance.id)
    }

    def delete(Long id) {
        def visitorInstance = Visitor.get(id)
        if (!visitorInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'visitor.label', default: 'Visitor'), id])
            redirect(action: "list")
            return
        }

        try {
            visitorInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'visitor.label', default: 'Visitor'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'visitor.label', default: 'Visitor'), id])
            redirect(action: "show", id: id)
        }
    }
}
