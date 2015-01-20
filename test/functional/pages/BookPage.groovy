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
}
