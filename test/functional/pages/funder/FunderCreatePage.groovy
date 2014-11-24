package pages.funder

import pages.FormPage
import pages.GetPageTitle

/**
 * Created by Rubens on 24/02/14.
 */
class FunderCreatePage extends FormPage {
    static url = "funder/create"

    static at = {

        GetPageTitle gp = new GetPageTitle()
        def currentFunder = gp.msg("default.funder.label")
        def currentTitle = gp.msg("default.create.label", [currentFunder])

        title ==~ currentTitle
    }

    def fillFunderCode(String code, String name){
        $("form").code = code
        $("form").name = name
    }
    def selectSave(){
        $(class: 'save').click()
    }
}
