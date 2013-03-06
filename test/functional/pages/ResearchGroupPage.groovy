package pages

import geb.Page	

class ResearchGroupPage extends Page{
	static url = "researchGroup/list"
	
		static at = {
			title ==~ /Research Group Listagem/
			
		}
	
		static content = {
		}
	
		def selectNewResearchGroup() {
			$('a.create').click()
		}
		
		def showResearchGroup(String a){
			$('a', title: a).click()
		}
}
