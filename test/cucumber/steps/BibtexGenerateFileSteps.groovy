import pages.BibtexGenerateFilePage

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

And(~'^I have an article named "([^"]*)"$') {String title ->
    article = Periodico.findByTitle(title)
    assert article != null
}

When(~'^I generate a BibTex file$') {->
    // Do something that I have no idea at the moment
}

Then(~'^the BibTex file has unique citation-keys for each article$') {->
    // Do something that I have no idea at the moment
}

Given(~'^I am on the "Publications" menu$') {->
    to LoginPage
    at LoginPage
    page.add("admin", "adminadmin")
    at PublicationsPage
}

When(~'^I select a subset of publications$') {->
    // Do something that I have no idea at the moment
}

And(~'^I click on the "([^"]*)" option$') {String o ->
    at PublicationsPage
    page.select(o)
}

Then(~'^the system generates a BibTex file containing only the publications from the selected subset$') {->
    // Do something that I have no idea at the moment
    // I guess I am mixing GUI and controller stuff here
}

Given(~'I am logged into the system$') {->
    to LoginPage
    at LoginPage
    page.add("admin","adminadmin")
}

And(~'I am at the "([^"]*)"$') {->
    at BibTexMainMenuPage
}

And(~'A BibTeX entry is "([^"]*)"$') {String entrada ->
    at BibTexMainMenuPage
    page.verificarEntrada(entrada)
}

When(~'I click to "([^"]*)"$') {String o ->
    at BibTexMainMenuPage
    page.select(o)
}

Then(~'I see an error message$'){->
    at BibTexMainMenuPage
}

Then(~'a BibTex file is generated$'){->
}
