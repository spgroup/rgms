package rgms.member

import rgms.publication.Publication;

class ResearchGroup {
    String name
    String description
//#if($researchGroupHierarchy)
    ResearchGroup childOf;
//#end
    static hasMany = [memberships : Membership]
    
    public String toString()
    {
        return this.name
    }
    static constraints = {
        name(maxSize:50,blank:false)
        description(maxSize:1000,blank:false)

//#if($researchGroupHierarchy)
        childOf(nullable:true)
//#end

    }
	static getPublications(researchGroup){
		def memberships = Membership.getAllMembers(researchGroup)
		return Publication.getPublicationsByMembershipList(memberships)
	}
}
