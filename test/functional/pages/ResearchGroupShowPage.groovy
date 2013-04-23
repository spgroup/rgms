package pages

import geb.Page
class ResearchGroupShowPage extends Page{
	static url = "researchGroup/show/1"
	
		static at = {
            String teste = "/" + GetPageTitle.getMessage("default.button.show.label") +
                    " " + GetPageTitle.getMessage("researchGroup.label") + "/"
            title ==~  teste
			
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
