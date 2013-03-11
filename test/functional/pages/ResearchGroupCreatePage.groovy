package pages

import geb.Page

class ResearchGroupCreatePage extends Page{
	static url = "researchGroup/create"
	
		static at = {
			title ==~ /Criar Research Group/
			
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
