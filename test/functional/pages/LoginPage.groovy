package pages

import geb.Page

class LoginPage extends Page {
    static url = "auth/login"

    static at = {
        title ==~ /Login/
    }

    static content = {

    }

    def fillLoginData(String l, String p) {
        $("form").username = l
        $("form").password = p
        $("form").signIn().click()
    }
}