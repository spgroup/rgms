import pages.LoginPage
import pages.MembershipPage
import pages.MembershipCreatePage
import steps.TestDataAndOperations
import rgms.member.Membership
import rgms.member.Member
import rgms.member.ResearchGroup

import static cucumber.api.groovy.EN.*

// New Membership
Given(~'^the system has member "([^"]*)" and research group "([^"]*)"$') { String username, groupname ->
	TestDataAndOperations.createMember(username)
	member = Member.findByUsername(username)
	assert member != null
	TestDataAndOperations.createResearchGroup(groupname)
	researchgroup = ResearchGroup.findByName(groupname)
	assert researchgroup != null	
}

And(~'^the system has no membership with member "([^"]*)", research group "([^"]*)" between "([^"]*)" and "([^"]*)"$') { String username, groupname, date1, date2 ->
	member = Member.findByUsername(username)
	researchgroup = ResearchGroup.findByName(groupname)
	membership = Membership.findByMemberAndResearchGroupAndDateJoinedAndDateLeft(member, researchgroup, new Date(date1), new Date(date2))
	assert membership == nulll
}

When(~'^I create a membership with member "([^"]*)", research group "([^"]*)" between "([^"]*)" and "([^"]*)"$') {String username, rgroup, date1, date2 ->
	TestDataAndOperations.createMembership(username)
}

Then(~'^Then the membership with member "([^"]*)", research group "([^"]*)" between "([^"]*)" and "([^"]*)" is created$') {String username, rgroup, date1, date2 ->
	at MembershipCreatePage
	assert Membership.findAllByResearchGroupAndDateJoinedAndDateLeft(rgroup, date1, date2) != null
}

// Delete Membership

Given(~'^the system has membership with member "([^"]*)", research group "([^"]*)" between "([^"]*)" and "([^"]*)"$') { String username, rgroup, date1, date2 ->
	TestDataAndOperations.createMember(username)
	member = Member.findByUsername(username)
	assert member != null
	TestDataAndOperations.createResearchGroup(groupname)
	researchgroup = ResearchGroup.findByName(groupname)
	assert researchgroup != null
	
	membership = Membership.findByMemberAndResearchGroupAndDateJoinedAndDateLeft(member, researchgroup, new Date(date1), new Date(date2))
	assert membership != nulll
	
	assert Membership.findAllByResearchGroupAndDateJoinedAndDateLeft(rgroup, date1, date2) != null
}

When(~'^I delete a membership with member "([^"]*)", research group "([^"]*)" between "([^"]*)" and "([^"]*)"$') {String username, rgroup, date1, date2 ->
	TestDataAndOperations.deleteMembership(username)
}

Then(~'^Then the membership with member "([^"]*)", research group "([^"]*)" between "([^"]*)" and "([^"]*)" is deleted$') {String username, rgroup, date1, date2 ->
	at MembershipPage
	assert Membership.findAllByResearchGroupAndDateJoinedAndDateLeft(rgroup, date1, date2) == null;
}