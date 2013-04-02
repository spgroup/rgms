package pages

import geb.Page

class ResearchLineEditPage extends Page {
	static url = "researchline/edit"

	static at = {
		title ==~ /Editar Research Line/
	}

	static content = {
	}

	def changeResearchLineDetails(String name) {
		def old_name = $("form").name
		assert old_name == name
		$("form").name = "Modelo Cascata Editado"
		$("form").description = "Edição do modelo original"
	}
}

