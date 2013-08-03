package rgms.member

import org.apache.shiro.crypto.hash.Sha256Hash
import org.springframework.dao.DataIntegrityViolationException
import rgms.XMLService

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
        member.setUniversity(params.university ?: grailsApplication.getConfig().getProperty("defaultUniversity"));
        member.setCity(params.city ?: grailsApplication.getConfig().getProperty("defaultCity"));
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

        sendMail {
            to memberInstance.email
            from grailsApplication.config.grails.mail.username
            subject "[GRMS] Your account was successfully created!"
//#literal()
            body "Hello ${ memberInstance.name},\n\nYour account was successfully created!\n\nHere is your username: ${ username} and password: ${ password}\n\n${ createLink(absolute: true, uri: '/')}\n\nBest Regards,\nAdministrator of the Research Group Management System".toString()
//#end
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
    //#if($XMLImp && $Member)
    def returnWithMessage(String msg, Member newMember) {
        render(view: "create", model: [memberInstance: newMember])
        flash.message = message(code: msg)
    }

    def uploadMemberXML() {
        String flashMessage = 'XML data extracted. Complete the remaining fields'
        boolean errorFound = false
        Member newMember = new Member(params)

        try {
            XMLService serv = new XMLService()
            Node xmlFile = serv.parseReceivedFile(request)
            fillMemberInfo(xmlFile, newMember, serv)
        }
        catch (SAXParseException) { //Se o arquivo nÃ£o for XML ou nÃ£o passaram nenhum
            flashMessage = 'default.xml.parserror.message'
            errorFound = true
        }
        catch (NullPointerException) { //Se a estrutura do XML estÃ¡ errada (cast em NÃ³ nulo)

            flashMessage = 'default.xml.structure.message'
            errorFound = true
        }
        catch (Exception) {
            flashMessage = 'default.xml.unknownerror.message'
            errorFound = true
        }

        returnWithMessage(flashMessage, newMember)
        if (errorFound) return
    }

    private static void fillMemberInfo(Node xmlFile, Member newMember) {
        Node dadosGerais = (Node) xmlFile.children()[0]
        List<Object> dadosGeraisChildren = dadosGerais.children()
        Node endereco = (Node) dadosGeraisChildren[2]
        Node enderecoProfissional = (Node) endereco.value()[0]

        newMember.name = XMLService.getAttributeValueFromNode(dadosGerais, "NOME-COMPLETO")
        newMember.university = XMLService.getAttributeValueFromNode(enderecoProfissional, "NOME-INSTITUICAO-EMPRESA")
        newMember.phone = XMLService.getAttributeValueFromNode(enderecoProfissional, "DDD") +
                XMLService.getAttributeValueFromNode(enderecoProfissional, "TELEFONE")
        newMember.website = XMLService.getAttributeValueFromNode(enderecoProfissional, "HOME-PAGE")
        newMember.city = XMLService.getAttributeValueFromNode(enderecoProfissional, "CIDADE")
        newMember.country = XMLService.getAttributeValueFromNode(enderecoProfissional, "PAIS")
        newMember.email = XMLService.getAttributeValueFromNode(enderecoProfissional, "E-MAIL")
    }

    //#end
}
