package rgms

import org.springframework.dao.DataIntegrityViolationException
import rgms.PublicacaoController

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
		//params.member.each{periodicoInstance.author = periodicoInstance.author + Member.findAllById(it)}
		for(i in periodicoInstance.members){
			periodicoInstance.author =  i.name + "," + periodicoInstance.author
		}
		
		periodicoInstance.author = periodicoInstance.author.replace(']', '').replace('[', ',')
		PublicacaoController pb = new PublicacaoController()
		#if($bibtex)
		periodicoInstance.bibTex = periodicoInstance.setBib()
		#end
		
		if (periodicoInstance.save(flush: true)) {
			pb.upload(periodicoInstance)
			render(view: "show", model: [periodicoInstance: periodicoInstance])
			return
		}
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
}
