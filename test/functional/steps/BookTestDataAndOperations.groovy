package steps

/**
 * Created with IntelliJ IDEA.
 * User: dyego
 * Date: 22/02/14
 * Time: 15:37
 * To change this template use File | Settings | File Templates.
 */

import rgms.publication.BookController
import org.springframework.web.context.request.RequestContextListener

class BookTestDataAndOperations {
    static books = [
            [title: "Livro de Teste", publicationDate: (new Date("12 October 2012")),
                    publisher: "Person", volume: 1, pages: "20"],
            [title: "SPL Development", publicationDate: (new Date("12 October 2012")),
                    publisher: "Addison", volume: 2, pages: "541"],
            [title: "Software Engineering", publicationDate: (new Date("25 July 2012")),
                    publisher: "Penguim", volume: 3, pages: "272"]
    ]

    static public def findBookByTitle(String title) {
        books.find { book ->
            book.title == title
        }
    }

    static public void createBook(String title, String filename) {
        def cont = new BookController()
        cont.params << findBookByTitle(title) << [file: filename]
        cont.request.setContent(new byte[1000])
        cont.create()
        cont.save()
        cont.response.reset()
    }

    static public boolean bookCompatibleTo(book, String title) {
        def testBook = findBookByTitle(title)
        def compatible = false
        if (testBook == null && book == null) {
            compatible = true
        } else if (testBook != null && book != null) {
            compatible = true
            testBook.each { key, data ->
                compatible = compatible && (book."$key" == data)
            }
        }
        return compatible
    }
}
