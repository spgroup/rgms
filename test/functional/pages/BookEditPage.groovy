package pages

import geb.Page

/**
 * Created by Dickster on 21/01/2015.
 */
class BookEditPage extends Page {
    static url = "book/edit/1"

    static at = {
        GetPageTitle gp = new GetPageTitle()
        def currentBook = gp.msg("default.book.label")
        def currentTitle = gp.msg("default.edit.label", [currentBook])

        title ==~ currentTitle
    }

    def edit(String newTitle, newFile) {
        $("form").title = newTitle
        $("form").file = newFile
    }

    def select(String s) {
        $("form").find("input", value: s).click()
    }

    def doEdit() {
        $("form").find("input", name: "_action_update").click()
    }
}
