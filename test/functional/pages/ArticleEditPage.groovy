package pages

import geb.Page

class ArticleEditPage extends Page {
    static url = "periodico/edit/1"

    static at = {
        //title ==~ /Editar Peri�dico/
        GetPageTitle gp = new GetPageTitle()
        def currentPeriodico = gp.msg("default.periodico.label")
        def currentTitle = gp.msg("default.edit.label", [currentPeriodico])

        title ==~ currentTitle
    }

    def edit(String novovalor) {
        $("form").title = novovalor
    }

    def select(String s) {
        $("form").find("input", value: s).click()
    }
}
