package pages.visit

import geb.Page
import pages.GetPageTitle

class VisitCreatePage extends Page {
    static url = "visit/create"

    static at = {
        //title ==~ /Criar Visita/
        GetPageTitle gp = new GetPageTitle()
        def currentVisit = gp.msg("default.visit.label")
        def currentTitle = gp.msg("default.create.label", [currentVisit])

        title ==~ currentTitle 
    }

    static content = {
    }

    def clickOnCreate() {
        $('input.save').click()
    }

    def fillVisitDetails() {
        $("form").nameVisitor = "Visitor"
        //#if( $descricaovisita )
        $("form").description = "First Visit"
        //#end
        clickOnCreate()
    }
    
	def fillVisitDetails(String name) {
		$("form").nameVisitor = name
		//#if( $descricaovisita )
		$("form").description = "None"
		//#end
		clickOnCreate()
	}
}
