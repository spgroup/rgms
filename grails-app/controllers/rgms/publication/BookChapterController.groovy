package rgms.publication


class BookChapterController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	AuxiliarController aux = new AuxiliarController();

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
		boolean isReturned = aux.check(id, bookChapterInstance, 'bookChapter.label', 'BookChapter');
		if(!isReturned){
			[bookChapterInstance: bookChapterInstance]			
		}
    }

    def edit(Long id) {
        def bookChapterInstance = BookChapter.get(id)
		boolean isReturned = aux.check(id, bookChapterInstance, 'bookChapter.label', 'BookChapter');
		if(!isReturned){
			[bookChapterInstance: bookChapterInstance]
		}
    }

	def delete(Long id) {
		def bookChapterInstance = BookChapter.get(id)
		aux.delete(id, bookChapterInstance, 'bookChapter.label', 'BookChapter');
	}
	
    def update(Long id, Long version) {
        def bookChapterInstance = BookChapter.get(id)
        boolean isReturned = aux.check(id, bookChapterInstance, 'bookChapter.label', 'BookChapter');
		if(!isReturned){
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
    }
	
}
