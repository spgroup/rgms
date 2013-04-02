package pages

import geb.Page

class BookChapterCreatePage extends Page{
    static url = "bookChapter/create"

    static at = {
        title ==~ /Criar BookChapter/
        publisher != null
    }

    static content = {
        publisher {
            $("input", id: "publisher")
        }
        flashmessage {
            $("div", class: "message")
        }
    }

    def fillBookChapterDetails() {
        $("form").title = "A theory of Software Product Line Refinement"
        $("form").publisher = "Theoretical Computer Science"
        $("form").chapter = 1
        // Could parametrize, obtaining data from class TestDataAndOperations
    }

    def fillTitle (){
        $("form").title = "A theory of Software Product Line Refinement"
        $("form").create().click()
    }
}
