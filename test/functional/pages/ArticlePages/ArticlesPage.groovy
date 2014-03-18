package pages.ArticlePages

import geb.Page
import pages.GetPageTitle
import rgms.publication.Periodico

class ArticlesPage extends Page {
    static url = "periodico/list"


    static at = {
        //title ==~ /Periodico Listagem/
        GetPageTitle gp = new GetPageTitle()
        def currentPeriodico = gp.msg("default.periodico.label")
        def currentTitle = gp.msg("default.list.label", [currentPeriodico])

        title ==~ currentTitle
    }

    static content = {
    }

    def uploadWithoutFile() {
        $('input.save').click()
    }

    def selectNewArticle() {
        $('a.create').click()
    }

    def checkIfArticlesListIsEmpty() {
        def listDiv = $('div', id: 'list-periodico')
        def articleTable = (listDiv.find('table'))[0]
        def articleRows = articleTable.find('tbody').find('tr')
        def articleColumns = articleRows[0].find('td')

        assert articleColumns.size() < 23
    }

    /**
     * @author Guilherme
     */
    def selectViewArticle(title) {
        def listDiv = $('div', id: 'list-periodico')
        def articleTable = (listDiv.find('table'))[0]
        def articleRow = articleTable.find('tbody').find('tr')
        def showLink = articleRow.find('td').find([text: title])
        showLink.click()
    }

    /**
     * @author Guilherme
     */
    def checkArticleAtList(title, row) {
        def listDiv = $('div', id: 'list-periodico')
        def articleTable = (listDiv.find('table'))[0]
        def articleRows = articleTable.find('tbody').find('tr')
        def articleColumns = articleRows[row].find('td')

        def testarticle = Periodico.findByTitle(title)
        assert articleColumns[0].text() == testarticle.title
        assert articleColumns[2].text() == testarticle.file
        assert articleColumns[5].text() == testarticle.journal
    }
}
