package pages

import geb.Page

class LoginPage extends Page {
    def titleName = /${(new GetPageTitle()).getMessageServerLocale("user.login.title")}/
	static url = "auth/login"

    static at = {
        title ==~ titleName && rememberMe != null
    }

    static content = {
        rememberMe { $("form input#rememberMe") }
        readFlashMessage() { $("div.message").text() }
        readErrorsMessage() { $("div.errors").text() }
    }


    def getLink(String linkName) { $("div#status a", text: linkName) }

    def submitForm = { $("form input[type='submit']").click() }


    def fillLoginDataOnly(String username, String password) {
        $("form").username = username
        $("form").password = password
    }

    def fillLoginDataAndSubmit(String username, String password) {
        fillLoginDataOnly(username, password);
        $("form").signIn().click()
    }

    def fillLoginData(String l, String p) {
        $("form").username = l
        $("form").password = p
        $("form").signIn().click()
    }


}
