import steps.TestDataAndOperations
import pages.LoginPage
import pages.PublicationsPage
import pages.FerramentaPage
import pages.FerramentaCreate
import pages.FerramentaShowPage
import pages.FerramentaEditPage
import rgms.publication.Dissertacao
import rgms.publication.Ferramenta

import static cucumber.api.groovy.EN.*

Given(~'The system has a ferramenta entitled "([^"]*)"$') { String title ->
    article = Ferramenta.findByTitle(title)
    assert article != null
}
When(~'^I create the ferramenta "([^"]*)" without website$') {String title ->
    at FerramentaCreate
    page.createFerramentaWithoutWebsite()
}
Then(~'^The ferramenta "([^"]*)" is not stored$'){String title->
    article = Ferramenta.findByTitle(title)
    assert article == null
}
When(~'^I create the ferramenta "([^"]*)"$') {String title ->
    at FerramentaCreate
    page.createNewFerramenta(title)
}
Then(~'^The ferramenta "([^"]*)" is not stored twice$') { String title ->
    ferramenta = Ferramenta.findAllByTitle(title)
    assert ferramenta.size() == 1
}
When(~'^I change the website to "([^"]*)"$') {String website ->
    at FerramentaEditPage
    page.editWebsite(website)
}
Then(~'^The edited ferramenta entitled "([^"]*)" is properly stored in the system with the new website "([^"]*)"$') { String title, website ->
    ferramenta = Ferramenta.findAllByTitle(title)
    assert ferramenta.website == website
}

When(~'^I select the new ferramenta option at the ferramenta page$') {->
    at FerramentaPage
    page.selectNewArticle()
}
When(~'^I select the create option at the ferramenta page$') {->
    at FerramentaCreate
    page.createNewFerramentaWithoutInformation()
}
Then(~'^The ferramenta is not stored$'){ ->
    article = Ferramenta.findByTitle(null)
    assert article == null
}



When(~'^I select "([^"]*)" at the ferramenta page$') {String title ->
	at FerramentaPage
	page.selectFerramenta(title)
}


Then(~'^I click on edit at the Ferramenta page$'){->
	at FerramentaShowPage
	page.editDissertation()
}

Then(~'^The ferramenta entitle "([^"]*)" is properly deleted of the system$'){String title ->
    article = Ferramenta.findByTitle(title)
    assert article == null
}


Given(~'^the system has no ferramenta entitled "([^"]*)"$') { String title ->
	article = Ferramenta.findByTitle(title)
	assert article == null
}




Then(~'^the ferramenta "([^"]*)" is properly stored by the system$') { String title ->
	ferramenta = Ferramenta.findByTitle(title)
	assert ferramenta != null
}

