package pages

import geb.Page

class LoginPage extends Page {
	static url = "auth/login"

    static at = {
        title ==~ /Login/ && rememberMe != null
    }

    static content = {
        rememberMe {$("form input#rememberMe")}
        readFlashMessage(){ $("div .message").text() }
        readErrorsMessage(){ $("div .errors").text()}
    }


    def getLink (String linkName) { $("div#status a", text: linkName) }
    def submitForm = { $("form input[type='submit']").click() }


    def fillLoginDataOnly(String username, String password) {
        $("form").username = username
        $("form").password = password
    }
    def fillLoginDataAndSubmit(String username, String password) {
        $("form").username = username
        $("form").password = password
 		$("form").signIn().click()
    }
    
	def fillLoginData(String l, String p) {
		$("form").username = l
		$("form").password = p
		$("form").signIn().click()
	}
       

}
