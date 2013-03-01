package pages

import geb.Page
import rgms.publication.Periodico

class ArticleShowPage extends Page {
    static url = "periodico/show/1"

    static at = {
        title ==~ /Ver Periódico/
    }

    static content = {
    }

	def select(String e,v) {
		$("form").find(e, class: v).click()
	}
}