package pages.news

/**
 * Created with IntelliJ IDEA.
 * User: dyego
 * Date: 09/01/14
 * Time: 18:30
 * To change this template use File | Settings | File Templates.
 */

import geb.Page
import pages.GetPageTitle

class NewsShowPage extends Page {
    static url = "news/show/1"

    static at = {
        //title ==~ /Ver Nóticias/
        GetPageTitle gp = new GetPageTitle()
        def currentNews = gp.msg("default.news.label")
        def currentTitle = gp.msg("default.show.label", [currentNews])

        title ==~ currentTitle
    }

    static content = {
    }

    def remove() {
        assert withConfirm(true) { $("form").find('input', class: 'delete').click() }
    }
}