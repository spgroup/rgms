package rgms.member

import rgms.news.News
import rgms.publication.Publication

class ResearchGroup {
    String name
    String description
    String twitter

//#if($researchGroupHierarchy)
    ResearchGroup childOf;
//#end
	
    static hasMany = [memberships: Membership, news: News]

    static constraints = {
        name(maxSize: 10, blank: false, unique: true)
        description(maxSize: 1000, blank: false)
        twitter(nullable: true)

//#if($researchGroupHierarchy)
        childOf(nullable: true, blank: true)
//#end
    }

    public String toString() {
        return this.name
    }
    
    static getPublications(researchGroup) {
        def memberships = Membership.getAllMembers(researchGroup)
        return Publication.getPublicationsByMembershipList(memberships)
    }
    
}

