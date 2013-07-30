package rgms.member

import org.springframework.dao.DataIntegrityViolationException
import rgms.news.News
import rgms.news.NewsController
import rgms.news.TwitterConnection
import twitter4j.Status

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

    def save() {
        def researchGroupInstance = new ResearchGroup(params)
        if (!researchGroupInstance.save(flush: true)) {
            render(view: "create", model: [researchGroupInstance: researchGroupInstance])
            return
        }
        Membership.editMembersToResearchGroup(params.members, researchGroupInstance)

        flash.message = message(code: 'default.created.message', args: [message(code: 'researchGroup.label', default: 'Research Group'), researchGroupInstance.id])
        redirect(action: "show", id: researchGroupInstance.id)
    }

    def show() {
        def researchGroupInstance = ResearchGroup.get(params.id)
        if (!verifyResearchGroupInstance(researchGroupInstance, params.id)) {
            return
        }
        //def cm = Membership.getCurrentMemberships(researchGroupInstance)
        [researchGroupInstance: researchGroupInstance, publicationsInstance: listPublicationByGroup(), currentMemberships: Membership.getCurrentMemberships(researchGroupInstance), currentNews: News.getCurrentNews(researchGroupInstance)]
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
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'researchGroup.label', default: 'Research Group'), params.id])
            redirect(action: "list")
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
        if (!verifyResearchGroupInstance(researchGroupInstance, params.id)) {
            return
        }
        Membership.editMembersToResearchGroup(params.members, researchGroupInstance)
        if (params.version) {
            def version = params.version.toLong()
            if (!verifyVersion(researchGroupInstance, version)) {
                return
            }
        }
        researchGroupInstance.properties = params

        if (!researchGroupInstance.save(flush: true)) {
            render(view: "edit", model: [researchGroupInstance: researchGroupInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'researchGroup.label', default: 'Research Group'), researchGroupInstance.id])
        redirect(action: "show", id: researchGroupInstance.id)
    }

    def delete() {
        def researchGroupInstance = ResearchGroup.get(params.id)
        if (!verifyResearchGroupInstance(researchGroupInstance, params.id)) {
            return
        }
        Membership.editMembersToResearchGroup([], researchGroupInstance)
        try {
            researchGroupInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'researchGroup.label', default: 'Research Group'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException ignore) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'researchGroup.label', default: 'Research Group'), params.id])
            redirect(action: "show", id: params.id)
        }
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

    def updateNewsFromTwitter() {
        def researchGroupInstance = ResearchGroup.get(params.id)
        TwitterConnection twConn = new TwitterConnection()
        List<Status> timeline = twConn.getTimeLine(researchGroupInstance.twitter)
        timeline.each {
            def newContr = new NewsController()
            newContr.params << [description: it.getText(), date: it.getCreatedAt(), researchGroup: researchGroupInstance]
            newContr.create()
            newContr.save()
            newContr.response.reset()
            //researchGroupInstance.addToNews(new News(description: it.getText(), date: it.getCreatedAt()))
        }
        researchGroupInstance.save()
        redirect(action: "show", id: researchGroupInstance.id)
    }
}
