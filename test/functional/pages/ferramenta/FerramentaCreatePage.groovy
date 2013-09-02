package pages.ferramenta

import pages.FormPage
import geb.Page

class FerramentaCreatePage extends FormPage {
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
        fillFerramentaTitle("Ferramenta Teste 1")
        fillFerramentaDescription("Description")
        clickCreateFerramenta()
        // Could parametrize, obtaining data from class TestDataAndOperations
    }

    def fillFerramentaDetailsWithoutTitle(name) {
        fillFerramentaFile(name)
        fillFerramentaDescription("Description")
        fillFerramentaWebsite("website")
        clickCreateFerramenta()
    }

    def createNewFerramenta(String title) {
        fillFerramentaTitle(title)
        fillFerramentaDescription("Description")
        fillFerramentaWebsite("website")
        clickCreateFerramenta()
    }

    def fillFerramentaTitle(title){
        $("form").title = title
    }

    def fillFerramentaDescription(description){
        $("form").description = "Description"
    }

    def fillFerramentaWebsite(website){
        $("form").website = "website"
    }

    def fillFerramentaFile(String name){
        def path = new File(".").getAbsolutePath() + File.separator + "test" + File.separator + "functional" + File.separator + "steps" + File.separator + name + ".pdf"
        $("form").file = path
    }

    def clickCreateFerramenta(){
        $("form").create().click()
    }

    def fillTitleWithMaxCaracteres(){
        $("form").title = "titulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotituloAAA"
    }
}
