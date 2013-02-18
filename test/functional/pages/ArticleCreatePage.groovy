package pages

import geb.Page

class ArticleCreatePage extends Page {
    static url = "periodico/create"

    static at = {
        title ==~ /Create Journal/
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