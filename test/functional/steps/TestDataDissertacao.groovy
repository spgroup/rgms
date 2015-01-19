package steps

import rgms.member.Member
import rgms.publication.*

class TestDataDissertacao
{
    static dissertacoes = [
            [title: "Micro Development", publicationDate: (new Date("12 October 2012")),
             publisher: "Carl", volume: 1, pages: "20"],
            [title: "SQL Development", publicationDate: (new Date("12 October 2012")),
             publisher: "Ian", volume: 2, pages: "541"],
            [title: "Data Base", publicationDate: (new Date("25 July 2012")),
             publisher: "John", volume: 3, pages: "272"]
    ]

    static public def findByTitle(String title) {
        dissertacoes.find { dissertacao ->
            dissertacao.title == title
        }
    }

    static public def canDownloadFile(String filename){
        if(filename == null || filename.isEmpty()) assert false
        int p = filename.lastIndexOf('.')
        assert (p != (filename.length()-1) )
    }

    static public def findAllByTitle(title){
        dissertacoes.findAll { dissertacao ->
            dissertacao.title == title
        }
    }


    static public void createDissertacao(String title, filename, school) {
        def cont = new DissertacaoController()
        ThesisOrDissertationTestDataAndOperations.createThesisOrDissertation(title,filename,school,cont)
    }


    static public void createDissertacaoWithotSchool(String title, filename) {
        def cont = new DissertacaoController()
        def date = new Date()
        cont.params << [title: title, publicationDate: new Date(2013, 03, 02),
                address: "Boa Viagem", file: filename]
        cont.request.setContent(new byte[1000]) // Could also vary the request content.
        cont.create()
        cont.save()
        cont.response.reset()
    }
    static public void createDissertacaoWithoutAddress(String title, filename) {
        def cont = new DissertacaoController()
        def date = new Date()
        cont.params << [title: title, publicationDate: new Date(2013, 03, 02),school: "UFPE", file: filename]
        cont.request.setContent(new byte[1000]) // Could also vary the request content.
        cont.create()
        cont.save()
        cont.response.reset()
    }

    static public Dissertacao editDissertacao(oldtitle, newtitle) {
        def article = Dissertacao.findByTitle(oldtitle)
        article.setTitle(newtitle)
        def cont = new DissertacaoController()
        cont.params << article.properties
        cont.update()

        def updatedarticle = Dissertacao.findByTitle(newtitle)
        return updatedarticle
    }

    static public void uploadDissertacao(filename) {
        def cont = new XMLController()
        def xml = new File(filename);
        def records = new XmlParser()
        cont.saveDissertations(records.parse(xml));
        cont.response.reset()
    }

    static public void removeDissertacao(String title) {
        def testDissertation = Dissertacao.findByTitle(title)
        def cont = new DissertacaoController()
        def date = new Date()
        cont.params << [id: testDissertation.id]
        cont.delete()
    }

}