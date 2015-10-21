import cucumber.runtime.PendingException
import pages.Conferencia.ConferenciaCreatePage
import pages.Conferencia.ConferenciaPage
import pages.LoginPage
import pages.PublicationsPage
import rgms.member.Member
import rgms.publication.Conferencia
import steps.TestDataAndOperations
import steps.TestDataAndOperationsPublication
import steps.ConferenciaTestDataAndOperations

import static cucumber.api.groovy.EN.*

Given(~'^the system has no conferencia entitled "([^"]*)"$') { String title ->
    conferencia = Conferencia.findByTitle(title)
    assert conferencia == null
}

When(~'^I create the conferencia "([^"]*)" with file name "([^"]*)"$') { String title, String filename ->
    ConferenciaTestDataAndOperations.createConferencia(title, filename)
}

Then(~'^the conferencia "([^"]*)" is properly stored by the system$') { String title ->
    conferencia = Conferencia.findByTitle(title)
    assert ConferenciaTestDataAndOperations.conferenciaCompatibleTo(conferencia, title)
}

Given(~'^the conferencia "([^"]*)" is stored in the system with file name "([^"]*)"$') { String title, String filename ->
    ConferenciaTestDataAndOperations.createConferencia(title, filename)
    conferencia = Conferencia.findByTitle(title)
    assert conferencia != null
}

Then(~'^the conferencia "([^"]*)" is not stored twice$') { String title ->
    conferencia = Conferencia.findAllByTitle(title)
    assert conferencia.size() == 1
}

When(~'^I remove the conferencia "([^"]*)"$') { String title ->
    ConferenciaTestDataAndOperations.removeConferencia(title)
}

Then(~'^the conferencia "([^"]*)" is properly removed by the system$') { String title ->
    conferencia = Conferencia.findByTitle(title)
    assert conferencia == null
}

Given(~'^I am at the publications$') {->
    LogInToPublication()
}

def LogInToPublication(){
    to LoginPage
    at LoginPage
    page.fillLoginData("admin", "adminadmin")
    at PublicationsPage
}

Given(~'^I am at the conferencias page$') {->
    LogInToPublication()
    page.select("Conferencia")
    at ConferenciaPage
}

When(~'^I select the conferencia option at the publications menu$') {->
    page.select("Conferencia")
}

When(~'^I select the new conferencia option at the conferencia page$') {->
    at ConferenciaPage
    page.selectNewConferencia()
}

When(~'^I select the home option at the conferencia page$') {->
    at ConferenciaPage
    page.selectHome()
}

When(~'^I select the conferencia "([^"]*)"$') {String title ->
    page.select(title)
}

When(~'^I click on remove$') {->
    page.select("Remove")
}

Then(~'^I can fill the conferencia details$') {->
    at ConferenciaCreatePage
    page.fillConferenciaDetails()
}

Then(~'^a list of conferencias stored by the system is displayed at the conferencia page$') {->
    at ConferenciaPage
    page.listConferencia()
}

Then(~'^I can remove one conferencia$') {->
    at ConferenciaPage
    page.removeConferencia()
}

Then(~'^I see my user listed as an author member of conferencia by default$') {->
    at ConferenciaCreatePage
    assert TestDataAndOperationsPublication.containsUser(page.selectedMembers())
}

Then(~'^I am back at the publications and conferencias menu$') {->
    at PublicationsPage
}

When(~'^I try to remove the conferencia "([^"]*)"$') { String title ->
    assert Conferencia.findByTitle(title) == null
}

Then(~'^nothing happens$') {->

}

Given(~'^the system has some conferencias stored$') {->
    initialSize = Conferencia.findAll().size()
}
When(~'^I upload the conferencias of "([^"]*)"$') { filename ->
    String path = "test" + File.separator + "functional" + File.separator + "steps" + File.separator + filename
    initialSize = Conferencia.findAll().size()
    ConferenciaTestDataAndOperations.uploadConferencias(path)
    finalSize = Conferencia.findAll().size()
    assert initialSize < finalSize
}
Then(~'^the system has all the conferencias of the xml file$') {->
    assert Conferencia.findByTitle("Latin American Conference On Computing (CLEI 1992)") != null
    assert Conferencia.findByTitle("Engineering Distributed Objects Workshop, 21st ACM International Conference on Software Engineering (ICSE 1999)") != null
    assert Conferencia.findByTitle("6th International Conference on Software Reuse (ICSR 2000)") != null

}

