package rgms.publication

import org.springframework.dao.DataIntegrityViolationException


class BookChapterController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [bookChapterInstanceList: BookChapter.list(params), bookChapterInstanceTotal: BookChapter.count()]
    }

    def create() {
        [bookChapterInstance: new BookChapter(params)]
    }

    def save() {
        def bookChapterInstance = new BookChapter(params)
		PublicationController pb = new PublicationController()
        if (!pb.upload(bookChapterInstance) || !bookChapterInstance.save(flush: true)) {
            render(view: "create", model: [bookChapterInstance: bookChapterInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'bookChapter.label', default: 'BookChapter'), bookChapterInstance.id])
        redirect(action: "show", id: bookChapterInstance.id)
    }

    def show(Long id) {
        def bookChapterInstance = BookChapter.get(id)
        if (!bookChapterInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bookChapter.label', default: 'BookChapter'), id])
            redirect(action: "list")
            return
        }

        [bookChapterInstance: bookChapterInstance]
    }

    def edit(Long id) {
        def bookChapterInstance = BookChapter.get(id)
        if (!bookChapterInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bookChapter.label', default: 'BookChapter'), id])
            redirect(action: "list")
            return
        }

        [bookChapterInstance: bookChapterInstance]
    }

    def update(Long id, Long version) {
        def bookChapterInstance = BookChapter.get(id)
        if (!bookChapterInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bookChapter.label', default: 'BookChapter'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (bookChapterInstance.version > version) {
                bookChapterInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'bookChapter.label', default: 'BookChapter')] as Object[],
                          "Another user has updated this BookChapter while you were editing")
                render(view: "edit", model: [bookChapterInstance: bookChapterInstance])
                return
            }
        }

        bookChapterInstance.properties = params

        if (!bookChapterInstance.save(flush: true)) {
            render(view: "edit", model: [bookChapterInstance: bookChapterInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'bookChapter.label', default: 'BookChapter'), bookChapterInstance.id])
        redirect(action: "show", id: bookChapterInstance.id)
    }

    def delete(Long id) {
        def bookChapterInstance = BookChapter.get(id)
        if (!bookChapterInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bookChapter.label', default: 'BookChapter'), id])
            redirect(action: "list")
            return
        }

        try {
            bookChapterInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'bookChapter.label', default: 'BookChapter'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'bookChapter.label', default: 'BookChapter'), id])
            redirect(action: "show", id: id)
        }
    }
}
