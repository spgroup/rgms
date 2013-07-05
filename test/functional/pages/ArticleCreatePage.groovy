package pages

import geb.Page

class ArticleCreatePage extends Page {
    static url = "periodico/create"

    static at = {
        title ==~ /Criar Periodico/
        journal != null
    }

    static content = {
        journal {
            $("input", id: "journal")
        }
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

    def selectCreateArticle() {
        $("input", name: "create").click()
    }
}