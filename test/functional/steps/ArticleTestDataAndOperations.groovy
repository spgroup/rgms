package steps

import rgms.publication.Periodico
import rgms.publication.PeriodicoController
import rgms.publication.XMLController

/**
 * Created with IntelliJ IDEA.
 * User: tasj
 * Date: 30/08/13
 * Time: 16:34
 * To change this template use File | Settings | File Templates.
 */
class ArticleTestDataAndOperations {

	static articles = [
		[journal: "Theoretical Computer Science", volume: 455, number: 1, pages: "2-30",
			title: "A theory of software product line refinement",
			publicationDate: (new Date("12 October 2012"))],
		[journal: "Science of Computer Programming", volume: 455, pages: "2-30",
			title: "Algebraic reasoning for object-oriented programming",
			publicationDate: (new Date("12 October 2012"))],
		[journal: "Science of Computer Programming", volume: 300, pages: "50-70",
			title: "Modularity analysis of use case implementations",
			publicationDate: (new Date("12 October 2012"))],
		[journal: "Science of Computer Programming", volume: 255, pages: "30-50",
			filename: "TCS-01.pdf", publicationDate: (new Date("12 October 2012"))]
	]

	static public def findArticleByTitle(String title) {
		articles.find { article ->
			article.title == title
		}
	}

	static public void uploadArticle(filename) {
		def cont = new XMLController()
		def xml = new File(filename);
		def records = new XmlParser()
		cont.saveJournals(records.parse(xml))
		cont.response.reset()
	}

	static public boolean compatibleTo(article, title) {
		def testarticle = findArticleByTitle(title)
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
		createArticle(title,filename, null,null)
	}

	static public void createArticle(String title, filename, date, authorName) {
		def cont = new PeriodicoController()
		cont.params << ArticleTestDataAndOperations.findArticleByTitle(title) << [file: filename]
		if(date!=null){
			cont.params["publicationDate"] = new Date(date)
		}
		if(authorName!=null){
			cont.params["authors"] = authorName

		}
		cont.request.setContent(new byte[1000]) // Could also vary the request content.
		cont.create()
		cont.save()
		cont.response.reset()
	}

	static public void createArticleWithoutTitle(filename) {
		def cont = new PeriodicoController()
		cont.params << ArticleTestDataAndOperations.findArticleByFilename(filename)
		cont.request.setContent(new byte[1000]) // Could also vary the request content.
		cont.create()
		cont.save()
		cont.response.reset()
	}

	static void clearArticles() {
		Periodico.findAll()*.delete flush: true // Could also delete the created files.
	}

	static public void removeArticle(String title) {
		def testarticle = Periodico.findByTitle(title)
		def cont = new PeriodicoController()
		def date = new Date()
		cont.params << [id: testarticle.id]
		cont.delete()
	}

	static public boolean containsArticle(title, articles) {
		def testarticle = Periodico.findByTitle(title)
		def cont = new PeriodicoController()
		def result = cont.list().periodicoInstanceList
		return result.contains(testarticle)
	}

	static public Periodico editArticle(oldtitle, newtitle) {
		def article = Periodico.findByTitle(oldtitle)
		article.setTitle(newtitle)
		def cont = new PeriodicoController()
		cont.params << article.properties
		cont.update()

		def updatedarticle = Periodico.findByTitle(newtitle)
		return updatedarticle
	}

	static public def path(){
		return new File(".").getCanonicalPath() + File.separator + "test" + File.separator + "files" + File.separator
	}

	static public def isSorted(articles,sortType) {
		def isSorted = false
		switch (sortType) {
			case 'title':
				isSorted = (articles.size() < 2 || (1..<articles.size()).every { (articles[it - 1].title).compareTo(articles[it].title) < 0})
				break
			case 'publication date':
				isSorted = (articles.size() < 2 || (1..<articles.size()).every { (articles[it - 1].publicationDate).compareTo(articles[it].publicationDate) < 0})
				break
		}
		return isSorted
	}

	static public def isFiltered(articles,authorName) {
		for (article in articles) {
			if(!(article.authors).contains(authorName))
				return false
		}
		return true
	}

	static public def findArticleByFilename(String filename) {
		articles.find { article ->
			article.filename == filename
		}
	}

	static public List<Periodico> reportArticles() {
		def cont = new PeriodicoController()
		return cont.report().periodicoInstanceList
	}

	static public List<Periodico> findAllByAuthor(authorName) {
		def cont = new PeriodicoController()
		cont.params << [authorName: authorName]
		cont.filterByAuthor()
		return cont.modelAndView.model.periodicoInstanceList
	}

	static public void removeMultiplesArticles(String... titles) {
		def ids = []
		titles.each {ids.add(String.valueOf(Periodico.findByTitle(it).id))}
		def cont = new PeriodicoController()
		cont.params << [check: ids]
		cont.deleteMultiples()
	}
}

