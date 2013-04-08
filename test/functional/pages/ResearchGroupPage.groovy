package pages

import geb.Page
import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.parser.PdfTextExtractor
import geb.Page
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.xml.sax.InputSource
import rgms.member.Member
import rgms.member.ResearchGroup

import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory

class ResearchGroupPage extends Page{
	static url = "researchGroup/list"
	
		static at = {
			title ==~ /Research Group Listagem/
			
		}
	
		static content = {
		}
	
		def selectNewResearchGroup() {
			$('a.create').click()
		}
		
		def showResearchGroup(String a){
			$('a', text: a).click()
		}

    def checkPdf() {
        def pdf = $('form').find([title: "PDF"])
        assert pdf != null
    }

    def checkHtml() {
        def html = $('form').find([title: "HTML"])
        assert html != null
    }

    def checkXml() {
        def xml = $('form').find([title: "XML"])
        assert xml != null
    }

    def comparePDF(String s) {
        def downloadLink = $('form').find([title: "PDF"]).@href
        def bytes = downloadBytes(downloadLink)
        PdfReader reader = new PdfReader(bytes)
        def page = PdfTextExtractor.getTextFromPage(reader, 1)
        ResearchGroup r = ResearchGroup.findByName(s)
        def name = page.find(r.name)
        def description = page.find(r.description)
        assert name != null
        assert description != null
    }

    def compareHTML(String s) {
        def downloadLink = $('form').find([title: "PDF"]).@href
        def xml = new XmlSlurper()
        xml.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false)
        def parser = xml.parse(downloadLink)
        ResearchGroup r = ResearchGroup.findByName(s)
        def name = parser.find(r.name)
        def description = parser.find(r.description)
        assert name != null
        assert description != null
    }

    def compareXML(String s) {
        def downloadLink = $('form').find([title: "XML"]).@href
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance()
        DocumentBuilder db = dbf.newDocumentBuilder()
        Document doc = db.parse(downloadLink)
        Element langs = doc.getDocumentElement()
        NodeList list = langs.getElementsByTagName("name")
        Element name = (Element) list.item(0)
        NodeList list2 = langs.getElementsByTagName("description")
        Element description = (Element) list2.item(0)
        ResearchGroup r = ResearchGroup.findByName(s)
        assert name.textContent == r.name
        assert description.textContent == r.description
    }
}
