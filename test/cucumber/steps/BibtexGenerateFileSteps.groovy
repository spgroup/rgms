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


