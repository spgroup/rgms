package rgms

class Membership {
	Member member
	ResearchGroup researchGroup
	Date dateJoined
	Date dateLeft
	
	static belongsTo = Member
	
    static constraints = {
		dateLeft(nullable:true)
    }
	
	static void addMembersToResearchGroup(memberList, researchGroup){
		def listMembership = []	
		for(memberId in memberList){
				def member = Member.findById(memberId)
				def membershipInstance = new Membership(member: member, researchGroup: researchGroup, dateJoined: new Date(), dateLeft: null)
				if(!Membership.findByMemberAndResearchGroup(member, researchGroup)){
					def membership = membershipInstance.save(flush: true)
				}
//				member.researchGroups.add(membership)
//				member.save(flush: true)
//				listMembership.add(membershipInstance)
			}
	}
	
	static void editMembersToResearchGroup(memberList, researchGroup){
		Membership.removeMemberFromResearchGroup(researchGroup)
		Membership.addMembersToResearchGroup(memberList, researchGroup)
	}
	
	static List removeMemberFromResearchGroup(researchGroup){
		def listMembership = Membership.findByResearchGroup(researchGroup)
		for(membership in listMembership){
				membership.delete(flush: true)
		}
	}
}
