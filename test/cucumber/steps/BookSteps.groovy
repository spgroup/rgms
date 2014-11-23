/**
 * Created with IntelliJ IDEA.
 * User: dyego
 * Date: 22/02/14
 * Time: 15:30
 * To change this template use File | Settings | File Templates.
 */

import pages.BookCreatePage
import pages.BookPage
import pages.LoginPage
import pages.PublicationsPage
import rgms.publication.Book
import steps.BookTestDataAndOperations

import static cucumber.api.groovy.EN.*

Given(~'^the system has no book entitled "([^"]*)"$') { String title ->
    checkIfExists(title)
}

When(~'^I create the book "([^"]*)" with file name "([^"]*)" and description "([^"]*)"$') { String title, description, filename ->
    BookTestDataAndOperations.createBook(title, description, filename)
}

Then(~'^the book "([^"]*)" is properly stored by the system$') { String title ->
    book = Book.findByTitle(title)
    assert BookTestDataAndOperations.bookCompatibleTo(book, title)
}

Given(~'^the book "([^"]*)" is stored in the system with file name "([^"]*)"$') { String title, String filename ->
    BookTestDataAndOperations.createBook(title, filename)
    book = Book.findByTitle(title)
    assert BookTestDataAndOperations.bookCompatibleTo(book, title)
}

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

When(~'^I edit the book title from "([^"]*)" to "([^"]*)" and description from "([^"]*)" to "([^"]*)"$') { String oldtitle, newtitle, olddescription, newdescription ->
    def updatedBook = BookTestDataAndOperations.editBook(oldtitle, newtitle, olddescription, newdescription)
    assert updatedBook != null
    assert newdescription = null
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
    assert Book.findByTitle("Proceedings of the IV Brazilian Symposium on Programming Languages") != null
    assert Book.findByTitle("Testing Techniques in Software Engineering") != null
    assert Book.findByTitle("Proceedings of the XXIII Brazilian Symposium on Software Engineering") != null
    assert Book.findByTitle("Anais da IV ConferÃªncia Latina-Americana em Linguagens de PadrÃµes para ProgramaÃ§Ã£o (SugarLoafPLoP 2004)") != null
    assert Book.findByTitle("AOSD 2011 Proceedings and Companion Material") != null
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

And(~'^I use the webpage to create the book "([^"]*)" with file name "([^"]*)" and description "([^"]*)"$') { String title, description, filename ->
    at BookCreatePage
    createAndCheckBookOnBrowser(title, description, filename)
    to BookPage
    at BookPage
}

Then(~'^the book "([^"]*)" was stored by the system$') { String title ->
    book = Book.findByTitle(title)
    assert book != null
    to BookPage
    at BookPage
}

When(~'I select the option to remove the book "([^"]*)"$') {->
	at BookPage
	page.select('input', 'delete')
}

Then(~'^the system removes the book "([^"]*)"$') { String title ->
	assert bookNoExist(title)
}

When(~'^I view the book list$') { ->
    to BookPage
}

Then(~'my book list contains "([^"]*)"$') { String title ->
    at BookPage
    bookList = Book.findAll()
    assert BookTestDataAndOperations.containsBook(title)
}
And(~'^the book chapter "([^"]*)" with file name "([^"]*)" was created before$') { String title, filename ->
    page.selectNewBook()
    to BookCreatePage
    at BookCreatePage
    createAndCheckBookOnBrowser(title, filename)
    to BookPage
    at BookPage
}

Then(~'My resulting book list contains "([^"]*)"$') { String title ->
    checkIfBookIsOnListAtBookPage(title)
}

Given(~'^the book "([^"]*)" has comments$') { String title, comment ->
    checkIfExists(title)
    checkIfCommentsExist(comment)
}

When(~'^I view the book "([^"]*)" information$') { title ->
    BookTestDataAndOperations.viewInformation(title)
}

Then(~'the system will show the comments about "([^"]*)"$') { String title ->
    showBookComments(title)
}

When(~'^I click on the book comment option from the "([^"]*)" page$') { String title ->
    page.selectBook()
    to BookPage
    at BookPage
}

Then(~'the comments about "([^"]*)" will be show in the book "([^"]*)" page$') { String title ->
    showComments(title)
}


def checkIfExists(String title) {
    book = Book.findByTitle(title)
    assert book == null
}

def createAndCheckBookOnBrowser(String title, String filename) {
    page.fillBookDetails(title, filename)
    page.clickSaveBook()
    book = Book.findByTitle(title)
    assert book != null
}