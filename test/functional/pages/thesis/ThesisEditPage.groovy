package pages.thesis

import geb.Page
import pages.GetPageTitle

/**
 * Created by Bruno Soares on 16/01/14.
 */
class ThesisEditPage extends Page {
    static url = "tese/edit/1"

    static at = {

        //title ==~ /Editar Orientation/

        GetPageTitle gp = new GetPageTitle()
        def currentTese = gp.msg("default.tese.label")
        def currentTitle = gp.msg("default.edit.label", [currentTese])

        title ==~ currentTitle
    }

    static content = {
        flashmessage {
            $("div", class: "message")
        }
    }

    def select(String s) {
        $("form").find("input", value: s).click()
    }
}
