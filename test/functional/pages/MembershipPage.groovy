package pages

import geb.Page

class MembershipPage extends Page {
	static url = "membership/list"

	static at = {
		title ==~ /Membership Listagem/
	}

	static content = {
	}

	def selectNewMembership() {
		$('a.create').click()
	}
}