package steps

import rgms.publication.BibtexFile
import rgms.publication.BibtexFileController

class TestDataBibTexFile
{
    static public def openBibTexFile(String path){
        BibtexFileController bibtexFileController = new BibtexFileController()
        BibtexFile bibtexFile = bibtexFileController.transform(new File(path))
    }
}