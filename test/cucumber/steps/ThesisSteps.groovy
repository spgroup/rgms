import org.codehaus.groovy.grails.web.context.ServletContextHolder
import pages.LoginPage
import pages.PublicationsPage
import pages.ThesisPage
import pages.thesis.ThesisCreatePage
import pages.thesis.ThesisEditPage
import pages.thesis.ThesisShowPage
import rgms.authentication.User
import rgms.publication.Tese
import steps.TestDataAndOperationsPublication
import steps.ThesisTestDataAndOperations

import static cucumber.api.groovy.EN.*

Given(~'^The system has no thesis entitled "([^"]*)"$') { String title ->
    article = Tese.findByTitle(title)
    assert article == null
}

Given(~'^The thesis "([^"]*)" is stored in the system with file name "([^"]*)"$') {
    String title, filename ->
        ThesisTestDataAndOperations.createTese(title, filename, "UFPE")
        article = Tese.findByTitle(title)
        assert article != null
}

When(~'^I create the thesis "([^"]*)" with file name "([^"]*)" and school "([^"]*)"$') {
    String title, filename, school ->
        ThesisTestDataAndOperations.createTese(title, filename, school)
}

Then(~'^The thesis "([^"]*)" is not stored twice$') { String title ->
    theses = Tese.findAllByTitle(title)
    assert theses.size() == 1
}

Then(~'^The thesis "([^"]*)" is properly stored by the system$') { String title ->
    thesis = Tese.findByTitle(title)
    assert thesis != null
}

Given(~'^I am at the create thesis page$') { ->
    to LoginPage
    at LoginPage
    page.fillLoginData("admin", "adminadmin")
    to ThesisCreatePage
    at ThesisCreatePage
}

When(~'^I fill the thesis details with "([^"]*)", "([^"]*)", "([^"]*)", "([^"]*)", "([^"]*)" and "([^"]*)"$') {
    title, pub_day, pub_month, pub_year, school, address ->
        def absolutePath = ServletContextHolder.servletContext.getRealPath("/test/functional/steps/NewthesisGUI.txt")
        absolutePath = absolutePath.replace("\\", "/").replaceAll("/web-app", "")
        page.fillThesisDetails(title, pub_day, pub_month, pub_year, school, address, absolutePath)
}

When(~'^I fill some thesis details with "([^"]*)", "([^"]*)", "([^"]*)", "([^"]*)", "([^"]*)" and "([^"]*)"$') {
    title, pub_day, pub_month, pub_year, school, address ->
        page.fillSomeThesisDetails(title, pub_day, pub_month, pub_year, school, address)
}

Then(~'^I am on the thesis show page$') { ->
    at ThesisShowPage
}

Then(~'^I am still on the create thesis page with the error message$') { ->
    at ThesisCreatePage
}
/**
 * @author carloscemb
 */
When(~'^I select the new thesis option at the thesis page$') { ->
    at ThesisPage
    page.selectNewThesis()
    at ThesisCreatePage
}

/**
 * @author carloscemb
 */
Then(~'^I see my user listed as an author member of thesis by default$') { ->
    at ThesisCreatePage
    assert TestDataAndOperationsPublication.containsUser(page.selectedMembers())
}

Then(~'^I see my school name as school of thesis by default$') { ->
    at ThesisCreatePage
    userData = User.findByUsername('admin')?.author?.university
    assert page.currentSchool() == userData
}
/**
 * @author bss3 e rlfs
 */

//#7
Given(~'^I am at the thesis page and the thesis "([^"]*)" is stored in the system$') { title ->

    Login()
    at PublicationsPage
    page.select("Tese")
    at ThesisPage

    if (Tese.findByTitle(title) == null) { //tese ja existe não e preciso criar denovo
        System.out.println("Tese " + title + " não existia, criando-a")
        page.selectNewThesis()
        at ThesisCreatePage
        def absolutePath = ServletContextHolder.servletContext.getRealPath("/test/functional/steps/NewthesisGUI2.txt")
        absolutePath = absolutePath.replace("\\", "/").replaceAll("/web-app", "")
        page.fillThesisDetails(title, "10", "8", "1998", "UFPE", "Recife", absolutePath)
        page.selectCreateThesis()
        tese = Tese.findByTitle(title)
        assert tese != null

        at ThesisShowPage
    }

    to ThesisPage
    at ThesisPage
}

When(~'^I select to view thesis "([^"]*)" in resulting list$') { title ->
    at ThesisPage
    page.selectViewThesis(title)

    at ThesisShowPage

}

When(~'^I select the remover option at the thesis show page$') { ->
    at ThesisShowPage
    page.delete()
    //passo para remover o alert
}

Then(~'^the thesis "([^"]*)" is removed from the system$') { title ->
    at ThesisPage
    thesisDoNotExists(title)
}

// #6
Given(~'^the system has thesis entitled "([^"]*)"$') { title ->
    ThesisTestDataAndOperations.createTese(title, title+".txt", 'UFPE')
    thesis = Tese.findByTitle(title)
    assert thesis != null
}

When(~'^I delete the thesis "([^"]*)"$') { title ->
    ThesisTestDataAndOperations.deleteTeseByTitle(title)
}

Then(~'^the thesis "([^"]*)" is properly removed by the system$') { title ->
    tese = Tese.findByTitle(title)
    assert tese == null
}

//Scenario: order thesis list by date
Given(~'^at least two thesis is stored in the system$') { ->
    Login()

    for (int i = 1; i <= 2; i++) {
        to ThesisCreatePage
        at ThesisCreatePage

        def absolutePath = ServletContextHolder.servletContext.getRealPath("/test/functional/steps/TCS-0" +i +".pdf")
        absolutePath = absolutePath.replace("\\", "/").replaceAll("/web-app", "")
        def day = (3 - i).toString()
        page.fillThesisDetails("thesis"+i, day, "1", "1914", "UFPE", "Cidade Universitaria", absolutePath)
    }
}

