import pages.PublicationsPage
import pages.ferramenta.*
import rgms.publication.Ferramenta
import steps.FerramentaTestDataAndOperations
import steps.TestDataAndOperationsPublication

import java.awt.Menu

import static cucumber.api.groovy.EN.*

// new ferramenta without website
Given(~'^the system has no ferramenta entitled "([^"]*)"$') { String title ->
    ferramenta = Ferramenta.findByTitle(title)
    assert ferramenta == null
}
When(~'^I create the ferramenta "([^"]*)" with file name "([^"]*)" without its website$') { String title, String filename ->
    FerramentaTestDataAndOperations.createFerramenta(title, filename)
}
Then(~'^the ferramenta "([^"]*)" is not stored$') { String title ->
    def tool = Ferramenta.findByTitle(title)
    assert tool == null
}
Then(~'^the ferramenta "([^"]*)" is stored$') { String title ->
    def tool = Ferramenta.findByTitle(title)
    assert tool != null
}

When(~'^I create the ferramenta "([^"]*)" with file name "([^"]*)" with website "([^"]*)"$') { String title, String filename, String website ->
    FerramentaTestDataAndOperations.createFerramentaWeb(title, filename, website)
}

// duplicate ferramenta
Given(~'^the ferramenta "([^"]*)" is stored in the system with file name "([^"]*)"$') { String title, String filename ->
    FerramentaTestDataAndOperations.createFerramenta(title, filename)
    ferramenta = Ferramenta.findByTitle(title)
    assert ferramenta != null
}
When(~'^I create the ferramenta "([^"]*)" with file name "([^"]*)"$') { String title, String filename ->
    FerramentaTestDataAndOperations.createFerramenta(title, filename)
}
Then(~'^the ferramenta "([^"]*)" is not stored twice$') { String title ->
    ferramentas = Ferramenta.findAllByTitle(title)
    assert ferramentas.size() == 1
    // Should actually check whether elements in articles are not equal except for their filename,
    // which is changed by the system during the file upload.
}

// edit existing ferramenta
Given(~'^the system has a ferramenta entitled "([^"]*)" with file name "([^"]*)"$') { String title, String filename ->
    FerramentaTestDataAndOperations.createFerramenta(title, filename)
    ferramenta = Ferramenta.findByTitle(title)
    assert ferramenta != null
}
When(~'^I edit the ferramenta title from "([^"]*)" to "([^"]*)"$') { String oldtitle, String newtitle ->
    def updatedFerramenta = FerramentaTestDataAndOperations.editFerramenta(oldtitle, newtitle)
    assert updatedFerramenta != null
}
Then(~'^the ferramenta "([^"]*)" is properly updated by the system$') { String title ->
    newFerramenta = Ferramenta.findByTitle(title)
    assert newFerramenta != null
}

// list ferramentas
Then(~'^The system list "([^"]*)" and "([^"]*)" ferramentas$') { String title, otherTitle ->
    ferramentas = Ferramenta.findAllByTitle(title)
    assert ferramentas.size() == 1
    ferramentas = Ferramenta.findAllByTitle(otherTitle)
    assert ferramentas.size() == 1
}

// upload dissertation with a file
Given(~'^the system has some ferramenta stored$') {->
    inicialSize = Ferramenta.findAll().size()
}
When(~'^I upload a new ferramenta "([^"]*)"$') { filename ->
    inicialSize = Ferramenta.findAll().size()
    def path = new File(".").getCanonicalPath() + File.separator + "test" + File.separator + "files" + File.separator
    FerramentaTestDataAndOperations.uploadFerramenta(path + filename)
    finalSize = Ferramenta.findAll().size()
    assert inicialSize < finalSize
    //para funcionar é necessario que tenha um FilePath válido
    // não consegui fazer de uma maneira que todos os passos sejam independentes
}
Then(~'the system has more ferramenta now$') {->
    finalSize = Ferramenta.findAll().size()
}

// remove existing ferramenta
When(~'^I remove the ferramenta entitled "([^"]*)"$') { String arg1 ->
    FerramentaTestDataAndOperations.removeFerramenta(arg1)
}

// GUI testes

When(~'^I select "([^"]*)" at the ferramenta page$') { String title ->
    at FerramentaPage
    page.selectFerramenta(title)
}

// new ferramenta web
When(~'^I select the new ferramenta option at the ferramenta page$') {->
    at FerramentaPage
    page.selectNewFerramenta()
}
Then(~'^I can create a ferramenta filling the details$') {->
    at FerramentaCreatePage
    page.createNewFerramenta("CCFinder")
}

// new ferramenta without any information
And(~'^I click on Criar button$') {->
    at FerramentaCreatePage
    page.clickCreateFerramenta()
}

Given(~'^I am at the ferramenta page$') {->
    to FerramentaPage
    at FerramentaPage
}

Then(~'^I am still on create new ferramenta page$') {->
    at FerramentaCreatePage
}

And(~'^the ferramenta is not displayed in the ferramentas list page$') {->
    to FerramentaPage
    at FerramentaPage
}

// upload ferramenta without a file
// #if($UploadFerramentaWithoutAFile)
Given(~'^I am at publications menu$'){->
    to PublicationsPage
    at PublicationsPage
}

When(~'^I select the upload button at the ferramenta page without attaching a file$'){->
    to FerramentaPage
    at FerramentaPage
    page.click("Upload")
}

Then(~'^the ferramenta wont be inserted into the system$') {->
    to FerramentaPage
    at FerramentaPage
}
// #end

And(~'^I select the upload button at the ferramenta page$') {->

}

Then(~'^I am still on ferramenta page$') {->
    at FerramentaPage
}

// new ferramenta filled with user data by default
Then(~'^I see my user listed as an author member of ferramenta by default$') {->
    at FerramentaCreatePage
    assert TestDataAndOperationsPublication.containsUser(page.selectedMembers())
}

// edit ferramenta
When(~'^I create a new ferramenta at ferramenta create page$') {->
    at FerramentaCreatePage
    page.createNewFerramenta("CCFinder")
}
When(~'^I select the edit option at ferramenta show page$') {->
    at FerramentaShowPage
    page.editFerramenta()
}
When(~'^I can modify the name to "([^"]*)" at the edit ferramenta page$') { String title ->
    at FerramentaEditPage
    page.editTitle(title)
}
Then(~'^I can see the new title "([^"]*)" at ferramenta show page$') { String newTitle->
    at FerramentaShowPage
    page.checkFerramentaTitle(newTitle)
}

// new ferramenta with Titulo exceding caracteres limits
And(~'^I fill Titulo with more than (\\d+) caracteres$') { int arg1 ->
    at FerramentaCreatePage
    page.fillTitleWithMaxCaracteres()
}
And(~'^fill the others fields with valid values without Titulo$') {->
    at FerramentaCreatePage
    page.fillFerramentaDetailsWithoutTitle("Tool without title")
}
