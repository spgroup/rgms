package pages

import geb.Page
import rgms.publication.Periodico

class TechnicalReportPage extends Page {
	static url = "technicalReport/list"

	static at = {
		title ==~ /TechnicalReport Listagem/
	}

	static content = {
	}


	def selectNewArticle() {
		$('a.create').click()
	}

	/**
	 * @author Guilherme e Felipe
	 */
	def selectViewTech(title) {
		def listDiv = $('div', id: 'list-technicalReport')
		def techTable = (listDiv.find('table'))[0]
		def techRow  = techTable.find('tbody').find('tr')
		def showLink = techRow.find('td').find([text:title])
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
