package rgms.researchProject

import org.springframework.dao.DataIntegrityViolationException

class ProjectMemberController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [projectMemberInstanceList: ProjectMember.list(params), projectMemberInstanceTotal: ProjectMember.count()]
    }

    def create() {
        [projectMemberInstance: new ProjectMember(params)]
    }

    def save() {
        def projectMemberInstance = new ProjectMember(params)
        if (!projectMemberInstance.save(flush: true)) {
            render(view: "create", model: [projectMemberInstance: projectMemberInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'projectMember.label', default: 'ProjectMember'), projectMemberInstance.id])
        redirect(action: "show", id: projectMemberInstance.id)
    }

    def show(Long id) {
        def projectMemberInstance = ProjectMember.get(id)
        if (!projectMemberInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'projectMember.label', default: 'ProjectMember'), id])
            redirect(action: "list")
            return
        }

        [projectMemberInstance: projectMemberInstance]
    }

    def edit(Long id) {
        def projectMemberInstance = ProjectMember.get(id)
        if (!projectMemberInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'projectMember.label', default: 'ProjectMember'), id])
            redirect(action: "list")
            return
        }

        [projectMemberInstance: projectMemberInstance]
    }

    def update(Long id, Long version) {
        def projectMemberInstance = ProjectMember.get(id)
        if (!projectMemberInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'projectMember.label', default: 'ProjectMember'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (projectMemberInstance.version > version) {
                projectMemberInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'projectMember.label', default: 'ProjectMember')] as Object[],
                        "Another user has updated this ProjectMember while you were editing")
                render(view: "edit", model: [projectMemberInstance: projectMemberInstance])
                return
            }
        }

        projectMemberInstance.properties = params

        if (!projectMemberInstance.save(flush: true)) {
            render(view: "edit", model: [projectMemberInstance: projectMemberInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'projectMember.label', default: 'ProjectMember'), projectMemberInstance.id])
        redirect(action: "show", id: projectMemberInstance.id)
    }

    def delete(Long id) {
        def projectMemberInstance = ProjectMember.get(id)
        if (!projectMemberInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'projectMember.label', default: 'ProjectMember'), id])
            redirect(action: "list")
            return
        }

        try {
            projectMemberInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'projectMember.label', default: 'ProjectMember'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'projectMember.label', default: 'ProjectMember'), id])
            redirect(action: "show", id: id)
        }
    }
}
