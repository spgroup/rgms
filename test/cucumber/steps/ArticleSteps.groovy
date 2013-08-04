import pages.*
import rgms.member.Member
import rgms.publication.Periodico
import rgms.tool.FacebookTool
import rgms.tool.TwitterTool
import steps.TestDataAndOperations

import static cucumber.api.groovy.EN.*

Given(~'^the system has no article entitled "([^"]*)"$') { String title ->
    article = Periodico.findByTitle(title)
    assert article == null
}

When(~'^I create the article "([^"]*)" with file name "([^"]*)"$') { String title, filename ->
    TestDataAndOperations.createArticle(title, filename)
}

Then(~'^the article "([^"]*)" is properly stored by the system$') { String title ->
    article = Periodico.findByTitle(title)
    assert TestDataAndOperations.compatibleTo(article, title)
}

Then(~'^the article "([^"]*)" is not stored by the system because it is invalid$') { String title ->
    article = Periodico.findByTitle(title)
    assert article == null
}

Given(~'^the article "([^"]*)" is stored in the system with file name "([^"]*)"$') { String title, filename ->
    TestDataAndOperations.createArticle(title, filename)
    article = Periodico.findByTitle(title)
    assert article != null
}

Then(~'^the article "([^"]*)" is not stored twice$') { String title ->
    articles = Periodico.findAllByTitle(title)
    assert articles.size() == 1
    // Should actually check whether elements in articles are not equal except for their filename,
    // which is changed by the system during the file upload.
}

When(~'^I select the new article option at the article page$') {->
    at ArticlesPage
    page.selectNewArticle()
}

Then(~'^I can fill the article details$') {->
    at ArticleCreatePage
    page.fillArticleDetails()
}

//AULA
//------------------------------------------------------//----------------------------------------------------------//
//ATIVIDADE 5

/**
 * @author Guilherme
 */
Given(~'^the system has article entitled "([^"]*)" with file name "([^"]*)"$') { String title, String filename ->
    TestDataAndOperations.createArticle(title, filename)
    assert Periodico.findByTitle(title) != null
}

/**
 * @author Guilherme
 */

Given(~'^I am at the articles page and the article "([^"]*)" is stored in the system with file name "([^"]*)"$') { String title, filename ->
    to LoginPage
    at LoginPage
    page.fillLoginData("admin", "adminadmin")
    at PublicationsPage
    page.select("Periodico")
    at ArticlesPage
    page.selectNewArticle()
    at ArticleCreatePage
    def path = new File(".").getCanonicalPath() + File.separator + "test" + File.separator + "files" + File.separator
    page.fillArticleDetails(path + filename, title)
    page.selectCreateArticle()
    article = Periodico.findByTitle(title)
    assert article != null
    to ArticlesPage
    at ArticlesPage
}

/**
 * @author Guilherme
 */
When(~'^I delete the article "([^"]*)"$') { String title ->
    TestDataAndOperations.removeArticle(title)
}

/**
 * @author Guilherme
 */
When(~'^I edit the article title from "([^"]*)" to "([^"]*)"$') { String oldtitle, newtitle ->
    def updatedArticle = TestDataAndOperations.editArticle(oldtitle, newtitle)
    assert updatedArticle != null
}

/**
 * @author Guilherme
 */
When(~"^I view the article list\$") {->
    articles = Periodico.findAll()
    assert articles != null
}

/**
 * @author Guilherme
 */
When(~'^I select to view "([^"]*)" in resulting list$') { String title ->
    page.selectViewArticle(title)
    at ArticleShowPage
}

/**
 * @author Guilherme
 */
When(~'^I select to view "([^"]*)" in resulting list and I change the article title to "([^"]*)"$') { String oldtitle, newtitle ->
    at ArticlesPage
    page.selectViewArticle(oldtitle)
    at ArticleShowPage
    page.select('a', 'edit')
    at ArticleEditPage
    page.edit(newtitle)
}

/**
 * @author Guilherme
 */
