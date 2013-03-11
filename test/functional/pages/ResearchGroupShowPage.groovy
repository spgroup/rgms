package pages

import geb.Page
class ResearchGroupShowPage extends Page{
	static url = "researchGroup/show/1"
	
		static at = {
			title ==~ /Ver Research Group/
			
		}
	
		static content = {
		}
		
		def selectEditResearchGroup() {
			$('a.edit').click()
		}
		
		def selectRemoveResearchGroup() {
			$('input.delete').click()
		} 
}
