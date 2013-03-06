package pages

import geb.Page

class ResearchGroupCreatePage extends Page{
	static url = "researchGroup/create"
	
		static at = {
			title ==~ /Criar Research Group/
			
		}
	
		static content = {
		}
	
		def fillResearchGroupDetails(){
			$("form").name = "Group X"
			$("form").description = "A research group called X"
		}	
		
}
