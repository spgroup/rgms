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
		def new_name = "Modelo Cascata Editado"
		def description = "Edição do modelo original"
		$("form").name = new_name
		$("form").description = description
		assert $("form").name == new_name
		assert $("#description").attr('value') == description
	}
}
