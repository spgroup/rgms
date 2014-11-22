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

    /**
     * @author droa
     * BEGIN
     */

    def selectNewBook() {
        $('a.create').click()
    }

    def selectBook(String title) {
        def listDiv = $('div', id: 'list-book')
        def articleTable = (listDiv.find('table'))[0]
        def articleRow = articleTable.find('tbody').find('tr')
        def showLink = articleRow.find('td').find([text: title])
        showLink.click()
    }

    /* END */
}