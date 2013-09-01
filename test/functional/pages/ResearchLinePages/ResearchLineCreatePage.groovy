package pages

import geb.Page
import steps.TestDataAndOperationsResearchLine


class ResearchLineCreatePage extends Page {
	static url = "researchline/create"

	static at = {
		title ==~ /Criar Research Line/
	}

	static content = {
	}

	def fillResearchLineDetails() {
		def name = "Modelo Cascata Renovado"
		def description = TestDataAndOperationsResearchLine.findResearchLineByName(name).description
		$("form").name = name
		$("form").description = description 
		assert $("form").name == name  
		assert $("#description").attr('value') == description
        $("input", name: "create").click()
	}

	def createsResearchLine(String name)
	{
		def research = TestDataAndOperationsResearchLine.findResearchLineByName(name)
		$("form").name = research.name
		$("form").description = research.description
        $("input", name: "create").click()
    }
}
