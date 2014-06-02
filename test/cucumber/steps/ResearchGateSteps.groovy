import pages.BibtexGenerateFilePage
import pages.LoginPage
import pages.PublicationsPage
import pages.ResearchGatePage
import rgms.publication.Periodico
import steps.ArticleTestDataAndOperations
import steps.ResearchGateTestDataAndOperations

import static cucumber.api.groovy.EN.*
// #if($ResearchGate)
Given(~'^I have the article "([^"]*)" with file name "([^"]*)" stored in the system$') { String title, filename ->
    ArticleTestDataAndOperations.createArticle(title, filename)
    assert Periodico.findByTitle(title) != null
}

When(~'^I export a file with the articles "([^"]*)" and "([^"]*)" to Research Gate$') { String article1, article2 ->
    Periodico p1 = Periodico.findByTitle(article1);
    Periodico p2 = Periodico.findByTitle(article2);
    ResearchGateTestDataAndOperations.exportFile(p1, p2);
}

Then(~'^The articles "([^"]*)" and "([^"]*)" are stored on Research Gate database$') { String article1, article2 ->
    Periodico p1 = Periodico.findByTitle(article1);
    Periodico p2 = Periodico.findByTitle(article2);
    assert ResearchGateTestDataAndOperations.fileIsOnResearchGate(p1)
    assert ResearchGateTestDataAndOperations.fileIsOnResearchGate(p2)
}

Given(~'^my Research Gate credentials were not given to the system previously$') {
    assert ResearchGateTestDataAndOperations.getUserResearchGateCredentials() == null
}

When(~'^I select Export to Research Gate option at the export bibtex page$') {
    at BibtexGenerateFilePage
    page.select("Export to Research Gate")
}

Then(~'^I can fill my Research Gate credentials$') {
    at ResearchGatePage
    page.fillCredentials()
}

Given(~'^I am at the export to research gate page$') {
    to LoginPage
    at LoginPage
    page.fillLoginData("admin", "adminadmin")
    at PublicationsPage
    page.select("Export bibtex file")
    at BibtexGenerateFilePage
    page.select("Export to Research Gate")
    at ResearchGatePage
}

Given(~'^I had filled the Research Gate credentials with valid credentials$') {
    at ResearchGatePage
    page.fillCredentials()
}

When(~'^I click on the button Export$') {
    at ResearchGatePage
    page.clickOnExport()
}

Then(~'^I can see the articles "([^"]*)" and "([^"]*)" on the Research Gate website$') { String article1, article2 ->
    at ResearchGateWebsite
    // TODO find articles
}
// #end
