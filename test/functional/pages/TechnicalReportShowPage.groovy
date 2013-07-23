package pages

import geb.Page

class TechnicalReportShowPage extends Page {
    static url = "technicalReport/show/2"

    static at = {
        title ==~ /Ver Relatório Técnico/
    }

    static content = {
    }

    def select(String e, v) {
        if (v == 'edit') {
            $("form").find(e, class: v).click()
        } else if (v == 'delete') {
            assert withConfirm(true) { $("form").find(e, class: v).click() }
        }
    }
}
