package pages

import geb.Page

class ConferenciaCreatePage extends Page{
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
        // Could parametrize, obtaining data from class TestConferenciaDataAndOperations
    }

    def fillTitle (){
        $("form").title = "A theory of Software Product Line Refinement"
        $("form").create().click()
    }
}
