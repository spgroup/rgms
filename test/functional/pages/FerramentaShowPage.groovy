package pages

import geb.Page

class FerramentaShowPage extends Page{
	static url = "ferramenta/show"
	
	static at = {
		title ==~ /Ver Ferramenta/
	}
	
	static content = {
		
	}
	
	def editFerramenta(){
		$('a.edit').click()
	}
	
	def deleteFerramenta(){
		assert withConfirm(true) {
			$('input.delete').click()
		}
	}
}
