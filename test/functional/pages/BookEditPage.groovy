package pages;

import geb.Page
import rgms.publication.Book
import steps.BookTestDataAndOperations

/**
 * Created by Gabriela on 04-Feb-15.
 */
public class BookEditPage extends Page {
    static url = "book/edit/12"

    static at = {
        GetPageTitle gp = new GetPageTitle()
        def currentBook = gp.msg("default.book.label")
        def currentTitle = gp.msg("default.edit.label", [currentBook])

        title ==~ currentTitle
    }

    static content = {
        publisher {
            $("input", id: "publisher")
        }
    }

    def editFilename(String newFile) {
        $("form").file = new File(".").getCanonicalPath() + File.separator + "test" + File.separator + "functional" + File.separator + "steps" + File.separator + newFile
    }

    def select(String s) {
        $("form").find("input", value: s).click()
    }

    def clickSaveBook() {
        $("form").find(class: "save").click()
    }
}

