package pages

import geb.Page

class RegisterPage extends Page {
	static url = "auth/register"

	static at = {
		title ==~ /RGMS/
	}

	static content = {
		
		//memberStatus { $("#status") }
		
	}

	def fillUserDetails(String name, String username, String password1, String password2, String email, String university, String status) {
		$("form").name = "Jose"
		$("form").username = "josesilva"
		$("form").password1 = "123456"
		$("form").password2 = "123456"
		$("form").email = "jasilva@ufpe.com"
		$("form").university = "UFPE"
		//memberStatus.value("Graduate Student")
		$("form").status = "Graduate Student"
		$("form").register().click()
	}
}