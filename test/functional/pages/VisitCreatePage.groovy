package pages

import geb.Page

class VisitCreatePage extends Page {
    static url = "visit/create"

    static at = {
        //title ==~ /Criar Visita/
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
        $("form").nameVisitor = "Visitante"
        $("form").descricao = "Primeira Visita"

        clickOnCreate()
    }
}