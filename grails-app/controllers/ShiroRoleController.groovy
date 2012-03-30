import org.springframework.dao.DataIntegrityViolationException

class ShiroRoleController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [shiroRoleInstanceList: ShiroRole.list(params), shiroRoleInstanceTotal: ShiroRole.count()]
    }

    def create = {
        [shiroRoleInstance: new ShiroRole(params)]
    }

    def save = {
        def shiroRoleInstance = new ShiroRole(params)
        if (!shiroRoleInstance.save(flush: true)) {
            render(view: "create", model: [shiroRoleInstance: shiroRoleInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'shiroRole.label', default: 'ShiroRole'), shiroRoleInstance.id])
        redirect(action: "show", id: shiroRoleInstance.id)
    }

    def show = {
        def shiroRoleInstance = ShiroRole.get(params.id)
        if (!shiroRoleInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'shiroRole.label', default: 'ShiroRole'), params.id])
            redirect(action: "list")
            return
        }

        [shiroRoleInstance: shiroRoleInstance]
    }

    def edit = {
        def shiroRoleInstance = ShiroRole.get(params.id)
        if (!shiroRoleInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'shiroRole.label', default: 'ShiroRole'), params.id])
            redirect(action: "list")
            return
        }

        [shiroRoleInstance: shiroRoleInstance]
    }

    def update = {
        def shiroRoleInstance = ShiroRole.get(params.id)
        if (!shiroRoleInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'shiroRole.label', default: 'ShiroRole'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (shiroRoleInstance.version > version) {
                shiroRoleInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'shiroRole.label', default: 'ShiroRole')] as Object[],
                          "Another user has updated this ShiroRole while you were editing")
                render(view: "edit", model: [shiroRoleInstance: shiroRoleInstance])
                return
            }
        }

        shiroRoleInstance.properties = params

        if (!shiroRoleInstance.save(flush: true)) {
            render(view: "edit", model: [shiroRoleInstance: shiroRoleInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'shiroRole.label', default: 'ShiroRole'), shiroRoleInstance.id])
        redirect(action: "show", id: shiroRoleInstance.id)
    }

    def delete = {
        def shiroRoleInstance = ShiroRole.get(params.id)
        if (!shiroRoleInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'shiroRole.label', default: 'ShiroRole'), params.id])
            redirect(action: "list")
            return
        }

        try {
            shiroRoleInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'shiroRole.label', default: 'ShiroRole'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'shiroRole.label', default: 'ShiroRole'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
