package rgms.publication

import org.springframework.dao.DataIntegrityViolationException

import rgms.publication.Dissertacao;


class DissertacaoController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [dissertacaoInstanceList: Dissertacao.list(params), dissertacaoInstanceTotal: Dissertacao.count()]
    }

    def create() {
        [dissertacaoInstance: new Dissertacao(params)]
    }

    def save() {
        def dissertacaoInstance = new Dissertacao(params)
		
		PublicationController pb = new PublicationController()

        if (!pb.upload(dissertacaoInstance) || !dissertacaoInstance.save(flush: true)) {
            render(view: "create", model: [dissertacaoInstance: dissertacaoInstance])
            return
        }
		
		flash.message = message(code: 'default.created.message', args: [message(code: 'dissertacao.label', default: 'Dissertacao'), dissertacaoInstance.id])
        redirect(action: "show", id: dissertacaoInstance.id)
    }

    def show() {
        def dissertacaoInstance = Dissertacao.get(params.id)
		

        if (!dissertacaoInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'dissertacao.label', default: 'Dissertacao'), params.id])
            redirect(action: "list")
            return
        }

        [dissertacaoInstance: dissertacaoInstance]
    }

    def edit() {
        def dissertacaoInstance = Dissertacao.get(params.id)
        if (!dissertacaoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'dissertacao.label', default: 'Dissertacao'), params.id])
            redirect(action: "list")
            return
        }

        [dissertacaoInstance: dissertacaoInstance]
    }

    def update() {
        def dissertacaoInstance = Dissertacao.get(params.id)
        if (!dissertacaoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'dissertacao.label', default: 'Dissertacao'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (dissertacaoInstance.version > version) {
                dissertacaoInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'dissertacao.label', default: 'Dissertacao')] as Object[],
                          "Another user has updated this Dissertacao while you were editing")
                render(view: "edit", model: [dissertacaoInstance: dissertacaoInstance])
                return
            }
        }

        dissertacaoInstance.properties = params

        if (!dissertacaoInstance.save(flush: true)) {
            render(view: "edit", model: [dissertacaoInstance: dissertacaoInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'dissertacao.label', default: 'Dissertacao'), dissertacaoInstance.id])
        redirect(action: "show", id: dissertacaoInstance.id)
    }

    def delete() {
        def dissertacaoInstance = Dissertacao.get(params.id)
        if (!dissertacaoInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'dissertacao.label', default: 'Dissertacao'), params.id])
            redirect(action: "list")
            return
        }

        try {
            dissertacaoInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'dissertacao.label', default: 'Dissertacao'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'dissertacao.label', default: 'Dissertacao'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
	static scaffold = true 
}
