package rgms

import java.util.List;


class Publication {
	
    Date publicationDate
    String title
    ResearchLine researchLine

    static belongsTo = Member
    static hasMany = [members : Member]
    
    static constraints = {
        title()
        publicationDate()
        researchLine(nullable:true)
    }
    
    public String toString()
    {        
        return title
    }
	
	static Set getPublicationsByMembershipList(membershipList){
		def set = [] as Set
		for(membership in membershipList){
			set.addAll(Publication.getPublicationsByMembership(membership))
		}
                return set
	}
	
	static Set getPublicationsByMembership(membership){
		def publications = membership?.member.publications
		def query = !membership.dateLeft ?
					{ it.publicationDate?.compareTo(membership.dateJoined) > 0 }:
					{ it.publicationDate?.compareTo(membership.dateJoined) > 0  &&
						it.publicationDate?.compareTo(membership.dateLeft) < 0}
                def p = publications?.findAll(query)
		return p
	}
}
