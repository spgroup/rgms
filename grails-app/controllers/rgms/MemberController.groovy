package rgms

import org.apache.shiro.crypto.hash.Sha256Hash
import org.springframework.dao.DataIntegrityViolationException
import java.security.SecureRandom
import rgms.*

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
        [memberInstance: new Member(params)]
    }

    def save = {
        if (!grailsApplication.config.grails.mail.username) {
            throw new RuntimeException(message(code: 'mail.plugin.not.configured', 'default' : 'Mail plugin not configured'))
        }
        def memberInstance = new Member(params)
        def username = memberInstance?.username
        def password = ""
        
        if (!memberInstance.passwordHash) {
            
            password = new BigInteger(130, new SecureRandom()).toString(32)
            memberInstance.passwordHash = new Sha256Hash(password).toHex()
        }
        memberInstance.passwordChangeRequiredOnNextLogon = true
        
        //feature record
        def hist = new Record(start:new Date(),status_H:memberInstance.status)
        hist.save()
            
        memberInstance.addToHistorics(hist)
        memberInstance.save()
        
       //end feature record
        
        if (!memberInstance.save(flush: true)) { 
            render(view: "create", model: [memberInstance: memberInstance])
            return
        }
        
        sendMail {
            to memberInstance.email
            from grailsApplication.config.grails.mail.username
            subject "[GRMS] Your account was successfully created!"
            body "Hello ${memberInstance.firstName} ${memberInstance.lastName},\n\nYour account was successfully created!\n\nHere is your username: ${username} and password: ${password}\n\n${createLink(absolute:true,uri:'/')}\n\nBest Regards,\nAdministrator of the Research Group Management System".toString()
        }
        
        flash.message = message(code: 'default.created.message', args: [message(code: 'member.label', default: 'Member'), memberInstance.id])
        redirect(action: "show", id: memberInstance.id)
    }

    def show = {
        def memberInstance = Member.get(params.id)
        if (!memberInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'member.label', default: 'Member'), params.id])
            redirect(action: "list")
            return
        }

        [memberInstance: memberInstance]
    }

    def edit = {
        def memberInstance = Member.get(params.id)
        if (!memberInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'member.label', default: 'Member'), params.id])
            redirect(action: "list")
            return
        }

        [memberInstance: memberInstance]
    }

    def update = {
        def memberInstance = Member.get(params.id)
        
        if (!memberInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'member.label', default: 'Member'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (memberInstance.version > version) {
                memberInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                    [message(code: 'member.label', default: 'Member')] as Object[],
                          "Another user has updated this Member while you were editing")
                render(view: "edit", model: [memberInstance: memberInstance])
                return
            }
        }

        //feature record
        def status0 = memberInstance.status //pega o status anterior do usuario
        
        memberInstance.properties = params //atualiza todos os parametros

        if (!memberInstance.save(flush: true)) {
            render(view: "edit", model: [memberInstance: memberInstance])
            return
        }
        
        //feature record
        
        //salva o historico se o status mudar
        String newStatus = memberInstance.status
        
        if (newStatus != status0){
            try{
                def hist = Record.findWhere(end: null, status_H:status0)
                hist.end = new Date()
            
                def h = Record.merge(hist)
                h.save()
                memberInstance.addToHistorics(h)    
            } catch(Exception ex){
                render "You do not have permission to access this account."
//                flash.message = message(code: 'historic.failed')
            }
            
            def historic = new Record(start: new Date(), status_H: newStatus)
            historic.save();
            memberInstance.addToHistorics(historic)
            memberInstance.save()
        }
        //end feature record
        
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
}
