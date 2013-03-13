package pages

import geb.Page
import rgms.publication.Periodico

class TechnicalReportShowPage extends Page {
	static url = "technicalReport/show/2"

	static at = {
		title ==~ /Ver TechnicalReport/
	}

	static content = {
	}

	def select(String e,v) {
		$("form").find(e, class: v).click()
//		if(v == 'delete'){
//			assert withConfirm(true) { $('input', class: 'delete').click() } == "Are you sure?"
//		}
	}
}
