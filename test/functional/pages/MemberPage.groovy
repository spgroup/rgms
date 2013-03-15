package pages

import com.itextpdf.text.pdf.PdfReader
import geb.Page
import com.itextpdf.text.pdf.parser.PdfTextExtractor
import org.xml.sax.InputSource
import rgms.member.Member

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory

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
        def downloadLink = $('form').find([title: "PDF"]).@href
        def xml = new XmlSlurper()
        xml.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false)
        def parser = xml.parse(downloadLink)
        Member m = Member.findById(Integer.getInteger(s))
        def name = parser.find(m.name)
        def university = parser.find(m.university)
        def email = parser.find(m.email)
        assert name != null
        assert university != null
        assert email != null
    }

    def compareXML(String s)
    {
        def downloadLink = $('form').find([title: "XML"]).@href
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder()
        Document doc = db.parse(downloadLink)
        Element langs = doc.getDocumentElement()
        NodeList list = langs.getElementsByTagName("name")
        Element name = (Element) list.item(0)
        NodeList list2 = langs.getElementsByTagName("university")
        Element university = (Element) list2.item(0)
        NodeList list3 = langs.getElementsByTagName("email")
        Element email = (Element) list3.item(0)
        Member m = Member.findById(Integer.getInteger(s))
        assert name.textContent == m.name
        assert university.textContent == m.university
        assert email.textContent == m.email
    }
}