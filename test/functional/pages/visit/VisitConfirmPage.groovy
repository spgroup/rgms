package pages.visit

import geb.Page
import pages.GetPageTitle

class VisitConfirmPage extends Page {
	static url = "visit/confirm"

	static at = {
		//title ==~ /Confirmar Visitante/
		GetPageTitle gp = new GetPageTitle()
		def currentVisit = gp.msg("default.visit.label")
		def currentTitle = gp.msg("default.confirm.label", [currentVisit])

		//title ==~ currentTitle
	}

	static content = {
	}

  def clickOnYes() {
    $('input.save').click()
  }
	
	def clickOnNo() {
		$('input.show').click()
	}
}
