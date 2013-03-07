package pages

import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.parser.PdfTextExtractor
import geb.Page
import rgms.member.ResearchGroup

class ResearchGroupPage extends Page {
    static url = "researchGroup/show"

    static at = {
        title ==~ /Ver Research Group/
    }

    static content = {
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

    def comparePDF(String s)
    {
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

    def compareHTML(String s)
    {
        //TO DO
    }

    def compareXML(String s)
    {
        //TO DO
    }
}