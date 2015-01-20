package pages

import geb.Page

/**
 * Created by droa on 22/11/2014.
 */
class BookShowPage extends Page {
    static url = "book/show/1"

    static at = {

        GetPageTitle gp = new GetPageTitle()
        def currentBook = gp.msg("default.book.label")
        def currentTitle = gp.msg("default.show.label", [currentBook])

        title ==~ currentTitle
    }

    def select(String e, v) {
        if (v == 'delete') {
            assert withConfirm(true) { $("form").find(e, class: v).click() }
        } else {
            $("form").find(e, class: v).click()
        }
    }

    def clickOnTwitteIt(String login, pw) {
        $("#button_twitter").click()
    }
}
