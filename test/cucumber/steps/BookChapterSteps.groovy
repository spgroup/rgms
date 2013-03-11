import pages.BookChapterCreatePage
import pages.BookChapterPage
import pages.LoginPage
import pages.PublicationsPage
import rgms.publication.BookChapter
import steps.TestDataAndOperations

import static cucumber.api.groovy.EN.*

Given(~'^the system has no book chapter entitled "([^"]*)"$') { String title ->
    // Express the Regexp above with the code you wish you had
    bookChapter = BookChapter.findByTitle(title)
    assert bookChapter == null
}

When(~'^I create the book chapter "([^"]*)" with file name "([^"]*)"$') { String title, filename ->
    // Express the Regexp above with the code you wish you had
    TestDataAndOperations.createBookChapter(title, filename)
}

Then(~'^the book chapter "([^"]*)" is properly stored by the system$') { String title ->
    // Express the Regexp above with the code you wish you had
    bookChapter = BookChapter.findByTitle(title)
    assert TestDataAndOperations.compatibleTo(bookChapter, title)
}

Given(~'^the book chapter "([^"]*)" is stored in the system with file name "([^"]*)"$') { String title, String filename ->
    // Express the Regexp above with the code you wish you had
    TestDataAndOperations.createBookChapter(title, filename)
    bookChapter = BookChapter.findByTitle(title)
    assert bookChapter != null
}

Then(~'^the book chapter  "([^"]*)" is not stored twice$') { String title ->
    // Express the Regexp above with the code you wish you had
    bookChapters = BookChapter.findAllByTitle(title)
    assert bookChapters.size() == 1
}

When(~'^I remove the book chapter "([^"]*)"$') { String title ->
    // Express the Regexp above with the code you wish you had
    TestDataAndOperations.removeBookChapter(title)
}

Then(~'^the book chapter "([^"]*)" is properly removed by the system$') { String title ->
    // Express the Regexp above with the code you wish you had
    bookChapter = BookChapter.findByTitle(title)
    assert bookChapter == null
}

Given(~'^the book chapter "([^"]*)" is stored in the system with file name  "([^"]*)"$') { String title, String filename ->
    // Express the Regexp above with the code you wish you had
    TestDataAndOperations.createBookChapter(title, filename)
    bookChapter = BookChapter.findByTitle(title)
    assert bookChapter != null
}

Given(~'^I am at the publication menu$') { ->
    // Express the Regexp above with the code you wish you had
    to LoginPage
    at LoginPage
    page.fillLoginData("admin", "adminadmin")
    at PublicationsPage
}

When(~'^I select the "([^"]*)" option at the publication menu$') { String option ->
    page.select(option)
}

When(~'^I select the Novo BookChapter option at the book chapter page$') { ->
    // Express the Regexp above with the code you wish you had
    at BookChapterPage
    page.selectNewBookChapter()
}

Then(~'^I can fill the book chapter details$') { ->
    // Express the Regexp above with the code you wish you had
    at BookChapterCreatePage
    page.fillBookChapterDetails()
}

Given(~'^I am at the book chapter page$') { ->
    // Express the Regexp above with the code you wish you had
    to LoginPage
    at LoginPage
    page.fillLoginData("admin", "adminadmin")
    at PublicationsPage
    page.select("BookChapter")
    at BookChapterPage
}

And (~'^I fill only the title field at book chapter create page$') { ->
    at BookChapterCreatePage
    page.fillTitle()
}

Then(~'^I still on the book chapter create page$') { ->
    at BookChapterCreatePage
}
