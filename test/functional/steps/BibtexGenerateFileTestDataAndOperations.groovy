package steps

import rgms.publication.BibtexGenerateFileController
import rgms.publication.PublicationController
import rgms.publication.ThesisOrDissertationController

import javax.servlet.http.HttpServletResponse

/**
 * Created by ELLIS on 21/05/2014.
 */
// #if($bibtexGenerateFile)
class BibtexGenerateFileTestDataAndOperations {

    static HttpServletResponse bibtexResponse = null;
    static String bibtexContent = null;

    static void checkPublicationInsideBibtex(String title, String filename) {
        assert bibtexResponse.getHeader("Content-disposition").contains("filename=" + filename)
        assert bibtexContent.contains(title)
    }
}
// #end