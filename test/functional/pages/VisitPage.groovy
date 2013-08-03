package pages

import geb.Page
import rgms.visit.Visit
import rgms.visit.Visitor
import steps.TestDataAndOperations


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

    def selectViewVisit(name, initialDate, finalDate) {
        def listDiv = $('div', id: 'list-visit')
        def visitTable = (listDiv.find('table'))[0]
        def visitRow  = visitTable.find('tbody').find('tr')
        def testVisit = TestDataAndOperations.findVisit(name, initialDate, finalDate)
        def dataInicio = testVisit.dataInicio[0].format('dd/MM/yyyy')
        def showLink = visitRow.find('td').find([text:dataInicio])
        showLink.click()
    }

    /**
     * @author carloscemb
     */
    def checkVisitAtList(name, initialDate, finalDate, row){
        def listDiv = $('div', id: 'list-visit')
        def visitTable = (listDiv.find('table'))[0]
        def visitRows  = visitTable.find('tbody').find('tr')
        def visitColumns = visitRows[row].find('td')

        def testVisit = TestDataAndOperations.findVisit(name, initialDate, finalDate)

        assert testVisit != null
        assert visitColumns[0].text() == testVisit.dataInicio[0].format('dd/MM/yyyy')
        assert visitColumns[1].text() == testVisit.dataFim[0].format('dd/MM/yyyy')
        assert visitColumns[2].text() == testVisit.visitor[0].name
    }
}

