package pages

import geb.Page

/**
 * Created by ELLIS on 20/05/2014.
 */
// #if($ResearchGate)
class ResearchGatePage extends Page {
    static url = "bibtexGenerateFile/toResearchGate"

    static at = {
        title ==~ /Exportar para Research Gate/
    }

    def fillCredentials() {
        $("input#username").text = "ellis"
        $("input#password").text = "123"
    }

    def clickOnExport() {
        $("#btnExport").click()
    }
}
// #end