package steps

import rgms.publication.Periodico
import rgms.publication.PeriodicoController

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


	/**
	 * Test of removing a Article
	 * @param title
	 * @param filename
	 * @author Guilherme
	 */
	static public void removeArticle(String title) {
		def testarticle = Periodico.findByTitle(title)
		def cont = new PeriodicoController()
		def date = new Date()
		cont.params << [id: testarticle.id]
		cont.delete()
	}

	/**
	 * Test of listing Articles
	 * @param title
	 * @param articles
	 * @author Guilherme
	 */
	static public boolean containsArticle(title, articles) {
		def testarticle = Periodico.findByTitle(title)
		def cont = new PeriodicoController()
		def result = cont.list().periodicoInstanceList
		return result.contains(testarticle)
	}
	
	/**
	 * Test of editing Article
	 * @param article
	 * @param newtitle
	 * @author Guilherme
	 */
	static public Periodico editArticle(oldtitle, newtitle) {
		def article = Periodico.findByTitle(oldtitle)
		article.setTitle(newtitle)
		def cont = new PeriodicoController()
		cont.params << article.properties 
		cont.update()
		
		def updatedarticle = Periodico.findByTitle(newtitle)
		return updatedarticle
	}
}