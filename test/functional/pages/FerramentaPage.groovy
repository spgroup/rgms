package pages

import geb.Page

class FerramentaPage extends Page {
    static url = "ferramenta/list"

    static at = {
        title ==~ /Ferramenta Listagem/
    }

    static content = {
    }

    def selectNewFerramenta() {
        $('a.create').click()
    }

    def selectFerramenta(String s) {
        $('a', text: s).click()
    }
    def uploadWithoutFile(){
        $('input.save').click()
    }

    def checkAnyFerramentaAtList(){
        checkFirstFerramentaNameAtList(null)
    }

    def checkFirstFerramentaNameAtList(name){
        def listDiv = $('div', id: 'list-ferramenta')
        def ferramentaTable = (listDiv.find('table'))[0]
        def ferramentaRows  = ferramentaTable.find('tbody').find('tr')
        def ferramentaColumns = ferramentaRows[1].find('td')

        assert ferramentaColumns[1].text() == name
    }


}