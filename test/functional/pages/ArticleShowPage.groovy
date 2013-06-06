package pages

import geb.Page
import rgms.publication.Periodico

class ArticleShowPage extends Page {
	static url = "periodico/show/1"

	static at = {
		//title ==~ /Ver Periódico/
		GetPageTitle gp = new GetPageTitle()
		def currentPeriodico = gp.msg("default.periodico.label")
		def currentTitle = gp.msg("default.show.label", [currentPeriodico])
		
		title ==~ currentTitle
	}

	static content = {
	}

	def select(String e,v) {
		if(v == 'delete'){
			assert withConfirm(true) { $("form").find(e, class: v).click() }
		} else {
			$("form").find(e, class: v).click()
		}
	}
}