//#if($funder)
package rgms.researchProject

import org.springframework.dao.DataIntegrityViolationException

class FunderController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [funderInstanceList: Funder.list(params), funderInstanceTotal: Funder.count()]
    }

    def create() {
        [funderInstance: new Funder(params)]
    }

    def save() {
        def funderInstance = new Funder(params)
        if (!funderInstance.save(flush: true)) {
            render(view: "create", model: [funderInstance: funderInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'funder.label', default: 'Funder'), funderInstance.id])
        redirect(action: "show", id: funderInstance.id)
    }

    def show(Long id) {
        def funderInstance = Funder.get(id)
        if (!funderInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'funder.label', default: 'Funder'), id])
            redirect(action: "list")
            return
        }

        [funderInstance: funderInstance]
    }

    def edit(Long id) {
        def funderInstance = Funder.get(id)
        if (!funderInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'funder.label', default: 'Funder'), id])
            redirect(action: "list")
            return
        }

        [funderInstance: funderInstance]
    }

    def update(Long id, Long version) {
        def funderInstance = Funder.get(id)
        if (!funderInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'funder.label', default: 'Funder'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (funderInstance.version > version) {
                funderInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'funder.label', default: 'Funder')] as Object[],
                        "Another user has updated this Funder while you were editing")
                render(view: "edit", model: [funderInstance: funderInstance])
                return
            }
        }

        funderInstance.properties = params

        if (!funderInstance.save(flush: true)) {
            render(view: "edit", model: [funderInstance: funderInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'funder.label', default: 'Funder'), funderInstance.id])
        redirect(action: "show", id: funderInstance.id)
    }

    def delete(Long id) {
        def funderInstance = Funder.get(id)
        if (!funderInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'funder.label', default: 'Funder'), id])
            redirect(action: "list")
            return
        }

        try {
            funderInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'funder.label', default: 'Funder'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'funder.label', default: 'Funder'), id])
            redirect(action: "show", id: id)
        }
    }
}
//#end