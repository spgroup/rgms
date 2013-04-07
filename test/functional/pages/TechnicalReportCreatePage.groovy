package pages

import geb.Page

class TechnicalReportCreatePage extends Page {
	static url = "technicalReport/create"

	static at = {
		title ==~ /Criar Technical Report/
	}
}
