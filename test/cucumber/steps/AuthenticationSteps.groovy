import org.apache.shiro.crypto.hash.Sha256Hash
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.remote.SessionId
import pages.*
import pages.member.MemberListPage
import rgms.authentication.User
import rgms.member.Member
import steps.TestDataAuthentication

import static cucumber.api.groovy.EN.*

Then (~'I am redirected to the Publications Menu page') { ->
    at PublicationsPage
}
Then (~'I am redirected to the Login Page') { ->
    at LoginPage
}
Then (~'I am redirected to the User Register Page'){ ->
    at UserRegisterPage
}
Given (~'I access the Root Page') { ->
    to RootPage
}
When (~'I directly access the Member List Page') { ->
    to MemberListPage
}

Given(~'I am at the Login Page') { ->
    to LoginPage
    at LoginPage
}
Given(~'I am at the User Register Page') {  ->
    to UserRegisterPage
    at UserRegisterPage
}
Given(~'I am at the Member Listagem page') { ->
    to LoginPage
    at LoginPage
    page.fillLoginData("admin", "adminadmin")
    at PublicationsPage
    page.select("Member")
    //to LoginPage
    //at LoginPage
    //page.fillLoginData("admin", "adminadmin")
    //to PublicationsPage
    //at PublicationsPage
    //page.getLink("Member").click()
    at MemberListPage
}

When (~'I click the "([^"]*)" link') { linkText ->
    assert( page.getLink(linkText).click() != null )
}
When (~'I select the "([^"]*)" menu option') { String option ->
    assert( page.getMenuOption(option).click() != null )
}
When (~'I submit the form') { ->
    assert( page.submitForm() != null )
}

When(~'I try to login with an user that does not exist'){ ->
    page.fillLoginData('NonExistentUser','NonExistentUserPass')
}
When (~'I try to login with an existent user, though with wrong password') {->
    page.fillLoginData("admin","123")
}
Then(~'A login failure message is displayed'){ ->
    assert ( page.readFlashMessage() != null )
}


When(~'I register a user with success')  {  ->
    assert ( page.createNewUser() != null )
}
Then(~'A message indicating the user was successfully registered is displayed'){->
    assert ( page.readFlashMessage() != null )
}


Given (~'The user of "([^"]*)" username is not yet enabled') { username ->
    User usuarioNaoHabilitado = User.findByUsername("naoHabilitado")
    if (!usuarioNaoHabilitado){
        usuarioNaoHabilitado = new User(username: 'naoHabilitado', passwordHash: new Sha256Hash("senha").toHex(), enabled:false)
            def author = new Member(name:"Usuario Nao Habilitado", email:"naohabilitado@cin.ufpe.br", status:"Graduate Student", university:"UFPE")
        author.save()
        usuarioNaoHabilitado.author = author;
        usuarioNaoHabilitado.save()

    }
    def user = User.findByUsername(username)
    assert( !user?.enabled )
}

When (~'I try to create a "([^"]*)" username with the "([^"]*)" email') {String novoUsuario, String emailInvalido  ->
    to UserRegisterPage
    at UserRegisterPage
    page.university = "UFPE"
    page.name = "usuarioTeste"
    page.username = novoUsuario
    page.email = emailInvalido
    page.password1 = "senha123"
    page.password2 = "senha123"
    page.status = "Graduate Student"

}
Then (~"A message indicating the email is invalid is displayed") { ->
    page.submitForm()
    assert (page.readErrorsMessage() != null)
}



Then (~'The University field is filled with "([^"]*)"') { defaultName ->
    to UserRegisterPage
    at UserRegisterPage
    assert( page.university.value() ==~ /${defaultName}/ )
}


When (~'I mistype my confirmation password at Register Page') { ->
    def user = TestDataAuthentication.findByUsername("user186")
    page.password1.value(user.password)
    page.password2.value(user.password+"aa")
}

Then (~'The password fields are empty') {->
    assert(
        page.password1.value() == "" &&
        page.password2.value() == ""
    )
}
Then (~'My remaining user data is still at their corresponding fields') {->
    def user = TestDataAuthentication.findByUsername("user186")
    assert(
    page.name.value() == user.name &&
            page.username.value() == user.username &&
            page.email.value() == user.email &&
            page.university.value() == user.university &&
            page.status.value() == user.status
    )
}


Given (~'I am not logged') { ->
    page.browser.config.setAutoClearCookies(false)
    page.driver.manage().deleteAllCookies()
}

















When (~'I close and reopen the browser') { ->
    page.browser.config.setAutoClearCookies(false)
    ChromeDriver driverr = (ChromeDriver) page.driver
    SessionId session = driverr.getSessionId()
    def capabilities = driverr.getCapabilities()
    driverr.close()
    page.browser.config.setAutoClearCookies(false)
    driverr.startSession(capabilities)
    //driverr.setSessionId(session.toString())
    driverr.startClient()
}
When (~'I fill my login data') { ->
    at LoginPage
    page.fillLoginDataOnly("admin", "adminadmin")
}

When (~'Press the Remember me checkbox'){ ->
    page.rememberMe = true
}

When (~'The login procedure is successful') { ->
    page.submitForm()
    at PublicationsPage
}







When(~'I try loggin with "([^"]*)"'){ username ->
    page.fillLoginData(username, "senha")
}

Then(~'Inform the user that don`t have permission to loggin yet'){ ->
    to UnauthorizedPage
    at UnauthorizedPage
}

Given(~'I am at Register Page registering myself'){ ->
    to UserRegisterPage
    at UserRegisterPage
    def user = TestDataAuthentication.findByUsername("user186")
    page.name.value(user.name)
    page.username.value(user.username)
    page.email.value(user.email)
    page.university.value(user.university)
    page.status.value(user.status)
}
