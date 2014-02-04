package pages.thesis

import pages.FormPage
import pages.GetPageTitle

class ThesisCreatePage extends FormPage {
    static url = "tese/create"

    static at = {
        GetPageTitle gp = new GetPageTitle()
        def currentTese = gp.msg("default.tese.label")
        def currentTitle = gp.msg("default.create.label", [currentTese])
        title ==~ currentTitle
    }

    static content = {
        /*journal {
            $("input", id: "journal")
        }*/
    }

    def fillThesisDetails(title, pub_day, pub_month, pub_year, school, address, path) {
        $("form").file = path
        fillSomeThesisDetails(title, pub_day, pub_month, pub_year, school, address)
    }

    def fillSomeThesisDetails(title, pub_day, pub_month, pub_year, school, address) {
        $("form").title = title
        $("form").publicationDate_day = pub_day
        $("form").publicationDate_month = pub_month
        $("form").publicationDate_year = pub_year
        $("form").school = school
        $("form").address = address
        $("input", id: "create").click()
    }

    def currentSchool() {
        $("form").school
    }

    def selectCreateThesis() {
        $("input", name: "create", class: "save").click()
    }
}
