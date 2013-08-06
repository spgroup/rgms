package pages

import geb.Page
import rgms.publication.BookChapter

class BookChapterPage extends Page {

    static url = "bookChapter/list"

    static at = {
        //title ==~ /BookChapter Listagem/
    }

    static content = {

    }

    def selectNewBookChapter() {
        $('a.create').click()
    }
    def checkBookChapterAtList(title,row){
        def listDiv = $('div', id: 'list-bookChapter')
        def bookTable = (listDiv.find('table'))[0]
        def bookRows  = bookTable.find('tbody').find('tr')
        def bookColumns = bookRows[row].find('td')

        def testarbook = BookChapter.findByTitle(title)
        println testarbook.file;
        println bookColumns[2].text();
        assert bookColumns[0].text() == testarbook.title
        assert bookColumns[2].text() == testarbook.file
        assert bookColumns[4].text() == testarbook.publisher
    }
}