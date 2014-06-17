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
            [journal: "Science of Computer Programming", volume: 455, number: 1, pages: "2-30",
                    title: "Algebraic reasoning for object-oriented programming",
                    publicationDate: (new Date("12 October 2012"))],
            [journal: "Eletronic Notes In Theoretical Computer Science", volume: 130, number: 1, pages: "3-21",
                    title: "An Abstract Equivalence Notion for Object Models",
                    publicationDate: (new Date("12 October 2005")),
                    authors: ["Paulo Henrique Monteiro Borba", "Tiago Massoni", "Rohit Gheyi"] as Set]
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
                if(key != 'publicationDate') compatible = compatible && (article."$key" == data) //para evitar problema de lidar com data
            }
        }
        return compatible
    }

    static public void createArticle(String title, filename) {
        def cont = new PeriodicoController()
        cont.params << ArticleTestDataAndOperations.findArticleByTitle(title) << [file: filename]
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

    static public def path() {
        return new File(".").getCanonicalPath() + File.separator + "test" + File.separator + "files" + File.separator
    }
}
