package rgms.member

import org.springframework.dao.DataIntegrityViolationException
import rgms.news.News
import rgms.news.NewsController
import rgms.news.TwitterConnection
import twitter4j.Status
import rgms.EmailService

class ResearchGroupController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [researchGroupInstanceList: ResearchGroup.list(params), researchGroupInstanceTotal: ResearchGroup.count()]
    }

    def create() {
        def members = refreshMemberList()
        [researchGroupInstance: new ResearchGroup(params), membersInstance: members]
    }

    //#if($researchGroupHierarchy)
    def validarChildOf(def researchGroupInstance, def researchGroupParent) {

        def parents = [researchGroupInstance]
        def current = researchGroupParent

        while(current != null && ! parents.contains(current)) {
            parents.add(current)
            current = current.childOf
        }

        if(current != null) {
            flash.message = message(code: 'researchGroup.hasCycle', args: [])
            redirect(action: "edit", id: params.id)
        	throw new RuntimeException("Cycle found")
        }
    }
    //#end

    def save() {
        def researchGroupInstance = new ResearchGroup(params)
        //#if($researchGroupHierarchy)
        try {
            validarChildOf(researchGroupInstance, researchGroupInstance.getChildOf())
        } catch(Exception e) {
            return;
        }
        //#end

        if (!researchGroupInstance.save(flush: true)) {
            render(view: "create", model: [researchGroupInstance: researchGroupInstance])
        } else {
            //#if($researchGroupHierarchyNotify)
            if (researchGroupInstance.getChildOf() != null) {
                notifyChangeChildOfResearchGroup(researchGroupInstance, params.members)
            }
            //#end

            Membership.editMembersToResearchGroup(params.members, researchGroupInstance)
            showMessageAndRedirectWithId('created', researchGroupInstance.id)
        }
    }

    def show() {
        def researchGroupInstance = ResearchGroup.get(params.id)
        if (!verifyResearchGroupInstance(researchGroupInstance, params.id)) {
            return
        }
        //def cm = Membership.getCurrentMemberships(researchGroupInstance)
        [researchGroupInstance: researchGroupInstance, publicationsInstance: listPublicationByGroup(), currentMemberships: Membership.getCurrentMemberships(researchGroupInstance), currentNews: News.getCurrentNewsOrderByMostRecentDate(researchGroupInstance)]
    }

    def edit() {
        def researchGroupInstance = ResearchGroup.get(params.id)
        assert researchGroupInstance != null
        if (!researchGroupInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'researchGroup.label', default: 'Research Group'), params.id])
            //redirect(action: "list")
            //return
        }
        def members = refreshMemberList()

        //def deb = [session["groups"],session["groups"].contains(params.groups),entrou1,entrou2,entrou3,params]
        [researchGroupInstance: researchGroupInstance, membersInstance: members]
    }

    boolean verifyResearchGroupInstance(Object obj, Object id) {
        if (!obj) {
            showMessageAndRedirect('not.found', "list")
            return false;
        }
        return true;
    }

    boolean verifyVersion(Object researchGroupInstance, Long version) {
        if (researchGroupInstance.version > version) {
            researchGroupInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                    [message(code: 'researchGroup.label', default: 'Research Group')] as Object[],
                    "Another user has updated this ResearchGroup while you were editing")
            render(view: "edit", model: [researchGroupInstance: researchGroupInstance])
            return false
        }
        return true
    }

    def update() {
        def researchGroupInstance = ResearchGroup.get(params.id)

        //#if($researchGroupHierarchy)
        def researchGroupInstanceChildOf = null
        if(params.childOf?.id != "null") {
            researchGroupInstanceChildOf = ResearchGroup.get(params.childOf?.id)
        }
        //#end

        try {
            if (!verifyResearchGroupInstance(researchGroupInstance, params.id)) {
                throw new RuntimeException()
            }

            if (params.version && !verifyVersion(researchGroupInstance, params.version.toLong())) {
                throw new RuntimeException()
            }

            //#if($researchGroupHierarchy)
            validarChildOf(researchGroupInstance, researchGroupInstanceChildOf)
            //#end

        } catch(Exception e) {
            return;
        }

        researchGroupInstance.properties = params

        if (!researchGroupInstance.save(flush: true)) {
            render(view: "edit", model: [researchGroupInstance: researchGroupInstance])
        } else {
            Membership.editMembersToResearchGroup(params.members, researchGroupInstance)

            //#if($researchGroupHierarchyNotify)
            if (isChildOfResearchGroupChanged(researchGroupInstance, researchGroupInstanceChildOf)) {
                notifyChangeChildOfResearchGroup(researchGroupInstance, params.members)
            }
            //#end

            showMessageAndRedirectWithId('updated', researchGroupInstance.id)
        }

    }

    def delete() {
        def researchGroupInstance = ResearchGroup.get(params.id)
        if (!verifyResearchGroupInstance(researchGroupInstance, params.id)) {
            return
        }
        Membership.editMembersToResearchGroup([], researchGroupInstance)
        try {
            researchGroupInstance.delete(flush: true)
            showMessageAndRedirect('deleted', "list")
        }
        catch (DataIntegrityViolationException ignore) {
            showMessageAndRedirectWithId('not.deleted', params.id)
        }
    }

    def showMessageAndRedirect(def operation, def redirection) {
        flash.message = message(code: 'default.' + operation + '.message', args: [message(code: 'researchGroup.label', default: 'Research Group'), params.id])
        redirect(action: redirection)
    }

    def showMessageAndRedirectWithId(def operation, def researchGroupId) {
        flash.message = message(code: 'default.' + operation + '.message', args: [message(code: 'researchGroup.label', default: 'Research Group'), researchGroupId])
        redirect(action: "show", id: researchGroupId)
    }

    void addOrRemoveGroupsOfSession() {
        if (session["groups"].contains(params.groups as Long)) {
            session["groups"].remove(params.groups as Long)
        } else {
            session["groups"].add(params.groups as Long)
        }
    }

    void addMembersToSet(Set members) {
        for (groupId in session["groups"]) {
            def rGroup = ResearchGroup.get(groupId as Long)
            if (rGroup) {
                def collection = rGroup?.memberships?.collect { it.member }
                if (collection) {
                    members.addAll(collection)
                }
            }
        }
    }

    private Set refreshMemberList() {
        def members = [] as Set

        if (request.post == false) {
            session["groups"] = ResearchGroup.list()?.collect { it.id }
        } else {
            addOrRemoveGroupsOfSession()
        }

        addMembersToSet(members);

        if (members.empty) {
            members = Member.list()
        }
        def map = [researchGroupInstance: new ResearchGroup(params), membersInstance: members]
        if (request.xhr) {
            render(view: "/researchGroup/editMembers", model: map)
        }
        return members
    }

    def Set listPublicationByGroup() {
        def researchGroupInstance = ResearchGroup.get(params.id)
        def list = ResearchGroup.getPublications(researchGroupInstance)
        return list
    }

    //#if($researchGroupHierarchyNotify)
    void notifyChangeChildOfResearchGroup(researchGroup, members) {
        for (memberId in members) {
            def member = Member.get(memberId)
            assert member != null
            if (member.getEmail()) {
                def email = member.getEmail()
                def mailSender = grailsApplication.config.grails.mail.username
                def title = message(code: 'mail.title.researchgroup.child')
                def content = message(code: 'mail.body.researchgroup.child', args: [member.name, researchGroup.getChildOf().getName()])

                EmailService emailService = new EmailService()
                emailService.sendEmail(email, mailSender, title, content)
            }
        }
    }

    boolean isChildOfResearchGroupChanged(researchGroupInstance, newResearchGroupChildOf) {
        def result = (researchGroupInstance != null) && (newResearchGroupChildOf != null) && (researchGroupInstance.getChildOf() != null)
        result = result && (newResearchGroupChildOf != researchGroupInstance.getChildOf())
        result
    }
    //#end

    def updateNewsFromTwitter() {
        def researchGroupInstance = ResearchGroup.get(params.id)
        TwitterConnection twConn = new TwitterConnection()
        List<Status> timeline = twConn.getTimeLine(researchGroupInstance.twitter)

        timeline.each {
            def newContr = new NewsController()
            newContr.params << [description: it.getText(), date: it.getCreatedAt(), researchGroup: researchGroupInstance]
            newContr.create()
            newContr.saveFromTwitter()
            newContr.response.reset()
        }
        researchGroupInstance.save()
        redirect(action: "show", id: researchGroupInstance.id)
    }
}
