package rgms.authentication

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

    def getShiroRoleInstanceById(id) {
        def shiroRoleInstance = Role.get(id)
        if (!shiroRoleInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'shiroRole.label', default: 'Role'), params.id])
            redirect(action: "list")
            return
        }
        shiroRoleInstance
    }

    def returnShiroRoleInstance() {
        def shiroRoleInstance =  getShiroRoleInstanceById(params.id)

        [shiroRoleInstance: shiroRoleInstance]
    }

    def show = {
        returnShiroRoleInstance()
    }

    def edit = {
        returnShiroRoleInstance()
    }

    def update = {
        def shiroRoleInstance = getShiroRoleInstanceById(params.id)

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
        def shiroRoleInstance = getShiroRoleInstanceById(params.id)

        try {
            shiroRoleInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'shiroRole.label', default: 'Role'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'shiroRole.label', default: 'Role'), params.id])+" Error:"+e.toString()
            redirect(action: "show", id: params.id)
        }
    }
}
