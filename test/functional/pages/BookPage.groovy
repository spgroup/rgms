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

    def selectOrderBy(sortType){
        switch (sortType) {
            case 'title':
                $('a[href="/rgms/book/list?sort=title&max=10&order=asc"]').click()
                break
            case 'publication date':
                $('a[href="/rgms/book/list?sort=publicationDate&max=10&order=asc"]').click()
                break
        }
    }

    def checkOrderedBy(sortType){
        def firstBookColumns 	= this.getBookColumns(0)
        def secondBookColumns 	= this.getBookColumns(1)
        switch (sortType) {
            case 'title':
                assert firstBookColumns[0].text().compareTo(secondBookColumns[0].text()) < 0
                break
            case 'publication date':
                assert firstBookColumns[1].text().compareTo(secondBookColumns[1].text()) < 0
                break
        }
    }

    def getBookColumns(row){
        def listDiv = $('div', id: 'list-book')
        def bookTable = (listDiv.find('table'))[0]
        def bookRows = bookTable.find('tbody').find('tr')
        def bookColumns = bookRows[row].find('td')
        return bookColumns
    }
}