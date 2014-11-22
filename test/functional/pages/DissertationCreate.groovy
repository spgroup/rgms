package pages

class DissertationCreate extends FormPage {
    static url = "dissertacao/create"

    static at = {
        title ==~ /Criar Dissertacao/
    }

    static content = {
        /*journal {
            $("input", id: "journal")
        }*/
    }

    def fillDissertationDetailsWithoutFile() {
        $("form").title = "Dissertacao Teste 1"
        $("form").school = "WebSite"
        $("form").address = "Description"
        $("form").create().click()
        // Could parametrize, obtaining data from class TestDataAndOperations
    }

    def fillDissertationDetailsWithFile(title, filename) {
        $("form").title = title
        $("form").file = filename
        $("form").school = "WebSite"
        $("form").address = "Description"
        $("form").create().click()
    }

    def fillDissertationDetailsWithFile(filename) {
        $("form").title = "Dissertacao Teste 1"
        $("form").file = filename
        $("form").school = "WebSite"
        $("form").address = "Description"
        $("form").create().click()
    }
    	
    def currentSchool() {
        $("form").school
    }
	
}
