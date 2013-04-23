import pages.LoginPage
import pages.PublicationsPage

import static cucumber.api.groovy.EN.*

Given(~'^I am at the publications menu$') {->
	to LoginPage
	at LoginPage
	page.fillLoginData("admin", "adminadmin")
	at PublicationsPage
}

When(~'^I select the "([^"]*)" option at the publications menu$') { String option ->
	page.select(option)
}
