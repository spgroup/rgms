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
//#if(ConferenciaFeature)
Given(~'^the system has conference entitled "([^"]*)" with a file name "([^"]*)"$') { String title ->
    conferencia = Conferencia.findByTitle(title)
    assert conferencia != null
}
//#end

//#if(ConferenciaFeature)
    When(~'^I change the conference file from "([^"]*)" to "([^"]*)"$') { String filename ->
    ConferenciaTestDataAndOperations.editConferencia(filename)
}
//#end

//#if(ConferenciaFeature)
    And(~'^I change the conferencia file to "([^"]*)"$') { String newarchive ->
    page.select('a', 'edit')
    at ConferenceEditPage
    def path = new File(".").getCanonicalPath() + File.separator + "test" + File.separator + "files" + File.separator
    page.edit(path + newarchive)
}
//#end

//#if(ConferenciaFeature)
    Given(~'^I am at the conference page$') {->
    at ConferenciaPage
}
//#end

//#if(ConferenciaFeature)
And(~'^the conferencia entitled "([^"]*)" is stored in the system with file name "([^"]*)"$') { String title, String filename ->
    ConferenciaTestDataAndOperations.createConferencia(title, filename)
    conferencia = Conferencia.findByTitle(title)
    assert conferencia != null
}
//#end

//#if(ConferenciaFeature)
When(~'^I select to view the conference "([^"]*)" in resulting list$') { String title ->
    page.selectViewConference(title)
    at ConferenceShowPage
}
//#end

//#if(ConferenciaFeature)
And(~'^I change the conference file to "([^"]*)"$') { String newfile ->
 //   page.select('a', 'edit')
  //  at ArticleEditPage
    def path = new File(".").getCanonicalPath() + File.separator + "test" + File.separator + "files" + File.separator
    page.edit(path + newfile)
}
//#end

//#if(ConferenciaFeature)
And(~'^I select the "Alterar" option in Conference Registration Page$') { String newfile ->
    page.select('a', 'edit')
    at ConferenciaEditPage
}
//#end

//#if(ConferenciaFeature)
Given(~'^I am at the conference registration page$') {
    at ConferenciaEditPage
}
//#end

//#if(ConferenciaFeature)
Then(~'^the conference is not stored by the system because it is invalid$') {
     finalSize = Conferencia.findAll().size()
    assert initialSize == finalSize
}
//#end

//#if(ConferenciaFeature)
When(~'^I create the conference with some field blank$') {
    at ConferenciaCreatePage
     page.fillConferenciaDetailsPartially()
     assert page.isSomeConferenciaFildsBlank()
}
//#end


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

//#if(ConferenciaFeature)
When(~'^I click on the column "Date" at the conferencia list table$') {->
    at ConferenciaPage
    page.selectColumn("Date")
}
//#end

//#if(ConferenciaFeature)
Then(~'^a list of conferencias stored by the system is displayed at the conferencia page by publication ascending date order$') {->
    at ConferenciaPage
    page.listConferencia()
    page.orderListConferencia("Date")
}
//#end

//#if(ConferenciaFeature)
When(~'^I click on the column "Research Line" at the conferencia list table$') {->
    at ConferenciaPage
    page.selectColumn("Research Line")
}
//#end

//#if(ConferenciaFeature)
Then(~'^a list of conferencias stored by the system is displayed at the conferencia page by publication ascending alphabetic order$') {->
    at ConferenciaPage
    page.listConferencia()
    page.orderListConferencia("Research Line")
}
//#end

//#if(ConferenciaFeature)
When(~'^I select the search conferencia option at the conferencia page$') {->
    at ConferenciaPage
    page.selectSearchConferencia()
}
//#end

//#if(ConferenciaFeature)
When(~'^I search for the conferencia "([^"]*)"$') { String title ->
    ConferenciaTestDataAndOperations.searchConferencia(title)
}
//#end

//#if(ConferenciaFeature)
Then(~'^there is no change in the data stored by the system$') {

}
//#end

//#if(ConferenciaFeature)
And(~'^The system has conference dated "([^"]*)"$') {String date->
    conferencia = Conferencia.findByDate(date)
}
//#end

//#if(ConferenciaFeature)
When(~'^I write "([^"]*)" at the date field$') {String date->
    at ConferenciaPage
    page.fillDateField(date)
}
//#end

//#if(ConferenciaFeature)
When(~'^I select the option Search for Conference at the conference page$') {->
    at at ConferenciaPage 
    page.selectSearchConferencia()
}
//#end

//#if(ConferenciaFeature)
Then(~'^a list of all conferences containing the date "([^"]*)" will be presented in the conference screen$') { String date->
    at ConferenciaPage
    page.FindByDate(date)
}
//#end    