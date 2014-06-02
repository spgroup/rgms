package steps

import rgms.publication.BibtexGenerateFileController
import rgms.publication.Periodico
import rgms.publication.PublicationController

/**
 * Created by ELLIS on 16/05/2014.
 */

// #if($ResearchGate)
class ResearchGateTestDataAndOperations {

    static void exportFile(Periodico p1, Periodico p2) {
        BibtexGenerateFileController bibtexController = new BibtexGenerateFileController()
        String bibtex = bibtexController.memberPublications(PublicationController.getLoggedMember().id.intValue())
        // TODO criar um metodo para gerar o arquivo bibtex
        // TODO exportar o bibtex para o Research Gate
    }

    static boolean fileIsOnResearchGate(Periodico p) {
        // TODO implementar funcionalidade ResearchGate
        return false
    }

    static def getUserResearchGateCredentials() {
        // TODO implementar funcionalidade ResearchGate
        return null
    }
}
// #end