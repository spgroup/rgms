package rgms.member

import org.springframework.dao.DataIntegrityViolationException

class OrientationController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [orientationInstanceList: Orientation.list(params), orientationInstanceTotal: Orientation.count()]
    }

    def create() {
        [orientationInstance: new Orientation(params)]
    }

    def save() {
        def orientationInstance = new Orientation(params)

        if (orientationInstance.orientador.id == orientationInstance.orientando.id)
        {
            flash.message = message(code: 'orientation.same.member', args: [message(code: 'orientation.label', default: 'Orientation'), orientationInstance.id])
            render(view: "create", model: [orientationInstance: orientationInstance])
            return
        }
        else if (!orientationInstance.save(flush: true)) {
            render(view: "create", model: [orientationInstance: orientationInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'orientation.label', default: 'Orientation'), orientationInstance.id])
        redirect(action: "show", id: orientationInstance.id)
    }

    def show(Long id) {
        def orientationInstance = Orientation.get(id)
        if (!orientationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'orientation.label', default: 'Orientation'), id])
            redirect(action: "list")
            return
        }

        [orientationInstance: orientationInstance]
    }

    def edit(Long id) {
        def orientationInstance = Orientation.get(id)
        if (!orientationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'orientation.label', default: 'Orientation'), id])
            redirect(action: "list")
            return
        }

        [orientationInstance: orientationInstance]
    }

    def update(Long id, Long version) {
        def orientationInstance = Orientation.get(id)
        if (!orientationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'orientation.label', default: 'Orientation'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (orientationInstance.version > version) {
                orientationInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'orientation.label', default: 'Orientation')] as Object[],
                          "Another user has updated this Orientation while you were editing")
                render(view: "edit", model: [orientationInstance: orientationInstance])
                return
            }
        }

        if (orientationInstance.orientador.id == orientationInstance.orientando.id)
        {
            flash.message = message(code: 'orientation.same.member', args: [message(code: 'orientation.label', default: 'Orientation'), orientationInstance.id])
            render(view: "edit", model: [orientationInstance: orientationInstance])
            return
        }

        orientationInstance.properties = params

        if (!orientationInstance.save(flush: true)) {
            render(view: "edit", model: [orientationInstance: orientationInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'orientation.label', default: 'Orientation'), orientationInstance.id])
        redirect(action: "show", id: orientationInstance.id)
    }

    def delete(Long id) {
        def orientationInstance = Orientation.get(id)
        if (!orientationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'orientation.label', default: 'Orientation'), id])
            redirect(action: "list")
            return
        }

        try {
            orientationInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'orientation.label', default: 'Orientation'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'orientation.label', default: 'Orientation'), id])
            redirect(action: "show", id: id)
        }
    }

    def getOrientationsByMember(int memberId)
    {
        return Orientation.findByOrientador{t -> t.orientador.id == memberId}
    }

    def getMemberOrientations(int memberId)
    {
        return Orientation.findByOrientando{t -> t.orientando.id == memberId}
    }
}
