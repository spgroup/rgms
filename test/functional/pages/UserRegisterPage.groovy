package pages

import geb.Page
import rgms.authentication.User
import steps.MemberTestDataAndOperations
import steps.TestDataAuthentication

class UserRegisterPage extends Page {
    def titleName = /${(new GetPageTitle()).getMessageServerLocale("user.register.title")}/
    static url = "auth/register"

    static at = {
        title ==~ titleName && university != null && registerButton != null
    }

    static content = {
        university {$("form input", name: "university")}
        name {$("form input", name: "name")}
        username {$("form input", name: "username")}
        password1 {$("form input", name: "password1")}
        password2 {$("form input", name: "password2")}
        email {$("form input", name: "email")}
        status {$("form select", name: "status")}
        registerButton { $("form input", value: "register") }
        readFlashMessage(){ $("div .message").text() }
        readErrorsMessage(){ $("div .errors").text()}
    }


    def submitForm = { $("form input[type='submit']").click() }

    def createNewUser(){
        def user = TestDataAuthentication.generateUnregisteredUser();
        name.value(user.name);
        username.value(user.username);
        password1.value(user.password);
        password2.value(user.password);
        email.value(user.email);
        university.value(user.university);
        status.value(user.status);
		//facebook_id.value("teste");
		//access_token.value(user.access_token);
        submitForm();
        User.findByUsername(user.username)
    }

	def fillFormData(String userName){
		println("userName="+userName)
		def userData = MemberTestDataAndOperations.findByUsername(userName)
		$("form").university = userData.university
		$("form").name = userData.name
		$("form").username = userName
		$("form").email = userData.email

		$("form").password1 = "adminadmin"
		$("form").password2 = "adminadmin"
		$("form").status = "Graduate Student"
	}
}
