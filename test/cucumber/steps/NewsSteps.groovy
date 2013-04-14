import rgms.member.ResearchGroup;
import rgms.news.News;
import steps.TestDataAndOperations
import static cucumber.api.groovy.EN.*

Given(~'^the system has no news with description "([^"]*)" and date "([^"]*)"$') { String description, String date ->
	Date dateAsDateObj = Date.parse("dd-MM-yyyy",date)		   
	news = News.findByDescriptionAndDate(description, dateAsDateObj);
	assert news == null
}

When(~'^I create a news with description "([^"]*)" and date "([^"]*)"$') { String description, String date ->
	Date dateAsDateObj = Date.parse("dd-MM-yyyy",date)
	TestDataAndOperations.createNews(description,dateAsDateObj)
}

Then(~'^the news  with description  "([^"]*)" and date "([^"]*)" is properly stored by the system$') { String description, String date ->
	Date dateAsDateObj = Date.parse("dd-MM-yyyy",date)
	news = News.findByDescriptionAndDate(description, dateAsDateObj);
	assert news != null
}

Given(~'^the system has a news with description "([^"]*)" and date "([^"]*)"$') { String description, String date ->
	Date dateAsDateObj = Date.parse("dd-MM-yyyy",date)
	TestDataAndOperations.createNews(description,dateAsDateObj)
	news = News.findByDescriptionAndDate(description, dateAsDateObj);
	assert news != null
}

When(~'^I delete a news with description "([^"]*)" and date "([^"]*)"$') { String description, String date ->
	Date dateAsDateObj = Date.parse("dd-MM-yyyy",date)
	TestDataAndOperations.deleteNews(description,dateAsDateObj);
}

Then(~'^the news  with "([^"]*)" and date "([^"]*)" doesnt exists$') { String description, String date ->
	Date dateAsDateObj = Date.parse("dd-MM-yyyy",date)
	news = News.findByDescriptionAndDate(description, dateAsDateObj);
	assert news == null
}

Then(~'^the news  with "([^"]*)" and date "([^"]*)" is not registered$') { String description, String date ->
	Date dateAsDateObj = Date.parse("dd-MM-yyyy",date)
	newsList = News.findAllByDescriptionAndDate(description, dateAsDateObj);
	assert newsList.size() == 1
}

Given(~'^the research group "([^"]*)" in the system has no Twitter account associated$') { String groupName ->
	TestDataAndOperations.createResearchGroup(groupName)
	researchGroup = ResearchGroup.findByName(groupName)
	assert researchGroup != null
}

When(~'^I associate the account "([^"]*)" to "([^"]*)" group$') { String twitter, String groupName ->
	researchGroup = ResearchGroup.findByName(groupName)
	researchGroup.twitter = twitter
	researchGroup.save()
	//TestDataAndOperations.editResearchGroupTwitter(researchGroup, twitter)	
}

Then(~'^"([^"]*)" research group has a twitter account "([^"]*)" registred$') { String groupName, String twitter ->
	researchGroup = ResearchGroup.findByName(groupName)
	assert researchGroup.twitter == twitter
}

Given(~'^the research group "([^"]*)" in the system has Twitter account "([^"]*)" associated$') { String groupName, String twitter ->
	
}

When(~'^I request to update the news from Twitter "([^"]*)"$') { String twitter ->
	// Express the Regexp above with the code you wish you had
	//throw new PendingException()
}

Then(~'^news of "([^"]*)" research group has been updated$') { String groupName ->
	// Express the Regexp above with the code you wish you had
	//throw new PendingException()
}