And(~'^I select the upload button at the conferencia page$') {->
    at ConferenciaPage
    page.uploadWithoutFile()
}
Then(~'^I\'m still on conferencia page$') {->
    at ConferenciaPage
}
And(~'^the conferencias are not stored by the system$') {->
    at ConferenciaPage
    page.checkIfConferenciaListIsEmpty()

}


Given(~'^I'm registering a new Article$') {String authorName ->
	at articleResgitrationPage
	page.fillArticleAuthorName(authorName, null)
}

When(~'^I type "([^"]*)" if there author names as "([^"]*)" or "([^"]*)" registered in the system) { String authorName ->
	page.fillArticleAuthorName(ArticleTestDataAndOperations.path() + authorName)
	at page.fillArticleAuthorName.suggest("([^"]*)" for aurthorName)
}

Then(~'^I choose between " Anderso " and " Candido " or if it is not neither I fill with the desired name) { String authorName ->
	at page.fillArticleAuthorName("([^"*])" or type "authorName")
}


Given(~'^I want to remove the article "([^"]*)" with the file name "([^"]*)") { String title, filename ->
        
	ArticleTestDataAndOperations.createArticle(title, filename,null,null)
        assert Periodico.findByTitle(title) != null
}

When(~'^I click on "([^"]*)" that is on the list of articles published in the conference page) { String title ->
	ArticleTestDataAndOperations.removeArticles(title)

	def testDeleteArticle1 = Periodico.findByTitle(title)
	assert testDeleteArticle == null
}

Then(~'^I click the button to remove and the "A theory of software" is removed from the list of articles) { string title ->
	assert periodicoNoExist(title)
}
And(~'^the aquirvo "ATOS.pdf" is removed from the system) {String fileName ->
  	assert fileNoExist(fileName)
}



/*
Given(~'^I am at the Conferece Articles page$') {->
    to LoginPage
    at LoginPage
    page.fillLoginData("admin","adminadmin")
    at ConferenciaPage
}
And(~'^the system has some conference articles authored  by "([^"]*)", among several publications$') { String author ->
    article = TestDataAndOperationsPublication.containsUser(author)
    assert article != null
}
When(~'^I write "([^"]*)" at the Search field$') { String author ->
    at ConferenciaPage
    page.fillSearch(author)
}
And (~'^I click on the Search button$'){
    page.select("search")
}
Then (~'^a list of all conference articles by "([^"]*)" is displayed$'){ String author ->
    author = TestDataAndOperationsPublication.containsUser(author)
    assert author != null
    page.listConferenceArticles(author)
}

Given(~'^I am at the Conference page$'){
    to LoginPage
    at LoginPage
    page.fillLoginData("admin","adminadmin")
    at ConferenciaPage
        }
And(~'^an Author named "([^"]*)" had published the articles  "([^"]*)",  "([^"]*)" and "([^"]*)" for the conferences "([^"]*)", "([^"]*)" and "([^"]*)"$'){ String author ->
    assert Conferencia.findByTitle("International Conference on Software Engineering") != null
    assert Conferencia.findByTitle("Information and Software Technology") != null
    assert Conferencia.findByTitle("Scenario: Search for conferences which an Author have published web") != null
    assert ArticleTestDataAndOperations.findArticleByTitle("An Analysis and Survey of the Development of Mutation Testing") != null
    assert ArticleTestDataAndOperations.findArticleByTitle("A Systematic Survey of Program Comprehension through Dynamic Analysis") != null
    assert ArticleTestDataAndOperations.findArticleByTitle("Engineering Privacy") != null
    author = TestDataAndOperationsPublication.containsUser(author)
    assert author != null
}
When(~'^I write ([^"]*)" at the search field$') { String author ->
    at ConferenciaPage
    page.fillSearch(author)
}
And(~'^I click on the search button$'){
    page.select("search")
}
Then(~'^a list of all conferences, composed by "([^"]*)",  "([^"]*)" and "([^"]*)", that "([^"]*)" published an article is displayed$') { String author ->
    assert ArticleTestDataAndOperations.findArticleByTitle("An Analysis and Survey of the Development of Mutation Testing") != null
    assert ArticleTestDataAndOperations.findArticleByTitle("A Systematic Survey of Program Comprehension through Dynamic Analysis") != null
    assert ArticleTestDataAndOperations.findArticleByTitle("Engineering Privacy") != null
    author = TestDataAndOperationsPublication.containsUser(author)
    assert author != null
    page.listConferencia(author)
}
*/

