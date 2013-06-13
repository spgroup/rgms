package rgms.visit

import org.springframework.dao.DataIntegrityViolationException

import rgms.member.ResearchGroup
import rgms.tool.TwitterTool

class VisitController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [visitInstanceList: Visit.list(params), visitInstanceTotal: Visit.count()]
    }

    def create() {
        [visitInstance: new Visit(params)]
    }

    def save() {
		def visitInstance
		String nome  = params.get("name")
		
		//#if( $twitter )
		  String twitterAccessToken = params.get("twitterAccessToken");
		  String twitterAccessSecret = params.get("twitterAccessSecret");
		//#end
		
		if (Visitor.findByName(nome) == null){
			Visitor novoVisitante
			
			//#if( $twitter )
			   novoVisitante = new Visitor(name:nome,twitterAccessToken:twitterAccessToken,twitterAccessSecret:twitterAccessSecret)
		    //#else
			   novoVisitante = new Visitor(name:nome)
			//#end
			
			novoVisitante.save()
		
			Date dataInicio = params.get("dataInicio")
			Date dataFim = params.get("dataFim")
			String researchGroupName = params.get("nameGroup");
			ResearchGroup researchGroup = ResearchGroup.findByName(researchGroupName);
			
			visitInstance = new Visit(dataInicio: dataInicio,dataFim: dataFim,visitor: novoVisitante,researchGroup:researchGroup)
			
			//#if( $twitter )
		    	final def text = "Nova visita agendada, inicio dia " + dataInicio + " até dia  " + dataFim + " #RMGS"
	                  def textTitulo = "Visita " + nome + ":" + dataInicio + "-" + dataFim; 
			    if (twitterAccessToken) {
				    	TwitterTool.sendGTwitter(textTitulo,twitterAccessToken, twitterAccessSecret, text)
			     }
		    //#end
		
		}else{
			params.visitor = Visitor.findByName(nome)
			visitInstance = new Visit(params)
		}
		
		if (!visitInstance.save(flush: true)) {
			render(view: "create", model: [visitInstance: visitInstance])
			return
		}

		flash.message = message(code: 'default.created.message', args: [message(code: 'visit.label', default: 'Visit'), visitInstance.id])
		redirect(action: "show", id: visitInstance.id)
//        def visitInstance = new Visit(params)
//        if (!visitInstance.save(flush: true)) {
//            render(view: "create", model: [visitInstance: visitInstance])
//            return
//        }
//
//        flash.message = message(code: 'default.created.message', args: [message(code: 'visit.label', default: 'Visit'), visitInstance.id])
//        redirect(action: "show", id: visitInstance.id)
    }

    def show(Long id) {
        def visitInstance = Visit.get(id)
        if (!visitInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'visit.label', default: 'Visit'), id])
            redirect(action: "list")
            return
        }

        [visitInstance: visitInstance]
    }

    def edit(Long id) {
        def visitInstance = Visit.get(id)
        if (!visitInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'visit.label', default: 'Visit'), id])
            redirect(action: "list")
            return
        }

        [visitInstance: visitInstance]
    }

    def update(Long id, Long version) {
        def visitInstance = Visit.get(id)
        if (!visitInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'visit.label', default: 'Visit'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (visitInstance.version > version) {
                visitInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'visit.label', default: 'Visit')] as Object[],
                          "Another user has updated this Visit while you were editing")
                render(view: "edit", model: [visitInstance: visitInstance])
                return
            }
        }

        visitInstance.properties = params

        if (!visitInstance.save(flush: true)) {
            render(view: "edit", model: [visitInstance: visitInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'visit.label', default: 'Visit'), visitInstance.id])
        redirect(action: "show", id: visitInstance.id)
    }

    def delete(Long id) {
        def visitInstance = Visit.get(id)
        if (!visitInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'visit.label', default: 'Visit'), id])
            redirect(action: "list")
            return
        }

        try {
            visitInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'visit.label', default: 'Visit'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'visit.label', default: 'Visit'), id])
            redirect(action: "show", id: id)
        }
    }
}
