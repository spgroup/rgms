package pages

import com.itextpdf.text.pdf.PdfReader
import geb.Page
import com.itextpdf.text.pdf.parser.PdfTextExtractor
import rgms.member.Member

class MemberPage extends Page {
    static url = "member/show"

    static at = {
        title ==~ /Ver Member/
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
        Member m = Member.findById(Integer.getInteger(s))
        def name = page.find(m.name)
        def university = page.find(m.university)
        def email = page.find(m.email)
        assert name != null
        assert university != null
        assert email != null
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