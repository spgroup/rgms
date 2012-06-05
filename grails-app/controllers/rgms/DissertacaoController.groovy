package rgms

import org.springframework.dao.DataIntegrityViolationException

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
		
		//def memberInstance = Member.findAllById(params.member)
		//dissertacaoInstance.author = memberInstance
		//dissertacaoInstance.author = dissertacaoInstance.author.replace(']', '').replace('[', '')
		//def member = Member.findAllById(params.member)
		//println member.name
		//dissertacaoInstance.author = member.name.replace(']','').replace('[','').toString()
		params.member.each{dissertacaoInstance.author = dissertacaoInstance.author + Member.findAllById(params.member)}
		println dissertacaoInstance.author
		//println Member.findAllById(params.member)
		dissertacaoInstance.author = dissertacaoInstance.author.replace(']', '').replace('[', ',')
		
		
		//dissertacaoInstance.author
		PublicacaoController pb = new PublicacaoController()
		/**Velocity**/
		#if($bibtex)
			String bibTex = pb.bibTex(dissertacaoInstance)
			dissertacaoInstance.bibTex = bibTex
		#end
		/**Velocity**/
        if (!dissertacaoInstance.save(flush: true)) {
            render(view: "create", model: [dissertacaoInstance: dissertacaoInstance])
            return
        }
		pb.upload(dissertacaoInstance)
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
