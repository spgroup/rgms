import rgms.publication.*

import static cucumber.api.groovy.EN.*

//You can implement missing steps with the snippets below:
Given(~'^I am on Import Bibtex File Menu$') {->
}

When(~'^I click "([^"]*)"$') { String arg1 ->
}

When(~'^selected a bibtex file and I click "([^"]*)"$') { String arg1 ->
}

Then(~'^is created all corresponding publications$') {->
    BibtexFileController bibtexFileController = new BibtexFileController()
    BibtexFile bibtexFile = bibtexFileController.transform(new File("test//cucumber//steps//sample.bibtex"))
    assert bibtexFile.getPublications().size() == 3
}

Then(~'^all of then are stored$') {->
}

When(~'^selected a bibtex file unformatted and I click "([^"]*)"$') { String arg1 ->
}

//@Test(expected=RuntimeException.class)
Then(~'^the system output the message error "([^"]*)"$') { String arg1 ->
    BibtexFileController bibtexFileController = new BibtexFileController()
    BibtexFile bibtexFile = bibtexFileController.transform(new File("test//cucumber//steps//sample.bibtex"))
}

Then(~'^none publication is stored$') {->
}

When(~'^selected a bibtex file with one Dissertacao and two Tese and I click "([^"]*)"$') { String arg1 ->
}

Then(~'^is created one Dissertacao publication$') {->
    BibtexFileController bibtexFileController = new BibtexFileController()
    BibtexFile bibtexFile = bibtexFileController.transform(new File("test//cucumber//steps//sample.bibtex"))
    assert bibtexFile.getPublications(Conferencia.class).isEmpty()
    assert bibtexFile.getPublications(Dissertacao.class).size() == 1
}

Then(~'^is created two Tese publications$') {->
    BibtexFileController bibtexFileController = new BibtexFileController()
    BibtexFile bibtexFile = bibtexFileController.transform(new File("test//cucumber//steps//sample.bibtex"))
    assert bibtexFile.getPublications(TechnicalReport.class).isEmpty()
    assert bibtexFile.getPublications(Tese.class).size() == 2
}

Then(~'^one Dissertacao is stored and two Tese is stored$') {->
}
