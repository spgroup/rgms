import java.lang.reflect.Member;
import rgms.member.ResearchGroup;

Given(~'^the system has no news with description "([^"]*)" and date "([^"]*)"$') { String description, @Format("dd/MM/yyyy") Date date ->
	news = News.findByDescriptionAndDate(description, date);
	assert news == null
}

When(~'^I create a news with description "([^"]*)" and date "([^"]*)"$') { String description, @Format("dd/MM/yyyy") Date date ->
	TestDataAndOperations.createNews(description,date)
}

Then(~'^the news  with description  "([^"]*)" and date "([^"]*)" is properly stored by the system$') { String description, @Format("dd/MM/yyyy") Date date ->
	news = News.findByDescriptionAndDate(description, date);
	assert news != null
}

Given(~'^the system has a news with description "([^"]*)" and date "([^"]*)"$') { String description, @Format("dd/MM/yyyy") Date date ->
	TestDataAndOperations.createNews(description,date)
	news = News.findByDescriptionAndDate(description, date);
	assert news != null
}

When(~'^I delete a news with description "([^"]*)" and date "([^"]*)"$') { String description, @Format("dd/MM/yyyy") Date date ->
	TestDataAndOperations.deleteNews(description,date);
}

Then(~'^the news  with "([^"]*)" and date "([^"]*)" doesnt exists$') { String description, @Format("dd/MM/yyyy") Date date ->
	news = News.findByDescriptionAndDate(description, date);
	assert news = null
}

When(~'^I create a news with description "([^"]*)" and date "([^"]*)"$') { String description, @Format("dd/MM/yyyy") Date date ->
	TestDataAndOperations.createNews(description,date);
}

Then(~'^the news  with "([^"]*)" and date "([^"]*)" is not registered$') { String description, @Format("dd/MM/yyyy") Date date ->
	news = News.findByDescriptionAndDate(description, date);
	assert news.size() == 1	
}

Given(~'^the research group "([^"]*)" in the system has no Twitter account associated$') { String groupName ->
	researchGroup = TestDataAndOperations.createResearchGroup(groupName);
	assert researchGroup.twitterAccount == ""
}

When(~'^I associate the account "([^"]*)" to "([^"]*)" group$') { String twitterAcount, String groupName ->
	researchGroup = ResearchGroup.findResearchGroupByGroupName(groupName)
	TestDataAndOperations.editResearchGroupTwitterAccount(researchGroup, twitterAccount)	
	researchGroup = ResearchGroup.findResearchGroupByGroupName(groupName)
	assert researchGroup.twitterAccount == twitterAccount		
}

Then(~'^the news of "([^"]*)" research group can be retrieved by a Twitter post of "([^"]*)" acount$') { String groupName ->
	researchGroup = ResearchGroup.findResearchGroupByGroupName(groupName)
	TwitterConnection conn = new TwitterConnection(acount:researchGroup.twitterAcount)
	listNews = conn.listNews()
	assert listNews != null
}