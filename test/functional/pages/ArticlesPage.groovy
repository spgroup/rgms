package pages

import geb.Page
import rgms.publication.Periodico

class ArticlesPage extends Page {
    static url = "periodico/list"


	static at = {
		title ==~ /Periodico Listagem/
	}

	static content = {
	}


	def selectNewArticle() {
		$('a.create').click()
	}
   

	/**
	 * @author Guilherme
	 */
	def selectViewArticle(title) {
		def listDiv = $('div', id: 'list-periodico')
		def articleTable = (listDiv.find('table'))[0]
		def articleRow  = articleTable.find('tbody').find('tr')
		def showLink = articleRow.find('td').find([text:title])
		showLink.click()
	}

	/**
	 * @author Guilherme
	 */
	def checkArticleAtList(title,row){
		def listDiv = $('div', id: 'list-periodico')
		def articleTable = (listDiv.find('table'))[0]
		def articleRows  = articleTable.find('tbody').find('tr')
		def articleColumns = articleRows[row].find('td')

		def testarticle = Periodico.findByTitle(title)
		assert articleColumns[0].text() == testarticle.title
		assert articleColumns[2].text() == testarticle.file
		assert articleColumns[4].text() == testarticle.journal
	}
}
