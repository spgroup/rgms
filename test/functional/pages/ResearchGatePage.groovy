package pages

import geb.Page

/**
 * Created by ELLIS on 20/05/2014.
 */
// #if($researchGate)
class ResearchGatePage extends Page {
    def titleName = /${(new GetPageTitle()).getMessageServerLocale("researchgate.title")}/
    static url = "bibtexGenerateFile/toResearchGate"

    static at = {
        title ==~ titleName
    }

    def fillCredentials() {
        $("input#username").text = "ellis"
        $("input#password").text = "123"
    }

    def clickOnExport() {
        $("#btnExport").click()
    }

    def consultOnResearchGate(string filename) {
        return ResearchGateTool.consult(filename);
    }
}
// #end