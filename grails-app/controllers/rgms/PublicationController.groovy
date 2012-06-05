package rgms

import org.springframework.dao.DataIntegrityViolationException

class PublicationController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [publicationInstanceList: Publication.list(params), publicationInstanceTotal: Publication.count()]
    }

    def create() {
        [publicationInstance: new Publication(params)]
    }

    def save() {
        def publicationInstance = new Publication(params)
        if (!publicationInstance.save(flush: true)) {
            render(view: "create", model: [publicationInstance: publicationInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'publication.label', default: 'Publication'), publicationInstance.id])
        redirect(action: "show", id: publicationInstance.id)
    }

    def show() {
        def publicationInstance = Publication.get(params.id)
        if (!publicationInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'publication.label', default: 'Publication'), params.id])
            redirect(action: "list")
            return
        }

        [publicationInstance: publicationInstance]
    }

    def edit() {
        def publicationInstance = Publication.get(params.id)
        if (!publicationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'publication.label', default: 'Publication'), params.id])
            redirect(action: "list")
            return
        }

        [publicationInstance: publicationInstance]
    }

    def update() {
        def publicationInstance = Publication.get(params.id)
        if (!publicationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'publication.label', default: 'Publication'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (publicationInstance.version > version) {
                publicationInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'publication.label', default: 'Publication')] as Object[],
                          "Another user has updated this Publication while you were editing")
                render(view: "edit", model: [publicationInstance: publicationInstance])
                return
            }
        }

        publicationInstance.properties = params

        if (!publicationInstance.save(flush: true)) {
            render(view: "edit", model: [publicationInstance: publicationInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'publication.label', default: 'Publication'), publicationInstance.id])
        redirect(action: "show", id: publicationInstance.id)
    }

    def delete() {
        def publicationInstance = Publication.get(params.id)
        if (!publicationInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'publication.label', default: 'Publication'), params.id])
            redirect(action: "list")
            return
        }

        try {
            publicationInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'publication.label', default: 'Publication'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'publication.label', default: 'Publication'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
