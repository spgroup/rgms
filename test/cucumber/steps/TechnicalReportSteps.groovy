import pages.TechnicalReportCreatePage
import pages.TechnicalReportPage
import pages.TechnicalReportShowPage
import pages.TechnicalReportEditPage
import pages.LoginPage
import pages.PublicationsPage
import rgms.publication.TechnicalReport
import steps.TestDataAndOperations

import static cucumber.api.groovy.EN.*


/**
 * @author Felipe
 */
Given(~'^I am at the publications menu and the report "([^"]*)" is stored in the system with file name "([^"]*)"$') {String title, filename->
	to LoginPage
	at LoginPage
	page.fillLoginData("admin", "adminadmin")
	at PublicationsPage
}
When (~'^I select to view "([^"]*)" in resulting technical report list$') {String title  ->
	at TechnicalReportPage
	page.selectViewTech(title)
	at TechnicalReportShowPage
}
When (~'^I change the report title to "([^"]*)"$') {String newtitle  ->
	page.select('a','edit')
	at TechnicalReportEditPage
	page.edit(newtitle)
}
Then(~'I select the "([^"]*)" option and a error message is shown$') { String option ->
	at TechnicalReportEditPage
	page.select(option)
}


/**
 * Segunda
 */
When(~'I select the edit button$') { ->
	page.select('a','edit')
	at TechnicalReportEditPage
}
Then(~'I can select the "([^"]*)" option in edition after change the title to "([^"]*)"$') { String option, newtitle ->
	page.edit(newtitle)
	page.select(option)
}
/**
 * Terceira
 * 
 */
Given(~'^The system has no technical report entitled "([^"]*)"$') { String title ->
	tech = TechnicalReport.findByTitle(title)
	assert tech == null
}
When(~'^I create the technical report "([^"]*)" with file name "([^"]*)"$') { String title, filename ->
	TestDataAndOperations.createReport(title, filename)
}
Then(~'^The technical report "([^"]*)" is not properly stored by the system$') { String title ->
	tech = TechnicalReport.findByTitle(title)
	assert TestDataAndOperations.compatibleTechTo(tech, title)
}

/**
 * Quarta
 */
Given(~'^The system has an technical report entitled "([^"]*)" with file name "([^"]*)"$') { String title, filename ->
	TestDataAndOperations.createReport(title, filename)
	tech = TechnicalReport.findByTitle(title)
	assert tech != null
}
When (~'^I edit the technical report title from "([^"]*)" to "([^"]*)"$') {String oldtitle, newtitle  ->
	def updatedTech = TestDataAndOperations.editTech(oldtitle, newtitle)
	assert updatedTech != null
}
Then(~'^The technical report "([^"]*)" is not updated by the system$') { String title ->
	tech = TechnicalReport.findByTitle(title)
	assert tech != null
}

