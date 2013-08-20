package pages

import geb.Page

class VisitCreatePage extends Page {
    static url = "visit/create"

    static at = {
        //title ==~ /Criar Visit/
        GetPageTitle gp = new GetPageTitle()
        def currentVisit = gp.msg("default.visit.label")
        def currentTitle = gp.msg("default.create.label", [currentVisit])

        title ==~ currentTitle
    }

    static content = {
    }

    def clickOnCreate() {
        $('input.save').click()
    }

    def fillVisitDetails() {
        $("form").nameVisitor = "Visitor"
        $("form").description = "First Visit"

        clickOnCreate()
    }
}