package rgms

import org.springframework.dao.DataIntegrityViolationException

class RecordController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [recordInstanceList: Record.list(params), recordInstanceTotal: Record.count()]
    }

    def create() {
        [recordInstance: new Record(params)]
    }

    def save() {
        def recordInstance = new Record(params)
        if (!recordInstance.save(flush: true)) {
            render(view: "create", model: [recordInstance: recordInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'Record.label', default: 'Record'), recordInstance.id])
        redirect(action: "show", id: recordInstance.id)
    }

    def show() {
        def recordInstance = Record.get(params.id)
        if (!recordInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'Record.label', default: 'Record'), params.id])
            redirect(action: "list")
            return
        }

        [recordInstance: recordInstance]
    }

    def edit() {
        def recordInstance = Record.get(params.id)
        if (!recordInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'Record.label', default: 'Record'), params.id])
            redirect(action: "list")
            return
        }

        [recordInstance: recordInstance]
    }

    def update() {
        def recordInstance = Record.get(params.id)
        if (!recordInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'Record.label', default: 'Record'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (recordInstance.version > version) {
                recordInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'Record.label', default: 'Record')] as Object[],
                          "Another user has updated this Record while you were editing")
                render(view: "edit", model: [recordInstance: recordInstance])
                return
            }
        }

        recordInstance.properties = params

        if (!recordInstance.save(flush: true)) {
            render(view: "edit", model: [recordInstance: recordInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'Record.label', default: 'Record'), recordInstance.id])
        redirect(action: "show", id: recordInstance.id)
    }

    def delete() {
        def recordInstance = Record.get(params.id)
        if (!recordInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'Record.label', default: 'Record'), params.id])
            redirect(action: "list")
            return
        }

        try {
            recordInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'Record.label', default: 'Record'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'Record.label', default: 'Record'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
