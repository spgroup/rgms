package pages

import geb.Page
import rgms.publication.BookChapter

class BookChapterPage extends Page {
    static url = "bookChapter/list"

    static at = {
        //title ==~ /Capítulo de livro Listagem/

        GetPageTitle gp = new GetPageTitle()
        def currentBookChapter = gp.msg("default.bookchapter.label")
        def currentTitle = gp.msg("default.list.label", [currentBookChapter])

        title ==~ currentTitle
    }

    static content = {

    }

    def selectNewBookChapter() {
        $('a.create').click()
    }

    def uploadWithoutFile(){
        $('input.save').click()
    }

    def checkIfBookChapterListIsEmpty(){
        def listDiv = $('div', id: 'list-bookchapter')
        def bookChapterTable = (listDiv.find('table'))[0]
        def bookChapterRows  = bookChapterTable.find('tbody').find('tr')
        def bookChapterColumns = bookChapterRows[0].find('td')

        assert bookChapterColumns.size() == 0
    }
}