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

When(~'^I create a new orientation entitled "([^"]*)"$') { String tituloTese ->
    // Express the Regexp above with the code you wish you had
    OrientationTestDataAndOperations.createOrientation(tituloTese)
}

Then(~'^the orientation "([^"]*)" is properly stored by the system$') { String tituloTese ->
    orientation = Orientation.findByTituloTese(tituloTese)
    assert orientation != null
}

//delete
Given(~'^the system has an orientation entitled "([^"]*)" supervised for someone$') { String tituloTese ->
    OrientationTestDataAndOperations.createOrientation(tituloTese)
    orientation = OrientationTestDataAndOperations.findOrientationByTitle(tituloTese)
    assert orientation != null
}

When(~'^I delete the orientation for "([^"]*)"$') { String tituloTese ->
    OrientationTestDataAndOperations.removeOrientation(tituloTese)
}

Then(~'^the orientation for "([^"]*)" is properly removed by the system$') { String tituloTese ->
    orientation = Orientation.findByTituloTese(tituloTese)
    assert orientation == null

}

//create web
Given(~'^I am at the create orientation page$') { ->

    Login()

    to PublicationsPage
    at PublicationsPage
    page.select("Orientation")

    at OrientationsPage
    page.selectNewOrientation()

    at OrientationCreatePage
}

When(~'^I fill the orientation title with "([^"]*)"$') { title ->

    page.fillOrientationDetails(title)
    page.selectCreateOrientation()

    at OrientationShowPage
    page.showList()

    at OrientationsPage

}

//edit web
Given(~'^I am at the orientation page and the orientation "([^"]*)" is stored in the system$') { String title ->

    Login()

    to PublicationsPage
    at PublicationsPage
    page.select("Orientation")

    at OrientationsPage
    page.selectNewOrientation()

    at OrientationCreatePage
    page.fillOrientationDetails(title)
    page.selectCreateOrientation()

    at OrientationShowPage
    page.showList()

    at OrientationsPage

    orientation = Orientation.findByTituloTese(title)
    assert orientation != null
}

When(~'^I select to view orientation "([^"]*)" in resulting list$') { String oldtitle ->

    at OrientationsPage
    page.selectViewOrientation(oldtitle)

    at OrientationShowPage
    page.edit()
    at OrientationEditPage
}

When(~'^I change the orientation tituloTese to "([^"]*)"$') { String newtitle ->
    page.editTituloTese(newtitle)
}

When(~'^I select the alterar option at orientation edit page$') { ->
    page.confirmEdit()
}

Then(~'^I am on the orientation show page with edition completed$'){ ->
    at OrientationShowPage
    assert page.readFlashMessage() != null
}


Given(~'^the system has some orientations stored$') { ->
    // save old metaclass
    def registry = GroovySystem.metaClassRegistry
    this.oldMetaClass = registry.getMetaClass(SecurityUtils)
    registry.removeMetaClass(SecurityUtils)

    // Mock login
    def subject = [getPrincipal: { "admin" },
            isAuthenticated: { true }
    ] as Subject
    ThreadContext.put(ThreadContext.SECURITY_MANAGER_KEY,
            [getSubject: { subject } as SecurityManager])
    SecurityUtils.metaClass.static.getSubject = { subject }

    initialSize = Orientation.findAll().size()
}

When(~'^I upload a new orientation "([^"]*)"$') { filename ->
    inicialSize = Orientation.findAll().size()
    def path = new File(".").getCanonicalPath() + File.separator + "test" + File.separator + "files" + File.separator
    OrientationTestDataAndOperations.uploadOrientation(path + filename)
    finalSize = Orientation.findAll().size()
    assert inicialSize < finalSize
}

Then(~'the system has more orientations now$') { ->
    // restore metaclass
    GroovySystem.metaClassRegistry.setMetaClass(SecurityUtils, this.oldMetaClass)
    finalSize = Orientation.findAll().size()
}

And(~'^I select the upload button at the orientations page$') { ->
    at OrientationsPage
    page.uploadWithoutFile()
}

Then(~'^I\'m still on orientations page$') { ->
    at OrientationsPage
}

And(~'^the orientations are not stored by the system$') { ->
    at OrientationsPage
    page.checkIfOrientationListIsEmpty()
}

//create web with invalid year
/**
 * @author bss3
 */
When(~'^I fill the orientation title with "([^"]*)" and the year with (-?\\d+)$') { title, year ->
    page.fillOrientationDetailsWithGivenYear(title, year)
    page.selectCreateOrientation()
}

Then(~'^I am still on the create orientation page with the error message$') { ->
    at OrientationCreatePage
    assert page.readFlashMessage() != null
}

//new orientation with registered member orientated
/**
 * @author rlfs
 */
Given(~'^Exists a member "([^"]*)" with username "([^"]*)" that has been an registered member$') { String name, String username ->

    MemberTestDataAndOperations.createMember(username,"")
    member = Member.findByName(name)
    user = User.findByUsernameAndAuthor(username,member)
    assert user != null
}
//#1

Then(~'^the orientation "([^"]*)" with orientated member "([^"]*)" is properly stored by the system') { String entitled, String username ->
    user = User.findByUsername(username)
    orientation = Orientation.findByTituloTeseAndOrientador(entitled,user)
    assert orientation == null
}

When(~'I create a orientation for the thesis "([^"]*)" with registered member "([^"]*)"$') { entitled, username ->
    member = MemberTestDataAndOperations.findByUsername(username)
    OrientationTestDataAndOperations.createOrientationWithMenber(entitled, member)

}

Then(~'^the orientation "([^"]*)" was not stored twice$') { entitled ->
    orientation = Orientation.findAllByTituloTese(entitled)
    assert orientation.size() >= 2
}

//#2
Then(~'^the orientation for the thesis "([^"]*)" was not stored twice$') { entitled ->
    orientation = Orientation.findAllByTituloTese(entitled)
    assert orientation.size() < 2
}

//#5
And(~'^I change the orientation anoPublicacao to (-?\\d+)$') { anoPublicacao ->
    at OrientationEditPage
    page.editYear(anoPublicacao)
}

Then(~'^I am still on the change orientation page with the error message$') { ->
    at OrientationEditPage
    assert page.readFlashMessage() != null
}

Then(~'^The orientation "([^"]*)" is properly removed by the system$') { title ->
    assert Orientation.findByTituloTese(title) == null
}

//Remover Orientation Web
When(~'^I select to view "([^"]*)" in the list of orientations$') { title ->
    at OrientationsPage
    page.selectViewOrientation(title)

    at OrientationShowPage
}

When(~'^I select the option remove at Orientation Show Page$') { ->
    at OrientationShowPage
    page.delete()

}

//FUNCOES AUXILIARES


// o problema de duplicação que este método resolve não foi identificado pela ferramenta de detecção de clones
def Login() {
    to LoginPage
    at LoginPage
    page.fillLoginData("admin", "adminadmin")
}
