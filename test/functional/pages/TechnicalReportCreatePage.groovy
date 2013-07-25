package pages

class TechnicalReportCreatePage extends FormPage {
    static url = "technicalReport/create"

    static at = {
        //title ==~ /Criar TechnicalReport/

        GetPageTitle gp = new GetPageTitle()
        def currentReport = gp.msg("default.technicalReport.label")
        def currentTitle = gp.msg("default.create.label", [currentReport])
        title ==~ currentTitle

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

    def fillTechnicalReportDetails(filename, title, institution) {
        $("form").title = title
        $("form").institution = institution
        $("form").file = filename
    }

    def selectCreateTechnicalReport() {
        $("input", name: "create").click()
    }
}
