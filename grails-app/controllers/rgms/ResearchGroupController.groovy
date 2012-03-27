package rgms


import org.springframework.dao.DataIntegrityViolationException

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
        [researchGroupInstance: new ResearchGroup(params) , membersInstance: members]
    }

    def save() {
        def researchGroupInstance = new ResearchGroup(params)
        if (!researchGroupInstance.save(flush: true)) {
            render(view: "create", model: [researchGroupInstance: researchGroupInstance])
            return
        }
        Membership.addMembersToResearchGroup(params.members, researchGroupInstance)

	flash.message = message(code: 'default.created.message', args: [message(code: 'researchGroup.label', default: 'Research Group'), researchGroupInstance.id])
        redirect(action: "show", id: researchGroupInstance.id)
    }

    def show() {
        def researchGroupInstance = ResearchGroup.get(params.id)
        if (!researchGroupInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'researchGroup.label', default: 'Research Group'), params.id])
            redirect(action: "list")
            return
        }
		
        [researchGroupInstance: researchGroupInstance, publicationsInstance : listPublicationByGroup()]
    }

    def edit() {
        def researchGroupInstance = ResearchGroup.get(params.id)
        if (!researchGroupInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'researchGroup.label', default: 'Research Group'), params.id])
            //redirect(action: "list")
            //return
        }
        def members = refreshMemberList()
		
        //def deb = [session["groups"],session["groups"].contains(params.groups),entrou1,entrou2,entrou3,params]
        [researchGroupInstance: researchGroupInstance, membersInstance: members]
    }

    def update() {        
        def researchGroupInstance = ResearchGroup.get(params.id)
        if (!researchGroupInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'researchGroup.label', default: 'Research Group'), params.id])
            redirect(action: "list")
            return
        }
        Membership.editMembersToResearchGroup(params.members, researchGroupInstance)
        if (params.version) {
            def version = params.version.toLong()
            if (researchGroupInstance.version > version) {
                researchGroupInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                    [message(code: 'researchGroup.label', default: 'Research Group')] as Object[],
                          "Another user has updated this ResearchGroup while you were editing")
                render(view: "edit", model: [researchGroupInstance: researchGroupInstance])
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
        if (!researchGroupInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'researchGroup.label', default: 'Research Group'), params.id])
            redirect(action: "list")
            return
        }
        Membership.removeMemberFromResearchGroup(researchGroupInstance)
        try {
            researchGroupInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'researchGroup.label', default: 'Research Group'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'researchGroup.label', default: 'Research Group'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
        
    private Set refreshMemberList()
    {
        def members = [] as Set
        
        if (request.post == false){
            session["groups"] = ResearchGroup.list()?.collect{it.id}            
        }else{
            if (session["groups"].contains(params.groups as Long)){
                session["groups"].remove(params.groups as Long)                
            }else{
                session["groups"].add(params.groups as Long)         
            }            
        }
        for(groupId in session["groups"] ){
            def rGroup = ResearchGroup.get(groupId as Long)
            if(rGroup){
                members.addAll(rGroup?.memberships?.collect{it.member})
            }            
        }
		
        if(members.empty){
            members = Member.list()
        }
        def map = [researchGroupInstance: new ResearchGroup(params), membersInstance: members]
        if(request.xhr){
            render(view: "/researchGroup/editMembers", model: map)
        }
        return members        
    }
   
    def Set listPublicationByGroup(){
        def researchGroupInstance = ResearchGroup.get(params.id)
	//def g = rgms.ResearchGroup.findById(researchGroupInstance.id)
        def l = [] as Set
        def memberships = researchGroupInstance.memberships.findAll{
            researchGroupInstance.id = it.researchGroup.id
        }

        memberships.each{
            
            def dateJoined =  it.dateJoined
            for(publication in it.member.publications)
            {
                
                if( publication.publicationDate.compareTo(it.dateJoined) > 0)
                {
                    l.add(publication);
                } 
            }
        }
        return l  
	   
    }
   
}
