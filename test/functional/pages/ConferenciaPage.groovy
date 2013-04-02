package pages

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
}