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

#if ($InvalidEntryOfBibtex)
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
#end

#if ($CorrectEntryOfBibtex)
Then(~'a BibTex file is generated$'){->
}
#end
