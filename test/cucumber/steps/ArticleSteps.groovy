import pages.ArticlePages.*
import pages.*
import rgms.publication.Periodico
import rgms.tool.TwitterTool
import steps.TestDataAndOperationsFacebook
import steps.ArticleTestDataAndOperations
import steps.TestDataAndOperationsPublication

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
Given(~'^the system has article entitled "([^"]*)" with file name "([^"]*)"$') { String title, String filename ->
    ArticleTestDataAndOperations.createArticle(title, filename)
    assert Periodico.findByTitle(title) != null
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
    def testarticle = Periodico.findByTitle(title)
    assert testarticle != null
    ArticleTestDataAndOperations.removeArticle(title)
    def testDeleteArticle = Periodico.findByTitle(title)
    assert testDeleteArticle == null
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
    //on ArticleEditPage
}

Then(~'^I am at Article show page$') { ->
    at ArticleShowPage
}


//#if( $Twitter )
Given(~'^I am logged as "([^"]*)"$') { String userName ->
    to LoginPage
    at LoginPage
    page.fillLoginData(userName, "adminadmin")
}
Given (~'^I am at the Article Page$'){->

    addPage()
}

When(~'^I try to create an article named as "([^"]*)" with filename "([^"]*)"$') { String articleName, String filename ->
    selectNewArticleInArticlesPage()
    page.fillArticleDetails(ArticleTestDataAndOperations.path() + filename, articleName)
    page.selectCreateArticle()
}

When(~'^I click on Share it in Twitter with "([^"]*)" and "([^"]*)"$') { String twitterLogin, String twitterPw ->
    at ArticleShowPage
    page.clickOnTwitteIt(twitterLogin, twitterPw)
    at ArticleShowPage
}

Then(~'^A tweet is added to my twitter account regarding the new article "([^"]*)"$') { String articleTitle ->
    assert TwitterTool.consult(articleTitle)
}

Then (~'No tweet should be post about "([^"]*)"$'){ String article ->
    assert !TwitterTool.consult(null)
}


Then(~'^No twitte should be post about "([^"]*)"$') { String articleTitle ->
    assert !TwitterTool.consult(articleTitle)
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
    TestDataAndOperationsFacebook.ShareArticleOnFacebook(title)
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

/**
 * @author carloscemb
 */
Then(~'^I see my user listed as an author member of article by default$') {->
    at ArticleCreatePage
    assert TestDataAndOperationsPublication.containsUser(page.selectedMembers())
}


When(~'^I upload the articles of "([^"]*)"$') { String arg1 ->
    String path = "test" + File.separator + "functional" + File.separator + "steps" + File.separator + arg1
    initialSize = Periodico.findAll().size()
    ArticleTestDataAndOperations.uploadArticle(path)
    finalSize = Periodico.findAll().size()
    assert initialSize < finalSize
}
Then(~'^the system has all the articles of the xml file$') {->
    assert Periodico.findByTitle("A System For Translating Executable VDM Specifications Into Lazy ML") != null
    assert Periodico.findByTitle("From VDM Specifications To Functional Prototypes") != null
    assert Periodico.findByTitle("Basic laws of ROOL: an object-oriented language") != null
    assert Periodico.findByTitle("Implementing distribution and persistence aspects with AspectJ") != null
    assert Periodico.findByTitle("Developing adaptive J2ME applications using AspectJ") != null
    assert Periodico.findByTitle("Algebraic reasoning for object-oriented programming") != null
    assert Periodico.findByTitle("Refactoring Alloy Specifications") != null
    assert Periodico.findByTitle("Systematic development of concurrent object-oriented programs") != null
    assert Periodico.findByTitle("Portando Jogos em J2ME: Desafios, Estudo de Caso, e Diretrizes") != null
    assert Periodico.findByTitle("AspectH: Uma ExtensÃ£o Orientada a Aspectos de Haskell") != null
    assert Periodico.findByTitle("An Abstract Equivalence Notion for Object Models") != null
    assert Periodico.findByTitle("Distribution and Persistence as Aspects") != null
    assert Periodico.findByTitle("A Static Semantics for Alloy and its Impact in Refactorings") != null
    assert Periodico.findByTitle("Extracting and Evolving Code in Product Lines with Aspect-Oriented Programming") != null
    assert Periodico.findByTitle("A Framework for Establishing Formal Conformance between Object Models and Object-Oriented Programs") != null
    assert Periodico.findByTitle("Refactorings for introducing Alloy idioms (aceito para publicaÃ§Ã£o)") != null
    assert Periodico.findByTitle("Algebraic Laws for Feature Models") != null
    assert Periodico.findByTitle("Estimating manual test execution effort and capacity based on execution points") != null
    assert Periodico.findByTitle("Automatically Checking Feature Model Refactorings") != null
    assert Periodico.findByTitle("An Approach to Invariant-based Program Refactoring") != null
    assert Periodico.findByTitle("Modularity analysis of use case implementations") != null
    assert Periodico.findByTitle("A Theory of Software Product Line Refinement") != null
    assert Periodico.findByTitle("The Crosscutting Impact of the AOSD Brazilian Research Community (accepted)") != null
}
And(~'^I select the upload button at the article page$') {->
    at ArticlesPage
    page.uploadWithoutFile()
}
Then(~'^I\'m still on article page$') {->
    at ArticlesPage
}
And(~'^the articles are not stored by the system$') {->
    at ArticlesPage
    page.checkIfArticlesListIsEmpty()
}
Given(~'^the system has some articles stored$') {->
    initialSize = Periodico.findAll().size()
}

//Funcoes Auxiliares
def addPage(){
    Login()
    at PublicationsPage
    page.select("Periodico")
    to ArticlesPage
    def path = new File(".").getCanonicalPath() + File.separator + "test" + File.separator + "files" + File.separator + "TCS.pdf"
    println path
    def f = new File(path)
    println "exist Path?" + f.exists()
}
