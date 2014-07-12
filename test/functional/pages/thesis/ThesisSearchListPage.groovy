package pages.thesis

import geb.Page
import pages.GetPageTitle
import rgms.publication.Tese

class ThesisSearchListPage extends Page {

    static url = "tese/searchList"

    static at = {
        GetPageTitle gp = new GetPageTitle()
        def currentTese = gp.msg("default.tese.label")
        def currentTitle = gp.msg("default.list.label", [currentTese])
        title ==~ currentTitle
    }

    def getRow() {
        def listDiv = $('div', id: 'list-tese')
        def thesisTable = (listDiv.find('table'))[0]
        def thesisRow = thesisTable.find('tbody').find('tr')
        return thesisRow
    }

    def selectViewThesis(title) {
        def showLink = getRow().find('td').find([text: title])
        showLink.click()
    }

    def checkIfThesisWasFound(title) {
        def thesis = getRow().find('td').find([text: title])
        assert thesis != null
    }
}
