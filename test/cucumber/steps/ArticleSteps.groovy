import pages.ArticleCreatePage
import pages.ArticlesPage
import pages.ArticleShowPage
import pages.ArticleEditPage
import pages.LoginPage
import pages.PublicationsPage
import rgms.publication.Periodico
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


/*Given(~'^I am at the publications menu$') {->
	to LoginPage
	at LoginPage
	page.fillLoginData("admin", "adminadmin")
	at PublicationsPage
}

When(~'^I select the "([^"]*)" option at the publications menu$') { String option ->
	page.select(option)
}*/

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
Given(~'^the system has article entitled "([^"]*)" with file name "([^"]*)"$') { String title, filename ->
	TestDataAndOperations.createArticle(title, filename)
	article = Periodico.findByTitle(title)
	assert article != null
}

/**
 * @author Guilherme
 */

Given(~'^I am at the publications menu and the article "([^"]*)" is stored in the system with file name "([^"]*)"$') {String title, filename->
	to LoginPage
	at LoginPage
	page.fillLoginData("admin", "adminadmin")
	at PublicationsPage
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
When (~'^I edit the article title from "([^"]*)" to "([^"]*)"$') {String oldtitle, newtitle  ->
	def updatedArticle = TestDataAndOperations.editArticle(oldtitle, newtitle)
	assert updatedArticle != null
}

/**
 * @author Guilherme
 */
When (~"^I view the article list\$") { ->
	articles = Periodico.findAll()
	assert articles != null
}

/**
 * @author Guilherme
 */
When (~'^I select to view "([^"]*)" in resulting list$') {String title  ->
	at ArticlesPage
	page.selectViewArticle(title)
	at ArticleShowPage
}

/**
 * @author Guilherme
 */
When (~'^I select to view "([^"]*)" in resulting list and I change the article title to "([^"]*)"$') {String oldtitle, newtitle  ->
	at ArticlesPage
	page.selectViewArticle(oldtitle)
	at ArticleShowPage
	page.select('a','edit')
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
	assert TestDataAndOperations.containsArticle(title,articles)
}

/**
 * @author Guilherme
 */
Then(~'my resulting articles list contains "([^"]*)"$') { String title ->
	at ArticlesPage
	page.checkArticleAtList(title,0)
}

/**
 * @author Guilherme
 */
Then(~'the details are showed and I can select the option to remove$') { ->
	at ArticleShowPage
	page.select('input','delete')
}

/**
 * @author Guilherme
 */
Then(~'I can select the "([^"]*)" option$') { String option ->
	at ArticleEditPage
	page.select(option)
}

