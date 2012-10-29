package rgms.publication

import org.springframework.dao.DataIntegrityViolationException

import rgms.publication.ResearchLine;

class ResearchLineController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(max ?: 10, 100)
        [researchLineInstanceList: ResearchLine.list(params), researchLineInstanceTotal: ResearchLine.count()]
    }

    def create() {
        [researchLineInstance: new ResearchLine(params)]
    }

    def save() {
        def researchLineInstance = new ResearchLine(params)
        if (!researchLineInstance.save(flush: true)) {
            render(view: "create", model: [researchLineInstance: researchLineInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'researchLine.label', default: 'ResearchLine'), researchLineInstance.id])
        redirect(action: "show", id: researchLineInstance.id)
    }

    def show() {
        def researchLineInstance = ResearchLine.get(params.id)
        if (!researchLineInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'researchLine.label', default: 'ResearchLine'), params.id])
            redirect(action: "list")
            return
        }

        [researchLineInstance: researchLineInstance]
    }

    def edit() {
        def researchLineInstance = ResearchLine.get(params.id)
        if (!researchLineInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'researchLine.label', default: 'ResearchLine'), params.id])
            redirect(action: "list")
            return
        }

        [researchLineInstance: researchLineInstance]
    }

    def update() {
        def researchLineInstance = ResearchLine.get(params.id)
        if (!researchLineInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'researchLine.label', default: 'ResearchLine'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (researchLineInstance.version > version) {
                researchLineInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                    [message(code: 'researchLine.label', default: 'ResearchLine')] as Object[],
                          "Another user has updated this ResearchLine while you were editing")
                render(view: "edit", model: [researchLineInstance: researchLineInstance])
                return
            }
        }
        
        researchLineInstance.properties = params
        
        if (!researchLineInstance.save(flush: true)) {
            render(view: "edit", model: [researchLineInstance: researchLineInstance])
            return
        }
        flash.message = message(code: 'default.updated.message', args: [message(code: 'researchLine.label', default: 'ResearchLine'), researchLineInstance.id])
        redirect(action: "show", id: researchLineInstance.id)
    }

    def delete() {
        def researchLineInstance = ResearchLine.get(params.id)        

        try {
            for(p in researchLineInstance?.publications)
            {           
               // def pub = Publication.get(p.id);
                p.researchLine = null;                
                if(!p.save(flush:true))
                {
                    render(view: "edit", model: [researchLineInstance: researchLineInstance])
                    return
                }                
            }       
            researchLineInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'researchLine.label', default: 'ResearchLine'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'researchLine.label', default: 'ResearchLine'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
