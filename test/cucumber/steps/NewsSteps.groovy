import pages.PublicationsPage
import pages.ResearchGroup.ResearchGroupCreatePage
import pages.ResearchGroup.ResearchGroupPage
import pages.news.NewsCreatePage
import pages.news.NewsPage
import pages.news.NewsShowPage
import rgms.member.ResearchGroup
import rgms.news.News
import steps.NewsTestDataAndOperations
import steps.TestDataAndOperations

import static cucumber.api.groovy.EN.*

Given(~'^the system has no news with description "([^"]*)" and date "([^"]*)" for "([^"]*)" research group$') { String description, String date, String group ->
    assert !NewsTestDataAndOperations.checkExistingNews(description,date,group)
}

When(~'^I create a news with description "([^"]*)" and date "([^"]*)" for "([^"]*)" research group$') { String description, String date, String group ->
    if(NewsTestDataAndOperations.checkValidDate(date)) {
        Date dateAsDateObj = Date.parse("dd-MM-yyyy", date)
        def researchGroup = ResearchGroup.findByName(group)
        NewsTestDataAndOperations.createNews(description, dateAsDateObj, researchGroup)
    }
}

def findNewsByDescriptionDateAndGroup(String description, String date, String group) {
    Date dateAsDateObj = Date.parse("dd-MM-yyyy", date)
    def researchGroup = ResearchGroup.findByName(group)
    news = News.findByDescriptionAndDateAndResearchGroup(description, dateAsDateObj, researchGroup)
    return news
}

Then(~'^the news  with description  "([^"]*)", date "([^"]*)" and "([^"]*)" research group is properly stored by the system$') { String description, String date, String group ->
    news = findNewsByDescriptionDateAndGroup(description, date, group)
    assert news != null
}

Given(~'^the system has a news with description "([^"]*)" and date "([^"]*)" for "([^"]*)" research group$') { String description, String date, String group ->
    Date dateAsDateObj
    //noinspection GroovyAssignabilityCheck,GrReassignedInClosureLocalVar
    (dateAsDateObj, researchGroup) = createAndGetResearchGroup(date, group)
    //noinspection GroovyAssignabilityCheck
    NewsTestDataAndOperations.createNews(description, dateAsDateObj, researchGroup)
    news = News.findByDescriptionAndDateAndResearchGroup(description, dateAsDateObj, researchGroup)
    assert news != null
}

private List createAndGetResearchGroup(String date, String group) {
    Date dateAsDateObj = Date.parse("dd-MM-yyyy", date)
    researchGroup = TestDataAndOperations.createAndGetResearchGroupByName(group)
    [dateAsDateObj, researchGroup]
}

When(~'^I delete the news with description "([^"]*)" and date "([^"]*)" for "([^"]*)" research group$') { String description, String date, String group ->
    Date dateAsDateObj = Date.parse("dd-MM-yyyy", date)
    def researchGroup = ResearchGroup.findByName(group)
    NewsTestDataAndOperations.deleteNews(description, dateAsDateObj, researchGroup)
}

Then(~'^the news with "([^"]*)" and date "([^"]*)" doesnt exists to "([^"]*)" research group$') { String description, String date, String group ->
    assert !NewsTestDataAndOperations.checkExistingNews(description,date,group)
}

