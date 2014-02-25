package pages.funder

import pages.FormPage
import pages.GetPageTitle

/**
 * Created by Rubens on 24/02/14.
 */
class FunderPage extends FormPage {
    static url = "funder/list"

    static at = {

        GetPageTitle gp = new GetPageTitle()
        def currentFunder = gp.msg("default.funder.label")
        def currentTitle = gp.msg("default.list.label", [currentFunder])

        title ==~ currentTitle
    }

    def selectNewFunder(){
        $('a', class: 'create').click()
    }
}
