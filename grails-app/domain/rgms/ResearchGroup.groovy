package rgms

class ResearchGroup {
    String name
    String description
    ResearchGroup childOf;
    static hasMany = [memberships : Membership]
    
    public String toString()
    {
        return this.name
    }
    static constraints = {
        name(maxSize:50,blank:false)
        description(maxSize:1000,blank:false)
        
    }
	static getPublications(researchGroup){
		def memberships = Membership.getAllMembers(researchGroup)
		return Publication.getPublicationsByMembershipList(memberships)
	}
}
