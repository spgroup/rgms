package pages

import geb.Page

class DissertationShowPage extends Page{
	static url = "dissertacao/show"
	
	static at = {
		title ==~ /Ver Dissertacao/
	}
	
	static content = {
		
	}
	
	def editDissertation(){
		$('a.edit').click()
	}
	
	def deleteDissertation(){
		assert withConfirm(true) {
			$('input.delete').click()
		}
	}
}
