package pages

import geb.Page

class VisitEditPage extends Page {
    static url = "visit/edit/1"

    static at = {
        //title ==~ /Editar Visita/
        GetPageTitle gp = new GetPageTitle()
        def currentVisit = gp.msg("default.visit.label")
        def currentTitle = gp.msg("default.edit.label", [currentVisit])

        title ==~ currentTitle
    }

    static content = {
    }

    def edit() {
        $("form").nameVisitor += " updated"
    }

    def select(String s) {
        $("form").find("input", value: s).click()
    }
}