import pages.ArticlePages.*
import pages.*
import rgms.member.Member
import rgms.publication.Periodico
import rgms.tool.FacebookTool
import rgms.tool.TwitterTool
import steps.ArticleTestDataAndOperations

import static cucumber.api.groovy.EN.*

Given(~'^the system has no article entitled "([^"]*)"$') { String title ->
    assert periodicoNoExist(title)
}

When(~'^I create the article "([^"]*)" with file name "([^"]*)"$') { String title, filename ->
    ArticleTestDataAndOperations.createArticle(title, filename)
}

Then(~'^the article "([^"]*)" is properly stored by the system$') { String title ->
    article = Periodico.findByTitle(title)
    assert ArticleTestDataAndOperations.compatibleTo(article, title)
}

When(~'^I create the article "([^"]*)" with file name "([^"]*)" with the "([^"]*)" field blank$') { String title, String filename, String field ->
    ArticleTestDataAndOperations.createArticle(title, filename)
    def article = ArticleTestDataAndOperations.findArticleByTitle(title)
    assert article.{field} == null
}

Then(~'^the article "([^"]*)" is not stored by the system because it is invalid$') { String title ->
    assert periodicoNoExist(title)
}

Then(~'^the article "([^"]*)" is not stored twice$') { String title ->
    articles = Periodico.findAllByTitle(title)
    assert articles.size() == 1
    // Should actually check whether elements in articles are not equal except for their filename,
    // which is changed by the system during the file upload.
}

When(~'^I select the new article option at the article page$') {->
    selectNewArticleInArticlesPage()
}

Then(~'^I can fill the article details$') {->
    at ArticleCreatePage
    page.fillArticleDetails()
}


/**
 * @author Guilherme
 */
Given(~'^the system has article entitled "([^"]*)" with file name "([^"]*)"$') { String title, filename ->
    ArticleTestDataAndOperations.createArticle(title, filename)
    assert !periodicoNoExist(title)
}

/**
 * @author Guilherme
 */

Given(~'^I am at the articles page and the article "([^"]*)" is stored in the system with file name "([^"]*)"$') { String title, filename ->

    Login()
    at PublicationsPage
    page.select("Periodico")
    selectNewArticleInArticlesPage()
    page.fillArticleDetails(ArticleTestDataAndOperations.path() + filename, title)
    page.selectCreateArticle()
    assert !periodicoNoExist(title)
    to ArticlesPage
    at ArticlesPage
}

/**
 * @author Guilherme
 */
When(~'^I delete the article "([^"]*)"$') { String title ->

    ArticleTestDataAndOperations.removeArticle(title)

}

/**
 * @author Guilherme
 */
When(~'^I edit the article title from "([^"]*)" to "([^"]*)"$') { String oldtitle, newtitle ->
    def updatedArticle = ArticleTestDataAndOperations.editArticle(oldtitle, newtitle)
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


When(~'^I change the article title to "([^"]*)"$') { String newtitle ->
    page.select('a', 'edit')
    at ArticleEditPage
    def path = new File(".").getCanonicalPath() + File.separator + "test" + File.separator + "files" + File.separator
    page.edit(newtitle, path + "TCS-99.pdf")
}
/**
 * @author Guilherme
 */
Then(~'^the article "([^"]*)" is properly updated by the system$') { String title ->
    assert periodicoNoExist(title)
}

/**
 * @author Guilherme
 */
Then(~'^the article "([^"]*)" is properly removed by the system$') { String title ->
    assert periodicoNoExist(title)
}

/**
 * @author Guilherme
 */
Then(~'my article list contains "([^"]*)"$') { String title ->
    articles = Periodico.findAll()
    assert ArticleTestDataAndOperations.containsArticle(title, articles)
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

When(~'I select the option to remove in show page$') {->
    at ArticleShowPage
    page.select('input', 'delete')
}

/**
 * @author Guilherme
 */
When(~'I select the "([^"]*)" option in Article Show Page$') { String option ->

    at ArticleEditPage
    page.select(option)

}

Then(~'^I am at Article show page$') { ->
    at ArticleShowPage
}


//#if( $Twitter )
Given(~'^There is a user "([^"]*)" with a twitter account$') { String userName ->
    to UserRegisterPage
    at UserRegisterPage
    page.fillFormData(userName)

    page.submitForm()

    Login()

    member = Member.findByUsername(userName)
    MemberEditionPage.url = "member/edit/" + member.getId()

    to MemberEditionPage
    at MemberEditionPage
    page.editEnableUser(userName)
}

Given(~'^I am logged as "([^"]*)" and at the Add Article Page$') { String userName ->

    Login()
    at PublicationsPage
    page.select("Periodico")
    to ArticlesPage
    def path = new File(".").getCanonicalPath() + File.separator + "test" + File.separator + "files" + File.separator + "TCS.pdf"
    println path
    def f = new File(path)
    println "exist Path?" + f.exists()
}

When(~'^I try to create an article named as "([^"]*)" with filename "([^"]*)"$') { String articleName, String filename ->
    selectNewArticleInArticlesPage()
    page.fillArticleDetails(ArticleTestDataAndOperations.path() + filename, articleName)
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
When(~'^I click on share it on Facebook, with login "([^"]*)", password "([^"]*)", and message "([^"]*)"$') { String facebookLogin, String facebookPw, String message ->
    at ArticleShowPage
    page.clickOnFacebookIt(facebookLogin, facebookPw, message)
    at ArticleShowPage
}

Then(~'^A facebook message is added for "([^"]*)"$') { String articleTitle ->
    assert FacebookTool.consult(articleTitle)
}

Then(~'^No facebook message is added for "([^"]*)"$') { String articleTitle ->
    assert !FacebookTool.consult(articleTitle)
}
//#end

def selectNewArticleInArticlesPage(){

    at ArticlesPage
    page.selectNewArticle()
    at ArticleCreatePage

    }

def periodicoNoExist(String title){
      return Periodico.findByTitle(title) == null
    }

def Login(){

    to LoginPage
    at LoginPage

        page.fillLoginData("admin", "adminadmin")


    }

