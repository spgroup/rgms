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
import rgms.publication.BookController
import steps.BookTestDataAndOperations

import static cucumber.api.groovy.EN.*

Given(~'^the system has no book entitled "([^"]*)"$') { String title ->
    checkIfExists(title)
}

//Author is not being used right now
When(~'^I create the book "([^"]*)" with file name "([^"]*)" and author name "([^"]*)"$') { String title, filename, author ->
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

When(~'I create the book "([^"]*)" with file name "([^"]*)"') { String title, String titlename ->
    BookTestDataAndOperations.createBook(title, filename)
    book = Book.findByTitle(title)
    assert BookTestDataAndOperations.bookCompatibleTo(book, title)
}

When(~'^I remove the book "([^"]*)"$') { String title ->
    BookTestDataAndOperations.removeBook(title)
}

Then((~'^the system lists "([^"]*)" and "([^"]*)"')) { String title1, String title2 ->
    List<Book> list = new ArrayList<Book>();
    list.add(Book.findByTitle(title1));
    list.add(Book.findByTitle(title2));

    assert (list[0]?.getTitle()?.equals(title1) && list[1]?.getTitle()?.equals(title2))
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

And(~'^I use the webpage to create the book "([^"]*)" with file name "([^"]*)" and author name "([^"]*)"$') { String title, filename, author ->
    at BookCreatePage
    createAndCheckBookOnBrowser(title, filename, author)
    to BookPage
    at BookPage
}

Then(~'^the book "([^"]*)" was stored by the system$') { String title ->
    book = Book.findByTitle(title)
    assert book != null
    to BookPage
    at BookPage
}

And(~'^the message "([^"]*)" is displayed') { String message ->
    System.out.println(message);
}














 And (~'The system has a book entitled "([^"]*)" volume "([^"]*)"$'){ String title, int volume ->
    // BookTestDataAndOperations.createBook(title, "teste")
    //Book.findByTitle(title).volume = 3

     bookVolumeTitle(title,volume)
}

When (~'^I fill the webpage search for a book with volume "([^"]*)" and click the search button$'){ int volume ->
    //aprender como se faz
}
Then (~'^The system displays the book entitled "([^"]*)" volume "([^"]*)"$'){ String title, int volume ->
    //aprender como se faz
}


Given (~'^The book entitled "([^"]*)" volume "([^"]*)" is stored on the system$'){ String title, int volume ->
    book = Book.findByTitle(title)
    assert book != null && book.volume == volume

}
When(~'^I search for a book with volume "([^"]*)"$'){ int volume ->
    book = Book.findByVolume(volume)

}
Then (~'^the system lists all books with volume "([^"]*)"$'){ int volume ->
    book = Book.findByVolume(volume)
    assert book.count() >= 1
}



And (~'^The system has a book with publisher "([^"]*)"$'){ String publisher ->
    book = Book.findByPublisher(publisher)
    assert book != null
}
When (~'^I fill the webpage search for a book with publisher "([^"]*)" and click the search button$'){ String publisher ->
}
Then (~'^The system displays the book with publisher "([^"]*)"$'){ String publisher ->
    // não sei fazer
}



Given (~'^The book with publisher "([^"]*)" is stored on the system$'){String publisher ->
    assert Book.findByPublisher(publisher) != null
}
When (~'^I search for a book with publisher "([^"]*)"$'){ String publisher ->
    assert Book.findByPublisher(publisher) != null
}
Then (~'^the system lists all books with publisher "([^"]*)"$'){ String publisher ->
    assert Book.findByPublisher(publisher).count() >= 1
}





And (~'^The system has a book entitled "([^"]*)"$'){ String title ->

    book = Book.findByTitle(title)
    assert book != null
}
When (~'^I fill the webpage search for a book with title "([^"]*)" and click the search button$'){ String title ->
}
Then (~'^The system displays the book entitled "([^"]*)"$') { String title ->
    // não sei fazer
}

Given (~'^The book entitled "([^"]*)" is stored on the system$'){ String title ->
    book = Book.findByTitle(title)
    assert book != null
}
When (~'^I search for a book entitled "([^"]*)"$'){ String title ->
    book = Book.findByTitle(title)
    assert book != null

}
Then (~'^the system lists all books entitled "([^"]*)"$'){ String title ->
    book = Book.findByTitle(title)
    assert book.count() >= 1
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

def createAndCheckBookOnBrowser(String title, String filename, String author) {
	page.fillBookDetails(title, filename, author)
	page.clickSaveBook()
	book = Book.findByTitle(title)
	assert book != null
}

def bookVolumeTitle(String title,int volume){
    /*
    BookController.params <<[title: title, volume: volume]
    BookController.request.setContent(new byte[1000])
    BookController.create()
    BookController.save()
    BookController.response.reset()
    */
    assert Book.findByTitleAndVolume(title,volume) != null

}