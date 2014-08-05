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

Given(~'^the system has conferencia entitled "([^"]*)" with file name "([^"]*)"$') { String title, String filename ->
   ConferenciaTestDataAndOperations.createConferencia(title, filename)
    conferencia = Conferencia.findByTitle(title)
    assert ConferenciaTestDataAndOperations.conferenciaCompatibleTo(conferencia, title)
}



    When(~'^I change the conferencia title from "([^"]*)" to "([^"]*)"$') { String title ->
    ConferenciaTestDataAndOperations.editConferencia(title)
}



    And(~'^I change the conferencia title to "([^"]*)"$') { String newtitle ->
    page.select('a', 'edit')
    at ConferenceEditPage
   page.edit(path + newtitle)
}



    Given(~'^I am at the conferencia page$') {->
    at ConferenciaPage
}



And(~'^the conferencia entitled "([^"]*)" is stored in the system with file name "([^"]*)"$') { String title, String filename ->
    ConferenciaTestDataAndOperations.createConferencia(title, filename)
    conferencia = Conferencia.findByTitle(title)
    assert conferencia != null
}



When(~'^I select to view the conferencia "([^"]*)" in resulting list$') { String title ->
    page.selectViewConference(title)
    at ConferenceShowPage
}




And(~'^I select the edit option in Conferencia Registration Page$') { String newfile ->
    page.select('a', 'edit')
    at ConferenciaEditPage
}



Given(~'^I am at the conferencia create page$') {->
    at ConferenciaCreatePage
}



Then(~'^the conferencia is not stored by the system because it is invalid$') {
     finalSize = Conferencia.findAll().size()
    assert initialSize == finalSize
}



When(~'^I create the conferencia with some field blank$') {
    at ConferenciaCreatePage
     page.fillConferenciaDetailsPartially()
     assert page.isSomeConferenciaFildsBlank() == true
}



When(~'^I create the conferencia "([^"]*)" with file name "([^"]*)"$') { String title, String filename ->
  ConferenciaTestDataAndOperations.createConferencia(title, filename)
  conferencia = Conferencia.findByTitle(title)
  assert conferencia != null
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
    def conferencia = Conferencia.findByTitle(title)
   assert conferencia != null
    ConferenciaTestDataAndOperations.removeConferencia(title)
    testRemoveConferencia = Conferencia.findByTitle(title)
    assert testRemoveConferencia == null
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


Then(~'^the conferencia entitled "([^"]*)" is found$') {->
 assert ConferenciaTestDataAndOperations.searchConferencia(title)
}

Given(~'^the system has some conferencias stored$') {->
    assert conferencia != null
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


When(~'^I click on the column "([^"]*)" at the conferencia list table$') {String columnName ->
    at ConferenciaPage
    page.selectColumn(columnName)
}



Then(~'^a list of conferencias stored by the system is displayed at the conferencia page by publication ascending date order$') {->
    at ConferenciaPage
    page.checkIfConferenciaListIsOrderedByDate()
}


Given(~'^the system has conferencia entitled "([^"]*)"$') { String title ->
    def conferencia = Conferencia.findByTitle(title)
    assert conferencia != null
}



Then(~'^a list of conferencias stored by the system is displayed at the conferencia page by ascending alphabetic order$') {->
    at ConferenciaPage
    page.checkIfConferenciaListIsOrderedByTitle()
}



When(~'^I select the search conferencia option at the conferencia page$') {->
    at ConferenciaPage
    page.selectSearchConferencia()
}



When(~'^I search for the conferencia entitled "([^"]*)"$') { String title ->
    assert ConferenciaTestDataAndOperations.searchConferencia(title)
}



Then(~'^the conferencia entitled "([^"]*)" is not found$') { String title ->    
    assert Conferencia.findByTitle(title) == null
}



And(~'^the system has conferencia dated "([^"]*)"$') {String date->
    conferencia = Conferencia.findByDate(date)
    assert conferencia !=null
}



When(~'^I write "([^"]*)" at the date field$') {String date->
    at ConferenciaPage
    page.fillDateField(date)
}



When(~'^I select the option Search for conferencia at the conferencia page$') {->
    at ConferenciaPage 
    page.selectSearchConferencia()
}



Then(~'^a list of all conferencias containing the date "([^"]*)" will be presented in the conferencia screen$') { String date->
    at ConferenciaPage
    page.FindByDate(date)
}
    

When(~'^I check the conferencia list$') {->
    def conferencias = Conferencia.findAll()
    assert conferencias !=null
}

Then(~'^the conferencia list contains "([^"]*)"$') {String title->
    def conferencia = Conferencia.findByTitle(title)
    assert conferencia !=null
}