Then(~'^the news  with "([^"]*)" and date "([^"]*)" is not registered to "([^"]*)" research group$') { String description, String date, String group ->
    Date dateAsDateObj
    //noinspection GroovyAssignabilityCheck,GrReassignedInClosureLocalVar
    (dateAsDateObj, researchGroup) = createAndGetResearchGroup(date, group)
    //noinspection GroovyAssignabilityCheck
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

def ifExistsSelectNewsByResearchGroup(String groupName) {
    researchGroup = ResearchGroup.findByName(groupName)
    newsByResearchGroup = News.getCurrentNews(researchGroup)
    assert newsByResearchGroup != null
    return newsByResearchGroup
}

Then(~'^news of "([^"]*)" research group has been updated$') { String groupName ->
    newsByResearchGroup = ifExistsSelectNewsByResearchGroup(groupName)
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
    newsByResearchGroup = ifExistsSelectNewsByResearchGroup(groupName)
    while  (newsByResearchGroup.size() > 0){
        //noinspection GrReassignedInClosureLocalVar
        news = newsByResearchGroup.pop()
        newsByResearchGroup.each {
           assert (it.date != news.date) || (it.description != news.description)
        }
    }
}

And(~'^the research group "([^"]*)" news list is empty$'){ String groupName ->
    newsByResearchGroup = ifExistsSelectNewsByResearchGroup(groupName)
    assert newsByResearchGroup.size() == 0
}

Given(~'^the system has a news stored with description "([^"]*)"$') { String description ->
    to NewsPage
    page.selectCreateNews()

    at NewsCreatePage
    page.fillNewDetails(description)
    page.clickOnCreate();

    news = News.findByDescription(description)
    assert news != null
}

Given(~'^the system has no stored news$') { ->
    News.withTransaction { status -> News.findAll().each { n -> n.delete() } }
    assert News.count() == 0
}

//#if ($Report && $HTML)
And (~'^I select the option Export to HTML at the News list page$'){ ->
    at NewsPage
    page.selectExportHTMLReport()
}

Then(~'^The system generate a HTML report with the news "([^"]*)" in it$'){ String news ->
    assert page.reportContainsNews(news)
}

Then(~'^I can not select the option Export to HTML at the News list page$'){ ->
    assert !page.canSelectExportHTMLReport()
}
//#end


When(~'^I select the novo noticias option at the news page$') {->
    selectNewNewsInNewsPage()
}

def selectNewNewsInNewsPage(){
    at NewsPage
    page.selectCreateNews()
    at NewsCreatePage
}

Then(~'^I can fill the news details$') { ->
    at NewsCreatePage
    page.fillNewDetails("essa eh a descricao")
    page.clickOnCreate()
    assert NewsTestDataAndOperations.checkExistingNewsByDescription("essa eh a descricao")
}

When(~'^I try to create a news with description "([^"]*)" and date "([^"]*)" for "([^"]*)" research group$') { String description, String date, String group ->
    if(NewsTestDataAndOperations.checkValidDate(date)) {
        Date dateAsDateObj = Date.parse("dd-MM-yyyy", date)
        def researchGroup = ResearchGroup.findByName(group)
        NewsTestDataAndOperations.createNews(description, dateAsDateObj, researchGroup)
    }
}

Then(~'^the news with description "([^"]*)", date "([^"]*)" and "([^"]*)" research group is not stored by the system because it is invalid$') { String description, String date, String group ->
    news = findNewsByDescriptionDateAndGroup(description, date, group)
    assert news == null
}

When(~'^I edit the news with description "([^"]*)" to "([^"]*)", date "([^"]*)" and "([^"]*)" research group$') { String description, String newDescr, String date, String group ->
    Date dateAsDateObj = Date.parse("dd-MM-yyyy", date)
    def researchGroup = ResearchGroup.findByName(group)
    NewsTestDataAndOperations.editNewsDescription(description, newDescr, dateAsDateObj, researchGroup)
}

Then(~'^the news "([^"]*)", date "([^"]*)" and "([^"]*)" research group is properly updated by the system$') { String description, String date, String group ->
    assert NewsTestDataAndOperations.checkExistingNews(description,date,group)
}


Given(~'^I select the news page$') { ->
    page.select("News")
}

And(~'^the news "([^"]*)" is stored in the system$') { String description ->
    selectNewNewsInNewsPage()

    page.fillNewDetails(description)
    page.clickOnCreate()
    assert NewsTestDataAndOperations.checkExistingNewsByDescription(description)
}

When(~'^I select to view the news "([^"]*)" in resulting list$') { String title ->
    page.selectViewNew(title)
    at NewsShowPage
}

And(~'I select the option to remove in news show page$') {->
    at NewsShowPage
    page.remove()
}

Then(~'^the news "([^"]*)" is properly removed by the system$') { String description ->
    assert !NewsTestDataAndOperations.checkExistingNewsByDescription(description)
}

// eh necessario a criacao de um research group para poder criar uma new
And(~'^I create a research group because it is necessary$') {->
    at PublicationsPage
    page.select("Research Group")
    at ResearchGroupPage
    page.selectNewResearchGroup()
    at ResearchGroupCreatePage
    page.fillResearchGroupDetails("grupo")
    page.clickOnCreate();
    researchGroup = ResearchGroup.findByName("grupo")
    assert researchGroup != null
    to PublicationsPage
    at PublicationsPage
}