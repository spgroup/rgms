package pages

import geb.Page

class MemberPage extends Page {
    static url = "member/show"

    static at = {
        title ==~ /Ver Member/
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