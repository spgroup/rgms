package pages

import geb.Page

/**
 * Created by ELLIS on 20/05/2014.
 */
// #if($researchGate)
class ResearchGatePage extends Page {
    static url = "https://www.researchgate.net/home.Home.html"

    static at = {

    }

    def fillCredentials() {
        $(".login-btn a").click()
        $("form", name: "loginForm").login = "evc2@cin.ufpe.br"
        $("form", name: "loginForm").password = "teste123"
    }

    def select(String s) {
        $('input', value: s).click()
    }

    def clickOnExport() {
        $("#btnExport").click()
    }

    def consultOnResearchGate(String filename) {
        return ResearchGateTool.consult(filename);
    }
}
// #end