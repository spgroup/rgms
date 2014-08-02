import pages.LoginPage
import pages.RegisterPage
import pages.member.MemberCreatePage
import pages.member.MemberListPage
import pages.member.MemberPage
import pages.member.MemberViewPage
import rgms.authentication.User
import rgms.member.Member
import steps.MemberTestDataAndOperations

import static cucumber.api.groovy.EN.*




When(~'^I create a member with username, phone "([^"]*)" "([^"]*)"$') { String username, phone ->
    MemberTestDataAndOperations.createMember(username, phone)
}



Then(~'^the system has no member with a username "([^"]*)"$') { String username ->
    user = User.findByUsername(username);
    member = user?.author
    assert member == null
}





Then(~'^I am still on the login page with an error message$') { ->
    at LoginPage
    assert page.readFlashMessage() != null
}

Given(~'^I am at the register page$') { ->
    to RegisterPage
    at RegisterPage
}

When(~'^I fill the user details with a name, username, passoword1, password2, email, university, status "([^"]*)" "([^"]*)" "([^"]*)" "([^"]*)" "([^"]*)" "([^"]*)" "([^"]*)"$') { String name, username, password1, password2, email, university, status ->
    at RegisterPage
    page.fillUserDetails(name, username, password1, password2, email, university, status)
}

Then(~'^I am still on the register page with the message user created$') { ->
    at RegisterPage
}


Given(~'^the system has member with username "([^"]*)"$') { String username ->
    MemberTestDataAndOperations.createMember(username, "")
    user = User.findByUsername(username);
    member = user?.author
    assert member != null
}

When(~'^I delete a member with username "([^"]*)"$') { String username ->
    MemberTestDataAndOperations.deleteMember(username)
}

Then(~'^the member with "([^"]*)" doesnt exist$') { String username ->
    user = User.findByUsername(username);
    member = user?.author
    assert member == null
}

/*Given(~'^the system has member with username "([^"]*)"$') { String username ->
    TestDataAndOperations.createMember(username)
    user = User.findByUsername(username);
    member = user?.author
    assert member != null
}*/

When(~'^I create the member with username "([^"]*)"$') { String username ->
    MemberTestDataAndOperations.createMember(username, "")
}


Then(~'^the member "([^"]*)" is not registered$') { String username ->
    users = User.findAllByUsername(username);
    assert users.size() == 1
}

//TODO duplicação temporária devido à confusão conceitual entre user e member
Then(~'^the member named "([^"]*)" is not registered$') { String name ->
    members = Member.findAllByName(name);
    assert members.size() == 0
}


Given(~'^I am at the create member page$') { ->
    to LoginPage
    at LoginPage
    page.fillLoginData("admin", "adminadmin")
    to MemberCreatePage
    at MemberCreatePage
}

When(~'^I fill the user details with "([^"]*)" "([^"]*)" "([^"]*)" "([^"]*)"$') { String name, username, email, university ->
    page.fillMemberDetails(name, username, email, university, "")
}

When(~'^I fill some user details with "([^"]*)" "([^"]*)" "([^"]*)" "([^"]*)"$') { String name, username, email, university ->
    page.fillSomeMemberDetails(name, username, email, university)
}

