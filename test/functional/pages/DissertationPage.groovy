package pages

import geb.Page

class DissertationPage extends Page {
    static url = "dissertacao/list"

    static at = {
        title ==~ /Dissertacao Listagem/
    }

    static content = {
    }

    def selectNewArticle() {
        $('a.create').click()
    }

    def selectDissertation(String s) {
        $('a', text: s).click()
    }
    def uploadWithoutFile(){
        $('input.save').click()
    }
}