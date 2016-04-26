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

Given(~'^the system has no conferencia entitled "([^"]*)"$') { String title ->
    conferencia = Conferencia.findByTitle(title)
    assert conferencia == null
}

When(~'^I create the conferencia "([^"]*)" with file name "([^"]*)"$') { String title, String filename ->
    ConferenciaTestDataAndOperations.createConferencia(title, filename)
}

Then(~'^the conferencia "([^"]*)" is properly stored by the system$') { String title ->
    conferencia = Conferencia.findByTitle(title)
    assert ConferenciaTestDataAndOperations.conferenciaCompatibleTo(conferencia, title)
}

Given(~'^the conferencia "([^"]*)" is stored in the system with file name "([^"]*)"$') { String title, String filename ->
    ConferenciaTestDataAndOperations.createConferencia(title, filename)
    conferencia = Conferencia.findByTitle(title)
    assert conferencia != null
}

Then(~'^the conferencia "([^"]*)" is not stored twice$') { String title ->
    conferencia = Conferencia.findAllByTitle(title)
    assert conferencia.size() == 1
}

When(~'^I remove the conferencia "([^"]*)"$') { String title ->
    ConferenciaTestDataAndOperations.removeConferencia(title)
}

Then(~'^the conferencia "([^"]*)" is properly removed by the system$') { String title ->
    conferencia = Conferencia.findByTitle(title)
    assert conferencia == null
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

Given(~'^I am at the conferencias page$') {->
    LogInToPublication()
    page.select("Conferencia")
    at ConferenciaPage
}

When(~'^I select the conferencia option at the publications menu$') {->
    page.select("Conferencia")
}

When(~'^I select the new conferencia option at the conferencia page$') {->
    at ConferenciaPage
    page.selectNewConferencia()
}

When(~'^I select the home option at the conferencia page$') {->
    at ConferenciaPage
    page.selectHome()
}

When(~'^I select the conferencia "([^"]*)"$') {String title ->
    page.select(title)
}

When(~'^I click on remove$') {->
    page.select("Remove")
}

Then(~'^I can fill the conferencia details$') {->
    at ConferenciaCreatePage
    page.fillConferenciaDetails()
}

Then(~'^a list of conferencias stored by the system is displayed at the conferencia page$') {->
    at ConferenciaPage
    page.listConferencia()
}

Then(~'^I can remove one conferencia$') {->
    at ConferenciaPage
    page.removeConferencia()
}

Then(~'^I see my user listed as an author member of conferencia by default$') {->
    at ConferenciaCreatePage
    assert TestDataAndOperationsPublication.containsUser(page.selectedMembers())
}

Then(~'^I am back at the publications and conferencias menu$') {->
    at PublicationsPage
}

When(~'^I try to remove the conferencia "([^"]*)"$') { String title ->
    assert Conferencia.findByTitle(title) == null
}

Then(~'^nothing happens$') {->

}

Given(~'^the system has some conferencias stored$') {->
    initialSize = Conferencia.findAll().size()
}
When(~'^I upload the conferencias of "([^"]*)"$') { filename ->
    String path = "test" + File.separator + "functional" + File.separator + "steps" + File.separator + filename
    initialSize = Conferencia.findAll().size()
    ConferenciaTestDataAndOperations.uploadConferencias(path)
    finalSize = Conferencia.findAll().size()
    assert initialSize < finalSize
}
Then(~'^the system has all the conferencias of the xml file$') {->
    assert Conferencia.findByTitle("Latin American Conference On Computing (CLEI 1992)") != null
    assert Conferencia.findByTitle("Engineering Distributed Objects Workshop, 21st ACM International Conference on Software Engineering (ICSE 1999)") != null
    assert Conferencia.findByTitle("6th International Conference on Software Reuse (ICSR 2000)") != null

}

And(~'^I select the upload button at the conferencia page$') {->
    at ConferenciaPage
    page.uploadWithoutFile()
}
Then(~'^I\'m still on conferencia page$') {->
    at ConferenciaPage
}
And(~'^the conferencias are not stored by the system$') {->
    at ConferenciaPage
    page.checkIfConferenciaListIsEmpty()

}



#if($searchBookTitle)
Given(I am at the Conference page){
	to LoginPage
	at LoginPage
	page.fillLoginData("admin", "adminadmin")
	at PublicationsPage
	to ConferencePage
}
And(~'^the conference with booktitle "([^"]*)" is stored in the system $'){String title1 ->
	conference = Book.findByBookTitle(title1)
	assert conference.size() = 1
}
When(~'^I fill the bookTitle field with "([^"]*)" $'){String title1 ->
	to ConferencePage
	page.fillBookTitleData(title1)
}
And(~'^I click Search for Conference at the conference page $'){
	to ConferencePage
	page.selectFindConference()
}
Then(~'^a list of all conferences containg this BookTitle will be displayed on the page$'){
	to ConferencePage
	page.listConferece()
}
#end
#if(searchPages)
Given(I am at the Conference page){
	to LoginPage
	at LoginPage
	page.fillLoginData("admin", "adminadmin")
	at PublicationsPage
	to ConferencePage
}
And(~'^the conference with pages "([^"]*)" is stored in the system $'){String title1 ->
	conference = Book.findByPages(title1)
	assert conference.size() = 1
}
When(~'^I fill the pages field with "([^"]*)" $'){String title1 ->
	to ConferencePage
	page.fillPagesData(title1)
}
And(~'^I click Search for Conference at the conference page $'){
	to ConferencePage
	page.selectFindConference()
}
Then(~'^a list of all conferences containg this Pages will be displayed on the page$'){
	to ConferencePage
	page.listConferece()
}
#end
