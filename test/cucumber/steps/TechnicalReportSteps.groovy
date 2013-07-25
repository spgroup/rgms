import pages.*
import rgms.member.Member
import rgms.publication.TechnicalReport
import steps.TestDataAndOperations

import static cucumber.api.groovy.EN.*

// new invalid technical report
Given(~'^The system has no technical report entitled "([^"]*)"$') { String title ->
    tech = TechnicalReport.findByTitle(title)
    assert tech == null
}
When(~'^I create the technical report "([^"]*)" with file name "([^"]*)"$') { String title, filename ->
    TestDataAndOperations.createTechnicalReport(title, filename)
}
Then(~'^The technical report "([^"]*)" is not properly stored by the system$') { String title ->
    tech = TechnicalReport.findByTitle(title)
    assert TestDataAndOperations.technicalReportCompatibleTo(tech, title)
}

// edit existing technical report with empty title
Given(~'^The system has an technical report entitled "([^"]*)" with file name "([^"]*)"$') { String title, filename ->
    TestDataAndOperations.createTechnicalReport(title, filename)
    tech = TechnicalReport.findByTitle(title)
    assert tech != null
}
When(~'^I edit the technical report title from "([^"]*)" to "([^"]*)"$') { String oldtitle, newtitle ->
    def updatedTech = TestDataAndOperations.editTech(oldtitle, newtitle)
    //


Given(~'^I select the "([^"]*)" option at the publications menu$') { String option ->
	page.select(option)
}
assert updatedTech != null
}
Then(~'^The technical report "([^"]*)" is not updated by the system$') { String title ->
    tech = TechnicalReport.findByTitle(title)
    assert tech != null
}
// edit existing technical report with invalid title web
Given(~'^I am at the technical reports page and the technical report "([^"]*)" is stored in the system with file name "([^"]*)"$') { String title, filename ->
    to LoginPage
    at LoginPage
    page.fillLoginData("admin", "adminadmin")
    at PublicationsPage
    page.select("Technical Report")
    at TechnicalReportPage
    page.selectNewTechnicalReport()
    at TechnicalReportCreatePage
    def path = new File(".").getCanonicalPath() + File.separator + "test" + File.separator + "functional" + File.separator + "steps" + File.separator
    page.fillTechnicalReportDetails(path + filename, title)
    page.selectCreateTechnicalReport()
    techReport = TechnicalReport.findByTitle(title)
    assert techReport != null
    to TechnicalReportPage
    at TechnicalReportPage
}

When(~'^I select to view "([^"]*)" in resulting list and I change the technical report title to a blank one') { String oldtitle ->
    page.selectViewTechnicalReport(oldtitle)
    at TechnicalReportShowPage
    page.select('a', 'edit')
    at TechnicalReportEditPage
    page.edit("")
}

Then(~'^I cannot select the "([^"]*)" option$') { String option ->
    at TechnicalReportEditPage
    page.select(option)
    at TechnicalReportEditPage
}

Then(~'^the technical report details are showed and I can select the option to remove$') {->
    at TechnicalReportShowPage
    page.select('input', 'delete')
}

Then(~'^the technical report "([^"]*)" is properly removed by the system$') { String title ->
    techReport = TechnicalReport.findByTitle(title)
    assert techReport == null
}

When(~'^I select to view the technical report "([^"]*)" in resulting list$') { String title ->
    page.selectViewTechnicalReport(title)
    at TechnicalReportShowPage
}

When(~'^I click on "New TechnicalReport" option at Technical Report list$') {->
    at TechnicalReportPage
    page.selectNewTechnicalReport()
}

Then(~'^I see my user listed as an author member of technical report by default$') {->
    at TechnicalReportCreatePage
    userData = Member.findByUsername('admin').id.toString()
    assert page.selectedMembers().contains(userData)
}
