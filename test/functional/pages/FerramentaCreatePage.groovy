package pages

import geb.Page

class FerramentaCreatePage extends Page {
    static url = "ferramenta/create"

    static at = {
        title ==~ /Criar Ferramenta/
    }

    static content = {
        /*journal {
            $("input", id: "journal")
        }*/
    }

    def fillFerramentaDetailsWithoutWebsite() {
        $("form").title = "Ferramenta Teste 1"
        $("form").description = "Description"
        $("form").create().click()
        // Could parametrize, obtaining data from class TestDataAndOperations
    }

    // Fill form means submit form too
    def fillFerramentaDetails() {
        fillFerramentaTitle("CCFinder")
        fillFerramentaDetailsWithoutTitle()
    }

    def fillFerramentaTitle(title){
        $("form").title = title
    }

    def fillFerramentaDetailsWithoutTitle() {
        def path = new File(".").getAbsolutePath() + File.separator + "test" + File.separator + "functional" + File.separator + "steps" + File.separator + "CCFinder.pdf"
        $("form").description = "Description"
        $("form").website = "website"
        $("form").file = path
        $("form").create().click()
    }

    def createNewFerramenta(String title) {
        $("form").title = title
        $("form").description = "Description"
        $("form").website = "website"
        $("form").create().click()
    }

    def createNewFerramentaWithoutInformation() {
        $("form").create().click()
    }

    def fillTitleWithMaxCaracteres(){
        $("form").title = "titulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotituloAAA"
    }

}
