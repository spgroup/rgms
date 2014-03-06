package pages

/**
 * Created with IntelliJ IDEA.
 * User: dyego
 * Date: 23/02/14
 * Time: 10:08
 * To change this template use File | Settings | File Templates.
 */

import geb.Page
import rgms.publication.Book

class BookCreatePage extends FormPage {
    static url = "book/create"

    static at = {
        GetPageTitle gp = new GetPageTitle()

        def currentReport = gp.msg("default.book.label")
        title ==~ gp.msg("default.create.label", [currentReport])
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

    def fillBookDetails(title, filename){
        fillTitle(title)
        $("form").publisher = "Person"
        $("form").volume = 1
        $("form").pages = "20"
        $("form").file = new File(".").getCanonicalPath() + File.separator + "test" + File.separator + "functional" + File.separator + "steps" + File.separator + filename
    }

    def clickSaveBook(){
        $("form").create().click()
    }

    def fillTitle(title) {
        $("form").title = title
        clickSaveBook()
    }
}