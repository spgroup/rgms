package steps

import rgms.publication.BibtexGenerateFileController
import rgms.publication.PublicationController
import rgms.publication.ThesisOrDissertationController

/**
 * Created by ELLIS on 21/05/2014.
 */
// #if($bibtexGenerateFile)
class BibtexGenerateFileTestDataAndOperations {

    static void checkPublicationInsideBibtex(String title, String filename) {
        BibtexGenerateFileController cont = new BibtexGenerateFileController()
        cont.params << [filename: filename]
        cont.request.setContent(new byte[1000])
        String content = cont.getBibTexFile();
        cont.response.reset()
        assert content.contains(title);
    }
}
// #end