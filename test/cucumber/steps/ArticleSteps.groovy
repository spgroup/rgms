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


Given(~'^I am at the articles page$'){->
	Login()
	at PublicationsPage
	page.select("Periodico")
	at ArticlesPage
}

And(~'^the article "([^"]*)" is stored in the system with file name "([^"]*)"$') { String title, filename ->
	at ArticlesPage
	page.selectNewArticle()
	at ArticleCreatePage
	page.fillArticleDetails(ArticleTestDataAndOperations.path() + filename, title)
	page.selectCreateArticle()
	assert !periodicoNoExist(title)
}

//#if($Report)
When(~'^the system reports the existing articles$') {->
	articles = ArticleTestDataAndOperations.reportArticles()
	assert articles != null
}

Then(~'^the system report contains "([^"]*)" article$') { String title ->
	articles = ArticleTestDataAndOperations.reportArticles()
	assert ArticleTestDataAndOperations.containsArticle(title, articles)
}

When(~'^I select to view the report of articles$') {->
	to ArticlesPage
	at ArticlesPage
	page.selectViewReports()
}

Then(~'^my resulting report of articles contains "([^"]*)"$') { String title ->
	at ArticlesReportPage
	page.checkArticleAtReport(title,0)
}
//#end

Given(~'^the system has article entitled "([^"]*)" with file name "([^"]*)" dated on "([^"]*)"$'){String title, filename, date->
	ArticleTestDataAndOperations.createArticle(title, filename, date, null)
	assert Periodico.findByTitle(title) != null
}

When(~'^the system orders the article list by title$') {->
	articlesSorted = Periodico.listOrderByTitle(order: "asc")
	assert ArticleTestDataAndOperations.isSorted(articlesSorted, "title")

}

When(~'^the system orders the article list by publication date$') {->
	articlesSorted = Periodico.listOrderByPublicationDate(order: "asc")
	assert ArticleTestDataAndOperations.isSorted(articlesSorted, "publication date")
}

Then(~'^the system article list content is not modified$') {->
	assert Periodico.findAll().size() == 2
	assert !periodicoNoExist('Modularity analysis of use case implementations')
	assert !periodicoNoExist('A theory of software product line refinement')
}

Given(~'^the system has some articles created$'){->
	at ArticlesPage
	page.selectNewArticle()
	at ArticleCreatePage
	page.fillArticleDetails(ArticleTestDataAndOperations.path() + 'MACI.pdf', 'Modularity analysis of use case implementations',"17","10","2013")
	page.selectCreateArticle()
	assert !periodicoNoExist('Modularity analysis of use case implementations')
	to ArticlesPage
	page.selectNewArticle()
	at ArticleCreatePage
	page.fillArticleDetails(ArticleTestDataAndOperations.path() + 'TCS-1401.pdf', 'A theory of software product line refinement',"12","11","2012")
	page.selectCreateArticle()
	assert !periodicoNoExist('A theory of software product line refinement')
	to ArticlesPage
}

When(~'^I select to view the list of articles$') {->
	at ArticlesPage
	page.selectViewArticleList()
}

And(~'^I select to order the list of articles by "([^"]*)"$') {String sortType->
	at ArticlesPage
	page.selectOrderBy(sortType)
}

Then(~'^my article list shows the articles ordered by "([^"]*)"$') {String sortType->
	at ArticlesPage
	page.checkOrderedBy(sortType);
}

Given(~'^the system has some articles authored by "([^"]*)"$'){String authorName->
	ArticleTestDataAndOperations.createArticle('A theory of software product line refinement', 'TCSOS.pdf', null, 'Paulo Borba')
	ArticleTestDataAndOperations.createArticle('Modularity analysis of use case implementations', 'MACI.pdf')
	assert (!periodicoNoExist('A theory of software product line refinement') && !periodicoNoExist('Modularity analysis of use case implementations'))
}

When(~'^the system filter the articles authored by author "([^"]*)"$') {String authorName->
	articlesFiltered = ArticleTestDataAndOperations.findAllByAuthor(authorName)
	assert ArticleTestDataAndOperations.isFiltered(articlesFiltered,authorName)
}

