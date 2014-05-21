package steps

import rgms.publication.ThesisOrDissertationController
// #if($bibtexGenerateFile)
/**
 * Created by ELLIS on 21/05/2014.
 */
class BibtexGenerateFileTestDataAndOperations {

    static void createThesis(String title) {
        ThesisOrDissertationController cont = new ThesisOrDissertationController()
        cont.params << [title: title, publicationDate: new Date(2013, 03, 02),
                        school: "school", address: "Boa Viagem", file: "filename"]
        cont.request.setContent(new byte[1000]) // Could also vary the request content.
        cont.createThesisOrDissertation(title, cont.params)
        cont.saveThesisOrDissertation(title, cont.params)
        cont.response.reset()
    }

    static void checkPublicationInsideBibtex(String title, String filename) {
        // TODO implementar criacao de arquivo Bibtex
        assert false
    }
}
// #end