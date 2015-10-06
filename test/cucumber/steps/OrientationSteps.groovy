import pages.OrientationPages.OrientationCreatePage
import pages.OrientationPages.OrientationEditPage
import pages.OrientationPages.OrientationShowPage
import pages.OrientationPages.OrientationsPage
import pages.PublicationsPage
import rgms.authentication.User
import rgms.member.Member
import rgms.member.Orientation
import steps.MemberTestDataAndOperations
import steps.OrientationTestDataAndOperations
import pages.LoginPage
import org.apache.shiro.SecurityUtils
import org.apache.shiro.subject.Subject
import org.apache.shiro.util.ThreadContext
import steps.TestDataAndOperations

import static cucumber.api.groovy.EN.*

// create
Given(~'^the system has no orientations entitled "([^"]*)"$') { String tituloTese ->
    checkIfOrientationDoesNotExists(tituloTese)
}

When(~'^I create a new orientation entitled "([^"]*)"$') { String tituloTese ->
    // Express the Regexp above with the code you wish you had
    OrientationTestDataAndOperations.createOrientation(tituloTese)
}

When(~'^I create a new orientation entitled "([^"]*)" and with type of "([^"]*)"$') { String tituloTese, String tipo->
    // Express the Regexp above with the code you wish you had
    OrientationTestDataAndOperations.createOrientation(tituloTese, tipo)
}

Then(~'^the orientation "([^"]*)" is properly stored by the system$') { String title ->
    checkIfOrientationExists(title)
}

//delete
Given(~'^the system has an orientation entitled "([^"]*)" supervised by someone$') { String title ->
    OrientationTestDataAndOperations.createOrientation(title)
    orientation = OrientationTestDataAndOperations.findOrientationByTitle(title)
    assert orientation != null
}

When(~'^I delete the orientation for "([^"]*)"$') { String title ->
    OrientationTestDataAndOperations.removeOrientation(title)
}

Then(~'^the orientation for "([^"]*)" is properly removed by the system$') { String title ->
    checkIfOrientationDoesNotExists(title)
}

private void checkIfOrientationDoesNotExists(String title) {
    orientation = Orientation.findByTituloTese(title)
    assert orientation == null
}

private void checkIfOrientationExists(String title){
    orientation = Orientation.findByTituloTese(title)
    assert orientation != null   
}

//create web
Given(~'^I am at the create orientation page$') { ->
    goToOrientationCreatePage()
}

When(~'^I fill the orientation title with "([^"]*)"$') { title ->
    fillOrientationWithTitleAndCreateThen(title)
}

private void fillOrientationWithTitleAndCreateThen(title) {
    page.fillOrientationDetails(title)
    page.selectCreateOrientation()

    at OrientationShowPage
    page.showList()

    at OrientationsPage
}


//edit web
Given(~'^I am at the orientation page$') { ->
    goToOrientationCreatePage()
}


And(~'^the orientation "([^"]*)" is stored in the system$') { String title ->
    fillOrientationWithTitleAndCreateThen(title)
    checkIfOrientationExists(title)
}

When(~'^I select to view orientation "([^"]*)" in resulting list$') { String oldtitle ->

    at OrientationsPage
    page.selectViewOrientation(OrientationTestDataAndOperations.getOrientationIdAsString(oldtitle))

    at OrientationShowPage
    page.edit()
    at OrientationEditPage
}

When(~'^I change the orientation title to "([^"]*)"$') { String newtitle ->
    page.editTituloTese(newtitle)
}

When(~'^I select the change option at the orientation edit page$') { ->
    page.confirmEdit()
}

Then(~'^the edited orientation "([^"]*)" is properly stored by the system$') { String title ->
    at OrientationShowPage
    checkIfOrientationExists(title)
}

Then(~'^I am on the orientation show page with edition completed$'){ ->
    at OrientationShowPage
    assert page.readFlashMessage() != null
}

