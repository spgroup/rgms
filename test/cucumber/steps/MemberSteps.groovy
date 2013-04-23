import pages.LoginPage
import rgms.member.Member
import steps.TestDataAndOperations
import pages.RegisterPage
import pages.MemberCreatePage
import pages.MemberListPage
import pages.MemberViewPage

import static cucumber.api.groovy.EN.*


Given(~'^the system has no member with username "([^"]*)"$') { String username ->
	member = Member.findByUsername(username);
	assert member == null
}

When(~'^I create a member with username "([^"]*)"$') { String username ->
	TestDataAndOperations.createMember(username)
}

Then(~'^the member with username "([^"]*)" is properly stored by the system$') { String username ->
	member = Member.findByUsername(username)
	//assert TestDataAndOperations.memberCompatibleTo(member, username)
	assert member != null
}

Given(~'^I am at the login page$') {->
	to LoginPage
	at LoginPage
	//assert (page.flashmessage?.size() == 0)
	//assert (page.flashmessage == null)
}

When(~'^I fill username and password with "([^"]*)" and "([^"]*)"$') { String login, password ->
	page.fillLoginData(login, password)
}

Then(~'^I am still on the login page with an error message$') { ->
	at LoginPage
	assert page.flashmessage != null
}

Given(~'^I am at the register page$') {->
	to RegisterPage
	at RegisterPage
}

When(~'^I fill the user details with "([^"]*)" "([^"]*)" "([^"]*)" "([^"]*)" "([^"]*)" "([^"]*)" "([^"]*)"$') { String name, username, password1, password2, email, university, status ->
	at RegisterPage
	page.fillUserDetails(name, username, password1, password2, email, university, status)
}

Then(~'^I am still on the register page with the message user created$') {->
	at RegisterPage	
}


Given(~'^the system has member with username "([^"]*)"$') { String username ->
	TestDataAndOperations.createMember(username)
	member = Member.findByUsername(username);
	assert member != null
}

When(~'^I delete a member with username "([^"]*)"$') { String username ->
	TestDataAndOperations.deleteMember(username)
}

Then(~'^the member with "([^"]*)" doesnt exist$') { String username ->
	member = Member.findByUsername(username)
	assert member == null
}



/*Given(~'^the system has member with username "([^"]*)"$') { String username ->
	TestDataAndOperations.createMember(username)
	member = Member.findByUsername(username);
	assert member != null
}*/

When(~'^I create the member with username "([^"]*)"$') { String username ->
	TestDataAndOperations.createMember(username)
}

Then(~'^the member "([^"]*)" is not registered$') { String username ->
	members= Member.findAllByUsername(username)
		assert members.size() == 1
}



Given(~'^I am at the create member page$') {->
	to LoginPage
	at LoginPage
	page.fillLoginData("admin", "adminadmin")
	to MemberCreatePage
	at MemberCreatePage
}

When(~'^I fill the user details with "([^"]*)" "([^"]*)" "([^"]*)" "([^"]*)"$') { String name, username, email, university ->
	at MemberCreatePage
	page.fillMemberDetails(name, username, email, university)
}

Then(~'^I am on the member view page$') { ->
	at MemberViewPage
}


/*Given(~'^I am at the create member page$') {->
	to MemberCreatePage
	at MemberCreatePage
}*/

/*When(~'^I fill the user details with "([^"]*)" "([^"]*)" "([^"]*)" "([^"]*)"$') { String name, username, email, university ->
	at MemberCreatePage
	page.fillMemberDetails(name, username, email, university)
}*/

Then(~'^I am still on the create member page with the error message$') {->
	at MemberCreatePage
	//assert mensagem != null
	
}