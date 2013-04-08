package pages

import geb.Page
import rgms.publication.Periodico

class ArticleEditPage extends Page {
    static url = "periodico/edit/1"

    static at = {
        title ==~ /Editar Periódico/
    }

    static content = {
    }


	def edit(String novovalor){
		$("form").title = novovalor
	}
	
	def select(String s) {
		$("form").find("input", value: s).click()
	}
}