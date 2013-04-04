package pages

import geb.Page

class DissertationCreate extends Page {
	static url = "dissertacao/create"

	static at = {
		title ==~ /Criar Dissertação/
	}

	static content = {
		/*journal {
			$("input", id: "journal")
		}*/
	}
	
	def fillDissertationDetailsWithoutFile() {
		$("form").title = "Dissertacao Teste 1"
		$("form").school = "WebSite"
		$("form").address = "Description"
		$("form").create().click()
		// Could parametrize, obtaining data from class TestDataAndOperations
	}
	
}
