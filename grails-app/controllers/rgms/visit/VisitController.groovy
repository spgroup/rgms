package rgms.visit

import org.springframework.dao.DataIntegrityViolationException

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

    def createVisitor() {
        def visitor = new Visitor()
        visitor.name = params.nameVisitor
        visitor = visitor.save(flush: true)
        return visitor
    }

    def save() {
        def visitor = getVisitor((String)params.nameVisitor)

        def visitInstance = new Visit(params)

        saveVisit(visitInstance, visitor, "create", "created")
    }

    def show(Long id) {
        showOrEdit(id)
    }

    def edit(Long id) {
        showOrEdit(id)
    }

    def update(Long id, Long version) {
        def visitInstance = getVisitInstance(id)
        if (!visitInstance) {
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

        def visitor = getVisitor((String)params.nameVisitor)

        saveVisit(visitInstance, visitor, "edit", "updated")
    }

    def delete(Long id) {
        def visitInstance = getVisitInstance(id)
        if (!visitInstance) {
            return
        }

        try {
            visitInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'visit.label', default: 'Visit'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'visit.label', default: 'Visit'), id]) + " " + e.getMessage()
            redirect(action: "show", id: id)
        }
    }

    def showOrEdit(Long id) {
        def visitInstance = getVisitInstance(id)
        if(!visitInstance) {
            return
        }
        [visitInstance: visitInstance]
    }

    def getVisitInstance(Long id) {
        def visitInstance = Visit.get(id)
        if (!visitInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'visit.label', default: 'Visit'), id])
            redirect(action: "list")
        }
        return visitInstance
    }

    def getVisitor(String nameVisitor) {
        def visitor = Visitor.findByName(nameVisitor)
        if(!visitor) {
            visitor = createVisitor()
        }
        return visitor
    }

    def saveVisit(Visit visitInstance, Visitor visitor, String view, String typeMessage) {
        visitInstance.visitor = visitor

        if (!visitInstance.save(flush: true)) {
            render(view: view, model: [visitInstance: visitInstance])
            return
        }

        flash.message = message(code: 'default.' + typeMessage + '.message', args: [message(code: 'visit.label', default: 'Visit'), visitInstance.id])
        redirect(action: "show", id: visitInstance.id)
    }
}