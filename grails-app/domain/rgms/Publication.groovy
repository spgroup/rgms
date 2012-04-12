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
	
	static List getPublicationsByMembershipList(membershipList){
		def set = [] as Set
		for(membership in membershipList){
			set.addAll(Publication.getPublicationsByMembership(membership))
		}
	}
	
	static List getPublicationsByMembership(membership){
		def publications = membership?.member.publications
		def query = !membership.dateLeft ?
					{ it.publicationDate?.compareTo(membership.dateJoined) > 0 }:
					{ it.publicationDate?.compareTo(membership.dateJoined) > 0  &&
						it.publicationDate?.compareTo(membership.dateLeft) < 0}
		return publications.findAll(query)
	}
}