And(~'^I am at the thesis list page$') { ->
    //Login()
    to ThesisPage
    at ThesisPage
}

And(~'^I click in order thesis by date$') { ->
    at ThesisPage
    page.orderByDate()
}

Then(~'^the returned thesis list has the same items but it is sorted by date$') { ->
    at ThesisPage

    page.checkTeseAtList("thesis1", 1)
    page.checkTeseAtList("thesis2", 0)
}

//Scenario: search an existing thesis
Given(~'^the system has one thesis entitled "([^"]*)" with author name "([^"]*)", year of publication "([^"]*)" and university "([^"]*)"$') { title, author, year, school ->
    ThesisTestDataAndOperations.createTese(title, author, year, school)
    thesis = Tese.findByTitle(title)

    assert thesis == null
}

And(~'^I am at the thesis search page$') { ->
    to ThesisSearchPage
    at ThesisSearchPage
}

When(~'^I search for "([^"]*)" by "([^"]*)"$') { title, author ->
    at ThesisSearchPage
    page.fillThesisSearchDetails(title, author)
    page.search()
}

And(~'^I select to view the entry that has university "([^"]*)" and publication year "([^"]*)"$') { university, year ->
    at ThesisPage
    page.selectViewThesis(university, year)
}

Then(~'^the thesis "([^"]*)" by "([^"]*)" appears in the thesis view page$') { title, year ->
    at ThesisShowPage
    page.checkThesisDetails(title, year)
}

//Scenario: create thesis without a file
When(~'^I fill the thesis fields with "([^"]*)", "([^"]*)", "([^"]*)", "([^"]*)", "([^"]*)", "([^"]*)"$') { title, year, month, day, school, address ->
    at ThesisCreatePage
    page.fillThesisDetailsWithoutFile(title, day, month, year, school, address)
}

And(~'^I try to create a new thesis$') { ->
    at ThesisCreatePage
    page.selectCreateThesis()
}

//Scenario: search an existing thesis filled by default
//#if($contextualInformation)
Given(~'^the system has at least one thesis entitled "([^"]*)"$') { title ->
    ThesisTestDataAndOperations.createTese(title, "teste.txt", "UFPE")
    tese = Tese.findByTitle(title)
    assert tese != null
}

And(~'^I have already done a search about "([^"]*)" previously$') { title ->
    at ThesisSearchPage
    page.fillTitleInSearch(title)
    page.search();
}

When(~'^I press "([^"]*)"$') { input ->
    at ThesisSearchPage
    page.fillTitleInSearch(input)
    page.showDropdownList()
}

And(~'^I choose "([^"]*)" in the list$') { title ->
    at ThesisSearchPage
    page.selectItemInDropdownList(title)
}

And(~'^I click in search button$') { ->
    at ThesisSearchPage
    page.search()
}

Then(~'^all theses entitled "([^"]*)" are shown$') { title ->
    at ThesisPage
    theses = Tese.findAllByTitle(title)
    page.checkThesisList(theses)
}
//#end

//Scenario: edit thesis title
When(~'^I change the title from "([^"]*)" to "([^"]*)"$') { title, newTitle ->
    thesesListBeforeModification = Tese.findAll()
    thesesListBeforeModification.remove(Tese.findByTitle(title))

    def updatedThesis = ThesisTestDataAndOperations.editThesis(title, newTitle)

    thesesListAfterModification = Tese.findAll()
    thesesListAfterModification.remove(Tese.findByTitle(newTitle))

    assert updatedThesis != null
}

Then(~'^the thesis entitled "([^"]*)" is properly renamed by the system$') { newTitle ->
    def thesis = Tese.findByTitle(newTitle)
    assert thesis != null
}

And(~'^the other theses are not changed by the system$') { ->
    assert thesesListBeforeModification.equals(thesesListAfterModification)
}

//Scenario: edit thesis with invalid data
When(~'^I try to change the title from "([^"]*)" to "([^"]*)"$') { title, newTitle ->
    thesesListBeforeModification = Tese.findAll()
    def updatedThesis = ThesisTestDataAndOperations.editThesis(title, newTitle)
    thesesListAfterModification = Tese.findAll()
    assert updatedThesis == null
}

Then(~'^the existing thesis are not changed by the system$') { ->
    assert thesesListBeforeModification.equals(thesesListAfterModification)
}

//Scenario: search a thesis
When(~'^I search for thesis entitled "([^"]*)"$') { title ->
    thesesListBeforeModification = Tese.findAll()
    Tese.findByTitle(title)
    thesesListAfterModification = Tese.findAll()
}

//Scenario: upload thesis with a file
When(~'^I upload the file "([^"]*)" with thesis entitled "([^"]*)"$') { filename, title ->
    thesesListBeforeModification = Tese.findAll()

    String path = new File(".").getCanonicalPath() + File.separator + "test" +  File.separator + "functional" + File.separator + "steps" + File.separator + filename
    ThesisTestDataAndOperations.uploadThesis(path)

    thesesListAfterModification = Tese.findAll()
    thesesListAfterModification.remove(Tese.findByTitle(title))
}

And(~'^the system properly stores the thesis entitled "([^"]*)"$') { title ->
    thesis = Tese.findByTitle(title)
    assert thesis != null
}

//VARIAVÉIS AUXILIARES

//FUNÇÔES AUXILIARES
def thesisDoNotExists(title) {
    tese = Tese.findByTitle(title)
    assert tese == null
}


def Login() {
    to LoginPage
    at LoginPage
    page.fillLoginData("admin", "adminadmin")
}