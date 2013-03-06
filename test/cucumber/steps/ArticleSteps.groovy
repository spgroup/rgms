import pages.ArticleCreatePage
import pages.ArticlesPage
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

When(~'^I select the new article option at the article page$') {->
    at ArticlesPage
    page.selectNewArticle()
}

Then(~'^I can fill the article details$') {->
    at ArticleCreatePage
    page.fillArticleDetails()
}