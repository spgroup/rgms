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

    static content = {
        //readFlashMessage(){ $("div .message").text() }
        //readErrorsMessage() { $("div.errors").text() }
    }

    String readFlashMessage(){
        String str = $("div .message").text().toString()
        return str
    }

    String readErrorsMessage() {
        return $("div .errors").text()
    }

    def selectButton(String name) {
        $('form').find('a', text: name).click()
    }

    def uploadWithoutFile(){
        $('input.save').click()
    }

    def selectFile(){
        $("fileInput").value("C:\\fakepath\\curriculo5.xml")
    }

    def uploadClick(){
        $('input.save').click()
    }

    boolean isRequiredEnabledOnToleranceSelect(){
        return $("#toleranceSelect").getAttribute("required")
    }

    def setToleranceValue(int value) {
        $("#toleranceSelect").value(value)
    }
}
