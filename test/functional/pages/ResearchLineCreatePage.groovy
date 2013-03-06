package pages

import geb.Page

class ResearchLineCreatePage extends Page {
	static url = "researchline/create"

	static at = {
		title ==~ /Criar Research Line/
	}

	static content = {
	}

	def fillResearchLineDetails() {
		$("form").name = "Modelo Cascata Renovado"
		$("form").description = "Alteração do modelo original"
	}
}

