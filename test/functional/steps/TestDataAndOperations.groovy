package steps

import rgms.publication.Periodico
import rgms.publication.PeriodicoController
import rgms.publication.Conferencia
import rgms.publication.ConferenciaController
import rgms.publication.BookChapter
import rgms.publication.BookChapterController

class TestDataAndOperations {
    static articles = [
            [journal: "Theoretical Computer Science", volume: 455, number: 1, pages: "2-30",
                    title: "A theory of software product line refinement",
                    publicationDate: (new Date("12 October 2012"))],
            [journal: "Science of Computer Programming", volume: 455, pages: "2-30",
                    title: "Algebraic reasoning for object-oriented programming",
                    publicationDate: (new Date("12 October 2012"))]
    ]

    static bookChapters = [
            [title: "Next Generation Software Product Line Engineering", publicationDate: (new Date("12 October 2012")), researchLine: "Software Product Lines",
                    publisher: "Person", chapter: 1, members: null]
    ]
    static conferencias = [
        [title: "I International Conference on Software Engineering",
                    publicationDate: (new Date("12 October 2012")), researchLine: "Software Product Lines", 
                    booktitle: "Software Engineering", pages: "20-120"],
        [title: "IV Conference on Software Product Lines",
                    publicationDate: (new Date("14 October 2012")), researchLine: "Software Product Lines",
                    booktitle: "Practices and Patterns", pages: "150-200"]
    ]

    static public def findByTitle(String title) {
        articles.find { article ->
            article.title == title
        }
    }

    static public def findBookChapterByTitle(String title) {
        bookChapters.findAll { bookChapter ->
            bookChapter.title == title
        }
    }
    static public def findConferenciaByTitle(String title) {
        conferencias.find { conferencia ->
	conferencia.title == title
        }
    }


    static public boolean compatibleTo(article, title) {
        def testarticle = findByTitle(title)
        def compatible = false
        if (testarticle == null && article == null) {
            compatible = true
        } else if (testarticle != null && article != null) {
            compatible = true
            testarticle.each { key, data ->
                compatible = compatible && (article."$key" == data)
            }
        }
        return compatible
    }

    static public boolean bookChapterCompatibleTo(bookChapter, title) {
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
    static public boolean conferenciaCompatibleTo(conferencia, title) {
        def testConferencia = findByTitle(title)
        def compatible = false
        if (testConferencia == null && conferencia == null) {
            compatible = true
        } else if (testConferencia != null && conferencia != null) {
            compatible = true
            testConferencia.each { key, data ->
                compatible = compatible && (conferencia."$key" == data)
            }
        }
        return compatible
    }

    static public void createArticle(String title, filename) {
        def cont = new PeriodicoController()
        def date = new Date()
        cont.params << TestDataAndOperations.findByTitle(title) << [file: filename]
        cont.request.setContent(new byte[1000]) // Could also vary the request content.
        cont.create()
        cont.save()
        cont.response.reset()
    }

    static public void createBookChapter (String title, filename){
        def cont = new BookChapterController()
        def date = new Date()
	def tempBookChapter = TestBookChapterDataAndOperations.findByTitle(title)
	tempBookChapter.members = null
        cont.params << tempBookChapter << [file: filename]
        cont.request.setContent(new byte[1000])
        cont.create()
        cont.save()
        cont.response.reset()
    }

    static public void createConferencia (String title, String filename){
        def cont = new ConferenciaController()
        def date = new Date()
        cont.params << TestConferenciaDataAndOperations.findByTitle(title) << [file: filename]
        cont.request.setContent(new byte[1000])
        cont.create()
        cont.save()
        cont.response.reset()
    }

    static void clearArticles() {
        Periodico.findAll()*.delete flush: true // Could also delete the created files.
    }

    static public void removeBookChapter (String title){
        def cont = new BookChapterController()
        def date = new Date()
        BookChapter.findByTitle(title).delete(flush:true)
    }

    static public void removeConferencia (String title){
        def cont = new ConferenciaController()
        def date = new Date()
        Conferencia.findByTitle(title).delete(flush:true)
    }
}