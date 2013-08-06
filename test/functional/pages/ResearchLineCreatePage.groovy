package pages

import geb.Page
import steps.TestDataAndOperations


class ResearchLineCreatePage extends Page {
	static url = "researchline/create"

	static at = {
		title ==~ /Criar Research Line/
	}

	static content = {
	}

	def fillResearchLineDetails() {
		def name = "Modelo Cascata Renovado"
		def description = TestDataAndOperations.findResearchLineByName(name).description
		$("form").name = name
		$("form").description = description 
		assert $("form").name == name  
		assert $("#description").attr('value') == description
	}

	def createsResearchLine(String name)
	{
		def research = TestDataAndOperations.findResearchLineByName(name)
		$("form").name = research.name
		$("form").description = research.description
		$("input.save").click()
	}
}
