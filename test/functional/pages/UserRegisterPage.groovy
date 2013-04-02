package pages

import geb.Page
import geb.navigator.Navigator
import rgms.member.Member
import steps.TestDataAuthentication

class UserRegisterPage extends Page {
    static url = "auth/register"

    static at = {
        title ==~ /RGMS/ && university != null && registerButton != null
    }

    static content = {
        university {$("form input", name: "university")}
        name {$("form input", name: "name")}
        username {$("form input", name: "username")}
        password1 {$("form input", name: "password1")}
        password2 {$("form input", name: "password2")}
        email {$("form input", name: "email")}
        status {$("form select", name: "status")}
        registerButton {$("form input", value: "Register")}
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
        submitForm();
        Member.findByUsername(user.username)
    }

}
