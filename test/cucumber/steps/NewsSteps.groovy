import rgms.member.ResearchGroup
import rgms.news.News
import steps.TestDataAndOperations

import static cucumber.api.groovy.EN.*

Given(~'^the system has no news with description "([^"]*)" and date "([^"]*)" for "([^"]*)" research group$') { String description, String date, String group ->
    Date dateAsDateObj = Date.parse("dd-MM-yyyy", date)
    researchGroup = TestDataAndOperations.createAndGetResearchGroupByName(group)
    news = News.findByDescriptionAndDateAndResearchGroup(description, dateAsDateObj, researchGroup)
    assert news == null
}

When(~'^I create a news with description "([^"]*)" and date "([^"]*)" for "([^"]*)" research group$') { String description, String date, String group ->
    Date dateAsDateObj = Date.parse("dd-MM-yyyy", date)
    def researchGroup = ResearchGroup.findByName(group)
    TestDataAndOperations.createNews(description, dateAsDateObj, researchGroup)
}

Then(~'^the news  with description  "([^"]*)", date "([^"]*)" and "([^"]*)" research group is properly stored by the system$') { String description, String date, String group ->
    Date dateAsDateObj = Date.parse("dd-MM-yyyy", date)
    def researchGroup = ResearchGroup.findByName(group)
    news = News.findByDescriptionAndDateAndResearchGroup(description, dateAsDateObj, researchGroup);
    assert news != null
}

Given(~'^the system has a news with description "([^"]*)" and date "([^"]*)" for "([^"]*)" research group$') { String description, String date, String group ->
    Date dateAsDateObj = Date.parse("dd-MM-yyyy", date)
    researchGroup = TestDataAndOperations.createAndGetResearchGroupByName(group)
    TestDataAndOperations.createNews(description, dateAsDateObj, researchGroup)
    news = News.findByDescriptionAndDateAndResearchGroup(description, dateAsDateObj, researchGroup);
    assert news != null
}

When(~'^I delete a news with description "([^"]*)" and date "([^"]*)" of "([^"]*)" research group$') { String description, String date, String group ->
    Date dateAsDateObj = Date.parse("dd-MM-yyyy", date)
    def researchGroup = ResearchGroup.findByName(group)
    TestDataAndOperations.deleteNews(description, dateAsDateObj, researchGroup);
}

Then(~'^the news  with "([^"]*)" and date "([^"]*)" doesnt exists to "([^"]*)" research group$') { String description, String date, String group ->
    Date dateAsDateObj = Date.parse("dd-MM-yyyy", date)
    researchGroup = TestDataAndOperations.createAndGetResearchGroupByName(group);
    news = News.findByDescriptionAndDateAndResearchGroup(description, dateAsDateObj, researchGroup);
    assert news == null
}

Then(~'^the news  with "([^"]*)" and date "([^"]*)" is not registered to "([^"]*)" research group$') { String description, String date, String group ->
    Date dateAsDateObj = Date.parse("dd-MM-yyyy", date)
    researchGroup = TestDataAndOperations.createAndGetResearchGroupByName(group);
    newsList = News.findAllByDescriptionAndDateAndResearchGroup(description, dateAsDateObj, researchGroup);
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
    TestDataAndOperations.createResearchGroup(groupName)
    researchGroup = ResearchGroup.findByName(groupName)
    assert researchGroup.twitter == twitter
}

When(~'^I request to update the news from Twitter "([^"]*)"$') { String twitter ->
    TestDataAndOperations.requestNewsFromTwitter(researchGroup)
}

Then(~'^news of "([^"]*)" research group has been updated$') { String groupName ->
    researchGroup = ResearchGroup.findByName(groupName)
    assert researchGroup.news.size() == 10
}
