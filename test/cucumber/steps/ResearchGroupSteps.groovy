import static cucumber.api.groovy.EN.*
import pages.LoginPage
import pages.PublicationsPage
import pages.ResearchGroupCreatePage
import pages.ResearchGroupPage
import pages.ResearchGroupShowPage
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
	page.select(option)
}

When(~'^i select the new research group option at research group list page$') {
	->
	at ResearchGroupPage
	page.selectNewResearchGroup()
}

Then(~'^i can fill the research group details$') {
	->
	at ResearchGroupCreatePage
	page.fillResearchGroupDetails()
}

Given(~'^the system has a research group stored in the system$') {
	->
}

Given(~'^i am at publications menu$') {
	->
	to LoginPage
	at LoginPage
	page.fillLoginData("admin", "adminadmin")
	at PublicationsPage
}

When(~'^i select "([^"]*)" option at publications menu$') { String arg1 ->
	page.select(arg1)
}

When(~'^i select a research group$') { -> at ResearchGroupPage }

Then(~'^the system will show the details of this research group$') {
	->
	page.showResearchGroup()
	at ResearchGroupShowPage
}
