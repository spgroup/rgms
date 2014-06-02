package pages.ArticlePages

import geb.Page
import pages.GetPageTitle
import rgms.publication.Periodico

class ArticlesReportPage extends Page {
    static url = "periodico/report"

    static at = {
        GetPageTitle gp 		= new GetPageTitle()
        def currentPeriodico 	= gp.msg("default.periodico.label")
        def currentTitle 		= gp.msg("default.report.label", [currentPeriodico])

        title ==~ currentTitle
    }

    static content = {
    }

    def checkArticleAtReport(title, row) {
        def reportDiv 		= $('div', id: 'report-periodico')
        def articleTable 	= (reportDiv.find('table'))[0]
        def reportRows 		= articleTable.find('tbody').find('tr')

        def testarticle = Periodico.findByTitle(title)
		assert (reportRows[1].find('td'))[1].text() == testarticle.title
		assert (reportRows[3].find('td'))[1].text() == testarticle.journal
    }
}
