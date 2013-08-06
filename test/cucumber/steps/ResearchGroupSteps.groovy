import static cucumber.api.groovy.EN.*
import pages.LoginPage
import pages.PublicationsPage
import pages.ResearchGroupCreatePage
import pages.ResearchGroupPage
import pages.ResearchGroupShowPage
import pages.ResearchGroupEditarPage
import rgms.member.ResearchGroup
import steps.TestDataAndOperations

Given(~'^the system has no research group entitled "([^"]*)" stored in the system$') { String name ->
	researchGroup = ResearchGroup.findByName(name)
	assert researchGroup == null
}

When(~'^I create a research group named "([^"]*)" with the description "([^"]*)"$') { String name, description ->
	TestDataAndOperations.createResearchGroup(name, description)
}

Then(~'^the research group "([^"]*)" is properly stored by the system$') { String name ->
	researchGroup = ResearchGroup.findByName(name)
	assert researchGroup != null
}

Given(~'^the system has a research group entitled "([^"]*)" with the description "([^"]*)" stored in the system$' ) { String name, description ->
	TestDataAndOperations.createResearchGroup(name, description)
	researchGroup = ResearchGroup.findByName(name)
	assert researchGroup != null
}

Then(~'^the research group "([^"]*)" is not stored again in the system$') { String name ->
	researchGroup = ResearchGroup.findAllByName(name)
	assert researchGroup.size() == 1
}

Then(~'^the research group "([^"]*)" is not stored in the system because exceeds the number of characters allowed$') { String name ->
	researchGroup = ResearchGroup.findAllByName(name)
	assert researchGroup.size() == 0
}

Given(~'^the system has no research group with no name stored in the system$') { ->
	researchGroup = ResearchGroup.findByName("")
	assert researchGroup == null
}

When(~'^I create a research group with no name and with the description "([^"]*)" $') { String arg1 ->
    TestDataAndOperations.createResearchGroup("", arg1)
}

Then(~'^the research group is not stored in the system because it has no name$') { ->
	researchGroup = ResearchGroup.findByName("")
	assert researchGroup == null
}

Given(~'^the system has no research group with name "([^"]*)"$') { String name ->
	researchGroup = ResearchGroup.findByName(name)
	assert researchGroup == null
}

When(~'^I create a research group with  with name "([^"]*)" and with no description$') { String arg1 ->
	TestDataAndOperations.createResearchGroup(arg1, "")
}

Then(~'^the research group with name "([^"]*)" is not stored in the system because it has no description$') { String arg1 ->
	researchGroup = ResearchGroup.findByName(arg1)
	assert researchGroup == null
}

When(~'^i modify the research group entitled "([^"]*)" to "([^"]*)" and its description to "([^"]*)"$') { String oldName, String newName, String newDescription ->
	researchGroup = ResearchGroup.findByName(oldName)
	TestDataAndOperations.editResearchGroup(researchGroup, newName, newDescription)
}

Then(~'^the edited research group "([^"]*)" with description "([^"]*)" is properly stored in the system$') { String name, String description ->
	researchGroup = ResearchGroup.findByName(name)
	assert researchGroup != null
	assert researchGroup.getDescription() == description
}

When(~'^i delete the research group entitled "([^"]*)"$') { String name ->
	researchGroup = ResearchGroup.findByName(name)
	TestDataAndOperations.deleteResearchGroup(researchGroup)
}

Then(~'^the research group "([^"]*)" is properly deleted of the system$') { String name ->
	researchGroup = ResearchGroup.findByName(name)
	assert researchGroup == null
}

When(~'^I create a research group with no name and with the description "([^"]*)"$') { String description ->
	TestDataAndOperations.createResearchGroup("", description)
}

Then(~'^the research group is not stored in the system because is invalid$') {
	->
	researchGroup = ResearchGroup.findByName("")
	assert researchGroup == null
}

When(~'^I create a research group with name "([^"]*)" and with no description$') { String name ->
	TestDataAndOperations.createResearchGroup(name, "")
}

Given(~'^i am at publication menu$') {
	->
	// Express the Regexp above with the code you wish you had
	to LoginPage
	at LoginPage
	page.fillLoginData("admin", "adminadmin")
	at PublicationsPage
}

