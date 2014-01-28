package pages.news

import geb.Page
import pages.GetPageTitle

class NewsPage extends Page {
    static url = "news/list"

    static at = {
        GetPageTitle gp = new GetPageTitle()
        def currentReport = gp.msg("default.news.label")
        def currentTitle = gp.msg("default.list.label", [currentReport])
        title ==~ currentTitle
    }

    static content = {
    }

    def selectCreateNews() {
        $('a.create').click()
    }
    //#if ($Report && $HTML)
    def selectExportHTMLReport() {
        $('a.jasperButton', title: "HTML").click()
    }

    def canSelectExportHTMLReport() {
        $('a.jasperButton', title: "HTML").size() == 1
    }

    def reportContainsNews(String desc) {
        $("p span", text: desc).size() == 1
    }
    //#end

    def selectViewNew(title) {
        getRow()
        def showLink = getRow().find('td').find([text:title])
        showLink.click()
    }
}
