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
        //#if($facebook)
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
	
	//#if($XMLUpload && $BookChapter)
	Closure returnWithMessage = {
		String msg ->

		redirect(action: "list")
		flash.message = message(code: msg)
	}

    def uploadXMLBookChapter()
    {
        String flashMessage = 'The non existent Book Chapters were successfully imported'

        if (XMLService.Import(saveBookChapters, returnWithMessage, flashMessage, request))
            return
    }

    Closure saveBookChapters = {
        Node xmlFile ->
            Node bookChapters = (Node)((Node)((Node)xmlFile.children()[1]).children()[2]).children()[1]
            List<Object> bookChaptersChildren = bookChapters.children()
            for (int i = 0; i < bookChaptersChildren.size(); ++i){
                List<Object> bookChapter = ((Node)bookChaptersChildren[i]).children()
                Node dadosBasicos = (Node) bookChapter[0]
                Node detalhamentoCapitulo = (Node) bookChapter[1]
                createNewBookChapter(dadosBasicos,detalhamentoCapitulo, i)
            }
    }

    def createNewBookChapter (Node dadosBasicos, Node detalhamentoCapitulo, int i){
        BookChapter newBookChapter = new BookChapter()
        newBookChapter.title = XMLService.getAttributeValueFromNode(dadosBasicos, "TITULO-DO-CAPITULO-DO-LIVRO")
        newBookChapter.publisher = XMLService.getAttributeValueFromNode(detalhamentoCapitulo, "NOME-DA-EDITORA")

        print(newBookChapter.title)
        if (Publication.findByTitle(newBookChapter.title) == null){
            fillBookChapterInfo(newBookChapter, dadosBasicos, i)
        }
    }

    def fillBookChapterInfo (BookChapter newBookChapter, Node dadosBasicos, int i){
        newBookChapter.publicationDate = new Date()

        String tryingToParse = XMLService.getAttributeValueFromNode(dadosBasicos, "ANO")
        if (tryingToParse.isInteger())
            newBookChapter.publicationDate.set(year: tryingToParse.toInteger())

        print(newBookChapter.publicationDate)
        newBookChapter.file = 'emptyfile' + i.toString()
        newBookChapter.chapter = 2
        newBookChapter.save(flush: false)
    }
    //#end
}
