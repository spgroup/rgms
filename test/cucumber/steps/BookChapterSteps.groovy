import pages.BookChapterCreatePage
import pages.BookChapterPage
import pages.LoginPage
import pages.PublicationsPage
import pages.*
import rgms.publication.BookChapter
import steps.BookChapterTestDataAndOperations
import steps.TestDataAndOperationsPublication

import static cucumber.api.groovy.EN.*

Given(~'^the system has no book chapter entitled "([^"]*)"$') { String title ->
    checkIfExists(title)
}

When(~'^I create the book chapter "([^"]*)" with file name "([^"]*)"$') { String title, filename ->
    BookChapterTestDataAndOperations.createBookChapter(title, filename)
}

Then(~'^the book chapter "([^"]*)" is properly stored by the system$') { String title ->
    bookChapter = BookChapter.findByTitle(title)
    assert BookChapterTestDataAndOperations.bookChapterCompatibleTo(bookChapter, title)
}

Given(~'^the book chapter "([^"]*)" is stored in the system with file name "([^"]*)"$') { String title, String filename ->
    BookChapterTestDataAndOperations.createBookChapter(title, filename)
    bookChapter = BookChapter.findByTitle(title)
    assert bookChapter != null
}

Then(~'^the book chapter "([^"]*)" is not stored twice$') { String title ->
    bookChapters = BookChapter.findAllByTitle(title)
    assert bookChapters.size() == 1
}

When(~'^I remove the book chapter "([^"]*)"$') { String title ->
    BookChapterTestDataAndOperations.removeBookChapter(title)
}

Then(~'^the book chapter "([^"]*)" is properly removed by the system$') { String title ->
    checkIfExists(title)
}

When(~'^I select the new book chapter option at the book chapter page$') { ->
    at BookChapterPage
    page.selectNewBookChapter()
}

Given(~'^I am at the book chapter page$') { ->
    to LoginPage
    at LoginPage
    page.fillLoginData("admin", "adminadmin")
    at PublicationsPage
    to BookChapterPage
    at BookChapterPage
}

And(~'^I fill only the title field with the value "([^"]*)"$') { String title ->
    at BookChapterCreatePage
    page.fillTitle(title)
}

Then(~'^A failure message is displayed$') { ->
    assert (page.readFlashMessage() != null)

}
And(~'^I still on the book chapter create page$') { ->
    at BookChapterCreatePage
}

Then(~'^I see my user listed as a member of book chapter by default$') { ->
    at BookChapterCreatePage
    assert TestDataAndOperationsPublication.containsUser(page.selectedMembers())
}

When(~'^I view the book chapter list$') { ->
    to BookChapterPage
}

Then(~'my book chapter list contains "([^"]*)"$') { String title ->
    at BookChapterPage
    bookChapterList = BookChapter.findAll()
    assert BookChapterTestDataAndOperations.containsBookChapter(title)
}
And(~'^the book chapter "([^"]*)" with file name "([^"]*)" was created before$') { String title, filename ->
    page.selectNewBookChapter()
    to BookChapterCreatePage
    at BookChapterCreatePage
    createAndCheckBookOnBrowser(title, filename)
    to BookChapterPage
    at BookChapterPage
}

Then(~'My resulting book chapter list contains "([^"]*)"$') { String title ->
    checkIfBookIsOnListAtBookChapterPage(title)
}

private void checkIfBookIsOnListAtBookChapterPage(String title) {
    at BookChapterPage
    page.checkBookChapterAtList(title, 0)
}

When(~'^I go to new book chapter page$') { ->
    to BookChapterPage
    page.selectNewBookChapter()
    at BookChapterCreatePage
}
And(~'^I use the webpage to create the book chapter "([^"]*)" with file name "([^"]*)"$') { String title, filename ->
    at BookChapterCreatePage
    createAndCheckBookOnBrowser(title, filename)
    to BookChapterPage
    at BookChapterPage
}
Then(~'^the book chapter "([^"]*)" was stored by the system$') { String title ->
    book = BookChapter.findByTitle(title)
    assert book != null
    to BookChapterPage
    at BookChapterPage
}
And(~'^it is shown in the book chapter list with title "([^"]*)"$') { String title ->
    to BookChapterPage
    at BookChapterPage
    page.checkBookChapterAtList(title, 0)
}
Given(~'^the system has some book chapters stored$') { ->
    initialSize = BookChapter.findAll().size()
}
When(~'^I upload the book chapters of "([^"]*)"$') { filename ->
    String path = "test" + File.separator + "functional" + File.separator + "steps" + File.separator + filename
    initialSize = BookChapter.findAll().size()
    BookChapterTestDataAndOperations.uploadBookChapter(path)
    finalSize = BookChapter.findAll().size()
    assert initialSize < finalSize
}
Then(~'^the system has all the book chapters of the xml file$') { ->
    assert BookChapter.findByTitle("Refinement of Concurrent Object Oriented Programs") != null
    assert BookChapter.findByTitle("A RUP-Based Software Process Supporting Progressive Implementation") != null
    assert BookChapter.findByTitle("Transformation Laws for Sequential Object-Oriented Programming") != null
    assert BookChapter.findByTitle("Mapping Features to Aspects: A Model-Based Generative Approach") != null
    assert BookChapter.findByTitle("Recommending Mechanisms for Modularizing Mobile Software Variabilities") != null
    assert BookChapter.findByTitle("An Introduction to Software Product Line Refactoring") != null
}


And(~'^I select the upload button at the book chapter page$') { ->
    at BookChapterPage
    page.uploadWithoutFile()
}
Then(~'^I\'m still on book chapter page$') { ->
    at BookChapterPage
}
And(~'^the book chapters are not stored by the system$') { ->
    at BookChapterPage
    page.checkIfBookChapterListIsEmpty()
}

And(~'^the system has a book chapter entitled "([^"]*)" with file name "([^"]*)"$') { String title, String filename ->
    book = BookChapter.findByTitle(title)
    if (book == null) {
        BookChapterTestDataAndOperations.createBookChapter(title, filename)
        to BookChapterCreatePage
    }
}

Then(~'^the book chapter "([^"]*)" was not stored twice$') { String entitled ->
    bookChapter = BookChapter.findAllByPublisher(entitled)
    assert bookChapter.size() < 2
}

And(~'^the system shows an error message$') { ->
    at BookChapterPage
    assert page.hasErrorUploadFile()
}


def createAndCheckBookOnBrowser(String title, String filename) {
    page.fillBookChapterDetails(title, filename)
    page.clickSaveBookChapter()
    book = BookChapter.findByTitle(title)
    assert book != null
}

def checkIfExists(String title) {
    bookChapter = BookChapter.findByTitle(title)
    assert bookChapter == null
}
