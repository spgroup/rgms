package pages.ArticlePages

import pages.FormPage
import pages.GetPageTitle

class ArticleCreatePage extends FormPage {
	static url = "periodico/create"

	static at = {
		//title ==~ /Criar Periódico/
		GetPageTitle gp = new GetPageTitle()
		def currentPeriodico = gp.msg("default.periodico.label")
		def currentTitle = gp.msg("default.create.label", [currentPeriodico])
		title ==~ currentTitle
		journal != null
	}
	
	static content = { journal { $("input", id: "journal") } }

	def fillArticleDetails() {
		def path = new File(".").getCanonicalPath() + File.separator + "test" + File.separator + "files" + File.separator + "TCS.pdf"
		fillArticleDetails(path, "A theory of software product line refinement")
	}

	def fillArticleDetails(filename, title) {
		$("form").title = title
		$("form").journal = "Theoretical Computer Science"
		$("form").file = filename
		$("form").volume = 455
		$("form").number = 1
		$("form").pages = "2-30"
		// Could parametrize, obtaining data from class TestDataAndOperations
	}

	def fillArticleDetails(filename, title, day, month, year) {
		$("form").title = title
		$("form").publicationDate_day 	= day
		$("form").publicationDate_month = month
		$("form").publicationDate_year 	= year
		$("form").journal = "Theoretical Computer Science"
		$("form").file = filename
		$("form").volume = 455
		$("form").number = 1
		$("form").pages = "2-30"

	}

	def fillArticleDetails(filename, title, authorName) {
		$("form").title = title
		$("form").journal = "Theoretical Computer Science"
		$("form").authors = authorName
		$("form").file = filename
		$("form").volume = 455
		$("form").number = 1
		$("form").pages = "2-30"
	}

	def fillArticleDetailsExceptTitle() {
		$("form").journal = "Theoretical Computer Science"
		$("form").file = new File(".").getCanonicalPath() + File.separator + "test" + File.separator + "files" + File.separator + "TCS.pdf"
		$("form").volume = 455
		$("form").number = 1
		$("form").pages = "2-30"
	}
	
	def selectCreateArticle() {
		$("input", name: "create").click()
	}
}
