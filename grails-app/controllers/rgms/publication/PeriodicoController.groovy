package rgms.publication

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.web.multipart.MultipartHttpServletRequest
import org.springframework.web.multipart.commons.CommonsMultipartFile
import rgms.publication.Periodico;

class PeriodicoController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [periodicoInstanceList: Periodico.list(params), periodicoInstanceTotal: Periodico.count()]
    }

    def create() {
        [periodicoInstance: new Periodico(params)]
    }

    def save () {
        def periodicoInstance = new Periodico(params)
        PublicationController pb = new PublicationController()

        if (!pb.upload(periodicoInstance) || !periodicoInstance.save(flush: true)) {
            render(view: "create", model: [periodicoInstance: periodicoInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'periodico.label', default: 'Periodico'), periodicoInstance.id])
        redirect(action: "show", id: periodicoInstance.id)
    }

    def show() {
        def periodicoInstance = Periodico.get(params.id)
        if (!periodicoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'periodico.label', default: 'Periodico'), params.id])
            redirect(action: "list")
            return
        }

        [periodicoInstance: periodicoInstance]
    }

    def edit() {
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

    def uploadXML()
    {
        Periodico newJournal = new Periodico(params)
        Node xmlFile
        try
        {
            xmlFile = parseReceivedFile()
            fillJournalInfo(newJournal, xmlFile)
        } catch (all) {
            //SAXParseException se o arquivo não for XML
            //NullPointerException se a estrutura do XML está errada (cast em Nó nulo)
            render(view: "create", model: [periodicoInstance: newJournal])
            flash.message = 'Insira um arquivo XML válido'
            return
        }
        render(view: "create", model: [periodicoInstance: newJournal])
        flash.message = 'Dados do XML extraídos. Complete as informações restantes'
    }

    private void fillJournalInfo(Periodico newJournal, Node xmlFile) {
        Node artigosPublicados = (Node) ((Node)xmlFile.children()[1]).children()[1]
        List<Object> firstArticle = ((Node)artigosPublicados.children()[0]).children()
        Node dadosBasicos = (Node) firstArticle[0]
        Node detalhamentoArtigo = (Node) firstArticle[1]
        newJournal.title = getAttributeValueFromNode(dadosBasicos, "TITULO-DO-ARTIGO")
        newJournal.publicationDate = new Date()
        newJournal.publicationDate.set(year: getAttributeValueFromNode(dadosBasicos, "ANO-DO-ARTIGO").toInteger()- 1900)
        newJournal.volume = getAttributeValueFromNode(detalhamentoArtigo, "VOLUME").toInteger()
        newJournal.number = getAttributeValueFromNode(detalhamentoArtigo, "FASCICULO").toInteger()
        newJournal.pages = getAttributeValueFromNode(detalhamentoArtigo, "PAGINA-FINAL").toInteger() -
                getAttributeValueFromNode(detalhamentoArtigo, "PAGINA-INICIAL").toInteger() + 1
        newJournal.journal = getAttributeValueFromNode(detalhamentoArtigo, "TITULO-DO-PERIODICO-OU-REVISTA")
        def a = 2

    }

    private Node parseReceivedFile() {
        MultipartHttpServletRequest mpr = (MultipartHttpServletRequest) request;
        CommonsMultipartFile f = (CommonsMultipartFile) mpr.getFile("file");
        File file = new File("testexml.xml");
        f.transferTo(file)
        def records = new XmlParser()

        records.parse(file)
    }
    String getAttributeValueFromNode(Node n, String attribute)
    {
        n.attribute attribute
    }
}
