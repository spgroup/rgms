package steps

import rgms.publication.Dissertacao
import rgms.publication.Tese
import rgms.publication.TeseController

class ThesisOrDissertationTestDataAndOperations {

    static protected void createThesisOrDissertation(String title, filename = "Joee.pdf", school = "UFPE", address = "Boa Viagem") {
        def cont = new TeseController()
        cont.params << [title: title, publicationDate: new Date(2013, 03, 02),
                school: school, address: address, file: filename]
        cont.request.setContent(new byte[1000]) // Could also vary the request content.
        cont.create()
        cont.save()
        cont.response.reset()
    }

    static protected void deleteThesisOrDissertation(String title, cont) {
        def test
        if (cont instanceof TeseController) {
            test = Tese.findByTitle(title)
        } else {
            test = Dissertacao.findByTitle(title)
        }
        cont.params << [id: test.id]
        cont.delete()
    }

    static public void editThesisOrDissertation(String option, String value, String title){
        def cont = new TeseController()
        def thesis = Tese.findByTitle(title)
        if(option == 'file')
            thesis.file = value
        cont.params << thesis.properties
        cont.update()
    }
}