Then(~'^I am on the member show page$') { ->
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

//TODO verificação teria que ser específica, bem menos parcial do que a abaixo
Then(~'^I am still on the create member page with the error message$') { ->
    at MemberCreatePage
    //assert mensagem != null

}

//When(~'^I fill many user details with "([^"]*)" "([^"]*)" "([^"]*)" "([^"]*)" "([^"]*)"$') { String name, username, email, university, additionalInfo ->
//    page.fillMemberDetails(name, username, email, university, additionalInfo)
//}

When(~'^I fill user details with "([^"]*)" "([^"]*)" "([^"]*)" "([^"]*)"$') { String name, username, email, university ->
    page.fillSomeMemberDetails(name, username, email, university)
}

When(~"^I view the member list\$") { ->
    members = Member.findAll()
    assert members != null
}

Then(~'my list members contains member "([^"]*)"$') { String username ->
    members = Member.findAll()
    assert MemberTestDataAndOperations.containsMember(username)
}

Then(~'the member with username "([^"]*)" is created$') { String username ->
    user = User.findByUsername(username)
    member = user?.author
    assert member != null
}

Then(~'^I see default data filled on create form$') { ->
    at MemberCreatePage
    defaultUniversity = "Federal University of Pernambuco"
    defaultCity = "Recife"
    assert page.compareMemberUniversity(defaultUniversity) && page.compareMemberCity(defaultCity)
}

Then(~'^I see default data filled on register form$') { ->
    at RegisterPage
    defaultUniversity = "Federal University of Pernambuco"
    assert page.compareMemberUniversity(defaultUniversity)
}

Given(~'^the system has member with email "([^"]*)"$') { String email ->
    String name = "Rodolfo"
    MemberTestDataAndOperations.createMemberWithEmail(name, email)
    member = Member.findByEmail(email)
    assert member.name == name
}

When(~'^I try to create the member "([^"]*)" with email "([^"]*)"$') { String name, String email ->
    MemberTestDataAndOperations.createMemberWithEmail(name, email)
    //member = Member.findByEmail(email)
    //assert member.name == name
}



//#if ($memberListAndPageImprovement)






Given(~'^I am at the member List page$'){ ->
    to LoginPage
    at LoginPage
    page.fillLoginData("admin", "adminadmin")
    to MemberListPage
    at MemberListPage

}
And(~'^there is "([^"]*)" members to order by name$'){String numberOfMembers ->
    assert Integer.parseInt(numberOfMembers)==MemberTestDataAndOperations.members.size()
}


When(~'^I click the attribute username of member$'){ ->

    at MemberListPage
    page.clickOnUsernameOrder()
    at MemberListPage
}


Then(~'^the system will display the list of "([^"]*)" members orderned$'){String numberOfMembers ->
    at MemberListPage
    MemberTestDataAndOperations.orderMembers(Integer.parseInt(numberOfMembers),"username")

}


Given(~'^the system has no member with username "([^"]*)"$') { String username ->
    to LoginPage
    at LoginPage
    page.fillLoginData("admin", "adminadmin")
    user = User.findByUsername(username);
    member = user?.author
    assert member == null
    ok = MemberTestDataAndOperations.sendEmailToMember(username)
    assert ok== true
}

When(~'^I create a member with username "([^"]*)"$') { String username ->
    MemberTestDataAndOperations.createMember(username, "")
}

Then(~'^the member with username "([^"]*)" is properly stored by the system$') { String username ->
    user = User.findByUsername(username);
    member = user?.author
    //assert TestDataAndOperations.memberCompatibleTo(member, username)
    assert member != null
}

And(~'^the system will send a welcoming email to the new "([^"]*)"$'){String username ->
    ok = MemberTestDataAndOperations.sendEmailToMember(username)
    assert ok== true
}


Given(~'^the administrator logs at system$'){ ->

    to LoginPage
    at LoginPage
    page.fillLoginData("admin", "adminadmin")

}
And(~'^there is "([^"]*)" of new not approved members$'){String numberOfMembers ->

   assert Integer.parseInt(numberOfMembers)==MemberTestDataAndOperations.newMembers.size()
}

/*When(~'^I am at the member List page$'){
    to MemberListPage
    at MemberListPage
}*/

Then(~'^I see the "([^"]*)" new and the not approved members$'){ String number ->
    assert Integer.parseInt(number)==page.returnNumberOfNewMembers()
}

Given(~'^I am at the login page$') { ->
    to LoginPage
    at LoginPage
    //assert (page.flashmessage?.size() == 0)
    //assert (page.flashmessage == null)
}

When(~'^I fill username and password with "([^"]*)" and "([^"]*)"$') { String login, password ->
    page.fillLoginData(login, password)
}

Then(~'^I see a link to logout$'){
    to MemberListPage
    at MemberListPage
    page.checkLogoutLink()
}


When(~'^I click to select the "([^"]*)"$'){ String username ->
    to MemberListPage
    at MemberListPage
    user = User.findByUsername(username);
    member = user?.author
    page.clickOnName(String.valueOf(member.id))
}

Then(~'^the administrator access the "([^"]*)" profile'){String name->
    to MemberPage
    at MemberPage
    page.compareName(name)
}


//#end