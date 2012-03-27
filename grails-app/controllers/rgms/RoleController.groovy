package rgms

import org.springframework.dao.DataIntegrityViolationException

class RoleController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [shiroRoleInstanceList: Role.list(params), shiroRoleInstanceTotal: Role.count()]
    }

    def create = {
        [shiroRoleInstance: new Role(params)]
    }

    def save = {
        def shiroRoleInstance = new Role(params)
        if (!shiroRoleInstance.save(flush: true)) {
            render(view: "create", model: [shiroRoleInstance: shiroRoleInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'shiroRole.label', default: 'Role'), shiroRoleInstance.id])
        redirect(action: "show", id: shiroRoleInstance.id)
    }

    def show = {
        def shiroRoleInstance = Role.get(params.id)
        if (!shiroRoleInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'shiroRole.label', default: 'Role'), params.id])
            redirect(action: "list")
            return
        }

        [shiroRoleInstance: shiroRoleInstance]
    }

    def edit = {
        def shiroRoleInstance = Role.get(params.id)
        if (!shiroRoleInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'shiroRole.label', default: 'Role'), params.id])
            redirect(action: "list")
            return
        }

        [shiroRoleInstance: shiroRoleInstance]
    }

    def update = {
        def shiroRoleInstance = Role.get(params.id)
        if (!shiroRoleInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'shiroRole.label', default: 'Role'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (shiroRoleInstance.version > version) {
                shiroRoleInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'shiroRole.label', default: 'Role')] as Object[],
                          "Another user has updated this Role while you were editing")
                render(view: "edit", model: [shiroRoleInstance: shiroRoleInstance])
                return
            }
        }

        shiroRoleInstance.properties = params

        if (!shiroRoleInstance.save(flush: true)) {
            render(view: "edit", model: [shiroRoleInstance: shiroRoleInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'shiroRole.label', default: 'Role'), shiroRoleInstance.id])
        redirect(action: "show", id: shiroRoleInstance.id)
    }

    def delete = {
        def shiroRoleInstance = Role.get(params.id)
        if (!shiroRoleInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'shiroRole.label', default: 'Role'), params.id])
            redirect(action: "list")
            return
        }

        try {
            shiroRoleInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'shiroRole.label', default: 'Role'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'shiroRole.label', default: 'Role'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
