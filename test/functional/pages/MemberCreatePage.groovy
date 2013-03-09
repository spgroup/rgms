package pages

import geb.Page

class MemberCreatePage extends Page {
	static url = "member/create"

	static at = {
		title ==~ /Criar Member/
	}

	static content = {
		
		mensagem {$("div", 4, class: "errors")}
	}

	def fillMemberDetails(String name, String username, String email, String university) {
		$("form").name = name
		$("form").username = username
		$("form").email = email
		$("form").university = university
		$("form").create().click()
	}
}
