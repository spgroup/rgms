package pages

import geb.Page
import org.codehaus.groovy.grails.web.context.ServletContextHolder 

class ThesisCreatePage extends Page {
	static url = "tese/create"

	static at = {
        GetPageTitle gp = new GetPageTitle()
        def currentTese = gp.msg("default.tese.label")
        def currentTitle = gp.msg("default.create.label", [currentTese])
        title ==~ currentTitle
	}

	static content = {
		/*journal {
			$("input", id: "journal")
		}*/
	}

	def fillThesisDetails() {
    	def absolutePath = ServletContextHolder.servletContext.getRealPath("/test/functional/steps/NewthesisGUI.txt")
    	absolutePath = absolutePath.replace("\\", "/").replaceAll("/web-app", "")
        $("form").file = absolutePath
        fillSomeThesisDetails()
    }

    def fillSomeThesisDetails() {
        $("form").title = "Tese001"
        $("form").publicationDate_day = 20
        $("form").publicationDate_month = 05
        $("form").publicationDate_year = 1998
        $("form").school = "UFPE"
        $("form").address = "Recife"
        $("input", id: "create").click()
    }
	
}