When(~'^i select the "([^"]*)" option at publications menu$') { String option ->
	at PublicationsPage
	page.select(option)
}

When(~'^i select the new research group option at research group list page$') {
	->
	at ResearchGroupPage
	page.selectNewResearchGroup()
}

Then(~'^i can fill the research group details with name "([^"]*)" and create a new one$') {  String name
	->
	at ResearchGroupCreatePage
	page.fillResearchGroupDetails(name)
	page.clickOnCreate();
	researchGroup = ResearchGroup.findByName(name)
	assert researchGroup != null
}

Given(~'^the system has a Research Group named "([^"]*)" stored in the system$') { String arg1 ->
	to LoginPage
	at LoginPage
	page.fillLoginData("admin", "adminadmin")
	at PublicationsPage

	page.select("Research Group")
	at ResearchGroupPage

	page.selectNewResearchGroup()

	at ResearchGroupCreatePage
	page.fillResearchGroupDetails(arg1)
	page.clickOnCreate();

	researchGroup = ResearchGroup.findByName(arg1)
	assert researchGroup != null
}

Given(~'^i am at Research Group list menu$') { ->
	to ResearchGroupPage
}

When(~'^i select a research group called "([^"]*)"$') { String arg1 ->
    at ResearchGroupPage
	page.showResearchGroup(arg1)
}

Then(~'^the system will show the details of this research group$') { ->
	at ResearchGroupShowPage
}

When(~'^i select the edit option$') {->
	at ResearchGroupShowPage
	page.selectEditResearchGroup()
}

Then(~'^i can change the research group name to "([^"]*)" and save it$') { String name->
    at ResearchGroupEditarPage
	page.changeResearchGroupDetails(name)
	page.selectAlterarResearchGroup()
	at ResearchGroupShowPage
}

When(~'^I modify the description of research group entitled "([^"]*)" to none$') { String oldName ->
    researchGroup = ResearchGroup.findByName(oldName)
    TestDataAndOperations.editResearchGroup(researchGroup, oldName, "")
}

Then(~'^the description of research group entitled "([^"]*)" is not none$') { String name ->
    researchGroup = ResearchGroup.findByName(name)
    assert researchGroup.getDescription().compareTo("") != 0
}

When(~'^I modify the name of research group entitled "([^"]*)" to none$') { String oldName ->
    researchGroup = ResearchGroup.findByName(oldName)
    TestDataAndOperations.editResearchGroup(researchGroup, oldName, "")
}

Then(~'^there is no research group entitled none$') { ->
    researchGroup = ResearchGroup.findByName("")
    assert researchGroup == null
}




Given(~'^the system has a research group entitled "([^"]*)" with childof none$') { String name ->
    TestDataAndOperations.createResearchGroup(name, "description")
    researchGroup = ResearchGroup.findByName(name)
    researchGroup.childOf = null
    researchGroup.save()

    researchGroup = ResearchGroup.findByName(name)
    assert researchGroup.getChildOf() == null
}

When(~'^I modify the childof of research group entitled "([^"]*)" to itself$') { String name ->
    researchGroup = ResearchGroup.findByName(name)
    TestDataAndOperations.editResearchGroupChildOf(researchGroup, researchGroup)
}

Then(~'^the childof of research group "([^"]*)" is none$') { String name ->
    researchGroup = ResearchGroup.findByName(name)
    assert researchGroup.getChildOf() == null
}


Given(~'^i am logged as "([^"]*)"$') { String userName ->
    to LoginPage
    at LoginPage
    page.fillLoginData(userName, "adminadmin")
}


Given(~'^i created a research group entitled "([^"]*)" with childof none$') { String name ->
    at PublicationsPage
    page.select("Research Group")

    at ResearchGroupPage

    page.selectNewResearchGroup()

    at ResearchGroupCreatePage
    page.fillResearchGroupDetails(name)
    page.clickOnCreate();
}


When(~'^i change the field childof to research group called "([^"]*)"$') { String name ->
    at ResearchGroupEditarPage
    researchGroup = ResearchGroup.findByName(name)
    page.changeChildOfTo(researchGroup.getId())
}

When(~'^i click on update button$') { ->
    at ResearchGroupEditarPage
    page.selectAlterarResearchGroup()
}


