package pages

import geb.Page

class MembershipCreatePage extends Page {
	static url = "membership/create"

	static at = {
		title ==~ /Criar Membership/
		membership != null
	}

	static content = {
		membership {
			$("input", id: "membership")
		}
	}

	
}