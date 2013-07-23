package pages

import geb.Page

class TechnicalReportEditPage extends Page {
    static url = "technicalReport/edit/2"

    static at = {
        title ==~ /Editar Relatorio Tecnico/
    }

    static content = {
    }


    def edit(String novovalor) {
        $("form").title = novovalor
    }

    def select(String s) {
        if (s == "Alterar")
            $("form").find("input", value: s).click()
        else
            assert withConfirm(true) { $("form").find("input", value: s).click() }
    }
}
