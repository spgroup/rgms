import rgms.member.Member
import steps.TestDataAndOperations

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