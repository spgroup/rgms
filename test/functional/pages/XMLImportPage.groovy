package pages

import geb.Page

/**
 * User: Raony Benjamim [RBAA]
 * Date: 29/08/13
 * Time: 23:10
 */
class XMLImportPage extends Page {
    static url = "XML/home"

    static at = {
        //title ==~ /XMLImport Listagem/

        GetPageTitle gp = new GetPageTitle()
        def currentBookChapter = gp.getMessageServerLocale("xml.label")
        def currentTitle = currentBookChapter + " " + gp.getMessageServerLocale("default.button.list.label")

        title ==~ currentTitle
    }

    def selectButton(String name) {
        $('form').find('a', text: name).click()
    }

    def uploadWithoutFile(){
        $('input.save').click()
    }
}
