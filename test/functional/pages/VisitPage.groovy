package pages

import geb.Page
import steps.TestDataAndOperations

class VisitPage extends Page {
	static url = "visit/list"
	
	static content = {
	}


	def selectNewVisit() {
		$('a.create').click()
	}
	
}
