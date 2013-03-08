package pages

import geb.Page
import rgms.publication.Periodico

class TechnicalReportEditPage extends Page {
	static url = "technicalReport/edit/2"

	static at = {
		title ==~ /Editar TechnicalReport/
	}

	static content = {
	}


	def edit(String novovalor){
		$("form").title = novovalor
	}

	def select(String s) {
		assert withConfirm(true) {$("form").find("input", value: s).click()}

	}
}