Given(~'^the system has some orientations stored$') { ->
    TestDataAndOperations.loginController(this)
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
    TestDataAndOperations.logoutController(this)
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

Then(~'^I am still on the create orientation page with an error message$') { ->
    at OrientationCreatePage
    assert page.readFlashMessage() != null
}

//new orientation with registered member orientated
/**
 * @author rlfs
 */
Given(~'^Exists a member "([^"]*)" with username "([^"]*)" that has been an registered member$') { String name, String username ->

    MemberTestDataAndOperations.createMember(username, "")
    member = Member.findByName(name)
    user = User.findByUsernameAndAuthor(username, member)
    assert user != null
}
//#1

Then(~'^the orientation "([^"]*)" with orientated member "([^"]*)" is properly stored by the system') { String entitled, String username ->
    user = User.findByUsername(username)
    orientation = Orientation.findByTituloTeseAndOrientador(entitled, user)
    assert orientation == null
}

When(~'I create a orientation for the thesis "([^"]*)" with registered member "([^"]*)"$') { entitled, username ->
    member = MemberTestDataAndOperations.findByUsername(username)
    OrientationTestDataAndOperations.createOrientationWithMenber(entitled, member)
}

//#2
Then(~'^the orientation for the thesis "([^"]*)" is not stored twice$') { entitled ->
    orientation = Orientation.findAllByTituloTese(entitled)
    assert orientation.size() < 2
}

//#5
And(~'^I fill the orientation publication year with (-?\\d+)$') { anoPublicacao ->
    at OrientationEditPage
    page.editYear(anoPublicacao)
}

Then(~'^I am still on the change orientation page with an error message$') { ->
    at OrientationEditPage
    assert page.readFlashMessage() != null
}

Then(~'^The orientation "([^"]*)" is properly removed by the system$') { title ->
    assert Orientation.findByTituloTese(title) == null
}

//Remover Orientation Web
When(~'^I select to view "([^"]*)" in the list of orientations$') { String title ->
    at OrientationsPage
    page.selectViewOrientation(OrientationTestDataAndOperations.getOrientationIdAsString(title))

    at OrientationShowPage
}

When(~'^I select the option remove at the orientation show page$') { ->
    at OrientationShowPage
    page.delete()

}

def orientationNoExist(String title){
    return OrientationTestDataAndOperations.findOrientationByTitle(title) == null
}

//filter
Given(~'^the system has some orientation with type "([^"]*)"$'){ String tipo ->
   OrientationTestDataAndOperations.createOrientation('A theory of software product line refinement', tipo)
   OrientationTestDataAndOperations.createOrientation('Modularity analysis of use case implementations')

   assert (!orientationNoExist('A theory of software product line refinement') && !orientationNoExist('Modularity analysis of use case implementations'))
}

When(~'^the system filter the orientation with type "([^"]*)"$'){ String tipo ->
    orientationFiltered = Orientation.findByTipo(tipo)
    assert Orientation.isFiltered(orientationFiltered, tipo)
}

Then(~'^the system orientation list content is not modified$'){
    assert Orientation.findAll().size() == 2
    assert !orientationNoExist('Modularity analysis of use case implementations')
    assert !orientationNoExist('A theory of software product line refinement')
}

And(~'^I create some orientation with type of "([^"]*)"$') {String tipo->
    at OrientationsPage
    page.selectNewOrientation()
    at OrientationCreatePage
    fillOrientationWithTitleAndCreateThen('Modularity analysis of use case implementations')
    page.selectCreateOrientation()
    assert !orientationNoExist('Modularity analysis of use case implementations')
    to OrientationsPage
    page.selectNewOrientation()
    at OrientationCreatePage
    fillOrientationWithTitleAndCreateThen('A theory of software product line refinement')
    page.selectCreateOrientation()
    assert !orientationNoExist('A theory of software product line refinement')
    to OrientationsPage
}

When(~'^I select to view the list of orientation$') {->
    at OrientationsPage
    page.selectViewOrientation()
}

And(~'^I select to filter the list of orientation by type of "([^"]*)"$') {String tipo->
    at OrientationsPage
    page.fillAndSelectFilter(tipo)
}

Then(~'^my orientation list shows only the orientation type of by "([^"]*)"$') {String tipo->
    at OrientationsPage
    assert page.checkFilteredBy(tipo);
}


Given(~'^the system has some orientation supervised by "([^"]*)"$'){ String leader ->
    OrientationTestDataAndOperations.createOrientation('The Book is on the table', leader)
    OrientationTestDataAndOperations.createOrientation('The Book is on the table')

    assert (!orientationNoExist('A theory of software product line refinement') && !orientationNoExist('Modularity analysis of use case implementations'))
}

When(~'^the system filter the articles supervised by leader "([^"]*)"$'){ String leader ->
    orientationFiltered = Orientation.findByOrientador(leader)
    assert Orientation.isFiltered(orientationFiltered, leader, null)
}

And(~'^I create some orientation supervised by "([^"]*)"$') {String leader->
    at OrientationsPage
    page.selectNewOrientation()
    at OrientationCreatePage
    fillOrientationWithTitleAndCreateThen('Modularity analysis of use case implementations', leader)
    page.selectCreateOrientation()
    assert !orientationNoExist('Modularity analysis of use case implementations')
    to OrientationsPage
    page.selectNewOrientation()
    at OrientationCreatePage
    fillOrientationWithTitleAndCreateThen('A theory of software product line refinement')
    page.selectCreateOrientation()
    assert !orientationNoExist('A theory of software product line refinement')
    to OrientationsPage
}

And(~'^I select to filter the list of orientation by leader "([^"]*)"$') {String leader->
    at OrientationsPage
    page.fillAndSelectFilter(leader)
}

Then(~'^my orientation list shows only the orientation supervised by "([^"]*)"$') {String leader->
    at OrientationsPage
    assert page.checkFilteredBy(leader);
}

//FUNCOES AUXILIARES

private void goToOrientationCreatePage() {
    to LoginPage
    at LoginPage
    page.fillLoginData("admin", "adminadmin")

    to PublicationsPage
    at PublicationsPage
    page.select("Orientation")

    at OrientationsPage
    page.selectNewOrientation()

    at OrientationCreatePage
}
