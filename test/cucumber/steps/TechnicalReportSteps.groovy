import pages.*
import pages.member.*
import pages.technicalReport.TechnicalReportCreatePage
import pages.technicalReport.TechnicalReportEditPage
import pages.technicalReport.TechnicalReportPage
import pages.technicalReport.TechnicalReportShowPage
import rgms.authentication.User
import rgms.member.Member
import rgms.publication.TechnicalReport
import steps.TestDataAndOperations
import steps.TechnicalReportTestDataAndOperations
import steps.TestDataAndOperationsPublication

import static cucumber.api.groovy.EN.*

//new technical report
Given(~'^The system has no technical report entitled "([^"]*)"$') { String title ->
    tech = TechnicalReport.findByTitle(title)
    assert tech == null
}

//new valid technical report
When(~'^I create the technical report "([^"]*)" with file name "([^"]*)"$') { String title, filename ->
    TechnicalReportTestDataAndOperations.createTechnicalReport(title, filename)
}
Then(~'^The technical report "([^"]*)" is properly stored by the system.$') { String title ->
    tech = TechnicalReport.findByTitle(title)
    assert TechnicalReportTestDataAndOperations.technicalReportCompatibleTo(tech, title)
}
// new invalid technical report: empty institution
When(~'^I create the technical report "([^"]*)" with file name "([^"]*)" and empty institution$') { String title, filename ->
    TechnicalReportTestDataAndOperations.createTechnicalReportWithEmptyInstitution(title, filename)
}
Then(~'^The technical report "([^"]*)" is not properly stored by the system$') { String title ->
	tech = TechnicalReport.findByTitle(title)
	assert tech == null
}

// edit existing technical report with empty title
Given(~'^The system has an technical report entitled "([^"]*)" with file name "([^"]*)"$') { String title, filename ->
    TechnicalReportTestDataAndOperations.createTechnicalReport(title, filename)
    tech = TechnicalReport.findByTitle(title)
    assert tech != null
}
When(~'^I edit the technical report title from "([^"]*)" to "([^"]*)"$') { String oldtitle, newtitle ->
    def updatedTech = TechnicalReportTestDataAndOperations.editTech(oldtitle, newtitle)
    //assert updatedTech != null
}
Then(~'^The technical report "([^"]*)" is not updated by the system$') { String title ->
    tech = TechnicalReport.findByTitle(title)
    assert tech != null
}

//new valid technical report
Given (~'I am at the technical report page'){->
	to LoginPage
	at Login Page
	page.add("admin", "adminadmin")
	at PublicationPage
	page.select("Technical Report")
	at TechinicalReportPage
}
And (~'The system has no technical report entitled "([^"]*)" '){String tittle
	page.selectTechincalReport()
	at TechnicalReportPage
	
}

When(~'I click the "([^"]*)" button'){String buttonName
	page.selectButton()
	at TechnicalReportPage
}

And (~'The report is saved after I filled the details with tittle "([^"]*)", file name "([^"]*)" and institution "([^"]*)"'){String tittle, filename, institution
	page.selectNewTechnicalReport()
	page.fillTechnicalReportDetails(TestDataAndOperations.getTestFilesPath(filename), title, instutution)
	at TechnicalReportPage
	
}

Then(~'The technical report "([^"]*)" is saved on the system'){String tittle
	page.selectSaveFile(tittle)
	at technicalReportPage

}


// edit existing technical report with invalid title web

Given (~'I am at the technical report page'){->
	to LoginPage
	at Login Page
	page.add("admin", "adminadmin")
	at PublicationPage
	page.select("Technical Report")
	at TechinicalReportPage
}
And (~'The technical report "([^"]*)" is stored in the system with the file name "([^"]*)"'){String title, filename
	page.selectTechincalReport()
	at TechnicalReportPage
	page.fillTechnicalReportDetails(TestDataAndOperations.getTestFilesPath(filename), title)
	page.selectEditTechincalReport()
	techReport = TechinicalReport.findByTittle(title)
	assert techReport != null
	to TechnicalReportPage
	at TechnicalReportPage
	
}

When(~'I change the title report to a blank one'){
	at TechincalReportPage
	page.edit("")
}

Then(~'The technical report is not saved by the system'){
	at TechnicalReportPage

}

And(~'I remain at the technical report edit page'){
	at technicalReportPage
	to technicalReportEditPage
}

//remove existing technical report
When(~'^I select to view the technical report "([^"]*)" in resulting list$') { String title ->
    page.selectViewTechnicalReport(title)
    at TechnicalReportShowPage
}
And(~'^I select the option to remove$') { ->
    page.select('input', 'delete')
}

Then(~'^The technical report "([^"]*)" is properly removed by the system$') { String title ->
	techReport = TechnicalReport.findByTitle(title)
	assert techReport == null
}

And(~'^The system goes to the technical reports page$') { ->
    at TechnicalReportPage
}

When(~'^I click on "New TechnicalReport" option at Technical Report list$') {->
    at TechnicalReportPage
    page.selectNewTechnicalReport()
}

Then(~'^I see my user listed as an author member of technical report by default$') {->
    at TechnicalReportCreatePage
    assert TestDataAndOperationsPublication.containsUser(page.selectedMembers())
}

Then(~'^I see my school name as institution of technical report by default$') {->
    at TechnicalReportCreatePage
    userData = User.findByUsername('admin')?.author?.university
    assert page.currentInstitution() == userData
}

And (~'^I select the option to edit$'){->
    at TechnicalReportShowPage
    page.select('a', 'edit')
    at TechnicalReportEditPage
}

And (~'^I press the button alterar$'){ ->
    at TechnicalReportEditPage
    page.select('Alterar')
}

Then(~'^The technical report is not saved by the system$'){ ->
    tech = TechnicalReport.findByTitle("")
    assert tech == null
}

And(~'^I remain at the technical report edit page$'){ ->
    at TechnicalReportEditPage
}

And(~'^I change the technical report title to "([^"]*)" filename to "([^"]*)" and institution to "([^"]*)"$') {String t, fn, i ->
    at TechnicalReportEditPage
    page.edit(t, TestDataAndOperations.getTestFilesPath(fn), i)
}

Given(~'^The system has an technical report entitled "([^"]*)" with file name "([^"]*)" and institution "([^"]*)"$'){ String title, filename , institute->
    def path = "web-app/uploads/"
    techReport = TechnicalReport.findByTitle(title)
    assert techReport != null
    assert techReport.file == path + filename
    assert techReport.title == title
    assert techReport.institution == institute
}

And(~'^I am at the technical reports list page$') { ->
    to TechnicalReportPage
    at TechnicalReportPage
}

Then(~'^The technical report "([^"]*)" with filename "([^"]*)" and institution "([^"]*)" is properly updated.$'){ String t, fn, i ->
    tech = TechnicalReport.findByTitleAndFileAndInstitution(t, fn, i)
    assert tech != null
}

