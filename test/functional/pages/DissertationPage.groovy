package pages

import geb.Page

class DissertationPage extends Page {
    static url = "dissertacao/list"

    static at = {
       // title ==~ /Dissertacao Listagem/
        GetPageTitle gp = new GetPageTitle()
        def currentPeriodico = gp.msg("default.dissertacao.label")
        def currentTitle = gp.msg("default.list.label", [currentPeriodico])
        title ==~ currentTitle

    }

    static content = {
    }

    def selectNewArticle() {
        $('a.create').click()
    }

    def selectDissertation(String s) {
        $('a', text: s).click()
    }

    def selectOrderByTitle(String s)
    {
        $('a', href: s).click()
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
    def getDissertationColumns(row){
        def listDiv = $('div', id: 'list-dissertacao')
        def dissertationTable = (listDiv.find('table'))[0]
        def dissertationRows = dissertationTable.find('tbody').find('tr')
        def dissertationColumns = dissertationRows[row].find('td')
        return dissertationColumns
    }
    def checkOrderedBy(sortType){
        def firstDissertationColumns 	= this.getDissertationColumns(0)
        def secondDissertationColumns 	= this.getDissertationColumns(1)
        switch (sortType) {
            case 'title':
                assert firstDissertationColumns[0].text().compareTo(secondDissertationColumns[0].text()) < 0
                break
            case 'publication date':
                assert firstDissertationColumns[1].text().compareTo(secondDissertationColumns[1].text()) < 0
                break
        }
    }
}
