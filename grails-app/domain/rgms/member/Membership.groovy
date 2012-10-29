package rgms.member

import java.util.List;


class Membership {
	Member member
	ResearchGroup researchGroup
	Date dateJoined
	Date dateLeft
	
	static belongsTo = Member
	
    static constraints = {
		dateLeft(nullable:true)
    }
	
	static List getCurrentMemberships(researchGroup){
		def list = Membership.findAllByResearchGroupAndDateLeft(researchGroup, null)
		return list
	}
	
	static List getAllMembers(researchGroup){
		def list = Membership.findAllByResearchGroup(researchGroup)
		return list
	}
	
	static void editMembersToResearchGroup(memberList, researchGroup){
		def researchGroupCurrentMemberships = Membership.findAllByResearchGroupAndDateLeft(researchGroup, null)
		for(memberId in memberList){
				def memberInstance = Member.findById(memberId)
				def membership = researchGroupCurrentMemberships.find{ it.member.id == (memberId as Long)}
				researchGroupCurrentMemberships.each { println("Id "+it.member.id)}
				println("Membership"+membership)
				println("MemberId"+memberId)
				println("research"+researchGroupCurrentMemberships)
				if(!membership){
					def membershipInstance = new Membership(member: memberInstance, researchGroup: researchGroup, dateJoined: new Date(), dateLeft: null)
					membership = membershipInstance?.save(flush: true)
				}else{
					println("Member already in")
					
					researchGroupCurrentMemberships.remove(membership)
				}
		}
		for(membership in researchGroupCurrentMemberships){
			membership.dateLeft = new Date()
			membership.save(flush: true)
		}
	}
}