Then(~'^the article "([^"]*)" is properly updated by the system$') { String title ->
    article = Periodico.findByTitle(title)
    assert article == null
}

/**
 * @author Guilherme
 */
Then(~'^the article "([^"]*)" is properly removed by the system$') { String title ->
    article = Periodico.findByTitle(title)
    assert article == null
}

/**
 * @author Guilherme
 */
Then(~'my article list contains "([^"]*)"$') { String title ->
    articles = Periodico.findAll()
    assert TestDataAndOperations.containsArticle(title, articles)
}

/**
 * @author Guilherme
 */
Then(~'my resulting articles list contains "([^"]*)"$') { String title ->
    at ArticlesPage
    page.checkArticleAtList(title, 0)
}

/**
 * @author Guilherme
 */
Then(~'the article details are showed and I can select the option to remove$') {->
    at ArticleShowPage
    page.select('input', 'delete')
}

/**
 * @author Guilherme
 */
Then(~'I can select the "([^"]*)" option$') { String option ->
    at ArticleEditPage
    page.select(option)
}

//#if( $Twitter )
Given(~'^There is a user "([^"]*)" with a twitter account$') { String userName ->
    to UserRegisterPage
    at UserRegisterPage
    page.fillFormData(userName)

    page.submitForm()

    to LoginPage
    at LoginPage
    page.fillLoginData("admin", "adminadmin")

    member = Member.findByUsername(userName)
    MemberEditionPage.url = "member/edit/" + member.getId()

    to MemberEditionPage
    at MemberEditionPage
    page.editEnableUser(userName)
}

Given(~'^I am logged as "([^"]*)" and at the Add Article Page$') { String userName ->
    to LoginPage
    at LoginPage
    page.fillLoginData(userName, "adminadmin")
    at PublicationsPage
    page.select("Periodico")
    to ArticlesPage
    def path = new File(".").getCanonicalPath() + File.separator + "test" + File.separator + "files" + File.separator + "TCS.pdf"
    println path
    def f = new File(path)
    println "exist Path?" + f.exists()
}

When(~'^I try to create an article named as "([^"]*)" with filename "([^"]*)"$') { String articleName, String filename ->
    at ArticlesPage
    page.selectNewArticle()
    at ArticleCreatePage
    def path = new File(".").getCanonicalPath() + File.separator + "test" + File.separator + "files" + File.separator
    page.fillArticleDetails(path + filename, articleName)
    page.selectCreateArticle()
}

Then(~'^A twitter is added to my twitter account regarding the new article "([^"]*)"$') { String articleTitle ->
    assert TwitterTool.consult(articleTitle)
}

Then(~'^No twitte should be post about "([^"]*)"$') { String articleTitle ->
    assert !TwitterTool.consult(articleTitle)
}

When(~'^I click on Share it in Twitter with "([^"]*)" and "([^"]*)"$') { String twitterLogin, String twitterPw ->
    at ArticleShowPage
    page.clickOnTwitteIt(twitterLogin, twitterPw)
    at ArticleShowPage
}
//#end

//#if( $Facebook )
When(~'^I click on Share on Facebook$') { ->
    at ArticleShowPage
    page.clickOnShareOnFacebook()
    at ArticleShowPage
}


Then(~'^A facebook message was posted$') { ->
    //TODO
    assert true
}


Then(~'^No facebook message was posted$') { ->
    //TODO
    assert true
}

Given(~'^I am logged as "([^"]*)"$') { String userName ->
    to LoginPage
    at LoginPage
    page.fillLoginData(userName, "adminadmin")
}

Given(~'^I am at the Add Article Page$') {  ->
    at PublicationsPage
    page.select("Periodico")
    to ArticlesPage
    def path = new File(".").getCanonicalPath() + File.separator + "test" + File.separator + "files" + File.separator + "TCS.pdf"
    println path
    def f = new File(path)
    println "exist Path?" + f.exists()
}


When(~'^I share the article entitled "([^"]*)" on facebook$') { String title ->
    TestDataAndOperations.ShareArticleOnFacebook(title)
}

//#end

