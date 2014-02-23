package pages

import geb.Page

class ResearchLinePage extends Page {
	static url = "researchLine/list"

	static at = {
		title ==~ /Research Line Listagem/
	}

	static content = {
	}

	def selectNewResearchLine() {
		$('a.create').click()
	}
	
	def visualizeResearchLine(String name)
	{
		$('table').find('a', text: name).click()		
	}

    def uploadWithoutFile(){
        $('input.save').click()
    }

    def hasErrorUploadXML() {
        GetPageTitle gp = new GetPageTitle()
        return gp.msg('default.xml.parserror.message') == $("div", class: "message", role: "status").text()
    }
}

