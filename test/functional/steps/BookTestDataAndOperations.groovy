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

    static public void createBook(String title, filename) {
        createBook(title, filename, null)
    }

    static public void createBook(String title, filename, authorName) {
        def cont = new BookController()
        cont.params << BookTestDataAndOperations.findBookByTitle(title) << [file: filename]
        if(authorName!=null){
            cont.params["authors"] = authorName

        }
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

    static public boolean containsBook(title, books) {
        def testBook = Book.findByTitle(title)
        def cont = new BookController()
        def result = cont.list().bookInstanceList
        return result.contains(testBook)
    }

    static public def isSorted(books,sortType) {
        def isSorted = false
        switch (sortType) {
            case 'title':
                isSorted = (books.size() < 2 || (1..<books.size()).every { (books[it - 1].title).compareTo(books[it].title) < 0})
                break
        }
        return isSorted
    }

    static public List<Book> findAllByAuthor(authorName) {
        def cont = new BookController()
        cont.params << [authorName: authorName]
        return cont.filterByAuthor(2, authorName)
    }

    static public def isFiltered(books,authorName) {
        for (book in books) {
            if(!(book.authors).contains(authorName))
                return false
        }
        return true
    }

    static public void removeMultiplesBooks(String title1, String title2) {
        def testBook1 = Book.findByTitle(title1)
        def testBook2 = Book.findByTitle(title2)
        def cont = new BookController()
        cont.params << [id: testBook1.id, id2: testBook2.id]
        cont.deleteMultiples(testBook1.id, testBook2.id)
    }
}
