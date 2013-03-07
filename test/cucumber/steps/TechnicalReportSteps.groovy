package steps

import cucumber.runtime.PendingException
import pages.ArticleCreatePage
import pages.ArticlesPage
import pages.LoginPage
import pages.PublicationsPage
import rgms.publication.Periodico
import rgms.publication.TechnicalReport
import rgms.publication.Publication
import rgms.publication.TechnicalReportController
import steps.TestReportDataAndOperations

import static cucumber.api.groovy.EN.*

Given(~'^the system has no technical report entitled "([^"]*)"$') { String title ->
    // Express the Regexp above with the code you wish you had
    def tr = TechnicalReport.findByTitle(title)
    assert tr == null
}

When(~'^I create the technical report "([^"]*)" with file name "([^"]*)"$') { String title, String file ->
    TestReportDataAndOperations.createReport(title, file)
}

Then(~'^the technical report "([^"]*)" is properly stored by the system$') { String title ->
    def tr = TechnicalReport.findByTitle(title)
    assert tr != null
}


Given(~'^the system has no technical report without a title') { ->
    // Express the Regexp above with the code you wish you had
    def tr = TechnicalReport.findByTitle(null)
    assert tr == null
}

When(~'^I create the technical report with with no title and file name "([^"]*)"$') { String file ->
    TestReportDataAndOperations.createReport(file)
}

Then(~'^the technical report will not be properly stored by the system because it\'s invalid') { ->
    def tr = TechnicalReport.findByFile(null)
    assert tr == null
}

Given(~'^the system has a technical report "([^"]*)" with institution "([^"]*)" stored$') { String title, String inst ->
    // Express the Regexp above with the code you wish you had
    def tr = TechnicalReport.find([title: title, institution: inst])
    assert tr != null
}

When(~'^I update that technical report "([^"]*)" with no title and no institution$') { String title ->
    TestReportDataAndOperations.updateReportWithNoTitleAndInst(title)
}