package rgms

class Membership {
	Member member
	ResearchGroup researchGroup
	Date dateJoined
	Date dateLeft
	
    static constraints = {
    }
	
	static List addMembersToResearchGroup(memberList, researchGroup){
		def listMembership = []	
		for(memberId in memberList){
				def member = Member.findById(memberId)
				def membershipInstance = new Membership(member: member, researchGroup: researchGroup, dateJoined: new Date(), dateLeft: null)
				def membership = membershipInstance.save(flush: true)
//				member.researchGroups.add(membership)
//				member.save(flush: true)
				listMembership.add(membershipInstance)
			}
		
	}
}
