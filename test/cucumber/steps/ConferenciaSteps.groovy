import cucumber.runtime.PendingException
import pages.Conferencia.ConferenciaCreatePage
import pages.Conferencia.ConferenciaPage
import pages.LoginPage
import pages.PublicationsPage
import rgms.member.Member
import rgms.publication.Conferencia
import steps.TestDataAndOperations
import steps.TestDataAndOperationsPublication
import steps.ConferenciaTestDataAndOperations

import static cucumber.api.groovy.EN.*

Given(~'^the system has no conference entitled "([^"]*)"$') { String title ->
    def conference = Conferencia.findByTitle(title)
    assert conference == null
}

When(~'^I create the conference "([^"]*)" with file name "([^"]*)"$') { String title, String filename ->
    ConferenciaTestDataAndOperations.createConferencia(title, filename)
}

Then(~'^the conference "([^"]*)" is properly stored by the system$') { String title ->
    def conference = Conferencia.findByTitle(title)
    assert ConferenciaTestDataAndOperations.conferenciaCompatibleTo(conference, title)
}

Given(~'^the conference "([^"]*)" is stored in the system with file name "([^"]*)"$') { String title, String filename ->
    ConferenciaTestDataAndOperations.createConferencia(title, filename)
    def conference = Conferencia.findByTitle(title)
    assert conference != null
}

Then(~'^the conference "([^"]*)" is not stored twice$') { String title ->
    def conference = Conferencia.findAllByTitle(title)
    assert conference.size() == 1
}

When(~'^I remove the conference "([^"]*)"$') { String title ->
    ConferenciaTestDataAndOperations.removeConferencia(title)
}

Then(~'^the conference "([^"]*)" is properly removed by the system$') { String title ->
    conference = Conferencia.findByTitle(title)
    assert conference == null
}

Given(~'^I am at the publications$') {->
    LogInToPublication()
}

def LogInToPublication(){
    to LoginPage
    at LoginPage
    page.fillLoginData("admin", "adminadmin")
    at PublicationsPage
}

Given(~'^I am at the conferences page$') {->
    LogInToPublication()
    page.select("Conferencia")
    at ConferenciaPage
}

When(~'^I select the conference option at the publications menu$') {->
    page.select("Conferencia")
}

When(~'^I select the new conference option at the conference page$') {->
    at ConferenciaPage
    page.selectNewConferencia()
}

When(~'^I select the home option at the conference page$') {->
    at ConferenciaPage
    page.selectHome()
}

When(~'^I select the conference "([^"]*)"$') {String title ->
    page.select(title)
}

When(~'^I click on remove$') {->
    page.select("Remove")
}

Then(~'^I can fill the conference details$') {->
    at ConferenciaCreatePage
    page.fillConferenciaDetails()
}

Then(~'^a list of conferences stored by the system is displayed at the conference page$') {->
    at ConferenciaPage
    page.listConferencia()
}

Then(~'^I can remove one conference$') {->
    at ConferenciaPage
    page.removeConferencia()
}

Then(~'^I see my user listed as an author member of conference by default$') {->
    at ConferenciaCreatePage
    assert TestDataAndOperationsPublication.containsUser(page.selectedMembers())
}

Then(~'^I am back at the publications and conferences menu$') {->
    at PublicationsPage
}

When(~'^I try to remove the conference "([^"]*)"$') { String title ->
    assert Conferencia.findByTitle(title) == null
}

Then(~'^nothing happens$') {->

}

Given(~'^the system has some conferences stored$') {->
    initialSize = Conferencia.findAll().size()
}
When(~'^I upload the conferences of "([^"]*)"$') { filename ->
    String path = "test" + File.separator + "functional" + File.separator + "steps" + File.separator + filename
    initialSize = Conferencia.findAll().size()
    ConferenciaTestDataAndOperations.uploadConferencias(path)
    finalSize = Conferencia.findAll().size()
    assert initialSize < finalSize
}
Then(~'^the system has all the conferences of the xml file$') {->
    assert Conferencia.findByTitle("Latin American Conference On Computing (CLEI 1992)") != null
    assert Conferencia.findByTitle("Engineering Distributed Objects Workshop, 21st ACM International Conference on Software Engineering (ICSE 1999)") != null
    assert Conferencia.findByTitle("6th International Conference on Software Reuse (ICSR 2000)") != null

}

And(~'^I select the upload button at the conference page$') {->
    at ConferenciaPage
    page.uploadWithoutFile()
}
Then(~'^I\'m still on conference page$') {->
    at ConferenciaPage
}
And(~'^the conferences are not stored by the system$') {->
    at ConferenciaPage
    page.checkIfConferenciaListIsEmpty()

}

Then(~'^I see my user listed as an author member of conference by default in the first position$') {
    at ConferenciaCreatePage
}

Given(~'^the member "([^"]*)" is registered in the system$') { String memberName ->

}

/*
When(~'^I select the "([^"]*)"$ option at the conference page$'){ String option ->

}
*/

And(~'^I click on "([^"]*)" at the create conference page$') { String option ->
    page.select(option)
}

Then(~'^I select the member "([^"]*)" in member list$') { String memberName ->

}

And(~'^I see the new member author "([^"]*)" at the last position of members authors$'){ String memberName ->

}

Given(~'^the conference "([^"]*)" is stored in the system$'){ String title ->
    def conference = Conferencia.findByTitle(title)
    assert conference != null
}

And(~'^I see the new member author "([^"]*)" at the last position of members authors list$') { String memberName ->

}

And(~'^the member "([^"]*)" is member author of the conference "([^"]*)"$'){ String memberName, String title ->

}

And(~'^I select the author "([^"]*)" at the member authors list in the show conference page$') { String memberName ->

}

And(~'^I click on "([^"]*)" at the show conference page$') { String option ->

}

And(~'^I click on "([^"]*)" at the edit conference page$') { String option ->

}

Then(~'^the member "([^"]*)" is no more listed in possible authors list$') { String memberName ->

}

And(~'^I click on "([^"]*)" at the show member page$') { String option ->

}

Then(~'^the member "([^"]*)" is not member author of the conference "([^"]*)"$') { String memberName, String title ->

}