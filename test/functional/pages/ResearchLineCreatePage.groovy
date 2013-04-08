package pages
import steps.TestDataAndOperationsEquipe4
import geb.Page

class ResearchLineCreatePage extends Page {
	static url = "researchline/create"

	static at = {
		//String teste = "/" + GetPageTitle.getMessage("default.button.create.label") + " " + GetPageTitle.getMessage("researchLine.label") + "/"
		//title ==~  teste
		title ==~ /Criar Research Line/
	}

	static content = {
	}

	def fillResearchLineDetails() {
		def name = "Modelo Cascata Renovado"
		def description = TestDataAndOperationsEquipe4.findResearchLineByName(name).description
		$("form").name = name
		$("form").description = description 
		assert $("form").name == name  
		assert $("#description").attr('value') == description
	}
	
	def createsResearchLine(String name)
	{
		def research = TestDataAndOperationsEquipe4.findResearchLineByName(name)
		$("form").name = research.name
		$("form").description = research.description
		$("input.save").click()
	}
}

