package pages

import geb.Page
import rgms.publication.Periodico

class TechnicalReportShowPage extends Page {
	static url = "technicalReport/show/2"

	static at = {
		title ==~ /Ver TechnicalReport/
	}

	static content = {
	}

	def select(String e, v) {
        if (v == 'edit') {
			$("form").find(e, class: v).click()
           // assert withConfirm(true) { $("form").find(e, class: v).click() }
        } 
		//else {
        //    $("form").find(e, class: v).click()
        //}
    }
}
