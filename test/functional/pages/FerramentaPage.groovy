package pages

import geb.Page

class FerramentaPage extends Page {
	static url = "ferramenta/list"

	static at = {
		title ==~ /Ferramenta Listagem/
	}

	static content = {
	}

	def selectNewArticle() {
		$('a.create').click()
	}
	
	def selectFerramenta(String s) {
		$('a', text: s).click()
	}
}