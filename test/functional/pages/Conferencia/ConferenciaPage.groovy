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

    def select(String s) {
		$('div', id: 'status').find('a', text: s).click()
	}
    
    //verificar pag da tabela
     def selectColumn(String s) {
		$('div', id: 'column').find('a', text: s).click()
	}
        
    def fillDateField(String date){
        $("form").date = date
    }
    
    def selectSearchConferencia(){
        $('a.search').click()
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
    
    def checkIfConferenciaListIsOrderedByTitle(){
         def listDiv = $('div', id: 'list-conferencia')
        def conferenciaTable = (listDiv.find('table'))[0]
        def conferenciaRows  = conferenciaTable.find('tbody').find('tr')
        
        def list = []
        def y = conferenciaRows.size()
        def x = 0
        
        while(y-- > 0){
            def conferenciaColumns = conferenciaRows[x].find('td')
            list.add(articleColumns[0].text())
            x++
        }
        def list2 = list.clone()
        list2.sort(it.title)
        assert list == list2
    }
    
    def checkIfConferenciaListIsOrderedByDate(){
         def listDiv = $('div', id: 'list-conferencia')
        def conferenciaTable = (listDiv.find('table'))[0]
        def conferenciaRows  = conferenciaTable.find('tbody').find('tr')
        
        def list = []
        def y = conferenciaRows.size()
        def x = 0
        
        while(y-- > 0){
            def conferenciaColumns = conferenciaRows[x].find('td')
            list.add(articleColumns[1].text())
            x++
        }
        def list2 = list.clone()
        list2.sort(it.date)
        assert list == list2
    }
}