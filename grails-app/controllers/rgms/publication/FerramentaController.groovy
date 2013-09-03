package rgms.publication

import org.springframework.dao.DataIntegrityViolationException
//#if($upXMLFerramenta)
import rgms.XMLService
//#end

class FerramentaController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    FerramentaControllerAuxiliar aux = new FerramentaControllerAuxiliar()
    AuxiliarController auxController = new AuxiliarController()

    def index() {
        redirectToList()
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [ferramentaInstanceList: Ferramenta.list(params), ferramentaInstanceTotal: Ferramenta.count()]
    }

    def create() {
        def ferramentaInstance = new Ferramenta(params)
        //#if($publicationContext)
        PublicationController.addAuthor(ferramentaInstance)
        //#end
        [ferramentaInstance: ferramentaInstance]
    }

    def save() {
        def ferramentaInstance = new Ferramenta(params)

        if (!PublicationController.newUpload(ferramentaInstance, flash, request) || !ferramentaInstance.save(flush: true)) {
            // com a segunda opção, o sistema se perde e vai para publication controller no teste Add a new article twitting it
            render(controller: "ferramenta", view: "create", model: [ferramentaInstance: ferramentaInstance])
            return
        }

        flash.message = messageGenerator('default.created.message',ferramentaInstance.id)
        redirectToShow(ferramentaInstance.id)
    }

    def show() {
        redirectAndReturnIfNotInstance { ferramentaInstance ->
            [ferramentaInstance: ferramentaInstance]
        }
    }

    def edit() {
        redirectAndReturnIfNotInstance { ferramentaInstance ->
            [ferramentaInstance: ferramentaInstance]
        }
    }

    def update() {
        redirectAndReturnIfNotInstance { ferramentaInstance ->
            def editErro = false
            if (params.version) {
                editErro = aux.checkVersionFailed(params.version.toLong(), ferramentaInstance)
            }

            ferramentaInstance.properties = params

            if (editErro || !ferramentaInstance.save(flush: true)) {
                render(view: "edit", model: [ferramentaInstance: ferramentaInstance])
                return
            }

            flash.message = messageGenerator('default.updated.message', ferramentaInstance.id)
            redirectToShow(ferramentaInstance.id)
        }
    }

    def delete() {
        redirectAndReturnIfNotInstance { ferramentaInstance ->
            auxController.delete(params.id, ferramentaInstance, 'ferramenta.label', 'Ferramenta')
        }
    }

	def messageGenerator (String code, def id){
		return message(code: code, args: [message(code: 'ferramenta.label', default: 'Ferramenta'), id])
	}

//#if($upXMLFerramenta)
    def uploadXMLFerramenta()
    {
        String flashMessage = 'The non existent dissertations were successfully imported'

        if (!XMLService.Import(saveTools, returnWithMessage, flashMessage, request))
            return
    }

    Closure returnWithMessage = {
        String msg ->
            redirectToList()
    }

    Closure saveTools = {
        Node xmlFile ->
            Node producaoTecnica = (Node)xmlFile.children()[2]

            for (Node currentNode : producaoTecnica.children()){
                if (currentNode.name().equals("SOFTWARE")){
                    saveNewTool(currentNode)
                }
            }
    }

    private void saveNewTool(Node currentNode){
        Node dadosBasicos = (Node) currentNode.children()[0]
        Node detalhamentoDoSoftware = (Node) currentNode.children()[1]
        Node informacoesAdicionais = XMLService.getNodeFromNode(currentNode, "INFORMACOES-ADICIONAIS")

        Ferramenta newTool = new Ferramenta()

        newTool.publicationDate = new Date()

        String tryingToParse = XMLService.getAttributeValueFromNode(dadosBasicos, "ANO")
        if (tryingToParse.isInteger())
            newTool.publicationDate.set(year: tryingToParse.toInteger())

        newTool.file = 'no File'
        newTool.website = 'no Website'

        newTool.title = XMLService.getAttributeValueFromNode(dadosBasicos, "TITULO-DO-SOFTWARE")

        String descricao =   XMLService.getAttributeValueFromNode(informacoesAdicionais, "DESCRICAO-INFORMACOES-ADICIONAIS")

        newTool.description = "País: "+XMLService.getAttributeValueFromNode(dadosBasicos, "PAIS") +", Ambiente: " + XMLService.getAttributeValueFromNode(detalhamentoDoSoftware, "AMBIENTE") + (descricao.equals("") ? "" : ", Informacoes adicionais: "+descricao)
        newTool.save(flush: false)
    }
//#end

    private def redirectAndReturnIfNotInstance (Closure c){
        def ferramentaInstance = Ferramenta.get(params.id)
        if (!ferramentaInstance) {
			flash.message = messageGenerator ('default.not.found.message', params.id)
            redirectToList()
            return
        }

        c.call ferramentaInstance
    }

    private def redirectToShow(Long id){
        redirect(controller: "ferramenta", action: "show", id: id)
    }

    private def redirectToList(){
        redirect(action: "list", params: params)
    }
}
