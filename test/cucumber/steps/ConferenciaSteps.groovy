import pages.ConferenciaCreatePage
import pages.ConferenciaPage
import pages.LoginPage
import pages.PublicationsPage
import rgms.publication.Conferencia
import rgms.publication.Publication
import steps.TestDataAndOperations

import static cucumber.api.groovy.EN.*

Given(~'^the system has no conferencia entitled "([^"]*)"$') { String title -> 
    conferencia = Conferencia.findByTitle(title)
    assert conferencia == null
}

When(~'^I create the conferencia "([^"]*)" with file name "([^"]*)"$') { String title, String filename ->
    TestDataAndOperations.createConferencia(title, filename)
}

Then(~'^the conferencia "([^"]*)" is properly stored by the system$') { String title ->
    conferencia = Conferencia.findByTitle(title)
    assert TestDataAndOperations.conferenciaCompatibleTo(conferencia, title)
}

Given(~'^the conferencia "([^"]*)" is stored in the system with file name "([^"]*)"$') { String title, String filename ->
    TestDataAndOperations.createConferencia(title, filename)
    conferencia = Conferencia.findByTitle(title)
    assert conferencia != null
}

Then(~'^the conferencia "([^"]*)" is not stored twice$') { String title ->
    conferencia = Conferencia.findByTitle(title)
    assert conferencia.size() == 1
}

When(~'^I remove the conferencia "([^"]*)"$') { String title ->
    TestDataAndOperations.removeConferencia(title)
}

Then(~'^the conferencia "([^"]*)" is properly removed by the system$') { String title ->
    conferencia = Conferencia.findByTitle(title)
    assert conferencia == null
}

Given(~'^I am at the publications and conferencias menu$') {->
    to LoginPage
    at LoginPage
    page.fillLoginData("admin", "adminadmin")
    at PublicationsPage
}

When(~'^I select the conferencia option at the publications menu$') { ->
    page.select("Conferencia")
}

When(~'^I select the new conferencia option at the conferencia page$') { ->
    at ConferenciaPage
    page.selectNewConferencia()
}

Then(~'^I can fill the conferencia details$') { ->
    at ConferenciaCreatePage
    page.fillConferenciaDetails()
}

Then(~'^a list of conferencias stored by the system is displayed at the conferencia page$') { ->
    at ConferenciaPage
    page.listConferencia()
}