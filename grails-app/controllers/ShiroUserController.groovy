import org.apache.shiro.crypto.hash.Sha256Hash
import org.springframework.dao.DataIntegrityViolationException
import java.security.SecureRandom

class ShiroUserController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [shiroUserInstanceList: ShiroUser.list(params), shiroUserInstanceTotal: ShiroUser.count()]
    }

    def create = {
        [shiroUserInstance: new ShiroUser(params)]
    }

    def save = {
		if (!grailsApplication.config.grails.mail.username) {
			throw new RuntimeException(message(code: 'mail.plugin.not.configured', 'default' : 'Mail plugin not configured'))
		}
        def shiroUserInstance = new ShiroUser(params)
		def password = ""
		if (!shiroUserInstance.passwordHash) {password = new BigInteger(130, new SecureRandom()).toString(32)
			shiroUserInstance.passwordHash = new Sha256Hash(password).toHex()
		}
		shiroUserInstance.passwordChangeRequiredOnNextLogon = true
        if (!shiroUserInstance.save(flush: true)) {
            render(view: "create", model: [shiroUserInstance: shiroUserInstance])
            return
        }
		sendMail {
		   to shiroUserInstance.email
		   from grailsApplication.config.grails.mail.username
		   subject "Your account was successfully created!"
		   body "Hello ${shiroUserInstance.firstName} ${shiroUserInstance.lastName},\n\nYour account was successfully created!\n\nHere is your password : ${password}\n\n${createLink(absolute:true,uri:'/')}\n\nBest Regards".toString()
		}
		flash.message = message(code: 'default.created.message', args: [message(code: 'shiroUser.label', default: 'ShiroUser'), shiroUserInstance.id])
        redirect(action: "show", id: shiroUserInstance.id)
    }

    def show = {
        def shiroUserInstance = ShiroUser.get(params.id)
        if (!shiroUserInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'shiroUser.label', default: 'ShiroUser'), params.id])
            redirect(action: "list")
            return
        }

        [shiroUserInstance: shiroUserInstance]
    }

    def edit = {
        def shiroUserInstance = ShiroUser.get(params.id)
        if (!shiroUserInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'shiroUser.label', default: 'ShiroUser'), params.id])
            redirect(action: "list")
            return
        }

        [shiroUserInstance: shiroUserInstance]
    }

    def update = {
        def shiroUserInstance = ShiroUser.get(params.id)
        if (!shiroUserInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'shiroUser.label', default: 'ShiroUser'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (shiroUserInstance.version > version) {
                shiroUserInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'shiroUser.label', default: 'ShiroUser')] as Object[],
                          "Another user has updated this ShiroUser while you were editing")
                render(view: "edit", model: [shiroUserInstance: shiroUserInstance])
                return
            }
        }

        shiroUserInstance.properties = params

        if (!shiroUserInstance.save(flush: true)) {
            render(view: "edit", model: [shiroUserInstance: shiroUserInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'shiroUser.label', default: 'ShiroUser'), shiroUserInstance.id])
        redirect(action: "show", id: shiroUserInstance.id)
    }

    def delete = {
        def shiroUserInstance = ShiroUser.get(params.id)
        if (!shiroUserInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'shiroUser.label', default: 'ShiroUser'), params.id])
            redirect(action: "list")
            return
        }

        try {
            shiroUserInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'shiroUser.label', default: 'ShiroUser'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'shiroUser.label', default: 'ShiroUser'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
