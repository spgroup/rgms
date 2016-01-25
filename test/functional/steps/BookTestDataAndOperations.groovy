package steps

/**
 * Created with IntelliJ IDEA.
 * User: dyego
 * Date: 22/02/14
 * Time: 15:37
 * To change this template use File | Settings | File Templates.
 */
import rgms.publication.Book
import rgms.publication.BookController
import rgms.publication.XMLController

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

    static public void removeBook(String title) {
        def testBook = Book.findByTitle(title)
        def cont = new BookController()
        cont.params << [id: testBook.id]
        cont.delete(testBook.id)
    }

    static public Book editBook(oldtitle, newtitle) {
        def book = Book.findByTitle(oldtitle)
        book.setTitle(newtitle)
        def cont = new BookController()
        cont.params << book.properties
        cont.update()

        def updatedBook = Book.findByTitle(newtitle)
        return updatedBook
    }

    static public void uploadBook(String filename) {
        def cont = new XMLController()
        String path = "test" + File.separator + "functional" + File.separator + "steps" + File.separator + filename
        def xml = new File(path);
        def records = new XmlParser()
        cont.saveBook(records.parse(xml));
        cont.response.reset()
    }

    //scc
    static public int nullOrNot(def testBook, Book book) {
        if(testBook == null && book == null) {
            return 0;
        } else if (testBook != null && book != null) {
            return 1;
        } else {
            return 2;
        }
    }

    //scc
    static public boolean bookCompatibleTo(book, String title) {
        def testBook = findBookByTitle(title)
        def compatible = false
        if (nullOrNot(testBook, book) == 0) {
            //testBook == null && book == null
            compatible = true
        } else if (nullOrNot(testBook, book) == 1) {
            //testBook != null && book != null
            compatible = true
            testBook.each { key, data ->
                compatible = compatible && (book."$key" == data)
            }
        }
        return compatible
    }
}
