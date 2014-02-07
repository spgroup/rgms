package steps

import rgms.publication.Dissertacao
import rgms.publication.Tese
import rgms.publication.TeseController

class ThesisOrDissertationTestDataAndOperations {

    static protected void createThesisOrDissertation(String title, filename, school, cont) {
        cont.params << [title: title, publicationDate: new Date(2013, 03, 02),
                school: school, address: "Boa Viagem", file: filename]
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
}
