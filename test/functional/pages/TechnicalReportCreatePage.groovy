package pages

import geb.Page

class TechnicalReportCreatePage extends Page {
	static url = "technicalReport/create"

	static at = {
		title ==~ /Criar Technical Report/
		journal != null
	}

	static content = {
		journal {
			$("input", id: "journal")
		}
	}

	def fillArticleDetails() {
		$("form").title = "A theory of Software Product Line Refinement"
		$("form").journal = "Theoretical Computer Science"
		// Could parametrize, obtaining data from class TestDataAndOperations
	}
}
