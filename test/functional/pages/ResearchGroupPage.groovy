package pages

import geb.Page

class ResearchGroupPage extends Page {
    static url = "researchGroup/show"

    static at = {
        title ==~ /Ver Research Group/
    }

    static content = {
    }

    def exportToPdf() {
        $('form').find([format: "PDF"]).click()
    }

    def exportToHtml() {
        $('form').find([format: "HTML"]).click()
    }

    def exportToXml() {
        $('form').find([format: "XML"]).click()
    }
}