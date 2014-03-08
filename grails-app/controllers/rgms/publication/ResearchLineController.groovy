//#if($researchLine)
package rgms.publication

import rgms.authentication.User
import rgms.member.Member
import rgms.EmailService

class ResearchLineController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [researchLineInstanceList: ResearchLine.list(params), researchLineInstanceTotal: ResearchLine.count()]
    }

    def create() {
        [researchLineInstance: new ResearchLine(params)]
    }

    //#if($ResearchLineConsulta)
    def listSearchMember ()
    {
        ArrayList<String> lista = new ArrayList<String>()
        for(researchline in ResearchLine.getAll())
            {
                for(member in researchline.getMembers())
                {
                    if(member.getName() == params.member)
                    {
                        lista.add(researchline.getName())


                    }
                }
               [researchLineInstanceList: lista]

            }

    }

    def listSearchArticle()
    {
        ArrayList<String> lista = new ArrayList<String>()
        for(researchline in ResearchLine.getAll())
        {
            for(publication in researchline.getPublications())
            {
                if(publication.getTitle() == params.artigo)
                {
                    lista.add(researchline.getName())


                }
            }
            [researchLineInstanceList: lista]

        }


    }
    //#end

    def save() {
        def researchLineInstance = new ResearchLine(params)
        saveResearchLine("create", researchLineInstance, "created", [])
    }

    def show() {
        tryFindInstance(params)
    }

    def edit() {
        tryFindInstance(params)
    }
    //#if($ResearchLineNotification)
    def getOldMembers(def id) {
        def oldMembers = ResearchLine.get(id)?.members
        def members = []
        for (m in oldMembers) {
            members.add(Member.get(m.id))
        }
        members
    }
    //#end

    def update() {
        def old_members = []
        //#if($ResearchLineNotification)
        old_members = getOldMembers(params.id)
        //#end
        def researchLineInstance = getInstance(params)
        if (!researchLineInstance)
            return

        if (!checkInstanceVersion(params, researchLineInstance))
            return
        researchLineInstance.properties = params
        if (!params.members) {
            researchLineInstance.properties.members = []
        }
        saveResearchLine("edit", researchLineInstance, "updated", old_members)
    }
    //#if($ResearchLineNotification)
    def sendEmail(def email, def title, def content) {
        EmailService emailService = new EmailService();
        emailService.sendEmail(email, grailsApplication.config.grails.mail.username, title, content)
    }
    //#end
    //#if($ResearchLineNotification)

    def sendNotifications(def researchLineInstance, def oldMembers) {
        def receivedMembers = (researchLineInstance?.members) ?: ([] as Set)
        def similarMembers = receivedMembers.intersect(oldMembers)
        def newMembers = receivedMembers.findAll { member -> !similarMembers.contains(member) }
        def membersWhoLeft = oldMembers.findAll { member -> !similarMembers.contains(member) }
        def researchLineName = researchLineInstance.name
        def contentJoined =  message(code: 'default.joinedmessage.message') + researchLineName + ":\n"
        def contentLeft = message(code: 'default.leftmessage.message') + researchLineName + ":\n"
        def title =message(code: 'default.notmember.message') + researchLineName + message(code: 'default.anymore.message')

        for (m in membersWhoLeft) {
            def content = message(code: 'default.hello.message', args: m.name) + researchLineName + message(code: 'default.anymore.message') + message (code: 'default.bestregard.message').toString()
            sendEmail(m.email, title, content)
            contentLeft += "* " + m.name + "\n"
        }

        title = message(code: 'default.newmember.message') + researchLineName
        for (m in newMembers) {
            def content =  message(code: 'default.hello2.message', args: m.name) + researchLineName +  message (code: 'default.bestregard.message').toString()
            sendEmail(m.email, title, content)
            contentJoined += "* " + m.name + "\n "
        }

        sendReports(newMembers, membersWhoLeft, similarMembers, researchLineName, contentJoined, contentLeft)
    }
    //#end
    //#if($ResearchLineNotification)
    def sendReports(def newMembers, def membersWhoLeft, def similarMembers, def researchLineName, def contentJoined, def contentLeft) {
        def title = message(code: 'default.change.message') + researchLineName
        def membersJoined = newMembers.size() > 0
        def membersLeft = membersWhoLeft.size() > 0

        for (m in similarMembers) {
            def content = "Hello ${ m.name},\n\n"

            if (membersJoined) {
                content += contentJoined + "\n"
            }
            if (membersLeft) {
                content += contentLeft + "\n"
            }
            if (membersJoined || membersLeft) {
                    content += message(code: 'default.bestregard.message')
                sendEmail(m.email, title, content)
            }
        }
    }
    //#end
    def delete() {
        def researchLineInstance = ResearchLine.get(params.id)
        try {
            if (!editPublications(researchLineInstance))
                return
            researchLineInstance.delete(flush: true)
            createMessage(action: "list")
        }
        catch (DataIntegrityViolationException) {
            createMessage(action: "show", id: params.id)
        }
    }

    def checkInstanceVersion(params, researchLineInstance) {
        if (params.version) {
            def version = params.version.toLong()
            if (researchLineInstance.version > version) {
                researchLineInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'researchLine.label', default: 'ResearchLine')] as Object[],
                        message(code: 'default.warningwhileedting.message'))
                render(view: "edit", model: [researchLineInstance: researchLineInstance])
                return false
            }
        }
        return true
    }

    def editPublications(researchLineInstance) {
        for (p in researchLineInstance?.publications) {
            p.researchLine = null;
            if (!p.save(flush: true)) {
                render(view: "edit", model: [researchLineInstance: researchLineInstance])
                return false
            }
        }
        true
    }

    def createMessage(params) {
        flash.message = message(code: 'default.deleted.message', args: [message(code: 'researchLine.label', default: 'ResearchLine'), params.id])
        redirect(params)
    }

    def saveResearchLine(def view, def researchLineInstance, def action, def oldMembers) {
        if (!researchLineInstance.save(flush: true)) {
            render(view: view, model: [researchLineInstance: researchLineInstance])
        } else {
            //#if($ResearchLineNotification)
            sendNotifications(researchLineInstance, oldMembers)
            //#end
	    //#if($facebook)
        //def user = User.findByUsername(SecurityUtils.subject?.principal)
        //Member author = user?.author
        //PublicationController.sendPostFacebook(author, researchLineInstance.toString())
        //#end
            flash.message = message(code: 'default.' + action + '.message', args: [message(code: 'researchLine.label', default: 'ResearchLine'), researchLineInstance.id])
            redirect(action: "show", id: researchLineInstance.id)
        }
    }

    def tryFindInstance(params) {
        def researchLineInstance = getInstance(params)

        if (researchLineInstance)
            [researchLineInstance: getInstance(params)]
    }

    def getInstance(params) {
        def researchLineInstance = ResearchLine.get(params.id)
        if (!researchLineInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'researchLine.label', default: 'ResearchLine'), params.id])
            redirect(action: "list")
            return
        }

        researchLineInstance
    }
}
//#end