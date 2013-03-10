package pages

import geb.Page

class LoginPage extends Page {
    //static url = "auth/login"
	static url = ""

    static at = {
        title ==~ /RGMS/
		//$('title').text ==~ /RGMS/
		//$('title').text == "RGMS"
    }

    static content = {
		//flashmessage { $("div.message") }
		flashmessage { $("div", class: "message") }
    }

    def fillLoginData(String l, String p) {
        $("form").username = l
        $("form").password = p
        $("form").signIn().click()
    }
}