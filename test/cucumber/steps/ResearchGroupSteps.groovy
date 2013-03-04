import rgms.member.ResearchGroup;
import steps.TestDataAndOperations

import static cucumber.api.groovy.EN.*

Given(~'^the system has no research group entitled "([^"]*)" stored in the system$') { String name ->
	researchGroup = ResearchGroup.findByName(name)
	assert researchGroup == null
}

When(~'^I create a research group named "([^"]*)" with the description "([^"]*)"$') { String name, description ->
	TestDataAndOperations.createResearchGroup(name, description)
}

Then(~'^the reserach group "([^"]*)" is properly stored by the system$') { String name ->
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
Given(~'^the research group "([^"]*)" has a membership with a member with username "([^"]*)" stored in the system$') { String name, username ->
	TestDataAndOperations.createResearchGroupMembershipWithMember(name, username)
}

Given(~'^the member with username "([^"]*)" is associated with a publication titled "([^"]*)", with date "([^"]*)" stored in the system$') { String username, String title, String date ->
	TestDataAndOperations.createMemberPublication(username, title, date)
}

When(~'^I get publications from a research group$') {
	->
	// NOP
}

Then(~'^the members publications of the research group "([^"]*)" are returned$') { String arg1 ->
	researchGroup = ResearchGroup.findAllByName(name)
	assert ResearchGroup.getPublications(researchGroup) != null
}
