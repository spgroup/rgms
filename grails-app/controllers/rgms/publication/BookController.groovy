package rgms.publication

class BookController {
    boolean busca= false,v= false,p= false,n = false;
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    AuxiliarController aux = new AuxiliarController()

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
    if (busca) {
        def bookInstanceList = Book.createCriteria().list(params) {
            if (params.query) {
                System.out.println("penes")
                if (v) {
                    ilike("Volume", "%${params.query}%")
                }
                if(p){
                    ilike("Publisher", "%${params.query}%")
                }
                if(n){
                    ilike("Title", "%${params.query}%")
                }
            }
        }
    }
            [bookInstanceList: Book.list(params), bookInstanceTotal: Book.count()]

    }

    def create() {
        def bookInstance = new Book(params)
        //#if($contextualInformation)
        PublicationController.addAuthor(bookInstance)
        //#end
        [bookInstance: bookInstance]
    }

    def save() {
        PublicationController pb = new PublicationController()
        def bookInstance = new Book(params)

        bookInstance = pb.extractAuthors(bookInstance)


        if (!bookInstance.save(flush: true)) {
            render(view: "create", model: [bookInstance: bookInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'book.label', default: 'Book'), bookInstance.id])
        redirect(action: "show", id: bookInstance.id)
    }

    def show(Long id) {
        def bookInstance = Book.get(id)
        if (!bookInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'book.label', default: 'Book'), id])
            redirect(action: "list")
            return
        }

        [bookInstance: bookInstance]
    }

    def edit(Long id) {
        def bookInstance = Book.get(id)
        if (!bookInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'book.label', default: 'Book'), id])
            redirect(action: "list")
            return
        }

        [bookInstance: bookInstance]
    }

    def update(Long id, Long version) {
        def bookInstance = Book.get(id)
        if (!bookInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'book.label', default: 'Book'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (bookInstance.version > version) {
                bookInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'book.label', default: 'Book')] as Object[],
                        "Another user has updated this Book while you were editing")
                render(view: "edit", model: [bookInstance: bookInstance])
                return
            }
        }

        bookInstance.properties = params

        if (!bookInstance.save(flush: true)) {
            render(view: "edit", model: [bookInstance: bookInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'book.label', default: 'Book'), bookInstance.id])
        redirect(action: "show", id: bookInstance.id)
    }

    def delete(Long id) {
        def bookInstance = Book.get(id)
        aux.delete(id, bookInstance, 'book.label', 'Book');
    }
   def valores ={
       def Input1 = params.Input1
       def Input2 = params.Input2
       ["Input1": Input1, "Input2": Input2]
   }

    def busca(String tipo){
        params.max = Math.min(params.max ? params.int('max') : 5, 100)

        def bookList = Book.createCriteria().list (params) {
            if ( params.query ) {
                ilike(tipo, "%${params.query}%")
            }
        }

        [bookInstanceList: bookList, bookInstanceTotal: bookList.totalCount]
    }

    def listSearchVolume ()
    {
        params.max = Math.min(params.max ? params.int('max') : 5, 100)

        def bookList = Book.createCriteria().list (params) {
            if ( params.query ) {
                ilike(tipo, "%${params.query}%")
            }
        }

        [bookInstanceList: bookList, bookInstanceTotal: bookList.totalCount]
    }
    def listSearchTitle ()
    {
        busca("title");

    }
    def listSearchPublisher(){
        busca("publisher")
    }


}
