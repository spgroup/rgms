package pages

import geb.Page

class ResearchGroupCreatePage extends Page{
	static url = "researchGroup/create"
	
		static at = {
            String teste = "/" + GetPageTitle.getMessage("default.button.create.label") +
                    " " + GetPageTitle.getMessage("researchGroup.label") + "/"
            title ==~  teste
		}
	
		static content = {
		}
	
		def fillResearchGroupDetails(String name){
			$("form").name = name
			$("form").description = "A research group called " + name
		}	
		
		def clickOnCreate(){
			$('input.save').click()
		}
		
}
