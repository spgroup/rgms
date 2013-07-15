package pages

import geb.Page

class TechnicalReportCreatePage extends Page {
	static url = "technicalReport/create"

	static at = {
		title ==~ /Criar TechnicalReport/
		institution != null
	}

	static content = {
		institution {
			$("input", id: "institution")
		}
	}

	def fillTechnicalReportDetails() {
		 def path = new File(".").getAbsolutePath() + File.separator + "test" + File.separator + "functional" + File.separator + "steps" + File.separator + "Joee.pdf"
		 fillTechnicalReportDetails(path, "Joe-E")
    }

    def fillTechnicalReportDetails(filename, title) {
        $("form").title = title
        $("form").institution = "UC Berkeley"
        $("form").file = filename
    }

    def selectCreateTechnicalReport() {
        $("input", name: "create").click()
    }
}
