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
Given(~'^I am at the technical reports page$') { ->
    to LoginPage
    at LoginPage
    page.fillLoginData("admin", "adminadmin")
    at PublicationsPage
    page.select("Technical Report")
    at TechnicalReportPage
}

When(~'^I select the new technical report button$'){ ->
    page.selectNewTechnicalReport()
    at TechnicalReportCreatePage
}

And(~'^I fill the technical report details with title "([^"]*)" file name "([^"]*)" and institution "([^"]*)"$'){String title, filename, institution ->
    page.fillTechnicalReportDetails(TestDataAndOperations.getTestFilesPath(filename), title, institution)
}

And(~'^I select the save technical report button$'){ ->
    page.selectCreateTechnicalReport()
}

Then(~'^The technical report "([^"]*)" details page is shown$') { String title ->
    at TechnicalReportShowPage
}

// edit existing technical report with invalid title web

And(~'^the technical report "([^"]*)" is stored in the system with file name "([^"]*)"$') { String title, filename ->
    page.selectNewTechnicalReport()
    at TechnicalReportCreatePage
    page.fillTechnicalReportDetails(TestDataAndOperations.getTestFilesPath(filename), title)
    page.selectCreateTechnicalReport()
    techReport = TechnicalReport.findByTitle(title)
    assert techReport != null
    to TechnicalReportPage
    at TechnicalReportPage
}

When(~'^I select to view "([^"]*)" in technical reports resulting list$') { String oldtitle ->
    page.selectViewTechnicalReport(oldtitle)
    at TechnicalReportShowPage
}

And(~'^I change the technical report title to a blank one$')  {  ->
    at TechnicalReportEditPage
    page.edit("")
}

Then(~'^I cannot select the "([^"]*)" option$') { String option ->
    at TechnicalReportEditPage
    page.select(option)
    at TechnicalReportEditPage
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

