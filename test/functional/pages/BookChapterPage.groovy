package pages

import geb.Page
import rgms.publication.BookChapter

class BookChapterPage extends Page {
    static url = "bookChapter/list"

    static at = {
        //title ==~ /Capítulo de livro Listagem/

        GetPageTitle gp = new GetPageTitle()
        def currentBookChapter = gp.getMessageServerLocale("default.bookchapter.label")
        def currentTitle = currentBookChapter + " " + gp.getMessageServerLocale("default.button.list.label")

        title ==~ currentTitle
    }

    static content = {

    }

    def selectNewBookChapter() {
        $('a.create').click()
    }

    def checkBookChapterAtList(String title, row) {
        def listDiv = $('div', id: 'list-bookChapter')
        def bookTable = (listDiv.find('table'))[0]
        def bookRows = bookTable.find('tbody').find('tr')
        //noinspection GroovyAssignabilityCheck
        def bookColumns = bookRows[row].find('td')

        def testarbook = BookChapter.findByTitle(title)
        println testarbook.file;
        println bookColumns[2].text();
        assert bookColumns[0].text() == testarbook.title
        assert bookColumns[2].text() == testarbook.file
        assert bookColumns[4].text() == testarbook.publisher
    }

    def uploadWithoutFile() {
        $('input.save').click()
    }

    def checkIfBookChapterListIsEmpty() {
        def listDiv = $('div', id: 'list-bookchapter')
        def bookChapterTable = (listDiv.find('table'))[0]
        def bookChapterRows = bookChapterTable.find('tbody').find('tr')
        def bookChapterColumns = bookChapterRows[0].find('td')

        assert bookChapterColumns.size() == 0
    }

    def hasErrorUploadFile() {
        GetPageTitle gp = new GetPageTitle()
        return gp.msg('file.already.exist.message') == $("div", class: "message", role: "status").text()
    }
}
