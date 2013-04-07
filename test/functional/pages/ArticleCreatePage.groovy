package pages

import geb.Page
import pages.ArticleShowPage
import pages.ArticlesPage
import pages.GetPageTitle

class ArticleCreatePage extends Page {
	static url = "periodico/create"

	static at = {
		String teste = "/" 	+ GetPageTitle.getMessage('default.button.create.label') +
						" " + GetPageTitle.getMessage('periodico.label') + "/"
		title ==~ teste
		journal != null
	}

	static content = {
		journal {
			$("input", id: "journal")
		}
	}

	def fillArticleDetails() {
		$("form").title 	= "A theory of Software Product Line Refinement"
		$("form").journal 	= "Theoretical Computer Science"
		$("form").file 		= "TCS.pdf"
		$("form").volume 	= 455
		$("form").number 	= 1
		$("form").pages 	= "2-30"
		// Could parametrize, obtaining data from class TestDataAndOperations
	}

	def selectCreateArticle(){
		$("input", name: "create").click()
	}
}