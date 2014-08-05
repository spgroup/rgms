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
    //#if($orderByTitle)
    def orderByTitle(){
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        def bookChapters = BookChapter.list(params)
        if(bookChapters.size() != 0){
            bookChapters = bookChapters.sort()
        }
        render(view:"list", model: [bookChapterInstanceList: bookChapters, bookChapterInstanceTotal: bookChapters.size()])

    }
    //#end

    //#if($filterByAuthor)
    def filterByAuthor(){
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        def bookChapters = BookChapter.list(params)
        def authorName = params.authorName
        if(authorName != ""){
            bookChapters = bookChapters.findAll{it.authors.contains(authorName)}
        }

        render(view: "list", model: [bookChapterInstanceList: bookChapters, bookChapterInstanceTotal: bookChapters.size()])

    }
    //#end

    def create() {
        def bookChapterInstance = new BookChapter(params)
        //#if($contextualInformation)
        PublicationController.addAuthor(bookChapterInstance)
        //#end
        [bookChapterInstance: bookChapterInstance]
    }

    def save() {
        PublicationController pb = new PublicationController()
        def bookChapterInstance = new BookChapter(params)

        bookChapterInstance = (BookChapter) pb.extractAuthors(bookChapterInstance)


        if (verifyBookChapter(pb, bookChapterInstance)) {
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

    public boolean verifyBookChapter(PublicationController pb, BookChapter bookChapterInstance) {
        Boolean state = false
        if(!pb.upload(bookChapterInstance) || !bookChapterInstance.save(flush: true)){
            state = true
        }
        return state
    }

    def accessBookChapter(Long id) {
        def bookChapterInstance = BookChapter.get(id)
        boolean isReturned = aux.check(id, bookChapterInstance, 'bookChapter.label', 'BookChapter');
        if (!isReturned) {
            [bookChapterInstance: bookChapterInstance]
        }
    }

    def show(Long id) {
        accessBookChapter(id)
    }

    def edit(Long id) {
        accessBookChapter(id)
    }

    def update(Long id, Long version) {
        def bookChapterInstance = BookChapter.get(id)
        boolean isReturned = aux.check(id, bookChapterInstance, 'bookChapter.label', 'BookChapter')
        if (!isReturned) {
            if (checkVersion(version, bookChapterInstance)) {
                outdatedVersionError((BookChapter) bookChapterInstance)
            } else {
                saveUpdate((BookChapter) bookChapterInstance)
            }
        }
    }

    public boolean checkVersion(long version, BookChapter bookChapterInstance) {
        version != null && bookChapterInstance.version > version

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
