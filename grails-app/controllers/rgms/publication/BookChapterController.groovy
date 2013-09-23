package rgms.publication

import org.apache.shiro.SecurityUtils
import org.springframework.dao.DataIntegrityViolationException
//#if($XMLUpload && $BookChapter)
import rgms.XMLService
//#end
import org.xml.sax.SAXParseException
import rgms.member.Member


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
        //#if($Facebook)
       // def user = Member.findByUsername(SecurityUtils.subject.principal)
       // pb.sendPostFacebook(user, bookChapterInstance.toString())
        //#end
        flash.message = message(code: 'default.created.message', args: [message(code: 'bookChapter.label', default: 'BookChapter'), bookChapterInstance.id])
        redirect(action: "show", id: bookChapterInstance.id)
    }
    def accessBookChapter(Long id) {
        def bookChapterInstance = BookChapter.get(id)
        boolean isReturned = aux.check(id, bookChapterInstance, 'bookChapter.label', 'BookChapter');
        if(!isReturned){
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
        if(!isReturned){
            if (version != null && bookChapterInstance.version > version) {
                outdatedVersionError((BookChapter) bookChapterInstance)
            }else{
                saveUpdate((BookChapter) bookChapterInstance)
            }
        }
    }

    def outdatedVersionError(BookChapter bookChapterInstance) {
        bookChapterInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                [message(code: 'bookChapter.label', default: 'BookChapter')] as Object[],
                "Another user has updated this BookChapter while you were editing")
        render(view: "edit", model: [bookChapterInstance: bookChapterInstance])
    }

    def saveUpdate(BookChapter bookChapterInstance){
        bookChapterInstance.properties = params
        if (!bookChapterInstance.save(flush: true)) {
            render(view: "edit", model: [bookChapterInstance: bookChapterInstance])
        } else {
            flash.message = message(code: 'default.updated.message', args: [message(code: 'bookChapter.label', default: 'BookChapter'), bookChapterInstance.id])
            redirect(action: "show", id: bookChapterInstance.id)
        }
    }

	def delete(Long id) {
		def bookChapterInstance = BookChapter.get(id)
		aux.delete(id, bookChapterInstance, 'bookChapter.label', 'BookChapter');
	}
}
