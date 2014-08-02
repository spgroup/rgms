package rgms.member

import org.springframework.dao.DataIntegrityViolationException
import rgms.EmailService

class MembershipController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [membershipInstanceList: Membership.list(params), membershipInstanceTotal: Membership.count()]
    }

    def create = {
        [membershipInstance: new Membership(params)]
    }

    def save = {
        def membershipInstance = new Membership(params)
        if (!membershipInstance.save(flush: true)) {
            render(view: "create", model: [membershipInstance: membershipInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'default.membership.label', default: 'Membership'), membershipInstance.id])
        redirect(action: "show", id: membershipInstance.id)
    }

    def show = {
        def membershipInstance = Membership.get(params.id)
        if (!membershipInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'default.membership.label', default: 'Membership'), params.id])
            redirect(action: "list")
            return
        }

        [membershipInstance: membershipInstance]
    }

    def edit = {
        def membershipInstance = Membership.get(params.id)
        if (!membershipInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'default.membership.label', default: 'Membership'), params.id])
            redirect(action: "list")
            return
        }

        [membershipInstance: membershipInstance]
    }

    def update = {
        def membershipInstance = Membership.get(params.id)
        if (!membershipInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'default.membership.label', default: 'Membership'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (membershipInstance.version > version) {
                membershipInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'default.membership.label', default: 'Membership')] as Object[],
                          "Another user has updated this Membership while you were editing")
                render(view: "edit", model: [membershipInstance: membershipInstance])
                return
            }
        }

        membershipInstance.properties = params

        if (!membershipInstance.save(flush: true)) {
            render(view: "edit", model: [membershipInstance: membershipInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'default.membership.label', default: 'Membership'), membershipInstance.id])
        redirect(action: "show", id: membershipInstance.id)
    }

    def delete = {
        def membershipInstance = Membership.get(params.id)
        if (!membershipInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'default.membership.label', default: 'Membership'), params.id])
            redirect(action: "list")
            return
        }

        try {
            membershipInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'default.membership.label', default: 'Membership'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'default.membership.label', default: 'Membership'), params.id])
            redirect(action: "show", id: params.id)
        }
    }

    //#if($NewMemberInGroupNotification)
    def sendMailToEveryoneAboutTheNewMember (ResearchGroup researchGroup, Member member) {
        def researchGroupCurrentMemberships = Membership.findAllByResearchGroupAndDateLeft(researchGroup, null)

        for (peerMember in researchGroupCurrentMemberships) {
            println "Sending mail to " + peerMember.member.email + " about " + member.name
            def email = peerMember.member.email
            def mailSender = grailsApplication.config.grails.mail.username
            def title = message(code: 'mail.title.membership.join', args: [researchGroup.name])
            def content = message(code: 'mail.body.membership.join', args: [member.name, researchGroup.name, member.email])

            EmailService emailService = new EmailService();
            emailService.sendEmail(email, mailSender, title, content)
        }
    }
	//#end
}
