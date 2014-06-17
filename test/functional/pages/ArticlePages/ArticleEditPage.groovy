//#if($Article)
package pages.ArticlePages

import geb.Page
import pages.GetPageTitle

class ArticleEditPage extends Page {
    static url = "periodico/edit/1"

    static at = {
        //title ==~ /Editar Peri�dico/
        GetPageTitle gp = new GetPageTitle()
        def currentPeriodico = gp.msg("default.periodico.label")
        def currentTitle = gp.msg("default.edit.label", [currentPeriodico])

        title ==~ currentTitle
    }

    def edit(String novovalor, filename) {
        $("form").title = novovalor
        $("form").file = filename
    }

    def select(String s) {
        $("form").find("input", value: s).click()
    }
}
//#end
