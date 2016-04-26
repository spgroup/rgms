import pages.DissertationCreate
import pages.DissertationEditPage
import pages.DissertationPage
import pages.DissertationShowPage
import rgms.authentication.User
import rgms.publication.Dissertacao
import steps.TestDataDissertacao
import steps.TestDataAndOperationsPublication
import pages.LoginPage
import pages.PublicationsPage
import static cucumber.api.groovy.EN.*


When(~'^I select the new dissertation option at the dissertation page$') {->
    at DissertationPage
    page.selectNewArticle()
}

When(~'^I cant add the dissertation without a file$') {->
    at DissertationCreate
    page.fillDissertationDetailsWithoutFile()
}

When(~'^I can add the dissertation with a file "([^"]*)"$'){ String filename->
    at DissertationCreate
    def path = new File(".").getCanonicalPath() + File.separator + "test" + File.separator + "files" + File.separator + filename
    page.fillDissertationDetailsWithFile(path)
}
Then((~'^the system has a dissertation entitled "([^"]*)"$')){ String title->
    article = Dissertacao.findByTitle(title)
    assert article != null
}



When(~'^I select "([^"]*)" at the dissertation page$') { String title ->
    at DissertationPage
    page.selectDissertation(title)
}

When(~'^I click on edit$') {->
    at DissertationShowPage
    page.editDissertation()
}

When(~'^I delete "([^"]*)"$') { String title ->
    TestDataDissertacao.removeDissertacao(title)
}

Then(~'^the school name is "([^"]*)"$') { String name ->
    page.nameIs(name)
}

When(~'^I edit the school to "([^"]*)"$') { String school ->
    at DissertationEditPage
    page.editSchool(school)
}

Given(~'^the system has no dissertation entitled "([^"]*)"$') { String title ->
    article = Dissertacao.findByTitle(title)
    assert article == null
}

Given(~'^the dissertation "([^"]*)" is stored in the system with file name "([^"]*)"$') { String title, filename ->
    TestDataDissertacao.createDissertacao(title, filename, "UFPE")
    article = Dissertacao.findByTitle(title)
    assert article != null
}


When(~'^I create the dissertation "([^"]*)" with file name "([^"]*)" and school "([^"]*)"$') { String title, filename, school ->
    TestDataDissertacao.createDissertacao(title, filename, school)
}


Then(~'^the dissertation "([^"]*)" is properly stored by the system$') { String title ->
    dissertation = Dissertacao.findByTitle(title)
    assert dissertation != null
}

Then(~'^the dissertation "([^"]*)" is not stored twice$') { String title ->
    dissertations = Dissertacao.findAllByTitle(title)
    assert dissertations.size() == 1
    //A propriedade title de publication deveria estar unique:true, mas como n�o est�, este teste vai falhar
}

When(~'^I create the dissertation "([^"]*)" with file name "([^"]*)" without school$') { String title, filename ->
    TestDataDissertacao.createDissertacaoWithotSchool(title, filename);
}

When(~'^I create the dissertation "([^"]*)" with file name "([^"]*)" without address$') { String title, filename ->
    TestDataDissertacao.createDissertacaoWithoutAddress(title, filename);
}

When(~'^I edit the dissertation title from "([^"]*)" to "([^"]*)"$') { String oldtitle, newtitle ->
    def updatedDissertation = TestDataDissertacao.editDissertacao(oldtitle, newtitle)
    assert updatedDissertation != null
}

Then(~'^the dissertation "([^"]*)" is properly updated by the system$') { String title ->
    def article = Dissertacao.findByTitle(title)
    assert article == null
}


When(~'^I upload a new dissertation "([^"]*)"$') { filename ->
    String path = new File(".").getCanonicalPath() + File.separator + "test" + File.separator + "functional" + File.separator + "steps" + File.separator + filename
    inicialSize = Dissertacao.findAll().size()
    TestDataDissertacao.uploadDissertacao(path)
    finalSize = Dissertacao.findAll().size()
    assert inicialSize < finalSize
    //para funcionar é necessario que tenha um FilePath válido
    // não consegui fazer de uma maneira que todos os passos sejam independentes
}
Then(~'the system has more dissertations now$') {->
    finalSize = Dissertacao.findAll().size()

}

Given(~'^the system has some dissertation stored$'){->
    size = Dissertacao.findAll().size()
    assert size > 0

}


When(~'^I upload a new dissertation "([^"]*)" with title "([^"]*)"$') {  filename, String title ->
    String path = new File(".").getCanonicalPath() + File.separator + "test" +  File.separator + "functional" + File.separator + "steps" + File.separator + filename
    inicialSize = Dissertacao.findAll().size()
    TestDataDissertacao.uploadDissertacao(path)
    finalSize = Dissertacao.findAll().size()
    assert inicialSize<finalSize
    //para funcionar é necessario que tenha um FilePath válido
    // não consegui fazer de uma maneira que todos os passos sejam independentes
}

Then(~'^I see my user listed as an author member of dissertation by default$') {->
    at DissertationCreate
    assert TestDataAndOperationsPublication.containsUser(page.selectedMembers())
}

Then(~'^I see my school name as school of dissertation by default$') {->
    at DissertationCreate
    userData = User.findByUsername('admin')?.author?.university
    assert page.currentSchool() == userData
}

Given(~'^the system has no dissertation stored$')   {->
    intialSize = Dissertacao.findAll().size()
    assert intialSize == 0
}


Given(~'^I am at the dissertation page$') { ->
    to LoginPage
    at LoginPage
    page.fillLoginData("admin", "adminadmin")
    at PublicationsPage
    to DissertationPage
}
When(~'^I select the upload button with no file selected$') {->
    page.uploadWithoutFile()
}
Then(~'^I stay on dissertation page with an error message$') { ->
    at DissertationPage
	assert page.readFlashMessage() != null
}


When(~'^I press to remove "([^"]*)" at the dissertation show page$'){ String title->
    at DissertationShowPage
	page.select('input', 'delete')
}

When(~'^I create the dissertation "([^"]*)" with file name "([^"]*)"$') { String title, String filename ->
    TestDataDissertacao.createDissertacao(title, filename, "UFPE")
    dissertation = Dissertacao.findByTitle(title)
    assert dissertation != null
}

Given(~'^the dissertation named "([^"]*)" is stored in the system$') { String title ->
    TestDataDissertacao.createDissertacao(title, "testDissertation.pdf", "UFPE")
    dissertation = Dissertacao.findByTitle(title)
    assert dissertation != null
}

When(~'^I select "([^"]*)" at the dissertation list$') { String title ->
    page.selectDissertation(title)
}

