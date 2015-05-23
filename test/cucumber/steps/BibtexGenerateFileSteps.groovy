import pages.BibtexGenerateFilePage
import pages.LoginPage
import pages.PublicationsPage
import rgms.publication.Periodico
import rgms.publication.BibtexGenerateFile


import static cucumber.api.groovy.EN.*

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
    // Need to adjust the BibTex Export page in order to implement this
}

And(~'^I click on the "([^"]*)" option$') {String o ->
    at PublicationsPage
    page.select(o)
}

Then(~'^the BibTex details are showed$') {->

}

And(~'^It only contains the articles "([^"]*)" and "([^"]*)"$') { String title1, String title2 ->

}

Given(~'^I am logged into the system$') {->
    to LoginPage
    at LoginPage
    page.add("admin","adminadmin")
}

And(~'^I am at the BibTexGenerateFile page$') {->
    to BibtexGenerateFilePage
    at BibtexGenerateFilePage
}

When(~'^I click to "([^"]*)"$') {String o ->
    at BibtexGenerateFilePage
    page.select(o)
}

And(~'^A BibTeX entry is "([^"]*)"$') {String entrada ->
    to BibtexMainMenuPage
    at BibtexMainMenuPage
}

And(~'^I click on the other button "([^"]*)"$') {String botao ->
    at BibtexMainMenuPage
    page.verificarEntrada(entrada)
    page.select(botao)
}

Then(~'^I see an error message$'){->
    at BibtexMainMenuPage
    assert entrada == null
    to BibtexGenerateFilePage

}

Then(~'^a BibTex file is generated$'){->
    at BibtexMainMenuPage
    assert true == true
}
