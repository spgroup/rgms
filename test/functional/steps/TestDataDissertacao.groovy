package steps

import rgms.publication.*

class TestDataDissertacao
{

    static public void createDissertacao(String title, filename, school) {
        ThesisOrDissertationTestDataAndOperations.createThesisOrDissertation(title,filename,school,new DissertacaoController())
    }


    static public void createDissertacaoWithotSchool(String title, filename) {
        def data = [title: title, publicationDate: new Date(2013, 03, 02),
                address: "Boa Viagem", file: filename]
        ThesisOrDissertationTestDataAndOperations.createThesisOrDissertation(new DissertacaoController(),data)
    }
    static public void createDissertacaoWithoutAddress(String title, filename) {
        def data = [title: title, publicationDate: new Date(2013, 03, 02),school: "UFPE", file: filename]
        ThesisOrDissertationTestDataAndOperations.createThesisOrDissertation(new DissertacaoController(),data)
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
        ThesisOrDissertationTestDataAndOperations.deleteThesisOrDissertation(new DissertacaoController(),Dissertacao.findByTitle(title))

    }

}