package pages

import geb.Page

/**
 * User: Raony Benjamim [RBAA]
 * Date: 29/08/13
 * Time: 23:10
 */
class BibtexImportPage extends Page {
    static url = "bibtexFile/home"

    static at = {
        title ==~ /BibTexImport Listagem/
    }

    def selectButton(String name) {
        $('form').find('a', text: name).click()
    }
}
