package pages.ferramenta

import geb.Page
import rgms.publication.Ferramenta

class BookShowPage extends Page{
    static url = "book/show"

    static at = {
        title ==~ /Ver Book/
    }

    static content = {
    }

    def select(String s){
        $('a.' + s).click();
    }

    def editBook(){
        $('a.edit').click()
    }

    def deleteBook(){
        assert withConfirm(true) {
            $('input.delete').click()
        }
    }

    def checkBookTitle(title){
        def listInformations = $('ol', class : 'book')
        def rowTitle = (listInformations.find('li'))[0]
        def titleBook = rowTitle.find('span', class: 'property-value')

        def editedBook = Book.findByTitle(title)
        assert titleBook.text() == editedBook.title
    }
}
