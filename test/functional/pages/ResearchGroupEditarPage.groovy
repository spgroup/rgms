package pages

import geb.Page

class ResearchGroupEditarPage extends Page{
	
	static at = {
		title ==~ /Editar Research Group/
		
	}
	
	def selectAlterarResearchGroup() {
		$('input.save').click()
	}
	
	def changeResearchGroupDetails(String name){
		$("form").name = name
		$("form").description = "grupo de pesquisa " + name
	}
}
