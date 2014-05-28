package cucumber.steps

import pages.LoginPage
import pages.PublicationsPage
import pages.BibtexImportPage
import rgms.publication.*
import steps.TestDataBibTexFile

import static cucumber.api.groovy.EN.*

def LogInToPublication(){
    to LoginPage
    at LoginPage
    page.fillLoginData("admin", "adminadmin")
    at PublicationsPage
}
//You can implement missing steps with the snippets below:
Given(~'^I am on Import Bibtex File Menu$') {->
    LogInToPublication()
    page.select("Import Bibtex File")
    at BibtexImportPage
}

When(~'^I click "([^"]*)"$') { String arg1 ->
    at BibtexImportPage
    page.selectButton(arg1)
}

When(~'^I select a bibtex file and I click "([^"]*)"$') { String arg1 ->
}

Then(~'^all corresponding publications are created$') {->
    BibtexFile bibtexFile = TestDataBibTexFile.openBibTexFile("test//cucumber//steps//sample.bibtex")
    assert bibtexFile.getPublications().size() >= 3
}

Then(~'^all of them are stored$') {->
}

When(~'^I select a bibtex file unformatted and I click "([^"]*)"$') { String arg1 ->
}

//@Test(expected=RuntimeException.class)
Then(~'^the system output the message error "([^"]*)"$') { String arg1 ->
    BibtexFile bibtexFile = TestDataBibTexFile.openBibTexFile("test//cucumber//steps//sample.bibtex")
}

Then(~'^no publication is stored$') {->
}

When(~'^I select a bibtex file with one Dissertation and two Thesis and I click "([^"]*)"$') { String arg1 ->
}

Then(~'^at least one Dissertation publication is created$') {->
    BibtexFile bibtexFile = TestDataBibTexFile.openBibTexFile("test//cucumber//steps//sample.bibtex")
    assert bibtexFile.getPublications(Dissertacao.class).size() >= 1
}

Then(~'^at least two Thesis publications is created$') {->
    BibtexFile bibtexFile = TestDataBibTexFile.openBibTexFile("test//cucumber//steps//sample.bibtex")
    assert bibtexFile.getPublications(Tese.class).size() >= 2
}

And(~'^one Dissertation is stored$') { ->
}
And(~'^two Thesis is stored$') { ->
}