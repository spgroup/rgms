package pages

import geb.Page

class BookChapterPage extends Page{

    static url = "bookChapter/list"

    static at = {
        title ==~ /BookChapter Listagem/
    }

    static content = {

    }

    def selectNewBookChapter() {
        $('a.create').click()
    }
}