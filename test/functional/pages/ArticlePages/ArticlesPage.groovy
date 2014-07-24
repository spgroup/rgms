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

	def selectViewReports() {
		def listAnchors  = $('a.list')
		def reportAnchor = listAnchors[1]
		reportAnchor.click()
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
		def articleColumns = this.getArticleColumns(row)
		def testarticle = Periodico.findByTitle(title)
		assert articleColumns[1].text() == testarticle.title
		assert articleColumns[3].text() == testarticle.file
		assert articleColumns[6].text() == testarticle.journal
	}

	def selectOrderBy(sortType){
		switch (sortType) {
			case 'title':
				$('a[href="/rgms/periodico/list?sort=title&max=10&order=asc"]').click()
				break
			case 'publication date':
				$('a[href="/rgms/periodico/list?sort=publicationDate&max=10&order=asc"]').click()
				break
		}
	}

	def checkOrderedBy(sortType){
		def firstArticleColumns 	= this.getArticleColumns(0)
		def secondArticleColumns 	= this.getArticleColumns(1)
		switch (sortType) {
			case 'title':
				assert firstArticleColumns[0].text().compareTo(secondArticleColumns[0].text()) < 0
				break
			case 'publication date':
				assert firstArticleColumns[1].text().compareTo(secondArticleColumns[1].text()) < 0
				break
		}
	}

	def getArticleColumns(row){
		def listDiv = $('div', id: 'list-periodico')
		def articleTable = (listDiv.find('table'))[0]
		def articleRows = articleTable.find('tbody').find('tr')
		def articleColumns = articleRows[row].find('td')
		return articleColumns
	}

	def selectViewArticleList() {
		def listAnchors  = $('a.list')
		def listAnchor = listAnchors[0]
		listAnchor.click()
	}

	def fillAndSelectFilter(authorName){
		$("form").authorName = authorName
		$("input", name: "buttonFilterByAuthor").click()
	}

	def checkFilteredBy(authorName){
		def listDiv = $('div', id: 'list-periodico')
		def articleTable = (listDiv.find('table'))[0]
		def articleRows = articleTable.find('tbody').find('tr')
		for (row in articleRows) {
			def articleColumns = row.find('td')
			if(!articleColumns[5].text().contains(authorName))
				return false
		}
		return true
	}

	def selectRemoveMultipleArticles() {
		assert withConfirm(true) {
			$("input", id: "removeMultiple").click()
		}
	}

	def markArticles(){
		def listcheckbox  = $('input', type:'checkbox')
		listcheckbox[0].click()
		listcheckbox[2].click()
	}
}
