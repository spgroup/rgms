import pages.ArticlePages.ArticleCreatePage
import pages.ArticlePages.ArticlesPage
import pages.BibtexGenerateFilePage
import pages.Conferencia.ConferenciaCreatePage
import pages.Conferencia.ConferenciaPage
import pages.PublicationsPage
import rgms.authentication.User
import rgms.publication.BibtexGenerateFileController
import rgms.publication.Conferencia
import rgms.publication.DissertacaoController
import rgms.publication.Periodico
import rgms.publication.PublicationController
import rgms.publication.Tese
import rgms.publication.TeseController
import rgms.publication.ThesisOrDissertationController
import steps.ArticleTestDataAndOperations
import steps.BibtexGenerateFileTestDataAndOperations
import steps.ConferenciaTestDataAndOperations
import steps.ThesisOrDissertationTestDataAndOperations

import static cucumber.api.groovy.EN.*
// #if($bibtexGenerateFile)
Then(~'^I can see the publication "([^"]*)" inside the bibtex$') { String title ->
    at BibtexGenerateFilePage
    String bibtex = page.getBibtexDetails()
    assert bibtex != null && bibtex.length() > 0
    assert bibtex.contains(title)
}

Then(~'^I can export this bibtex to a file$') { ->
    at BibtexGenerateFilePage
    page.select("Save in file")
}

Given(~'^I created one article named "([^"]*)"$') { String article ->
    at PublicationsPage
    page.select("Periodico")
    at ArticlesPage
    page.selectNewArticle()
    at ArticleCreatePage
    page.fillArticleDetails(ArticleTestDataAndOperations.path() + "filename", article)
    page.selectCreateArticle()
    to PublicationsPage
    at PublicationsPage
}

Given(~'^I created one conferencia named "([^"]*)"$') {  String conferencia ->
    at PublicationsPage
    page.select("Conferencia")
    at ConferenciaPage
    page.selectNewConferencia()
    at ConferenciaCreatePage
    page.fillConferenciaDetails(conferencia)
    page.selectCreateConferencia()
    to PublicationsPage
    at PublicationsPage
}

// #end