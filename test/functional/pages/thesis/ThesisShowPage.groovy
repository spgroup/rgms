package pages.thesis

import geb.Page
import pages.GetPageTitle

class selectTitleInPreviousSearchThesisShowPage extends Page {
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

    def checkThesisDetails(title, year, school) {
        def elements = $('div', id: 'show-tese').find('ol')[0].find('li.fieldcontain span[aria-labelledby]')

        assert elements[0].text() == title
        assert elements[1].text().split('/')[2].split(' ')[0] == year
        assert elements[3].text() == school
    }
}