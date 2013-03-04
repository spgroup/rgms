package steps

import rgms.publication.Periodico
import rgms.publication.PeriodicoController
import rgms.publication.Dissertacao
import rgms.publication.DissertacaoController

class TestDataAndOperations {
    static articles = [
            [journal: "Theoretical Computer Science", volume: 455, number: 1, pages: "2-30",
                    title: "A theory of software product line refinement",
                    publicationDate: (new Date("12 October 2012"))],
            [journal: "Science of Computer Programming", volume: 455, pages: "2-30",
                    title: "Algebraic reasoning for object-oriented programming",
                    publicationDate: (new Date("12 October 2012"))]
    ]

    static public def findByTitle(String title) {
        articles.find { article ->
            article.title == title
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

	static public void createDissertacao(String title, filename, school) {
        def cont = new DissertacaoController()
		def date = new Date()
		cont.params << [title: title, publicationDate: new Date(2013, 03, 02), 
			school: school, address: "Boa Viagem",file: filename]
		cont.request.setContent(new byte[1000]) // Could also vary the request content.
		cont.create()
		cont.save()
		cont.response.reset()
    }
	
	static public void createDissertacaoWithotSchool(String title, filename) {
		def cont = new DissertacaoController()
		def date = new Date()
		cont.params << [title: title, publicationDate: new Date(2013, 03, 02),
			address: "Boa Viagem",file: filename]
		cont.request.setContent(new byte[1000]) // Could also vary the request content.
		cont.create()
		cont.save()
		cont.response.reset()
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

    static void clearArticles() {
        Periodico.findAll()*.delete flush: true // Could also delete the created files.
    }
	
}