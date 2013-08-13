import pages.*
import rgms.member.Member
import rgms.publication.BookChapter
import rgms.publication.Periodico
import steps.TestDataAndOperations

import static cucumber.api.groovy.EN.*

Given(~'^the system has no book chapter entitled "([^"]*)"$') { String title ->
    bookChapter = BookChapter.findByTitle(title)
    assert bookChapter == null
}

When(~'^I create the book chapter "([^"]*)" with file name "([^"]*)"$') { String title, filename ->
    TestDataAndOperations.createBookChapter(title, filename)
}

Then(~'^the book chapter "([^"]*)" is properly stored by the system$') { String title ->
    bookChapter = BookChapter.findByTitle(title)
    assert TestDataAndOperations.bookChapterCompatibleTo(bookChapter, title)
}

Given(~'^the book chapter "([^"]*)" is stored in the system with file name "([^"]*)"$') { String title, String filename ->
    TestDataAndOperations.createBookChapter(title, filename)
    bookChapter = BookChapter.findByTitle(title)
    assert bookChapter != null
}

Then(~'^the book chapter "([^"]*)" is not stored twice$') { String title ->
    bookChapters = BookChapter.findAllByTitle(title)
    assert bookChapters.size() == 1
}

When(~'^I remove the book chapter "([^"]*)"$') { String title ->
    TestDataAndOperations.removeBookChapter(title)
}

Then(~'^the book chapter "([^"]*)" is properly removed by the system$') { String title ->
    bookChapter = BookChapter.findByTitle(title)
    assert bookChapter == null
}

Given(~'^the book chapter "([^"]*)" is stored in the system with file name  "([^"]*)"$') { String title, String filename ->
    TestDataAndOperations.createBookChapter(title, filename)
    bookChapter = BookChapter.findByTitle(title)
    assert bookChapter != null
}

Given(~'^I am at the publication menu$') {->
    to LoginPage
    at LoginPage
    page.fillLoginData("admin", "adminadmin")
    at PublicationsPage
}

When(~'^I select the "([^"]*)" option at the publication menu$') { String option ->
    page.select(option)
}

When(~'^I select the Novo BookChapter option at the book chapter page$') {->
    at BookChapterPage
    page.selectNewBookChapter()
}

Given(~'^I am at the book chapter page$') {->
    to LoginPage
    at LoginPage
    page.fillLoginData("admin", "adminadmin")
    at PublicationsPage
    page.select("BookChapter")
    at BookChapterPage
}

And(~'^I fill only the title field with the value "([^"]*)"$') { String title ->
    at BookChapterCreatePage
    page.fillTitle(title)
}

Then(~'^A failure message is displayed$') {->
    assert ( page.readFlashMessage() != null )

}
And(~'^I still on the book chapter create page$'){->
    at BookChapterCreatePage
}

Then(~'^I see my user listed as a member of book chapter by default$') {->
    at BookChapterCreatePage
    userData = Member.findByUsername('admin').id.toString()
    assert page.selectedMembers().contains(userData)
}

Given(~'the system has book chapter entitled "([^"]*)" with file name "([^"]*)"$'){ String title, filename ->
    TestDataAndOperations.createBookChapter(title, filename)
    bookChapter = BookChapter.findByTitle(title)
    assert bookChapter != null
}

When(~'^I view the book chapter list$') {->
    at BookChapterPage
}

Then(~'my book chapter list contains "([^"]*)"$') { String title ->
    at BookChapterPage
    bookChapterList = BookChapter.findAll()
    assert TestDataAndOperations.containsBookChapter(title, bookChapterList)
}
And(~'^the book chapter "([^"]*)" with file name "([^"]*)" was created before$'){ String title, filename ->
    page.selectNewBookChapter()
    to BookChapterCreatePage
    at BookChapterCreatePage
    page.fillBookChapterDetails(title, filename)
    page.clickSaveBookChapter()
    book = BookChapter.findByTitle(title)
    assert book != null
    to BookChapterPage
    at BookChapterPage
}

Then(~'My resulting book chapter list contains "([^"]*)"$') { String title ->
    at BookChapterPage
    page.checkBookChapterAtList(title, 0)
}
When(~'^I go to NewBookChapter page$'){->
    to BookChapterPage
    at BookChapterPage
    page.selectNewBookChapter()
    at BookChapterCreatePage
}
And(~'^I use the webpage to create the book chapter "([^"]*)" with file name "([^"]*)"$'){ String title, filename ->
    at BookChapterCreatePage
    page.fillBookChapterDetails(title, filename)
    page.clickSaveBookChapter()
    book = BookChapter.findByTitle(title)
    assert book != null
    to BookChapterPage
    at BookChapterPage
}
Then(~'^the book chapter "([^"]*)" was stored by the system$'){String title ->
    book = BookChapter.findByTitle(title)
    assert book != null
    to BookChapterPage
    at BookChapterPage
}
And(~'^it is shown in the book chapter list with title "([^"]*)"$'){ String title ->
    to BookChapterPage
    at BookChapterPage
    page.checkBookChapterAtList(title, 0)
}