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

And(~'^the other book chapters still stored in the system$'){
    bookChapters = BookChapter.findAll()
    assert bookChapters != null

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
    assert BookChapterTestDataAndOperations.containsBookChapter(title, bookChapters)
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
Given (~'^I am at the publications$') { ->
    LogInToPublication()
}

def LogInToPublication(){
    to LoginPage
    at LoginPage
    page.fillLoginData("admin", "adminadmin")
    at PublicationsPage
}

And (~'^the system has no book chapter entitled "([^"]*)"$') { String title ->
    checkIfExists(title)
}

When (~'^I select the book chapter option at the program menu'){ ->
    page.select("Book Chapter")
    at BookChapterPage

}

And(~'^I select the upload button at the Book Chapter page$'){ ->
    at BookChapterPage
    page.uploadWithoutFile()

}

And(~'^ I add the book chapters with the file "([^"]*)"$') { String filename ->
    at BookChapterPage
    page.uploadWithoutFile(filename)


}

Then (~'^the book chapters in the file "([^"]*)" are stored by the system$'){ String filename ->
    bookChapter = BookChapter.findByFile(filename)
    assert bookChapter != null
    to BookChapterPage
    at BookChapterPage

}

//Edit existing book chapter web
Given (~'^I am at the book chapters page and the book chapter "([^"]*)" is stored in the system with the file name "([^"]*)"$') { String title, filename ->
    to LoginPage
    at LoginPage
    page.fillLoginData("admin", "adminadmin")
    at PublicationsPage
    page.select("Book Chapter")
    selectNewBookChapterInBookChapterPage()
    page.fillBookChapterDetails(BookChapterTestDataAndOperations.path() + filename, title)
    page.selectCreateBookChapter()
    assert !bookChapterNoExist(title)
    to BookChapterPage
    at BookChapterPage
}

When (~'^I select to view "([^"]*)" in resulting list$'){ String title ->
    at BookChapterPage
    page.selectBookChapter(title)

}

And(~'^I change the book chapter title to "([^"]*)"$'){ String newtitle ->
    at BookChapterPage
    page.select('a', 'edit')
    at BookChapterPage
    def path = new File(".").getCanonicalPath() + File.separator + "test" + File.separator + "files" + File.separator
    page.edit(newtitle, path + "chapter.pdf") //nao tem

}

Then (~'^I select the "([^"]*)" option in Book Chapter Show Page$'){ String option ->
    at BookChapterPage
    page.select(option)

}

//Order existing book chapters by title web
When (~'^I select to view the list of book chapterst$'){ ->
    at BookChapterPage
    page.selectViewBookChapterList()

}

And (~'^I select to view all book chapters ordered by title in resulting list$'){ ->
    at BookChapterPage
    page.selectOrderBy(title)

}

Then (~'^The resulting book chapter list contains all book chapters ordered by title$'){ ->
    to BookChapterPage
    page.checkOrderBy(title)


}
//Filter book chapters web
When (~'^I select to view all my book chapters filtered by title in resulting list$'){ ->
    at BookChapterPage
    page.fillAndSelectFilter(authorName)

}

Then (~'^The resulting book chapter list contains only book chapters filtered by title$'){ ->
    at BookChapterPage
    assert page.checkBookChapterFilteredBy(authorName)

}

//Upload book chapters without a file
When (~'^I upload the book chapters of "([^"]*)"$'){ String filename->
    String path = "test" + File.separator + "functional" + File.separator + "steps" + File.separator + filename
    initialSize = BookChapter.findAll().size()
    BookChapterTestDataAndOperations.uploadArticle(path)
    finalSize = BookChapter.findAll().size()
    assert initialSize < finalSize

}

Then (~'^book chapters are not stored by the system$'){ ->
    assert BookChapterNotExist(title)

}

And(~'^the system has the same number of book chapters$'){
    initialSize = BookChapter.findAll().size()
    finalSize = BookChapter.findAll().size()
    initialSize == finalSize

}


//Edit book chapter
Given(~'^the book chapter "([^"]*)" is stored in the system with file name "([^"]*)"$') { String title, filename ->
    bookChapter = BookChapter.findByTitle(title)
    assert bookChapter != null
}

And(~'^the system has no book chapter entitled "([^"]*)"$'){ String title ->
    bookChapter = BookChapter.findByTitle(title)
    assert bookChapter == null

}

When (~'^I edit the book chapter title from "([^"]*)" to "([^"]*)"$'){ String title ->
    def updateBookChapter = TestDataBookChapter.editBookChapter(oldtitle, newtitle) //Nao tem
    assert updateBookChapter != null

}

Then (~'^the book chapter "([^"]*)" is properly updated by the system$'){ String title ->
    assert bookChapterNoExist(title)

}

//Order book chapters
And (~'^I choose to view the book chapter list ordered by title$'){ ->
    assert BookChapterTestDataAndOperations.isOrdered()

}

Then (~'^the system book chapter list content is not modified $'){ ->
    bookChapters = BookChapter.findAll().size()

}

//Filter book chapters
And(~'^I choose to filter my book chapter list by author "([^"]*)"$'){ String authorName ->
    bookChapterFiltered = BookChapter.findAllByAuthors(authorName)
    assert BookChapterTestDataAndOperations.isFiltered(authorName)
}

def bookChapterNoExist(String title){
    return BookChapter.findByTitle(title) == null
}
