import pages.BibtexGenerateFilePage
import pages.LoginPage
import pages.PublicationsPage
import rgms.publication.BibtexGenerateFile
import rgms.publication.Periodico
import pages.ArticlePages.ArticleShowPage
import pages.ArticlePages.ArticlesPage
import pages.LoginPage

import static cucumber.runtime.groovy.EN.Then
import static cucumber.runtime.groovy.EN.When
import static cucumber.runtime.groovy.EN.And
import static cucumber.runtime.groovy.EN.Given


When(~'^I select the export bibtex file option at the publications menu$') {->
    page.select("Export Bibtex File")
}

When(~'^I select Generate All BibTex option at the export bibtex page$') {->
    at BibtexGenerateFilePage
    page.showBibtex()
}

Then(~'^I can see the bibtex details$') {->
}

Given(~'^I have an article named "([^"]*)"$') {String title ->
    article = Periodico.findByTitle(title)
    assert article != null
}

And(~'^I have another article named "([^"]*)"$') {String title ->
    article = Periodico.findByTitle(title)
    assert article != null
}

When(~'^I generate a BibTex file from articles named "([^"]*)" and "([^"]*)"$') { String title1, String title2 ->
    article1 = Periodico.findByTitle(title1)
    article2 = Periodico.findByTitle(title2)
    bib1 = generateBibtexPeriodico(article1)
    assert bib1 != null
    bib2 = generateBibtexPeriodico(article2)
    assert bib2 != null
    bibtexFile = bib1 + "\n" + bib2
    assert bibtexFile != null
}

Then(~'^the BibTex file has unique citation-keys for the articles "([^"]*)" and "([^"]*)"$') { String title1, String title2 ->
    // The method that generates a BibTex does not include a citation-key
    // Need to fix this issue first
}

Given(~'^I am on the "Publications" menu$') {->
    to LoginPage
    at LoginPage
    page.add("admin", "adminadmin")
    at PublicationsPage
}

When(~'^I select the publications "([^"]*)" and "([^"]*)"$') { String p1, String p2 ->

And(~'^I click on the "([^"]*)" option$') {String o ->
    at PublicationsPage
    page.select(o)
}

Then(~'^the BibTex details are showed$') {->
}

And(~'^It only contains the articles "([^"]*)" and "([^"]*)"$') { String title1, String title2 ->
}

//---------------------------------------------------------------//


Given (~'^And I am at the article page$') {
    Login("admin","adminadmin")
    to ArticlesPage
    at ArticlesPage
}

When(~'^I select the article with title "([^"]*)"$') {String articleTitle ->
    page.selectViewArticle(articleTitle)
}

Then(~'^I should see the article with title "([^"]*)" details$')  { String articleTitle ->
    at ArticleShowPage
}

When(~'^I click the Bibtex button$') {
    at ArticleShowPage
    page.select('Bibtex', 'fieldcontain')
}

Then(~'^I should see each authors\' name at the author field separated by "and"$') {
    assert true
}

/*---------------------------------------------------------------*/


Given (~'^I am at the Bibtex Generate File$') {
    to BibtexGenerateFilePage
    at BibtexGenerateFilePage
}
And (~'^I have an article named "([^"]*)"$') { String articleTitle ->
    assert Periodico.findByTitle(articleTitle)
}
When (~'^I select generate Bibtex from the article "([^"]*)"$') { String articleTitle ->
    at BibtexGenerateFilePage
    page.select(Bibtex)
}
Then (~'^I should see the Bibtex file of the article named "([^"]*)"$') { String articleTitle ->
    assert true
}



