package rgms.publication

import org.apache.shiro.SecurityUtils
import org.springframework.dao.DataIntegrityViolationException
import rgms.member.Member
import rgms.publication.Ferramenta;

class FerramentaController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [ferramentaInstanceList: Ferramenta.list(params), ferramentaInstanceTotal: Ferramenta.count()]
    }

    def create() {
        def ferramentaInstance = new Ferramenta(params)
        //#if($publicationContext)
        def publcContextOn = grailsApplication.getConfig().getProperty("publicationContext");
        if(publcContextOn){
            if(SecurityUtils.subject?.principal != null){
                PublicationController.addAuthor(ferramentaInstance)
            }
        }
        //#end
        [ferramentaInstance: ferramentaInstance]
    }

    def save() {
        def ferramentaInstance = new Ferramenta(params)
		
		PublicationController pb = new PublicationController()
		
        if (!pb.upload(ferramentaInstance) || !ferramentaInstance.save(flush: true)) {
            render(view: "create", model: [ferramentaInstance: ferramentaInstance])
            return
        }

		flash.message = messageGenerator('default.created.message',ferramentaInstance.id)
        redirect(action: "show", id: ferramentaInstance.id)
    }

    def show() {
        def ferramentaInstance = Ferramenta.get(params.id)

        if (!ferramentaInstance) {
			flash.message = messageGenerator('default.not.found.message', params.id)
            redirect(action: "list")
            return
        }

        [ferramentaInstance: ferramentaInstance]
    }

    def edit() {
        def ferramentaInstance = Ferramenta.get(params.id)
		
        if (!ferramentaInstance) {
            flash.message = messageGenerator('default.not.found.message', params.id)
            redirect(action: "list")
            return
        }

        [ferramentaInstance: ferramentaInstance]
    }

    def update() {
        def ferramentaInstance = Ferramenta.get(params.id)
        if (!ferramentaInstance) {
            flash.message = messageGenerator('default.not.found.message', params.id)
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (ferramentaInstance.version > version) {
                ferramentaInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'ferramenta.label', default: 'Ferramenta')] as Object[],
                          "Another user has updated this Ferramenta while you were editing")
                render(view: "edit", model: [ferramentaInstance: ferramentaInstance])
                return
            }
        }

        ferramentaInstance.properties = params

        if (!ferramentaInstance.save(flush: true)) {
            render(view: "edit", model: [ferramentaInstance: ferramentaInstance])
            return
        }

		flash.message = messageGenerator('default.updated.message', ferramentaInstance.id)
        redirect(action: "show", id: ferramentaInstance.id)
    }

    def delete() {
        def ferramentaInstance = Ferramenta.get(params.id)
        if (!ferramentaInstance) {
			flash.message = messageGenerator ('default.not.found.message', params.id)
            redirect(action: "list")
            return
        }

        try {
            ferramentaInstance.delete(flush: true)
			flash.message = messageGenerator ('default.deleted.message', params.id)
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = messageGenerator ('default.not.deleted.message'+' Erro: '+e.message, params.id)
            redirect(action: "show", id: params.id)
        }
    }
	
	def messageGenerator (String code, def id)
	{
		return message(code: code, args: [message(code: 'ferramenta.label', default: 'Ferramenta'), id])
	}
}
