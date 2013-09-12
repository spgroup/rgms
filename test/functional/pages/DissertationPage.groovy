package pages

import geb.Page

class DissertationPage extends Page {
    static url = "dissertacao/list"

    static at = {
        title ==~ /Dissertacao Listagem/
    }

    static content = {
    }

    def selectNewArticle() {
        $('a.create').click()
    }

    def selectDissertation(String s) {
        $('a', text: s).click()
    }
    def uploadWithoutFile(){
        $('input.save').click()
    }

    def checkIfDissertationListIsEmpty(){
        def listDiv = $('div', id: 'list-dissertacao')
        def dissertationTable = (listDiv.find('table'))[0]
        def dissertationRows  = dissertationTable.find('tbody').find('tr')
        def dissertationColumns = dissertationRows[0].find('td')

        assert dissertationColumns.size() < 6
    }
}
