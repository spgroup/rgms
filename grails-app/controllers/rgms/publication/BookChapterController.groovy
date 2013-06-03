package rgms.publication

import org.springframework.dao.DataIntegrityViolationException
//#if($XMLUpload && $BookChapter)
import rgms.XMLService
//#end
import org.xml.sax.SAXParseException

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

    def update(Long id, Long version) {
        def bookChapterInstance = BookChapter.get(id)
		boolean isReturned = aux.check(id, bookChapterInstance, 'bookChapter.label', 'BookChapter')
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

		XMLService serv = new XMLService()
		Node xmlFile = serv.parseReceivedFile(request)
		if (!serv.Import(saveBookChapters, returnWithMessage, xmlFile, flashMessage))
			return
	}

	Closure saveBookChapters = {
		
		Node xmlFile ->
		
				Node bookChapters = (Node)((Node)((Node)xmlFile.children()[1]).children()[2]).children()[1]
				List<Object> bookChaptersChildren = bookChapters.children()
		
				for (int i = 0; i < bookChaptersChildren.size(); ++i)
				{
					List<Object> bookChapter = ((Node)bookChaptersChildren[i]).children()
					
					Node dadosBasicos = (Node) bookChapter[0]
					Node detalhamentoCapitulo = (Node) bookChapter[1]
								
					BookChapter newBookChapter = new BookChapter()
					newBookChapter.title = XMLService.getAttributeValueFromNode(dadosBasicos, "TITULO-DO-CAPITULO-DO-LIVRO")
		
					print(newBookChapter.title)
					
					if (Publication.findByTitle(newBookChapter.title) == null)
					{
						newBookChapter.publicationDate = new Date()
		
						String tryingToParse = XMLService.getAttributeValueFromNode(dadosBasicos, "ANO")
						if (tryingToParse.isInteger())
							newBookChapter.publicationDate.set(year: tryingToParse.toInteger())
		
							
						print(newBookChapter.publicationDate)
						newBookChapter.file = 'emptyfile' + i.toString()
						newBookChapter.publisher = "Empty"
						newBookChapter.chapter = 2
						newBookChapter.save(flush: false)
					}
				}
		
	}
	//#end
}
