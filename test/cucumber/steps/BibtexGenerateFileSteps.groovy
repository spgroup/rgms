import pages.BibtexGenerateFilePage
import rgms.publication.BibtexGenerateFileController
import rgms.publication.Conferencia
import rgms.publication.Periodico
import rgms.publication.PublicationController
import rgms.publication.Tese
import steps.ArticleTestDataAndOperations
import steps.BibtexGenerateFileTestDataAndOperations
import steps.ConferenciaTestDataAndOperations
import steps.ThesisOrDissertationTestDataAndOperations

import static cucumber.api.groovy.EN.*

When(~'^I select Generate All BibTex option at the export bibtex page$') {
    at BibtexGenerateFilePage
    page.showBibtex()
}
// #if($bibtexGenerateFile)
Then(~'^I can see the bibtex details$') {
    at BibtexGenerateFilePage
    page.checkBibtexDetails()
}

Given(~'^I created one article named "([^"]*)"$') { String article ->
    ArticleTestDataAndOperations.createArticle(article, "article")
    assert Periodico.findByTitle(article) != null
}

Given(~'^I created one conferencia named "([^"]*)"$') {  String conferencia ->
    ConferenciaTestDataAndOperations.createConferencia(conferencia, "conferencia")
    assert Conferencia.findByTitle(conferencia) != null
}

Given(~'^I created one thesis named "([^"]*)"$') { String thesis ->
    BibtexGenerateFileTestDataAndOperations.createThesis(thesis);
    assert Tese.findByTitle(thesis) != null
}

When(~'^I export all my publications to a bibtex file with the file name "([^"]*)"$') { String filename ->
    BibtexGenerateFileController cont = new BibtexGenerateFileController()
    cont.params << [id:PublicationController.loggedMember.id.intValue()]
    // TODO usar o file name
    cont.request.setContent(new byte[1000])
    cont.generateBibTex()
    cont.response.reset()
}

Then(~'The article "([^"]*)" is inside the bibtex file with the file name "([^"]*)"$') { String article, filename ->
    BibtexGenerateFileTestDataAndOperations.checkPublicationInsideBibtex(article, filename)
}

Then(~'The conferencia "([^"]*)" is inside the bibtex file with the file name "([^"]*)"$') { String conferencia, filename ->
    BibtexGenerateFileTestDataAndOperations.checkPublicationInsideBibtex(conferencia, filename)
}

Then(~'The thesis "([^"]*)" is inside the bibtex file with the file name "([^"]*)"$') { String thesis, filename ->
    BibtexGenerateFileTestDataAndOperations.checkPublicationInsideBibtex(thesis, filename)
}
// #end