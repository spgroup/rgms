package rgms.publication

import org.apache.shiro.SecurityUtils
import org.springframework.dao.DataIntegrityViolationException
import rgms.GoogleScholarService
import rgms.XMLService
import rgms.member.*
import rgms.publication.*

class PeriodicoController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST", share: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [periodicoInstanceList: Periodico.list(params), periodicoInstanceTotal: Periodico.count()]
    }

    //#if($Report)
    //
    def report() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        def GoogleScholarService gss = new GoogleScholarService()
        gss.findCitations(Periodico.list(params))
        [periodicoInstanceList: Periodico.list(params), periodicoInstanceTotal: Periodico.count()]
    }
    //#end

    def create() {
        def periodicoInstance = new Periodico(params)
        //#if($publicationContext)
        def publcContextOn = grailsApplication.getConfig().getProperty("publicationContext");
        if (publcContextOn) {
            if (SecurityUtils.subject?.principal != null) {
                PublicationController.addAuthor(periodicoInstance)
            }
        }
        //#end
        [periodicoInstance: periodicoInstance]
    }

    def save() {
        //def pb = new PublicationController()
        def periodicoInstance = new Periodico(params)
        if (Periodico.findByTitle(params.title)) {
            handleSavingError(periodicoInstance, 'periodico.duplicatetitle.failure')
            return
        }
        if (!PublicationController.newUpload(periodicoInstance, flash, request)) { // (!pb.upload(periodicoInstance)) {
            // com a segunda opção, o sistema se perde e vai para publication controller no teste Add a new article twitting it
            handleSavingError(periodicoInstance, 'periodico.filesaving.failure')
            return
        }
        if (!periodicoInstance.save(flush: true)) {
            handleSavingError(periodicoInstance, 'periodico.saving.failure')
            return
        }

        //#if($facebook)
        // def user = Member.findByUsername(SecurityUtils.subject?.principal)
        //PublicationController.sendPostFacebook(user, periodicoInstance.toString())
        //#end

        flash.message = message(code: 'default.created.message', args: [message(code: 'periodico.label', default: 'Periodico'), periodicoInstance.id])
        redirect(action: "show", id: periodicoInstance.id)
    }

    def handleSavingError(Periodico periodicoInstance, String message) {
        periodicoInstance.discardMembers()
        flash.message = message
        render(view: "create", model: [periodicoInstance: periodicoInstance])
    }

    def show() {
        accessArticle()
    }

    def edit() {
        accessArticle()
    }


    def accessArticle() {
        def periodicoInstance = Periodico.get(params.id)
        if (!periodicoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'periodico.label', default: 'Periodico'), params.id])
            redirect(action: "list")
            return
        }
        [periodicoInstance: periodicoInstance]
    }

    def update() {
        def periodicoInstance = Periodico.get(params.id)
        if (!periodicoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'periodico.label', default: 'Periodico'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (periodicoInstance.version > version) {
                periodicoInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'periodico.label', default: 'Periodico')] as Object[],
                        "Another user has updated this Periodico while you were editing")
                render(view: "edit", model: [periodicoInstance: periodicoInstance])
                return
            }
        }

        periodicoInstance.properties = params

        if (!periodicoInstance.save(flush: true)) {
            upload(periodicoInstance)
            render(view: "edit", model: [periodicoInstance: periodicoInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'periodico.label', default: 'Periodico'), periodicoInstance.id])
        redirect(action: "show", id: periodicoInstance.id)
    }

    def delete() {
        def periodicoInstance = Periodico.get(params.id)
        if (!periodicoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'periodico.label', default: 'Periodico'), params.id])
            redirect(action: "list")
            return
        }

        try {
            periodicoInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'periodico.label', default: 'Periodico'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'periodico.label', default: 'Periodico'), params.id])
            redirect(action: "show", id: params.id)
        }
    }

    private checkPeriodicoInstance(Map flash, Map params, Periodico periodicoInstance) {
        if (!periodicoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'periodico.label', default: 'Periodico'), params.id])
            redirect(action: "list")
            return
        }

        [periodicoInstance: periodicoInstance]
    }
    //#if($XMLImp && $Journal)
    Closure returnWithMessage = {
        String msg ->

            redirect(action: "list")
            flash.message = message(code: msg)
    }

    def uploadXMLPeriodico() {
        String flashMessage = 'The non existent articles were successfully imported'

        if (XMLService.Import(saveJournals, returnWithMessage, flashMessage, request))
            return
    }

    Closure saveJournals = {
        Node xmlFile ->

            Node artigosPublicados = (Node) ((Node) xmlFile.children()[1]).children()[1]
            List<Object> artigosPublicadosChildren = artigosPublicados.children()

            for (int i = 0; i < artigosPublicadosChildren.size(); ++i)
                saveNewJournal(artigosPublicadosChildren, i)
    }

    private void saveNewJournal(List artigosPublicadosChildren, int i) {
        List<Object> firstArticle = ((Node) artigosPublicadosChildren[i]).children()
        Node dadosBasicos = (Node) firstArticle[0]
        Node detalhamentoArtigo = (Node) firstArticle[1]
        Periodico newJournal = new Periodico()
        getJournalTitle(dadosBasicos, newJournal)

        if (Publication.findByTitle(newJournal.title) == null) {
            getJournalYear(dadosBasicos, newJournal)
            getJournalVolume(detalhamentoArtigo, newJournal)
            getJournalNumber(detalhamentoArtigo, newJournal)
            getJournalNumberOfPages(detalhamentoArtigo, newJournal)
            getPeriodicTitle(detalhamentoArtigo, newJournal)
            newJournal.file = 'emptyfile' + i.toString() //files are not available on lattes
            newJournal.save(flush: false)
        }
    }

    private void getJournalTitle(Node dadosBasicos, Periodico newJournal) {
        newJournal.title = XMLService.getAttributeValueFromNode(dadosBasicos, "TITULO-DO-ARTIGO")
    }

    private void getPeriodicTitle(Node detalhamentoArtigo, Periodico newJournal) {
        newJournal.journal = XMLService.getAttributeValueFromNode(detalhamentoArtigo, "TITULO-DO-PERIODICO-OU-REVISTA")
    }

    private void getJournalNumberOfPages(Node detalhamentoArtigo, Periodico newJournal) {
        String tryingToParse = XMLService.getAttributeValueFromNode(detalhamentoArtigo, "PAGINA-FINAL")
        String tryingToParse2 = XMLService.getAttributeValueFromNode(detalhamentoArtigo, "PAGINA-INICIAL")
        if (tryingToParse.isInteger() && tryingToParse2.isInteger())
            newJournal.pages = tryingToParse.toInteger() - tryingToParse2.toInteger() + 1
        else
            newJournal.pages = 1    //if not parsed successfully, least value possible
    }

    private void getJournalNumber(Node detalhamentoArtigo, Periodico newJournal) {
        String tryingToParse = XMLService.getAttributeValueFromNode(detalhamentoArtigo, "FASCICULO")
        if (tryingToParse.isInteger())
            newJournal.number = tryingToParse.toInteger()
        else
            newJournal.number = 1   //if not parsed successfully, least value possible
    }

    private void getJournalVolume(Node detalhamentoArtigo, Periodico newJournal) {
        String tryingToParse = XMLService.getAttributeValueFromNode(detalhamentoArtigo, "VOLUME")
        if (tryingToParse.isInteger())
            newJournal.volume = tryingToParse.toInteger()
        else
            newJournal.volume = 1   //if not parsed successfully, least value possible
    }

    private void getJournalYear(Node dadosBasicos, Periodico newJournal) {
        newJournal.publicationDate = new Date()
        String tryingToParse = XMLService.getAttributeValueFromNode(dadosBasicos, "ANO-DO-ARTIGO")
        if (tryingToParse.isInteger())
            newJournal.publicationDate.set(year: tryingToParse.toInteger() - 1900)
    }
    //#end

    //#if( $Facebook )
    def share(){
        def periodicoInstance = Periodico.get(params.id)
        System.out.println("1");
        def user = Member.findByUsername(SecurityUtils.subject?.principal)
        System.out.println("2");
        if(user.getFacebook_id()!= null && user.getFacebook_id().compareTo("") != 0)
            PublicationController.sendPostFacebook(user, periodicoInstance.toString())
        System.out.println("3");
        redirect(action: "show", id: params.id)
    }
    //#end
}
