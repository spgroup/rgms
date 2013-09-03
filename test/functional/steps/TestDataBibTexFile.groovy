package steps

import rgms.publication.BibtexFile
import rgms.publication.BibtexFileController

class TestDataBibTexFile
{
    static public def openBibTexFile(String filename){
        def path = new File(".").getCanonicalPath() + File.separator + "test" + File.separator + "cucumber" + File.separator + "steps" + File.separator + filename
        BibtexFileController bibtexFileController = new BibtexFileController()
        BibtexFile bibtexFile = bibtexFileController.transform(new File(path))
    }
}