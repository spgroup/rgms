package pages.funder

import pages.FormPage
import pages.GetPageTitle

/**
 * Created by Rubens on 24/02/14.
 */
class FunderShowPage extends FormPage {
    static url = "funder/show"

    static at = {

        GetPageTitle gp = new GetPageTitle()
        def currentFunder = gp.msg("default.funder.label")
        def currentTitle = gp.msg("default.show.label", [currentFunder])

        title ==~ currentTitle
    }
}
