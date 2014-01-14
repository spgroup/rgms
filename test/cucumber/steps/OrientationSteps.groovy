import geb.Browser
import geb.js.AlertAndConfirmSupport
import org.openqa.selenium.Alert
import org.openqa.selenium.browserlaunchers.BrowserLauncher
import pages.OrientationPages.*
import pages.*
import rgms.authentication.User
import rgms.member.Member
import rgms.member.Orientation
import rgms.publication.Periodico
import rgms.tool.FacebookTool
import rgms.tool.TwitterTool
import steps.MemberTestDataAndOperations
import steps.OrientationTestDataAndOperations

import org.apache.shiro.util.ThreadContext
import org.apache.shiro.subject.Subject
import org.apache.shiro.SecurityUtils

import static cucumber.api.groovy.EN.*

// create
Given(~'^the system has no orientations entitled "([^"]*)"$') { String tituloTese ->
    // Express the Regexp above with the code you wish you had
    orientation = Orientation.findByTituloTese(tituloTese)
    assert orientation == null
}

When(~'^I create a orientation for the thesis "([^"]*)"$') { String tituloTese ->
    // Express the Regexp above with the code you wish you had
    OrientationTestDataAndOperations.createOrientation(tituloTese)
}

Then(~'^the orientation "([^"]*)" is properly stored by the system$') { String tituloTese ->
    // Express the Regexp above with the code you wish you had
    orientation = Orientation.findByTituloTese(tituloTese)
    assert OrientationTestDataAndOperations.OrientationCompatibleTo(orientation, tituloTese)
}


//delete
Given(~'^the system has thesis entitled "([^"]*)" supervised for someone$') { String tituloTese ->

    orientation = OrientationTestDataAndOperations.findOrientationByTitle(tituloTese)
    if(orientation == null) OrientationTestDataAndOperations.createOrientation(tituloTese)
}

When(~'^I delete the orientation for "([^"]*)"$') { String tituloTese ->
    OrientationTestDataAndOperations.removeOrientation(tituloTese)
}

Then(~'^the orientation for "([^"]*)" is properly removed by the system$') { String tituloTese ->
    orientation = Orientation.findByTituloTese(tituloTese)
    assert orientation == null

}

//create web
Given(~'^I am at the create orientation page$') {->

    Login()

    to OrientationCreatePage
    at OrientationCreatePage

}

When(~'^I fill the orientation title with "([^"]*)"$') { title ->

    page.fillOrientationDetails(title)
    page.selectCreateOrientation()
}

Then(~'^I am on the orientation show page$') { ->
    at OrientationShowPage
}

//edit web
Given(~'^I am at the orientation page and the orientation "([^"]*)" is stored in the system$') { String title ->

    Login()
    at PublicationsPage
    page.select("Orientation")
    at OrientationsPage
    page.selectNewOrientation()

    at OrientationCreatePage
    page.fillOrientationDetails()
    page.selectCreateOrientation()
    orientation = Orientation.findByTituloTese(title)
    assert orientation != null

    to OrientationsPage
    at OrientationsPage


}

When(~'^I select to view orientation "([^"]*)" in resulting list$') { String oldtitle ->

    at OrientationsPage
    page.selectViewOrientation(oldtitle)

    to OrientationShowPage
    at OrientationShowPage
    page.select()
}

When(~'^I change the orientation tituloTese to "([^"]*)"$') { String newtitle ->

    at OrientationEditPage
    page.edit(newtitle)
}

When(~'^I select the "([^"]*)" option$') { String option ->
    at OrientationEditPage
    page.select(option)
    Browser.withConfirm(true)
    assert Browser.withConfirm (true) { $("input", name: "showConfirm").click() } == "Do you like Geb?"
    //'Tem certeza?'
    //AlertAndConfirmSupport.withAlert {}
}

Given(~'^the system has some orientations stored$') {->
    // save old metaclass
    def registry = GroovySystem.metaClassRegistry
    this.oldMetaClass = registry.getMetaClass(SecurityUtils)
    registry.removeMetaClass(SecurityUtils)

    // Mock login
    def subject = [getPrincipal: {"admin"},
        isAuthenticated: {true}
    ]as Subject
    ThreadContext.put(ThreadContext.SECURITY_MANAGER_KEY,
        [getSubject: {subject} as SecurityManager])
    SecurityUtils.metaClass.static.getSubject = {subject}

    initialSize = Orientation.findAll().size()
}

When(~'^I upload a new orientation "([^"]*)"$') { filename ->
    inicialSize = Orientation.findAll().size()
    def path = new File(".").getCanonicalPath() + File.separator + "test" + File.separator + "files" + File.separator
    OrientationTestDataAndOperations.uploadOrientation(path + filename)
    finalSize = Orientation.findAll().size()
    assert inicialSize < finalSize
}

Then(~'the system has more orientations now$') {->
    // restore metaclass
    GroovySystem.metaClassRegistry.setMetaClass(SecurityUtils, this.oldMetaClass)
    finalSize = Orientation.findAll().size()
}

And(~'^I select the upload button at the orientations page$') {->
    at OrientationsPage
    page.uploadWithoutFile()
}

Then(~'^I\'m still on orientations page$') {->
    at OrientationsPage
}

And(~'^the orientations are not stored by the system$') {->
    at OrientationsPage
    page.checkIfOrientationListIsEmpty()
}

//create web with invalid year
/**
 * @author bss3
 */
When(~'^I fill the orientation title with "([^"]*)" and the year with (-?\\d+)$') { title, year ->
    page.fillOrientationDetailsWithGivenYear(title,year)
    page.selectCreateOrientation()
}

Then(~'^I am still on the create orientation page with the error message$') { ->
    at OrientationCreatePage
}

//new orientation with registered member orientated
/**
 * @author rlfs
 */
And(~'^the "([^"]*)" has been an registered member$') { String username ->
    MemberTestDataAndOperations.createMember(username,"+558199999999")
    user = User.findByUsername(username)
    member = user?.author
    assert member != null
}

When(~'I create a orientation for the thesis "([^"]*)" with registered member "([^"]*)"$') { entitled, username ->
    user = User.findByUsername(username)
    OrientationTestDataAndOperations.createOrientation(entitled,user?.author)
    assert orientation == null
}

//#2
Then(~'^the orientation "([^"]*)" was not stored twice$') { entitled ->
    orientation = Orientation.findAllByTituloTese(entitled)
    assert orientation.size() >= 2
}

//#5
And(~'^I change the orientation anoPublicacao to (-?\\d+)$'){ anoPublicacao ->
    at OrientationEditPage
    page.editYear(anoPublicacao)
}

Then(~'^I am still on the change orientation page with the error message$'){ ->
    at OrientationEditPage
    assert page.readFlashMessage() != null
}

Then(~'^The orientation "([^"]*)" is properly removed by the system$') { title->
    at OrientationsPage
    assert orientationNoExist(title)
}


//FUNCOES AUXILIARES

def orientationNoExist(String title){
    return Orientation.findByTituloTese(title) == null
}

// o problema de duplicação que este método resolve não foi identificado pela ferramenta de detecção de clones
def Login(){
    to LoginPage
    at LoginPage
    page.fillLoginData("admin", "adminadmin")
}
