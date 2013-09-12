package pages.Conferencia

import geb.Page

class ConferenciaPage extends Page{

    static url = "conferencia/list"

    static at = {
        title ==~ /Conferencia Listagem/
    }

    static content = {

    }

    def selectNewConferencia() {
        $('a.create').click()
    }
	
    def listConferencia() {
        $('a.list').click()
    }

    def selectHome() {
        $('a.home').click()
    }

    def removeConferencia() {
        $('a.delete').click()
    }

    def checkIfConferenciaListIsEmpty(){
        def listDiv = $('div', id: 'list-conferencia')
        def conferenciaTable = (listDiv.find('table'))[0]
        def conferenciaRows  = conferenciaTable.find('tbody').find('tr')
        def conferenciaColumns = conferenciaRows[0].find('td')

        assert conferenciaColumns.size() == 0
    }
}