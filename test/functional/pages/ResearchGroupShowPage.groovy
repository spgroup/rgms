package pages

import geb.Page

class ResearchGroupShowPage extends Page {
    static url = "researchGroup/show/1"

    static at = {

        title ==~ /Ver Grupo de Pesquisa/

    }

    static content = {
    }

    def selectEditResearchGroup() {
        $('a', name: 'botaoEditar').click()
    }

    def selectRemoveResearchGroup() {
        $('input.delete').click()
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
        /*
def downloadLink = $('form').find([title: "PDF"]).@href
def bytes = downloadBytes(downloadLink)
PdfReader reader = new PdfReader(bytes)
def page = PdfTextExtractor.getTextFromPage(reader, 1)
ResearchGroup r = ResearchGroup.findByName(s)
def name = page.find(r.name)
def description = page.find(r.description)
assert name != null
assert description != null       */
    }

    def compareHTML(String s) {
        $('form').find([title: "HTML"]).click()
        def downloadLink = "http://localhost:8080/rgms/jasper/?_format=HTML&_name=export+HTML&_file=report&member_id=1"
        /*
        def downloadLink = $('form').find([title: "PDF"]).@href
        def xml = new XmlSlurper()
        xml.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false)
        def parser = xml.parse(downloadLink)
        ResearchGroup r = ResearchGroup.findByName(s)
        def name = parser.find(r.name)
        def description = parser.find(r.description)
        assert name != null
        assert description != null   */
    }

    def compareXML(String s) {
        def downloadLink = "/Users/phmb/Downloads/export.xml"
        $('form').find([title: "XML"]).click()
        /*
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
        assert description.textContent == r.description    */
    }

}
