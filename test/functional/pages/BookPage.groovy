package pages

import geb.Page

/**
 * Created with IntelliJ IDEA.
 * User: dyego
 * Date: 23/02/14
 * Time: 10:05
 * To change this template use File | Settings | File Templates.
 */
class BookPage extends Page {
    static url = "book/list"

    static at = {
        //title ==~ /Livro Listagem/

        GetPageTitle gp = new GetPageTitle()
        def currentBook = gp.getMessageServerLocale("default.book.label")
        def currentTitle = currentBook + " " + gp.getMessageServerLocale("default.button.list.label")

        title ==~ currentTitle
    }

    static content = {

    }

    def selectNewBook() {
        $('a.create').click()
    }
    
    def checkDownloadLink(String filename){
        return $('a', text:filename) != null
    }
    
    def selectOrderBy(String orderType){
        $('a', text: orderType).click()
    }

    def checkOrderedBy(String orderType){
        def header = $('table').find('thead').find('tr').find('td')
        def rows = $('table').find('tbody').find('tr')
        def dates = []

        // Recupera o indice da coluna relativo ao tipo de ordenacao
        def index = 1
        def found = false
        for(td in header){
            if(!found){
                if(td.text() == orderType){
                    found = true;
                }
                index++;
            }
        }

        // Adiciona em uma lista para verificar se essa lista é identica a lista ordenada
        for(row in rows){
            dates.add(row.find('td:nth-child(' + index + ')').text())
        }

        return dates == dates.sort()
    }
}
