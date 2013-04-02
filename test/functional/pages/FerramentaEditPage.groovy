package pages

import geb.Page;


class FerramentaEditPage extends Page{
	static url = "Ferramenta/edit"
	
	static at = {
		title ==~ /Editar Ferramenta/
	}
	
	static content = {
		
	}
	
	def editWebsite(String website){
		$("form").website = website
		$("form").save().click()
	}
}
