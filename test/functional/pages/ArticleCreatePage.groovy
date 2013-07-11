package pages

import geb.Page
import steps.TestDataAndOperations

class ArticleCreatePage extends Page {
    static url = "periodico/create"

	static at = {
		//title ==~ /Criar Periódico/
		GetPageTitle gp = new GetPageTitle()
		def currentPeriodico = gp.msg("default.periodico.label")
		def currentTitle = gp.msg("default.create.label", [currentPeriodico])
		title ==~ currentTitle
		journal != null
	}

    def fillArticleDetails() {
        fillArticleDetails("./test/functional/steps/TCS.pdf", "A theory of software product line refinement")
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

	def fillArticleDetails(def articleTitle) {
		def article = TestDataAndOperations.findArticleByTitle(articleTitle)
		$("form").title = articleTitle
		$("form").journal = article.journal
		$("form").volume = article.volume
		$("form").number = article.number
		$("form").pages = article.pages
		$("form").file 		= article.file
		//$("input", type: "submit", class: "save", id:"create").click()
		$("input.save").click()
	}
	def selectCreateArticle(){
		$("input", name: "create").click()
	}
}