And(~'^I create some articles authored by "([^"]*)"$') {String authorName->
	at ArticlesPage
	page.selectNewArticle()
	at ArticleCreatePage
	page.fillArticleDetails(ArticleTestDataAndOperations.path() + 'MACI.pdf', 'Modularity analysis of use case implementations', authorName)
	page.selectCreateArticle()
	assert !periodicoNoExist('Modularity analysis of use case implementations')
	to ArticlesPage
	page.selectNewArticle()
	at ArticleCreatePage
	page.fillArticleDetails(ArticleTestDataAndOperations.path() + 'TCS-1401.pdf', 'A theory of software product line refinement')
	page.selectCreateArticle()
	assert !periodicoNoExist('A theory of software product line refinement')
	to ArticlesPage
}

And(~'^I select to filter the list of articles by author "([^"]*)"$') {String authorName->
	at ArticlesPage
	page.fillAndSelectFilter(authorName)
}

Then(~'^my article list shows only the articles authored by "([^"]*)"$') {String authorName->
	at ArticlesPage
	assert page.checkFilteredBy(authorName);
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


def addNewArticleWeb(title, filename){
	selectNewArticleInArticlesPage()
	page.fillArticleDetails(ArticleTestDataAndOperations.path() + filename, title)
	page.selectCreateArticle()
	assert !periodicoNoExist(title)
	to ArticlesPage
	at ArticlesPage
}

Given(~'^the system has 3 articles entitled "([^"]*)" with file name "([^"]*)", "([^"]*)" with file name "([^"]*)" and "([^"]*)" with file name "([^"]*)"$') {String title1, filename1, title2, filename2, title3, filename3 ->
	ArticleTestDataAndOperations.createArticle(title1, filename1,null,null)
	ArticleTestDataAndOperations.createArticle(title2, filename2,null,null)
	ArticleTestDataAndOperations.createArticle(title3, filename3,null,null)
	assert Periodico.findByTitle(title1) != null
	assert Periodico.findByTitle(title2) != null
	assert Periodico.findByTitle(title3) != null
}

When(~'^I remove the articles "([^"]*)" and "([^"]*)"$') { String title1, title2 ->

	ArticleTestDataAndOperations.removeMultiplesArticles(title1, title2)

	def testDeleteArticle1 = Periodico.findByTitle(title1)
	def testDeleteArticle2 = Periodico.findByTitle(title2)
	assert testDeleteArticle1 == null
	assert testDeleteArticle2 == null
}

Then(~'^the system removes the articles "([^"]*)" and "([^"]*)"$') { String title1, title2 ->
	assert periodicoNoExist(title1)
	assert periodicoNoExist(title2)
}

And(~'^the system contains the "([^"]*)" article$') { String title1 ->
	assert Periodico.findByTitle(title1) != null
}

And(~'^I create 3 articles entitled "([^"]*)" with file name "([^"]*)", "([^"]*)" with file name "([^"]*)" and "([^"]*)" with file name "([^"]*)"$') { String title1, filename1, title2, filename2, title3, filename3 ->
	addNewArticleWeb(title1, filename1)
	addNewArticleWeb(title2, filename2)
	addNewArticleWeb(title3, filename3)
}

And(~'I select to remove the selected articles$') {->
	at ArticlesPage
	page.selectRemoveMultipleArticles()
}

Given(~'^I am at the new article page$'){->
	Login()
	at PublicationsPage
	page.select("Periodico")
	selectNewArticleInArticlesPage()
}

When(~'^I fill all article information except the title field$') {->
	at ArticleCreatePage
	page.fillArticleDetailsExceptTitle()
}

And(~'^I select to create the article$') {->
	at ArticleCreatePage
	page.selectCreateArticle()
}

When(~'^I create the article with filename "([^"]*)" and with the title field blank$') {String filename ->
	ArticleTestDataAndOperations.createArticleWithoutTitle(filename)
	def article = ArticleTestDataAndOperations.findArticleByFilename(filename)
	assert article.{title} == null
}

Given(~'^the system has no article without title and with filename "([^"]*)"$') {String filename ->
	def article = Periodico.findByFile(filename)
	assert article == null
}

Then(~'^the article with blank title and with filename "([^"]*)" field is not stored by the system$') {String filename ->
	def article = Periodico.findByFile(filename)
	assert article == null
}

Then(~'^an error message is showed for the title field$') { ->
	assert (page.readFlashMessage() != null)
}

And(~'^I mark multiple articles to be removed$') {->
	at ArticlesPage
	page.markArticles()
}
