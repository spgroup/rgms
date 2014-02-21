import pages.LoginPage
import pages.PublicationsPage

import static cucumber.api.groovy.EN.And
import static cucumber.api.groovy.EN.Given
import static cucumber.api.groovy.EN.When

Given(~'^I am at the publications menu$') {->
    to LoginPage
    at LoginPage
    page.fillLoginData("admin", "adminadmin")
    at PublicationsPage
}

And(~'^I am at the initial RGMS page$'){->
    to PublicationsPage
}

When(~'^I select the "([^"]*)" option at the publications menu$') { String option ->
    at PublicationsPage
    page.select(option)
}

When(~'^I select the "([^"]*)" option at the program menu$') { String option ->
    page.select(option)
}