package pages

import geb.Page
import pages.GetPageTitle

class ArticleCreatePage extends Page {
    static url = "periodico/create"

    static at = {
        //title ==~ /Criar Peri�dico/
        GetPageTitle gp = new GetPageTitle()
        def currentPeriodico = gp.msg("default.periodico.label")
        def currentTitle = gp.msg("default.create.label", [currentPeriodico])
        title ==~ currentTitle
        journal != null
    }

    def fillArticleDetails() {
        def path = new File(".").getCanonicalPath() + File.separator + "test" + File.separator + "files" + File.separator + "TCS.pdf"
        fillArticleDetails(path, "A theory of software product line refinement")
    }

    def fillArticleDetails(filename, title) {
        $("form").title = title
        $("form").journal = "Theoretical Computer Science"
        $("form").file = filename
        $("form").volume = 455
        $("form").number = 1
        $("form").pages = "2-30"
        // Could parametrize, obtaining data from class TestDataAndOperations
    }

    def selectCreateArticle() {
        $("input", name: "create").click()
    }
}
