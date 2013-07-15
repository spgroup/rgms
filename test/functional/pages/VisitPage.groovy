package pages

import geb.Page


class VisitPage extends Page {
	static url = "visit/list"

	static content = {
	}


	def selectNewVisit() {
		$('a.create').click()
	}

}

