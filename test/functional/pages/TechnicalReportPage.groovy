package pages

import geb.Page
import rgms.publication.TechnicalReport

class TechnicalReportPage extends Page {
    static url = "technicalReport/list"

    static at = {
        title ==~ /Technical Report Listagem/
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

    def checkTechincalReportAtList(title, row) {
        def listDiv = $('div', id: 'list-technicalReport')
        def techReportTable = (listDiv.find('table'))[0]
        def techReportRows = techReportTable.find('tbody').find('tr')
        def techReportColumns = techReportRows[row].find('td')

        def testTechReport = TechnicalReport.findByTitle(title)
        assert techReportColumns[0].text() == testTechReport.title
        assert techReportColumns[2].text() == testTechReport.file
        assert techReportColumns[4].text() == testTechReport.institution
    }
}
