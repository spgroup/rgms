package pages.news

import pages.FormPage
import pages.GetPageTitle

class NewsCreatePage extends FormPage {
    static url = "news/create"

    static at = {
        GetPageTitle gp = new GetPageTitle()
        def currentReport = gp.msg("default.news.label")
        title ==~ gp.msg("default.create.label", [currentReport])
    }

    static content = {
    }

    def fillNewDetails(description) {
        $("form").description = description
    }

    def clickOnCreate() {
        $("input", name: "create").click()
    }
}
