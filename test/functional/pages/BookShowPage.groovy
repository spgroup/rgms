package pages

import geb.Page

/**
 * Created by Casa on 20/01/2015.
 */
class BookShowPage extends Page {
    static url = "book/show"

    static at = {
        GetPageTitle gp = new GetPageTitle()
        def currentBook = gp.msg("default.book.label")
        def currentTitle = gp.msg("default.show.label", [currentBook])

        title ==~ currentTitle
    }

    static content = {
        book {
            $('input', id: 'book')
        }
    }

    def select(String e, v) {
        if (v == 'delete') {
            assert withConfirm(true) { $("form").find(e, class: v).click() }
        } else if (v == "edit"){
            $("form").find(e, class: "edit").click()
        } else {
            $("form").find(e, class: v).click()
        }
    }
}