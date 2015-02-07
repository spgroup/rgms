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
    thesisDoNotExists(title)
}

Given(~'^The thesis "([^"]*)" is stored in the system with file name "([^"]*)"$') {
    String title, filename ->
        ThesisTestDataAndOperations.createTese(title, filename, "UFPE")
        thesisDoExists(title)
}

When(~'^I create the thesis "([^"]*)" with file name "([^"]*)" and school "([^"]*)"$') {
    String title, filename, school ->
        ThesisTestDataAndOperations.createTese(title, filename, school)
}

/**
 * @author rff2
 * edit existing thesis test web
 * BEGIN
 */
And(~'^I modify the field School to "([^"]*)"$') {
    String school_name ->
        at ThesisEditPage
        page.fillThesisSchool(school_name)
        page.saveModifications()
}

When(~'^I select to edit thesis "([^"]*)" in resulting list$') { title ->
    at ThesisPage
    page.selectViewThesis(title)
    page.select()
    at ThesisEditPage

}

Then(~'^The thesis "([^"]*)" now has "([^"]*)" in the school field$') {
    String title, String school_name ->
        tese = Tese.findByTitle(title)
        assert tese.school == school_name
}
/**
 * @author rff2
 * edit existing thesis test web
 * END
 */

/**
 * @author rff2
 * filter Thesis List
 * BEGIN
 */
When(~'^The user adds a thesis entitled "([^"]*)"$') {
    title ->
        at ThesisPage
        page.selectNewThesis()
        at ThesisCreatePage

        def absolutePath = ServletContextHolder.servletContext.getRealPath("/test/functional/steps/NewthesisGUI.txt")
        absolutePath = absolutePath.replace("\\", "/").replaceAll("/web-app", "")
        page.fillThesisDetails(title, 01, 01, 1995, "UFPE", "Recife", absolutePath)

}

Then(~'^The thesis "([^"]*)" is properly stored after "([^"]*)"$') {
    String title1, String title2 ->
        at ThesisPage
        thesis1 = Tese.findByTitle(title1)
        thesis2 = Tese.findByTitle(title2)
        assert thesis1 == page.getRow(0)
        assert thesis2 == page.getRow(1)
}

/**
 * @author rff2
 * filter Thesis List
 * END
 */

Then(~'^The thesis "([^"]*)" is not stored twice$') { String title ->
    theses = Tese.findAllByTitle(title)
    assert theses.size() == 1
}

Then(~'^The thesis "([^"]*)" is properly stored by the system$') { String title ->
    thesisDoExists(title)
}

Given(~'^I am at the create thesis page$') { ->
    Login()
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
        thesisDoExists(title)

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
    ThesisTestDataAndOperations.createTese(title, 'teste.txt', 'UFPE')
    thesisDoExists(title)
}

When(~'^I delete the thesis "([^"]*)"$') { title ->
    ThesisTestDataAndOperations.deleteTeseByTitle(title)
}

Then(~'^the thesis "([^"]*)" is properly removed by the system$') { title ->
    thesisDoNotExists(title)
}

//Scenario: order thesis list by date
Given(~'^at least one thesis is stored in the system$') { ->

}

And(~'^I am at the thesis list page$') { ->

}

And(~'^I click in order thesis by date$') { ->

}

Then(~'^the returned thesis list has the same items but it is sorted by date$') { ->

}

//Scenario: search an existing thesis
Given(~'^the system has one thesis entitled "([^"]*)" with author name "([^"]*)", year of publication "([^"]*)" and university "([^"]*)"$') { title, author, year, university ->

}

And(~'^I am at the thesis search page$') { ->

}

When(~'^I search for "([^"]*)" by "([^"]*)"$') { title, author ->

}

And(~'^I select to view the entry that has university "([^"]*)" and publication year "([^"]*)"$') { university, year ->

}

Then(~'^the thesis "([^"]*)" by "([^"]*)" appears in the thesis view page$') { title, year ->

}

//Scenario: create thesis web without a file


When(~'^I fill the thesis fields with "([^"]*)", "([^"]*)", "([^"]*)","([^"]*)", "([^"]*)","([^"]*)"$') { title, date, university, address, author, advisor ->

}

And(~'^I click in create button$') { ->

}

Then(~'^the system shows a warning message "([^"]*)"$') { warningmessage ->

}

//#if($contextualInformation)
//    Scenario: search an existing thesis filled by default

Given(~'^the system has at least one thesis entitled "([^"]*)"$') { title ->

}


And(~'^I have already done a search about "([^"]*)" previously$') { title ->

}

When(~'^I press "([^"]*)"$') { input ->

}

And(~'^I choose "([^"]*)" in dropdown search list$') { title ->

}

And(~'^I click in search button$') { ->

}

Then(~'^all theses entitled "([^"]*)" are shown$') { title ->

}

//#end

//Scenario: edit thesis title
When(~'^I change the title from "([^"]*)" to "([^"]*)"$') { String title, newtitle ->

}

Then(~'^the thesis entitled "([^"]*)" is properly renamed by the system$') { String title, newtitle ->

}

And(~'^the other theses are not changed by the system$') { ->

}

//Scenario: edit thesis with invalid data
Then(~'^the existing thesis are not changed by the system$') { ->

}

//Scenario: search a thesis
Given(~'^the system has one thesis entitled "([^"]*)"$') { title ->
}

When(~'^I search for thesis entitled "([^"]*)"$') { title ->
}

//Scenario: upload thesis with a file
When(~'^I upload the file "([^"]*)"$') { file ->

}

And(~'^the system stores properly the thesis entitled "([^"]*)"$') { title ->

}

//FUNÇÔES AUXILIARES
def thesisDoNotExists(title) {
    tese = Tese.findByTitle(title)
    assert tese == null
}

def thesisDoExists(title) {
    tese = Tese.findByTitle(title)
    assert tese != null
}

def Login() {
    to LoginPage
    at LoginPage
    page.fillLoginData("admin", "adminadmin")
}