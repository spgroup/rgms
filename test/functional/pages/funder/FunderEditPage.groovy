package pages.funder

import pages.FormPage
import pages.GetPageTitle

/**
 * Created by Rubens on 24/02/14.
 */
class FunderEditPage extends FormPage {
    static url = "funder/edit"

    static at = {

        GetPageTitle gp = new GetPageTitle()
        def currentFunder = gp.msg("default.funder.label")
        def currentTitle = gp.msg("default.edit.label", [currentFunder])

        title ==~ currentTitle
    }
}
