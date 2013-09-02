package pages

import geb.Page
import rgms.publication.ResearchLine

class ResearchLineVisualizePage extends Page {
	static url = "researchline/show"

	static at = {
		title ==~ /Ver Research Line/
	}

	static content = {
	}
	
	def checkResearchLineDetails (String name)
	{
		def research = ResearchLine.findByName(name)
		assert research != null
		def name_html = $('ol').find("span[aria-labelledby='name-label']").text()
		def description_html = $('ol').find("span[aria-labelledby='description-label']").text()
		assert (research.name == name_html) && (research.description == description_html)
		
	}
	
	def editResearchLine()
	{
		$('a.edit').click()
	}
}



