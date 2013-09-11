package rgms.member

import org.apache.shiro.crypto.hash.Sha256Hash
import org.springframework.dao.DataIntegrityViolationException
import rgms.EmailService

import java.security.SecureRandom

class MemberController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [memberInstanceList: Member.list(params), memberInstanceTotal: Member.count()]
    }

    def create = {
        def member = new Member(params)

/**
 * @author penc
 */
//#if($contextualInformation)
        member.setUniversity(params.university ?: grailsApplication.getConfig().getProperty("defaultUniversity") as String);
        member.setCity(params.city ?: grailsApplication.getConfig().getProperty("defaultCity") as String);
//#end

        [memberInstance: member]
    }

    def save = {
//#if($Auth)
        if (!grailsApplication.config.grails.mail.username) {
            throw new RuntimeException(message(code: 'mail.plugin.not.configured', 'default': 'Mail plugin not configured'))
        }
//#end

        def memberInstance = new Member(params)
        def username = memberInstance?.username
        def password = ""

        if (!memberInstance.passwordHash) {

            password = new BigInteger(130, new SecureRandom()).toString(32)
            memberInstance.passwordHash = new Sha256Hash(password).toHex()
        }
        memberInstance.passwordChangeRequiredOnNextLogon = true

        if (!memberInstance.save(flush: true)) {
            render(view: "create", model: [memberInstance: memberInstance])
            return
        }

        def email = memberInstance.email
        def mailSender = grailsApplication.config.grails.mail.username
        def title = message(code: 'mail.title.create.account')
        def content = message(code: 'mail.body.create.account', args: [memberInstance.name, username, password, createLink(absolute: true, uri: '/')])

        EmailService emailService = new EmailService();
        emailService.sendEmail(email, mailSender, title, content)

        flash.message = message(code: 'default.created.message', args: [message(code: 'member.label', default: 'Member'), memberInstance.id])
        redirect(action: "show", id: memberInstance.id)
    }


    def show = {
        Get_MemberInstance()
    }

    def edit = {
        Get_MemberInstance()
    }

    boolean check_version(String version, Member memberInstance) {
        if (version) {
            def version_long = version.toLong()
            if (memberInstance.version > version_long) {
                memberInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'member.label', default: 'Member')] as Object[],
                        "Another user has updated this Member while you were editing")
                render(view: "edit", model: [memberInstance: memberInstance])
                return false
            }
        }
        return true
    }

    private Member getMember(String id) {
        def memberInstance = Member.get(id)
        if (!memberInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'member.label', default: 'Member'), id])
            redirect(action: "list")
        }
        return memberInstance
    }


    def update = {
        def memberInstance = getMember(params.id)
        if (!memberInstance) return

        if (!check_version(params.version, memberInstance)) return

//#if($History)
        def status0 = memberInstance.status //pega o status anterior do usuario
//#end

        memberInstance.properties = params //atualiza todos os parametros

        if (!memberInstance.save(flush: true)) {
            render(view: "edit", model: [memberInstance: memberInstance])
            return
        }

//#if($History)

        String newStatus = memberInstance.status //pega o novo status

        //salva o historico se o status mudar
        if (newStatus != status0) {
            try {
                def hist = Record.findWhere(end: null, status_H: status0)
                hist.end = new Date()

                def h = Record.merge(hist)
                h.save()
                memberInstance.addToHistorics(h)
            } catch (Exception ex) {
                render "You do not have permission to access this account."
            }
            saveHistory(memberInstance, newStatus) //refactoring - extract method
        }
//#end

        flash.message = message(code: 'default.updated.message', args: [message(code: 'member.label', default: 'Member'), memberInstance.id])
        redirect(action: "show", id: memberInstance.id)
    }

    def delete = {
        def memberInstance = Member.get(params.id)
        if (!memberInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'member.label', default: 'Member'), params.id])
            redirect(action: "list")
            return
        }

        try {
            memberInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'member.label', default: 'Member'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'member.label', default: 'Member'), params.id])
            redirect(action: "show", id: params.id)
        }
    }

    private void saveHistory(def memberInstance, String status) {

        def hist = new Record(start: new Date(), status_H: status)
        hist.save()

        memberInstance.addToHistorics(hist)
        memberInstance.save()
    }

    def Get_MemberInstance(){
        def memberInstance = Member.get(params.id)
        if (!memberInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'member.label', default: 'Member'), params.id])
            redirect(action: "list")
            return
        }

        [memberInstance: memberInstance]
    }
}
