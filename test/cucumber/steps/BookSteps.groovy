/**
 * Created with IntelliJ IDEA.
 * User: dyego
 * Date: 22/02/14
 * Time: 15:30
 * To change this template use File | Settings | File Templates.
 */

import pages.BookCreatePage
import pages.BookPage
import pages.BookShowPage
import pages.BookEditPage
import pages.LoginPage
import pages.PublicationsPage
import rgms.publication.Book
import steps.BookTestDataAndOperations

import static cucumber.api.groovy.EN.*

Given(~'^the system has no book entitled "([^"]*)"$') { String title ->
    checkIfExists(title)
}

When(~'^I create the book "([^"]*)" with file name "([^"]*)"$') { String title, filename ->
    BookTestDataAndOperations.createBook(title, filename)
}

Then(~'^the book "([^"]*)" is properly stored by the system$') { String title ->
    book = BookTestDataAndOperations.findBookByTitle(title)
    assert BookTestDataAndOperations.bookCompatibleTo(book, title)
}

//beginning of @gml's $updateExistingBook steps

Given(~'^the book "([^"]*)" is stored in the system with file name "([^"]*)"$') { String title, String filename ->
    BookTestDataAndOperations.createBook(title, filename)
    book = Book.findByTitleAndFile(title, filename)
    assert BookTestDataAndOperations.bookCompatibleTo(book, title)
}

Then(~'^the book "([^"]*)" has the archive file "([^"]*)" updated by the system$') { String title, String filename ->
    assert book.file.equals(filename)
}

When (~'^I edit the book file name from "([^"]*)" to "([^"]*)"$') { String oldFile, newFile ->
    BookTestDataAndOperations.editFile(oldFile, newFile)
}

When (~'^I edit the book "([^"]*)" file name from "([^"]*)" to "([^"]*)"$'){ String title, oldFilename, newFilename ->
    to BookPage
    page.selectNewBook()
    at BookCreatePage
    createAndCheckBookOnBrowser(title, oldFilename)
    to BookPage
    at BookPage

    page.selectViewBook(title)
    //to BookShowPage
    at BookShowPage
    page.select('input', 'edit')
    to BookEditPage
    //book.setFile(newFilename)
    page.editFilename(newFilename)
    System.out.print("Edited to: " + book.file)
    page.clickSaveBook()
}

Then(~'^I have the book entitled "([^"]*)" with file name "([^"]*)" stored on the system$') { String title, String filename ->
    book = Book.findByTitle(title)
    System.out.print(book.file)
    assert book != null
}


When(~'^I upload the file in the system with name "([^"]*)"$') { String fileName ->
    book.setFile(fileName)
    assert Book.findByFile(fileName)
}

//@gml's $bookWithoutFile steps

When (~'^I try to create the book "([^"]*)" without file$'){String title ->
    !checkIfExists(title)
}

Then(~'^the book "([^"]*)" is not stored by the system$') {String title ->
    !checkIfExists(title)
}

Then (~'I should see an error message containing "([^"]*)"$') {
    assert book == null
    assert (page.readFlashMessage() != null)

}
//end of @gml tests

When(~'^I remove the book "([^"]*)"$') { String title ->
    BookTestDataAndOperations.removeBook(title)
}

Then(~'^the book "([^"]*)" is properly removed by the system$') { String title ->
    checkIfExists(title)
}

Then(~'^the book "([^"]*)" is not stored twice$') { String title ->
    books = Book.findAllByTitle(title)
    assert books.size() == 1
}

When(~'^I edit the book title from "([^"]*)" to "([^"]*)"$') { String oldtitle, newtitle ->
    def updatedBook = BookTestDataAndOperations.editBook(oldtitle, newtitle)
    assert updatedBook != null
}

Then(~'^the book "([^"]*)" is properly updated by the system$') { String title ->
    assert Book.findByTitle(title) != null
}

Given(~'^the system has no books stored$') { ->
    initialSize = Book.findAll().size()
    assert initialSize == 0
}

When(~'^I upload the books of "([^"]*)"$') { filename ->
    initialSize = Book.findAll().size()
    BookTestDataAndOperations.uploadBook(filename)
    finalSize = Book.findAll().size()
    assert initialSize < finalSize
}

Then(~'^the system has all the books of the xml file$') { ->
    assert checkIfExists("Proceedings of the IV Brazilian Symposium on Programming Languages")
    assert checkIfExists("Testing Techniques in Software Engineering")
    assert checkIfExists("Proceedings of the XXIII Brazilian Symposium on Software Engineering")
    assert checkIfExists("Anais da IV ConferÃªncia Latina-Americana em Linguagens de PadrÃµes para ProgramaÃ§Ã£o (SugarLoafPLoP 2004)")
    assert checkIfExists("AOSD 2011 Proceedings and Companion Material")
}

Given(~'^I am at the book page$') { ->
    to LoginPage
    at LoginPage
    page.fillLoginData("admin", "adminadmin")
    at PublicationsPage
    to BookPage
}

When(~'^I go to new book page$') { ->
    to BookPage
    page.selectNewBook()
}
And(~'^I use the webpage to create the book "([^"]*)" with file name "([^"]*)"$') { String title, filename ->
    at BookCreatePage
    createAndCheckBookOnBrowser(title, filename)
    to BookPage
    at BookPage
}

Then(~'^the book "([^"]*)" was stored by the system$') { String title ->
    checkIfExists(title)
    to BookPage
    at BookPage
}

When(~'^I choose to view "([^"]*)" in book list$') { String title ->
    page.selectViewBook(title)
    at BookShowPage
}

And(~'^I press to remove at the book show page$') {->
    at BookShowPage
    page.select('input', 'delete')
}

Then(~'^the book list contains" ([^"]*)"$') { String title ->
    at BookPage
    page.checkArticleAtList(title, 0)
}


def checkIfExists(String title) {
    book = Book.findByTitle(title)
    return book != null
}

def createAndCheckBookOnBrowser(String title, String filename) {
    page.fillBookDetails(title, filename)
    page.clickSaveBook()
    checkIfExists(title)
}
