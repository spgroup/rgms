package pages.thesis

import geb.Page
import pages.GetPageTitle
import rgms.publication.Tese

class ThesisShowPage extends Page {
    static url = "tese/show"

    static at = {
        GetPageTitle gp = new GetPageTitle()
        def currentTese = gp.msg("default.tese.label")
        def currentTitle = gp.msg("default.show.label", [currentTese])
        title ==~ currentTitle
    }

    static content = {
    }

    def select() {
        $("form").find('a', class: 'edit').click()
    }

    def delete() {
        assert withConfirm(true) { $("form").find('input', class: 'delete').click() }
    }

}