import pages.BibtexGenerateFilePage

import static cucumber.api.groovy.EN.*

When(~'^I select the export bibtex file option at the publications menu$') {->
    page.select("Export Bibtex File")
}

When(~'^I select Generate All BibTex option at the export bibtex page$') {->
    at BibtexGenerateFilePage
    page.showBibtex()
}

And(~'^I type "([^"]*)" at the search toolbar in export bibtex page') {String topic ->
    at BibtexGenerateFilePage
    page.fillTextField(topic)
}

And(~'^I select "([^"]*)" option at the export bibtex page') { String button ->
    at BibtexGenerateFilePage
    page.selectButton(button)
}

Then(~'^I can see all the publications related with "([^"]*)"') { String topic ->
}

Then(~'^I can see the bibtex details$') {->
}


