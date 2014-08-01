package pages.ResearchLinePages

import geb.Page
import steps.ResearchGroupTestDataAndOperations
import steps.ResearchLineTestDataAndOperations
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
		def description = ResearchLineTestDataAndOperations.findResearchLineByNameOrientation(name).description
		$("form").name = name
		$("form").description = description 
		assert $("form").name == name  
		assert $("#description").attr('value') == description
        $("input", name: "create").click()
	}

	def createsResearchLine(String name)
	{
		def research = ResearchLineTestDataAndOperations.findResearchLineByNameOrientation(name)
		$("form").name = research.name
		$("form").description = research.description
        $("input", name: "create").click()
    }
}
