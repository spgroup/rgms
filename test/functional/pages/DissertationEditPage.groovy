package pages

import geb.Page;


class DissertationEditPage extends Page{
	static url = "dissertacao/edit"
	
	static at = {
		title ==~ /Editar Dissertação/
	}
	
	static content = {
		
	}
	
	def nameIs (String name){
		assert ($("form").school == name)
	}
	
	def editSchool(String school){
		$("form").school = school
	}
}
