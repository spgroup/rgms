package pages

import geb.Page
import rgms.publication.Tese

/**
 * @author carloscemb
 */
class ThesisPage extends Page {
    static url = "tese/list"

    static at = {
        //title ==~ /Tese Listagem/
        GetPageTitle gp = new GetPageTitle()
        def currentTese = gp.msg("default.tese.label")
        def currentTitle = gp.msg("default.list.label", [currentTese])
        title ==~ currentTitle
    }

    static content = {
        flashmessage {
            $("div", class: "message")
        }
    }

    def selectNewThesis() {
        $('a.create').click()
    }

    def getRow() {

        def listDiv = $('div', id: 'list-tese')
        def thesisTable = (listDiv.find('table'))[0]
        def thesisRow = thesisTable.find('tbody').find('tr')
        return thesisRow
    }

    def selectViewThesis(title) {
        //getRow()
        def showLink = getRow().find('td').find([text: title])
        showLink.click()
    }

    def checkTeseAtList(title, row) {
        def teseColumns = getRow()[row].find('td')

        def testtese = Tese.findByTitle(title)
        assert teseColumns[1].text() == testtese.title
        assert teseColumns[5].text() == testtese.school
    }

}
