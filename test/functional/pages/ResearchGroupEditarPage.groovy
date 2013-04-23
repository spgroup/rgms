package pages

import geb.Page

class ResearchGroupEditarPage extends Page{
	
	static at = {
        String teste = "/" + GetPageTitle.getMessage("default.button.edit.label") +
                " " + GetPageTitle.getMessage("researchGroup.label") + "/"
        title ==~  teste
	}
	
	def selectAlterarResearchGroup() {
		$('input.save').click()
	}
	
	def changeResearchGroupDetails(String name){
		$("form").name = name
		$("form").description = "grupo de pesquisa " + name
	}
}
