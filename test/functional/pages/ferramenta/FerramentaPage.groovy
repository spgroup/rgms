package pages.ferramenta

import geb.Page
import rgms.publication.Ferramenta

class FerramentaPage extends Page {
    static url = "ferramenta/list"

    static at = {
        title ==~ /Ferramenta Listagem/
    }

    static content = {
    }

    def select(String s){
        $('a.' + s).click();
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
        def ferramentaTable = (listDiv.find('table'))
        def ferramentaRows  = ferramentaTable.find('tbody').find('tr')
        def ferramentaColumns = ferramentaRows[1].find('td')

        assert ferramentaColumns[1].text() == name
    }

    def checkFerramentaList(String name1, name2){
        def list = $('div', id: 'list-ferramenta').find('table').find('tbody').find('tr')
        int exist = 0
        def listWeb = []
        for(int i = 0; i < list.size(); i++){
            def title =  list[i].find('td')[0].text()
            def file =  list[i].find('td')[2].text()
            def website = list[i].find('td')[4].text()
            def f = new Ferramenta()
            f.website = website
            f.file = file
            f.title = title
            listWeb.add(i,f)
            if(title == name1 || title == name2) exist++
        }

        def listSystem = Ferramenta.findAll()
        assert listWeb == listSystem && exist == 2
    }
}
