package pages.visit

import geb.Page
import pages.GetPageTitle
import steps.TestDataAndOperationsVisit


class VisitPage extends Page {
    static url = "visit/list"

    static at = {
        //title ==~ /Visita Listagem/
        GetPageTitle gp = new GetPageTitle()
        def currentVisit = gp.msg("default.visit.label")
        def currentTitle = gp.msg("default.list.label", [currentVisit])

        title ==~ currentTitle    
    }

    static content = {
    }

    def selectNewVisit() {
        $('a.create').click()
    }

    /**
     * @author carloscemb
     */
    def getVisitRows() {
        def listDiv = $('div', id: 'list-visit')
        def visitTable = (listDiv.find('table'))[0]
        def visitRows  = visitTable.find('tbody').find('tr')
        return visitRows
    }

    def selectViewVisit(name, initialDate, finalDate) {
        def visitRows = getVisitRows()
        def testVisit = TestDataAndOperationsVisit.findVisit(name, initialDate, finalDate)
        def dataInicio = testVisit.initialDate[0].format('dd/MM/yyyy')
        def showLink = visitRows.find('td').find([text:dataInicio])
        showLink.click()
    }

    /**
     * @author carloscemb
     */
    def checkVisitAtList(name, initialDate, finalDate, row){
        def visitRows = getVisitRows()
        def visitColumns = visitRows[row].find('td')

        def testVisit = TestDataAndOperationsVisit.findVisit(name, initialDate, finalDate)

        assert testVisit != null
        assert visitColumns[0].text() == testVisit.initialDate[0].format('dd/MM/yyyy')
        assert visitColumns[1].text() == testVisit.finalDate[0].format('dd/MM/yyyy')
        assert visitColumns[2].text() == testVisit.visitor[0].name
    }
}

