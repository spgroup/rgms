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
	java.text.DateFormat df = new java.text.SimpleDateFormat("dd-MM-yyyy");
	// http://docs.oracle.com/javase/6/docs/api/java/text/SimpleDateFormat.html

	member = Member.findByUsername(username)
	researchgroup = ResearchGroup.findByName(groupname)
	membership = Membership.findByMemberAndResearchGroupAndDateJoinedAndDateLeft(member, researchgroup, df.parse(date1), df.parse(date2))
	assert membership == null
}

When(~'^I create a membership with member "([^"]*)", research group "([^"]*)" between "([^"]*)" and "([^"]*)"$') {String username, groupname, date1, date2 ->
	TestDataAndOperations.createMembership(username, groupname, date1, date2)
}

Then(~'^the membership with member "([^"]*)", research group "([^"]*)" between "([^"]*)" and "([^"]*)" is created$') {String username, groupname, date1, date2 ->
	java.text.DateFormat df = new java.text.SimpleDateFormat("dd-MM-yyyy");
	member = Member.findByUsername(username)
	researchgroup = ResearchGroup.findByName(groupname)
	assert Membership.findByMemberAndResearchGroupAndDateJoinedAndDateLeft(member, researchgroup, df.parse(date1), df.parse(date2)) != null
}

// Delete Membership

Given(~'^the system has membership with member "([^"]*)", research group "([^"]*)" between "([^"]*)" and "([^"]*)"$') { String username, groupname, date1, date2 ->
	//TODO Criar um Membership com o member e researchgroup abaixo,
	//		salvar, e aí sim pedir para procurar e fazer o assert membership != null
	//		O problema é que métodos de criação de Membership não funcionam ainda.
	
	java.text.DateFormat df = new java.text.SimpleDateFormat("dd-MM-yyyy");
	
	TestDataAndOperations.createMember(username)
	member = Member.findByUsername(username)
	assert member != null
	TestDataAndOperations.createResearchGroup(groupname)
	researchgroup = ResearchGroup.findByName(groupname)
	assert researchgroup != null

	membership = Membership.findByMemberAndResearchGroupAndDateJoinedAndDateLeft(member, researchgroup, df.parse(date1), df.parse(date1))
	assert membership != null

	assert Membership.findAllByResearchGroupAndDateJoinedAndDateLeft(rgroup, date1, date2) != null
}

When(~'^I delete a membership with member "([^"]*)", research group "([^"]*)" between "([^"]*)" and "([^"]*)"$') {String username, rgroup, date1, date2 ->
	TestDataAndOperations.deleteMembership(username)
}

Then(~'^Then the membership with member "([^"]*)", research group "([^"]*)" between "([^"]*)" and "([^"]*)" is deleted$') {String username, rgroup, date1, date2 ->
	at MembershipPage
	assert Membership.findAllByResearchGroupAndDateJoinedAndDateLeft(rgroup, date1, date2) == null;
}