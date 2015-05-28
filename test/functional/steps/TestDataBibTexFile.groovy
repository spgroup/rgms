package steps

import rgms.publication.BibtexFile
import rgms.publication.BibtexFileController

class TestDataBibTexFile
{
    static public def openBibTexFile(String path){
        BibtexFileController bibtexFileController = new BibtexFileController()
        BibtexFile bibtexFile = bibtexFileController.transform(new File(path))
    }

    static public def createBibTexFile(String bibtexFormat){
        BibtexFileController bibtexFileController = new BibtexFileController()
        BibtexFile bibtexFile = bibtexFileController.transform(bibtexFormat)

        return bibtexFile
    }

    static public boolean checkValid(String bibtex){
        boolean valid = false

        /* Search for Commas */
        for (i in bibtex.each()){
            if (i == ',')
                valid = true
        }

        /* Search for bibtex tags that maybe are missing */
        //if (valid) {
            //def tags = ['author', 'title', 'journal']
            //for (i in bibtex.split(','))
        //}

        return valid
    }
}