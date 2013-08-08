import pages.FerramentaCreatePage
import pages.FerramentaEditPage
import pages.FerramentaPage
import pages.FerramentaShowPage
import rgms.member.Member
import rgms.publication.Ferramenta
import steps.TestDataAndOperations

import static cucumber.api.groovy.EN.*

Given(~'The system has a ferramenta entitled "([^"]*)"$') { String title ->
    article = Ferramenta.findByTitle(title)
    assert article != null
}
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

When(~'^I select "([^"]*)" at the ferramenta page$') { String title ->
    at FerramentaPage
    page.selectFerramenta(title)
}


Then(~'^I click on edit at the Ferramenta page$') {->
    at FerramentaShowPage
    page.editDissertation()
}

Then(~'^The ferramenta entitle "([^"]*)" is properly deleted of the system$') { String title ->
    article = Ferramenta.findByTitle(title)
    assert article == null
}

Then(~'^I can fill the ferramenta details$') {->
    at FerramentaCreatePage
    page.fillFerramentaDetails()
}

Then(~'^the ferramenta "([^"]*)" is properly stored by the system$') { String title ->
    ferramenta = Ferramenta.findByTitle(title)
    assert ferramenta != null
}
Given(~'^the system has some ferramenta stored$') {->
    inicialSize = Ferramenta.findAll().size()
}
When(~'^I upload a new ferramenta "([^"]*)"$') { filename ->
    inicialSize = Ferramenta.findAll().size()
    def path = new File(".").getCanonicalPath() + File.separator + "test" + File.separator + "files" + File.separator
    TestDataAndOperations.uploadFerramenta(path + filename)
    finalSize = Ferramenta.findAll().size()
    assert inicialSize < finalSize
    //para funcionar é necessario que tenha um FilePath válido
    // não consegui fazer de uma maneira que todos os passos sejam independentes
}
Then(~'the system has more ferramenta now$') {->
    finalSize = Ferramenta.findAll().size()
}
// new ferramenta web

When(~'^I select the new ferramenta option at the ferramenta page$') {->
    at FerramentaPage
    page.selectNewFerramenta()
}
When(~'^I change the website to "([^"]*)"$') { String website ->
    at FerramentaEditPage
    page.editWebsite(website)
}
Then(~'^The edited ferramenta entitled "([^"]*)" is properly stored in the system with the new website "([^"]*)"$') { String title, website ->
    ferramenta = Ferramenta.findAllByTitle(title)
    assert ferramenta.website == website
}

And(~'^I select the create option at the ferramenta page$') {->
    // Express the Regexp above with the code you wish you had

}
Then(~'^The ferramenta is not stored$') {->
    // Express the Regexp above with the code you wish you had

}

Then(~'^I see my user listed as an author member of ferramenta by default$') {->
    at FerramentaCreatePage
    userData = Member.findByUsername('admin').id.toString()
    assert page.selectedMembers().contains(userData)
}

And(~'^I select the upload button at the ferramenta page$') {->
    // Express the Regexp above with the code you wish you had

}
Then(~'^I am still on ferramenta page$') {->
    // Express the Regexp above with the code you wish you had

}