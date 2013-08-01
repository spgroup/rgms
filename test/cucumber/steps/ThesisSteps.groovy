import steps.TestDataAndOperations
import pages.LoginPage
import pages.ThesisCreatePage
import pages.ThesisShowPage
import rgms.publication.Tese

import java.io.File

import static cucumber.api.groovy.EN.*

Given(~'^The system has no thesis entitled "([^"]*)"$') {String title ->
    article = Tese.findByTitle(title)
    assert article == null
}

Given(~'^The thesis "([^"]*)" is stored in the system with file name "([^"]*)"$') { 
    String title, filename ->
    TestDataAndOperations.createTese(title, filename, "UFPE")
    article = Tese.findByTitle(title)
    assert article != null
}

When(~'^I create the thesis "([^"]*)" with file name "([^"]*)" and school "([^"]*)"$') {
    String title, filename, school ->
    TestDataAndOperations.createTese(title, filename, school)
}

Then(~'^The thesis "([^"]*)" is not stored twice$') { String title ->
    theses = Tese.findAllByTitle(title)
    assert theses.size() == 1
}

Then(~'^The thesis "([^"]*)" is properly stored by the system$') { String title ->
    thesis = Tese.findByTitle(title)
    assert thesis != null
}

Given(~'^I am at the create thesis page$') {->
    to LoginPage
    at LoginPage
    page.fillLoginData("admin", "adminadmin")
    to ThesisCreatePage
    at ThesisCreatePage
}

When(~'^I fill the thesis details$') {->
    page.fillThesisDetails()
}

When(~'^I fill some thesis details$') {->
    page.fillSomeThesisDetails()
}

Then(~'^I am on the thesis show page$') {->
    at ThesisShowPage
}

Then(~'^I am still on the create thesis page with the error message$') {->
    at ThesisCreatePage
}