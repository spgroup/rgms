import rgms.member.ResearchGroup
import rgms.news.News
import steps.TestDataAndOperations

import static cucumber.api.groovy.EN.*

Given(~'^the system has no news with description "([^"]*)" and date "([^"]*)" for "([^"]*)" research group$') { String description, String date, String group ->
    assert !TestDataAndOperations.checkExistingNews(description,date,group)
}

When(~'^I create a news with description "([^"]*)" and date "([^"]*)" for "([^"]*)" research group$') { String description, String date, String group ->
    Date dateAsDateObj = Date.parse("dd-MM-yyyy", date)
    def researchGroup = ResearchGroup.findByName(group)
    TestDataAndOperations.createNews(description, dateAsDateObj, researchGroup)
}

Then(~'^the news  with description  "([^"]*)", date "([^"]*)" and "([^"]*)" research group is properly stored by the system$') { String description, String date, String group ->
    Date dateAsDateObj = Date.parse("dd-MM-yyyy", date)
    def researchGroup = ResearchGroup.findByName(group)
    news = News.findByDescriptionAndDateAndResearchGroup(description, dateAsDateObj, researchGroup)
    assert news != null
}

Given(~'^the system has a news with description "([^"]*)" and date "([^"]*)" for "([^"]*)" research group$') { String description, String date, String group ->
    Date dateAsDateObj = Date.parse("dd-MM-yyyy", date)
    researchGroup = TestDataAndOperations.createAndGetResearchGroupByName(group)
    TestDataAndOperations.createNews(description, dateAsDateObj, researchGroup)
    news = News.findByDescriptionAndDateAndResearchGroup(description, dateAsDateObj, researchGroup)
    assert news != null
}

When(~'^I delete the news with description "([^"]*)" and date "([^"]*)" for "([^"]*)" research group$') { String description, String date, String group ->
    Date dateAsDateObj = Date.parse("dd-MM-yyyy", date)
    def researchGroup = ResearchGroup.findByName(group)
    TestDataAndOperations.deleteNews(description, dateAsDateObj, researchGroup)
}

Then(~'^the news with "([^"]*)" and date "([^"]*)" doesnt exists to "([^"]*)" research group$') { String description, String date, String group ->
    assert !TestDataAndOperations.checkExistingNews(description,date,group)
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
    assert researchGroup.twitter == null
}

When(~'^I associate the account "([^"]*)" to "([^"]*)" group$') { String twitter, String groupName ->
    researchGroup = ResearchGroup.findByName(groupName)
    //assert researchGroup != null
    //researchGroup.twitter = twitter
    //researchGroup.save()
    //TestDataAndOperations.editResearchGroupTwitter(researchGroup, twitter)
    TestDataAndOperations.editResearchGroupTwitterAcount(researchGroup, twitter)

    assert researchGroup.getTwitter() == twitter
}

Then(~'^"([^"]*)" research group has a twitter account "([^"]*)" registered$') { String groupName, String twitter ->
    researchGroup = ResearchGroup.findByName(groupName)
    assert researchGroup.getTwitter() == twitter
}

Given(~'^the research group "([^"]*)" in the system has a Twitter account "([^"]*)" associated$') { String groupName, String twitter ->
    TestDataAndOperations.createAndGetResearchGroupByNameWithTwitter(groupName, twitter)
    researchGroup = ResearchGroup.findByName(groupName)
    assert researchGroup != null
    assert researchGroup.getTwitter() == twitter
}

When(~'^I request to update the news from Twitter to research group "([^"]*)"$') { String nomeRGroup ->
    researchGroup = ResearchGroup.findByName(nomeRGroup)
    TestDataAndOperations.requestNewsFromTwitter(researchGroup)
}

Then(~'^news of "([^"]*)" research group has been updated$') { String groupName ->
    researchGroup = ResearchGroup.findByName(groupName)
    newsByResearchGroup = News.getCurrentNews(researchGroup)
    //assert researchGroup.getNews() != null
    //assert researchGroup.news.size() > 0
    assert newsByResearchGroup != null
    assert newsByResearchGroup.size() > 0
}

And(~'^twitter account associated with "([^"]*)" research group has been updated once$'){ String groupName ->
    researchGroup = ResearchGroup.findByName(groupName)
    TestDataAndOperations.requestNewsFromTwitter(researchGroup)
    newsByResearchGroup = News.getCurrentNews(researchGroup)
    assert newsByResearchGroup != null
    assert newsByResearchGroup.size() > 0
}

Then(~'^there is no duplicated news in Twitter account associated with research group "([^"]*)"$'){String groupName ->
    researchGroup = ResearchGroup.findByName(groupName)
    newsByResearchGroup = News.getCurrentNews(researchGroup)
    assert newsByResearchGroup != null
    while  (newsByResearchGroup.size() > 0){
        news = newsByResearchGroup.pop()
        newsByResearchGroup.each {
           assert (it.date != news.date) || (it.description != news.description)
        }
    }
}

And(~'^the research group "([^"]*)" news list is empty$'){ String groupName ->
    researchGroup = ResearchGroup.findByName(groupName)
    newsByResearchGroup = News.getCurrentNews(researchGroup)
    assert newsByResearchGroup != null
    assert newsByResearchGroup.size() == 0
}