package pages

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
        $("form").title = "Ferramenta Teste 1"
        $("form").description = "Description"
        $("form").create().click()
        // Could parametrize, obtaining data from class TestDataAndOperations
    }

    def fillFerramentaDetails() {
        def path = new File(".").getAbsolutePath() + File.separator + "test" + File.separator + "functional" + File.separator + "steps" + File.separator + "CCFinder.pdf"
        fillFerramentaDetails(path, "CCFinder")
    }

    def fillFerramentaDetails(filename, title) {
        $("form").title = title
        $("form").description = "Description"
        $("form").website = "website"
        $("form").file = filename
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

}
