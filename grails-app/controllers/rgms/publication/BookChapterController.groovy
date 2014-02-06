package rgms.publication
//#if($XMLUpload && $BookChapter)
//#end
class BookChapterController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    AuxiliarController aux = new AuxiliarController()

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [bookChapterInstanceList: BookChapter.list(params), bookChapterInstanceTotal: BookChapter.count()]
    }

    def create() {
        def bookChapterInstance = new BookChapter(params)
        //#if($contextualInformation)
        PublicationController.addAuthor(bookChapterInstance)
        //#end
        [bookChapterInstance: bookChapterInstance]
    }

    def save() {
        def bookChapterInstance = new BookChapter(params)
        PublicationController pb = new PublicationController()
        if (!pb.upload(bookChapterInstance) || !bookChapterInstance.save(flush: true)) {
            render(view: "create", model: [bookChapterInstance: bookChapterInstance])
            return
        }
        //#if($facebook)
        //def user = User.findByUsername(SecurityUtils.subject.principal)
        //Member author = user?.author
        //pb.sendPostFacebook(author, bookChapterInstance.toString())
        //#end
        //noinspection InvalidI18nProperty
        flash.message = message(code: 'default.created.message', args: [message(code: 'bookChapter.label', default: 'BookChapter'), bookChapterInstance.id])
        redirect(action: "show", id: bookChapterInstance.id)
    }

    def accessBookChapter(Long id) {
        BookChapter bookChapterInstance = checkBook(id)
        if(bookChapterInstance != null){
            [bookChapterInstance: bookChapterInstance]
        }
    }

    private BookChapter checkBook(long id) {
        def bookChapterInstance = BookChapter.get(id)
        //noinspection GroovyUnusedAssignment,GroovyUnusedAssignment
        boolean isReturned = aux.check(id, bookChapterInstance, 'bookChapter.label', 'BookChapter')
        isReturned ? bookChapterInstance : null
    }

    def show(Long id) {
        accessBookChapter(id)
    }

    def edit(Long id) {
        accessBookChapter(id)
    }

    def update(Long id, Long version) {

        def bookChapterInstance = checkBook(id)
        if (bookChapterInstance != null) {
            if (version != null && bookChapterInstance.version > version) {
                outdatedVersionError((BookChapter) bookChapterInstance)
            } else {
                saveUpdate((BookChapter) bookChapterInstance)
            }
        }
    }

    def outdatedVersionError(BookChapter bookChapterInstance) {
        //noinspection InvalidI18nProperty
        bookChapterInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                [message(code: 'bookChapter.label', default: 'BookChapter')] as Object[],
                "Another user has updated this BookChapter while you were editing")
        render(view: "edit", model: [bookChapterInstance: bookChapterInstance])
    }

    def saveUpdate(BookChapter bookChapterInstance) {
        bookChapterInstance.properties = params
        if (!bookChapterInstance.save(flush: true)) {
            render(view: "edit", model: [bookChapterInstance: bookChapterInstance])
        } else {
            //noinspection InvalidI18nProperty
            flash.message = message(code: 'default.updated.message', args: [message(code: 'bookChapter.label', default: 'BookChapter'), bookChapterInstance.id])
            redirect(action: "show", id: bookChapterInstance.id)
        }
    }

    def delete(Long id) {
        def bookChapterInstance = BookChapter.get(id)
        aux.delete(id, bookChapterInstance, 'bookChapter.label', 'BookChapter');
    }

}
