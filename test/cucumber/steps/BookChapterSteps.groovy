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
    assert bookChapterNoExist(title)
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

And(~'^the book chapter "([^"]*)" is still stored in the system$'){ String title ->
    assert bookChapter != null
    bookChapter = BookChapter.findAllByTitle(title)

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

And(~'^I am still on the book chapter create page$') { ->
    at BookChapterCreatePage
}

And (~'^the book chapter is not stored by the system$') { ->
    at BookChapterPage
    page.checkIfBookChapterListIsEmpty()
}

Then(~'^I see my user listed as a member of book chapter by default$') { ->
    at BookChapterCreatePage
    assert TestDataAndOperationsPublication.containsUser(page.selectedMembers())
}

When(~'^I view the book chapter list$') { ->
    bookChapters = BookChapter.findAll()
    bookChapters != null
}

Then(~'my book chapter list contains "([^"]*)"$') { String title ->
    bookChapters = BookChapter.findAll()
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
    at BookChapterPage
    page.checkBookChapterAtList(title, 0)
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
Then(~'^the book chapter "([^"]*)" is stored by the system$') { String title ->
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
    assert initialSize != 0
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

When(~'I select the Book Chapter option at the program menu'){ ->
    page.select("Book Chapter")
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

/**
 * @author lctf
 * @author fta
 */

//Upload book chapter with a file web


When (~'^I select the book chapter option at the program menu'){
    page.select("Book Chapter")
    at BookChapterPage

}

And(~'^I can add the book chapter with a file "([^"]*)"$') { String filename ->
    at BookChapterCreatePage
    def path = new File(".").getCanonicalPath() + File.separator + "test" + File.separator + "files" + File.separator + filename
    page.fillBookChapterDetails(path)
}

Then (~'^the system has a book chapter entitled "([^"]*)"$'){ String title ->
    bookChapter = BookChapter.findByTitle(title)
    assert bookChapter != null

}

//Edit existing book chapter web
Given (~'^I am at the book chapters page and the book chapter "([^"]*)" is stored in the system with the file name "([^"]*)"$') { String title, filename ->
    LogInToPublication()
    at PublicationsPage
    page.select("Book Chapter")
    selectNewBookChapterInBookChapterPage()
    page.fillBookChapterDetails(BookChapterTestDataAndOperations.path() + filename, title)
    page.selectCreateBookChapter()
    assert !bookChapterNoExist(title)
    to BookChapterPage
    at BookChapterPage
}

def selectNewBookChapterInBookChapterPage(){
    at BookChapterPage
    page.selectNewBookChapter()
    at BookChapterCreatePage

}

When (~'^I select to view the book chapter "([^"]*)" in resulting list$'){ String title ->
    page.selectBookChapter(title)
    at BookChapterPage


}

And(~'^I change the book chapter title to "([^"]*)"$'){ String newtitle ->
    page.select('a', 'edit')
    at BookChapterPage
    def path = new File(".").getCanonicalPath() + File.separator + "test" + File.separator + "files" + File.separator
    page.edit(newtitle, path + "chapter.pdf")

}

Then (~'^I select the "([^"]*)" option in Book Chapter Page$'){ String option ->
    at BookChapterPage
    page.select(option)

}

And(~'^I am at Book Chapter Page$'){
    at BookChapterpage

}

//Order existing book chapters by title web
Given(~'^I am at the book chapters page$') { ->
    at BookChapterPage
}

And(~'^The system has some book chapters stored$') { ->
    bookChapter = BookChapter.findAll()
    assert bookChapter != null
}

When (~'^I select to view all book chapters$'){ ->
    at BookChapterPage
    page.selectViewBookChapterList()

}

And (~'^I select to view the book chapters ordered by "([^"]*)"$'){ String title ->
    at BookChapterPage
    page.selectOrderByTitle(title)


}

Then (~'^The resulting book chapter list contains all book chapters ordered by "([^"]*)"$'){ String title ->
    to BookChapterPage
    page.checkOrderedByTitle(title)

}
//Filter book chapters web
And(~'^I select to view  the book chapters filtered by author "([^"]*)"$') { String authorName ->
    at BookChapterPage
    page.fillAndSelectFilter(authorName)
}

Then (~'^The resulting book chapter list contains only book chapters filtered by author "([^"]*)"$'){ String authorName ->
    at BookChapterPage
    assert page.checkBookChapterFilteredByAuthor(authorName)

}

//Upload book chapters without a file
When (~'^I can not upload the book chapters of "([^"]*)"$'){ String filename->
    assert filename == null

}

Then(~'^the system has the same number of book chapters$'){
    initialSize = BookChapter.findAll().size()
    assert initialSize == 1

}

//Edit book chapter


When (~'^I edit the book chapter title from "([^"]*)" to "([^"]*)"$'){ String oldTitle, newTitle ->
    def updateBookChapter = BookChapterTestDataAndOperations.editBookChapter(oldTitle, newTitle) //Nao tem
    assert updateBookChapter != null

}

Then (~'^the book chapter "([^"]*)" is properly updated by the system$'){ String title ->
    def bookChapter = BookChapter.findByTitle(title)
    assert bookChapter == null

}

//Order book chapters
Given(~'^The system has a book chapter entitled "([^"]*)" with file name "([^"]*)"$') { String title, String filename ->
    bookChapterTitle = BookChapter.findByTitle(title)
    assert bookChapter != null

}

When(~'^The system order the book chapters stored by title$') { ->
    bookChapters = BookChapter.findAll()
    assert BookChapterTestDataAndOperations.isOrdered(bookChapters)

}

//Filter book chapters
Given(~'^the system has some book chapters stored with author "([^"]*)"$') { String authorName ->
    BookChapterTestDataAndOperations.createBookChapter("Chapter test", "chaptertest.pdf", null, "Larissa" )
    BookChapterTestDataAndOperations.createBookChapter("Chapter test 2", "chaptertest2.pdf", null, "Larissa" )
    assert (!bookChapterNoExist("Chapter test") && !bookChapterNoExist("Chapter test 2"))
}

When(~'^The system filter the book chapters stored by author "([^"]*)"$') { String authorName ->
    bookChaptersF = BookChapter.findAllByAuthors(authorName)
    assert BookChapterTestDataAndOperations.isFiltered(bookChaptersF, authorName)

}

def bookChapterNoExist(String title){
    return BookChapter.findByTitle(title) == null

}


