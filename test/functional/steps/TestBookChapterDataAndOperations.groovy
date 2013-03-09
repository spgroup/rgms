package steps

import rgms.publication.BookChapter
import rgms.publication.BookChapterController

class TestBookChapterDataAndOperations {

    static bookChapters = [
            [title: "Next Generation Software Product Line Engineering",
                    publicationDate: (new Date("12 October 2012")), researchLine: "Software Product Lines",
                    publisher: "Person", chapter: 1],

            [title: "Software Product Lines: Practices and Patterns",
                    publicationDate: (new Date("14 October 2012")), researchLine: "Software Product Lines",
                    publisher: "Person", chapter: 2]

    ]


    static public def findByTitle(String title) {
        bookChapters.find { bookChapter ->
            bookChapter.title == title
        }
    }

    static public boolean compatibleTo(bookChapter, title) {
        def testBookChapter = findByTitle(title)
        def compatible = false
        if (testBookChapter == null && bookChapter == null) {
            compatible = true
        } else if (testBookChapter != null && bookChapter != null) {
            compatible = true
            testBookChapter.each { key, data ->
                compatible = compatible && (bookChapter."$key" == data)
            }
        }
        return compatible
    }

    static public void createBookChapter (String title, filename){
        def cont = new BookChapterController()
        def date = new Date()
        cont.params << TestBookChapterDataAndOperations.findByTitle(title) << [file:  filename]
        cont.request.setContent(new byte[1000])
        cont.create()
        cont.save()
        cont.response.reset()
    }

    static public void removeBookChapter (String title){
        def cont = new BookChapterController()
        def date = new Date()
        BookChapter.findByTitle(title).delete(flush:true)
    }
}
