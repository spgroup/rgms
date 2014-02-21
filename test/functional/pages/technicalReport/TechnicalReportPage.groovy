package pages.technicalReport

import geb.Page

class TechnicalReportPage extends Page {
    static url = "technicalReport/list"

    static at = {
        title ==~ /Relatorio Tecnico Listagem/
    }

    static content = {
    }


    def selectNewTechnicalReport() {
        $('a.create').click()
    }

    def selectViewTechnicalReport(title) {
        def listDiv = $('div', id: 'list-technicalReport')
        def techTable = (listDiv.find('table'))[0]
        def techRow = techTable.find('tbody').find('tr')
        def showLink = techRow.find('td').find([text: title])
        showLink.click()
    }
}
