package pages.Conferencia

import pages.FormPage
import steps.ArticleTestDataAndOperations

class ConferenciaCreatePage extends FormPage {
    static url = "conferencia/create"

    static at = {
        title ==~ /Criar Conferencia/
        booktitle != null
    }

    static content = {
        booktitle {
            $("input", id: "booktitle")
        }
        flashmessage {
            $("div", class: "message")
        }
    }

    def fillConferenciaDetails() {
        $("form").title = "A theory of Software Product Line Refinement"
        $("form").booktitle = "Theoretical Computer Science"
        $("form").pages = "20-100"
        $("form").create().click()
        // Could parametrize, obtaining data from class TestConferenciaDataAndOperations
    }

    def fillTitle() {
        $("form").title = "A theory of Software Product Line Refinement"
        $("form").create().click()
    }

    def fillConferenciaDetails(String title) {
        $("form").title = title
        $("form").booktitle = title
        $("form").pages = "20-100"
        $("form").file = ArticleTestDataAndOperations.path() + "filename"
    }

    def selectCreateConferencia() {
        $("form").create().click()
    }

    def selectHome() {
        $('a.home').click()
    }

}
