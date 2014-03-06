package pages

/**
 * Created with IntelliJ IDEA.
 * User: dyego
 * Date: 23/02/14
 * Time: 10:05
 * To change this template use File | Settings | File Templates.
 */

import geb.Page
import rgms.publication.Book

class BookPage extends Page {
    static url = "book/list"

    static at = {
        //title ==~ /Livro Listagem/

        GetPageTitle gp = new GetPageTitle()
        def currentReport = gp.msg("default.book.label")
        def currentTitle = gp.msg("default.list.label", [currentReport])
        title ==~ currentTitle
    }

    static content = {

    }

    def selectNewBook() {
        $('a.create').click()
    }
}