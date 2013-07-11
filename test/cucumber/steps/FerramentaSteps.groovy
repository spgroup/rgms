import steps.TestDataAndOperations
import pages.LoginPage
import pages.PublicationsPage
import pages.FerramentaPage
import pages.FerramentaCreatePage
import pages.FerramentaShowPage
import pages.FerramentaEditPage
import rgms.publication.Ferramenta

import static cucumber.api.groovy.EN.*

// new ferramenta without website
Given(~'^the system has no ferramenta entitled "([^"]*)"$') { String title ->
	ferramenta = Ferramenta.findByTitle(title)
    assert ferramenta == null
}
When(~'^I create the ferramenta "([^"]*)" with file name "([^"]*)" without its website$') { String title, String filename ->
	TestDataAndOperations.createFerramenta(title, filename)
}
Then(~'^the ferramenta "([^"]*)" is not stored$') { String title ->
	ferramentas = Ferramenta.findAllByTitle(title)
    assert ferramentas.size() == 0
}

// duplicate ferramenta 
Given(~'^the ferramenta "([^"]*)" is stored in the system with file name "([^"]*)"$') { String title, String filename ->
	TestDataAndOperations.createFerramenta(title, filename)
    ferramenta = Ferramenta.findByTitle(title)
    assert ferramenta != null
}
When(~'^I create the ferramenta "([^"]*)" with file name "([^"]*)"$') { String title, String filename ->
	TestDataAndOperations.createFerramenta(title, filename)
}
Then(~'^the ferramenta "([^"]*)" is not stored twice$') { String title ->
	ferramentas = Ferramenta.findAllByTitle(title)
    assert ferramentas.size() == 1
    // Should actually check whether elements in articles are not equal except for their filename,
    // which is changed by the system during the file upload.
}

// edit ferramenta
Given(~'^the system has a ferramenta entitled "([^"]*)" with file name "([^"]*)"$') { String title, String filename ->
	TestDataAndOperations.createFerramenta(title, filename)
    ferramenta = Ferramenta.findByTitle(title)
    assert ferramenta != null
}
When(~'^I edit the ferramenta title from "([^"]*)" to "([^"]*)"$') { String oldtitle, String newtitle ->
	def updatedFerramenta = TestDataAndOperations.editFerramenta(oldtitle, newtitle)
    assert updatedFerramenta != null
}
Then(~'^the ferramenta "([^"]*)" is properly updated by the system$') { String title ->
	ferramenta = Ferramenta.findByTitle(title)
    assert ferramenta == null
	// ideally, it should check whether the tool is stored with the new title
}

// new ferramenta web

When(~'^I select the new ferramenta option at the ferramenta page$') {->
	at FerramentaPage
	page.selectNewFerramenta()
}
Then(~'^I can fill the ferramenta details$') { ->
	at FerramentaCreatePage
    page.fillFerramentaDetails()
}