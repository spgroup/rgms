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

When(~'^I create the book "([^"]*)" with file name "([^"]*)"$') { String title, filename ->
    BookTestDataAndOperations.createBook(title, filename)
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
And(~'^I use the webpage to create the book "([^"]*)" with file name "([^"]*)"$') { String title, filename ->
    at BookCreatePage
    createAndCheckBookOnBrowser(title, filename)
    to BookPage
    at BookPage
}
Then(~'^the book "([^"]*)" was stored by the system$') { String title ->
    book = Book.findByTitle(title)
    assert book != null
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
Then(~'^the article "([^"]*)" is properly removed by the system$') { String title ->
    assert checkIfExists(title)
}



Then(~'^the book list contains" ([^"]*)"$') { String title ->
    at BookPage
    page.checkArticleAtList(title, 0)
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
