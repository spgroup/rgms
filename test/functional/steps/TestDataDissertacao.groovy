package steps

import rgms.member.Member
import rgms.publication.*

class TestDataDissertacao
{

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