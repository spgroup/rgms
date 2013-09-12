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
        //#if($contextualInformation)
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
