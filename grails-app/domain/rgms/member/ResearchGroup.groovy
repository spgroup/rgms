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
        name(maxSize:10,blank:false,unique:true)
        description(maxSize:1000,blank:false)

//#if($researchGroupHierarchy)
        childOf(nullable:true)
//#end

    }
	static getPublications(researchGroup){
		def memberships = Membership.getAllMembers(researchGroup)
		return Publication.getPublicationsByMembershipList(memberships)
	}
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ResearchGroup other = (ResearchGroup) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
