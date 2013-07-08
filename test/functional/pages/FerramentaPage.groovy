package pages

import geb.Page

class FerramentaPage extends Page {
    static url = "ferramenta/list"

    static at = {
        title ==~ /Ferramenta Listagem/
    }

    static content = {
    }

    def selectNewFerramenta() {
        $('a.create').click()
    }

    def selectFerramenta(String s) {
        $('a', text: s).click()
    }
    def uploadWithoutFile(){
        $('input.save').click()
    }
}