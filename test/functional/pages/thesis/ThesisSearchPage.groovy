package pages.thesis

import pages.FormPage
import pages.GetPageTitle

class ThesisSearchPage extends FormPage {
    static url = "tese/search"

    static at = {
        GetPageTitle gp = new GetPageTitle()
        def currentTese = gp.msg("default.tese.label")
        def currentTitle = gp.msg("default.search.label", [currentTese])
        title ==~ currentTitle
    }

    def fillThesisSearchDetails(title, year, school) {
        $("form").title = title
        $("form").publicationDate_year = year
        $("form").school = school
    }

    def searchTheses() {
        $("input", name: "search").click()
    }
}
