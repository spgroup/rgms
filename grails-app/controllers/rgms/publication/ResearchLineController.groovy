package rgms.publication

import org.springframework.dao.DataIntegrityViolationException

class ResearchLineController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [researchLineInstanceList: ResearchLine.list(params), researchLineInstanceTotal: ResearchLine.count()]
    }

    def create() {
        [researchLineInstance: new ResearchLine(params)]
    }

    def save() {
        def researchLineInstance = new ResearchLine(params)
        saveResearchLine("create", researchLineInstance, "created")
    }

    def show() {
        tryFindInstance(params)
    }

    def edit() {
        tryFindInstance(params)
    }

    def update() {
        def researchLineInstance = getInstance(params)
        if(!researchLineInstance)
           return 

		if (!checkInstanceVersion(params, researchLineInstance))
			return
        researchLineInstance.properties = params
        saveResearchLine("edit", researchLineInstance, "updated")
    }

    def delete() {
        def researchLineInstance = ResearchLine.get(params.id)        
        try {
            if (!editPublications(researchLineInstance))
               return
            researchLineInstance.delete(flush: true)
            createMessage(action: "list")
        }
        catch (DataIntegrityViolationException) {
            createMessage(action: "show", id: params.id)
        }
    }

    def checkInstanceVersion(params, researchLineInstance){
        if (params.version) {
            def version = params.version.toLong()
            if (researchLineInstance.version > version) {
                researchLineInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'researchLine.label', default: 'ResearchLine')] as Object[],
                        "Another user has updated this ResearchLine while you were editing")
                render(view: "edit", model: [researchLineInstance: researchLineInstance])
                return false
            }
        }
        return true
    }

    def editPublications(researchLineInstance){
        for(p in researchLineInstance?.publications)
        {
            p.researchLine = null;
            if(!p.save(flush:true))
            {
                render(view: "edit", model: [researchLineInstance: researchLineInstance])
                return false
            }
        }
        true
    }

    def createMessage(params){
        flash.message = message(code: 'default.deleted.message', args: [message(code: 'researchLine.label', default: 'ResearchLine'), params.id])
        redirect(params)
    }

    def saveResearchLine(view, researchLineInstance, action)
    {
        if (!researchLineInstance.save(flush: true)) {
            render(view: view, model: [researchLineInstance: researchLineInstance])
        }
        else {
            flash.message = message(code: 'default.' + action + '.message', args: [message(code: 'researchLine.label', default: 'ResearchLine'), researchLineInstance.id])
            redirect(action: "show", id: researchLineInstance.id)
        }
    }

    def tryFindInstance(params){
        def researchLineInstance = getInstance(params)

        if (researchLineInstance)
            [researchLineInstance: getInstance(params)]
    }

    def getInstance(params){
        def researchLineInstance = ResearchLine.get(params.id)
        if (!researchLineInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'researchLine.label', default: 'ResearchLine'), params.id])
            redirect(action: "list")
            return
        }

        researchLineInstance
    }
}
