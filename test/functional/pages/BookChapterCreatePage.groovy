package pages

import geb.Page
import rgms.publication.BookChapter

class BookChapterCreatePage extends FormPage {
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

    def fillBookChapterDetails(title, filename){
        fillTitle(title)
        $("form").publisher = "Person"
        $("form").chapter = 1
        $("form").file = new File(".").getCanonicalPath() + File.separator + "test" + File.separator + "functional" + File.separator + "steps" + File.separator + filename
    }

    def clickSaveBookChapter(){
        $("form").create().click()
    }

    def fillTitle(title) {
        $("form").title = title
        clickSaveBookChapter()
    }
}
