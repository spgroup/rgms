package pages
import steps.TestDataAndOperations
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
		assert $("form").name == "Modelo Cascata Renovado" && ("form").description == "Alteração do modelo original"
	}
	
	def createsResearchLine(String name)
	{
		def research = TestDataAndOperations.findResearchLineByName(name)
		$("form").name = research.name
		$("form").description = research.description
		$("input.save").click()
	}
}

