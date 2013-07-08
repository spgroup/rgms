package pages

import geb.Page

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

    def comparePDF(String s) {
        $('form').find([title: "PDF"]).click()
        def downloadLink = "/Users/phmb/Downloads/export.pdf"
        /*def bytes = downloadBytes(downloadLink)
        PdfReader reader = new PdfReader(bytes)
        def pdftext = PdfTextExtractor.getTextFromPage(reader, 1)
        Member m = Member.findById(Integer.getInteger(s))
        def name = pdftext.find(m.name)
        def university = pdftext.find(m.university)
        def email = pdftext.find(m.email)
        assert name != null
        assert university != null
        assert email != null    */
    }

    def compareHTML(String s) {
        $('form').find([title: "HTML"]).click()
        def downloadLink = "http://localhost:8080/rgms/jasper/?_format=HTML&_name=export+HTML&_file=report&member_id=1"
        def xml = new XmlSlurper()
        xml.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false)
        /*def parser = xml.parse(downloadLink)
        Member m = Member.findById(Integer.getInteger(s))
        def name = parser.find(m.name)
        def university = parser.find(m.university)
        def email = parser.find(m.email)
        assert name != null
        assert university != null
        assert email != null*/
    }

    def compareXML(String s) {
        def downloadLink = "/Users/phmb/Downloads/export.xml"
        $('form').find([title: "XML"]).click()
        /*DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
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
        assert email.textContent == m.email*/
    }
}