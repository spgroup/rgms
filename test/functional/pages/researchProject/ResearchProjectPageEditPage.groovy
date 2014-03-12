package pages.researchProject

import pages.FormPage
import pages.GetPageTitle

/**
 * Created by Bruno Soares on 24/02/14.
 */
class ResearchProjectPageEditPage extends FormPage{
    static url = "researchProject/edit"

    static at = {
            GetPageTitle gp = new GetPageTitle()
            def currentReseachProject = gp.msg("default.researchProject.label")
            def currentTitle = gp.msg("default.edit.label", [currentReseachProject])
            title ==~ currentTitle
    }

    static content = {
        flashmessage {
            $("div", class: "message")
        }
    }
}
