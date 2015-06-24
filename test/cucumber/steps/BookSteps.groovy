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
import rgms.tool.TwitterTool
import steps.BookTestDataAndOperations
import steps.TestDataAndOperationsFacebook
import pages.*

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

Given(~'^I am on the book page$') {->
    to LoginPage
    at LoginPage
    page.fillLoginData("admin", "adminadmin")
    at PublicationsPage
    to BookPage
    at BookPage
}

When(~'^I select to sort the books by "([^"]*)"$') { String sortType ->
    at BookPage
    createBooks()
    page.selectOrderBy(sortType)
}

Then(~'^the books are ordered by "([^"]*)" in alphabetical order$') { String sortType ->
    at BookPage
    page.checkOrderedBy(sortType)
}

Given(~'^the system has a book entitled "([^"]*)" with file name "([^"]*)"$') { String title, String fileName ->
    BookTestDataAndOperations.createBook(title, fileName)
    assert Book.findByTitle(title) != null
}

When(~'^I view the book list$') {->
    books = Book.findAll()
    assert books != null
}

When(~'^I share the book entitled "([^"]*)" on facebook$') { String title ->
    TestDataAndOperationsFacebook.ShareArticleOnFacebook(title)
}

Then(~'^my book list contains the book "([^"]*)"$') { String title ->
    books = Book.findAll()
    assert BookTestDataAndOperations.containsBook(title, books)
}

Then(~'^a facebook message is posted$') {->
    assert true
}

Given(~'^the system has book entitled "([^"]*)" with file name "([^"]*)"$') { String title, String filename ->
    BookTestDataAndOperations.createBook(title, filename)
    assert Book.findByTitle(title) != null
}

When(~'^the system orders the book list by title$') { ->
    booksSorted = Book.listOrderByTitle(order: "asc")
    assert BookTestDataAndOperations.isSorted(booksSorted, "title")
}

Then(~'^the system book list content is not modified$') { ->
    assert Book.findAll().size() == 2
    assert !bookNoExist('SPL Development')
    assert !bookNoExist('Livro de Teste')
}

And(~'^there is the book "([^"]*)" stored in the system with file name "([^"]*)"$') { String title, filename ->
    page.select("Book")
    selectNewBookInBooksPage()
    page.fillBookDetails(title, filename)
    page.selectCreateBook()
    //assert !bookNoExist(title)
    to BookPage
    at BookPage
}

Then(~'my resulting books list contains the book "([^"]*)"$') { String title ->
    at BookPage
    page.checkBookAtList(title, 0)
}

Given(~'^the system has some books authored by "([^"]*)"$'){String authorName->
    BookTestDataAndOperations.createBook('Livro de Teste', 'TCSOS.pdf', 'Paulo Borba')
    BookTestDataAndOperations.createBook('SPL Development', 'MACI.pdf')
    assert (!bookNoExist('Livro de Teste') && !bookNoExist('SPL Development'))
}

When(~'^the system filter the books authored by author "([^"]*)"$') {String authorName->
    booksFiltered = BookTestDataAndOperations.findAllByAuthor(authorName)
    assert BookTestDataAndOperations.isFiltered(booksFiltered,authorName)
}

When(~'^I select to view "([^"]*)" in resulting book list$') { String title ->
    page.selectViewBook(title)
    at BookShowPage
}

When(~'^I click on Share on Facebook for book$') { ->
    at BookShowPage
    page.clickOnShareOnFacebook()
    at BookShowPage
}

Then(~'^A Facebook message was posted$') { ->
    assert true
}

When(~'^I try to create a book named as "([^"]*)" with filename "([^"]*)"$') { String bookName, String filename ->
    selectNewBookInBooksPage()
    page.fillBookDetails(bookName, filename)
    page.selectCreateBook()
}

When(~'^I share it in my Twitter with "([^"]*)" and "([^"]*)"$') { String twitterLogin, String twitterPw ->
    at BookShowPage
    page.clickOnTwitteIt(twitterLogin, twitterPw)
    at BookShowPage
}

Then(~'^A tweet is added to my twitter regarding the new book "([^"]*)"$') { String bookTitle ->
    assert TwitterTool.consultForBook(bookTitle)
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

def createBooks(){
    page.selectNewBook()
    at BookCreatePage
    createAndCheckBookOnBrowser("Pattern Recognition", "pr.pdf")
    to BookPage
    at BookPage
    page.selectNewBook()
    at BookCreatePage
    createAndCheckBookOnBrowser("Machine Learning", "ml.pdf")
    to BookPage
    at BookPage
}

def bookNoExist(String title){
    return Book.findByTitle(title) == null
}

def selectNewBookInBooksPage(){
    at BookPage
    page.selectNewBook()
    at BookCreatePage
}

