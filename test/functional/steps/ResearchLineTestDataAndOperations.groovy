package steps

import rgms.publication.XMLController

/**
 * Created by Bruno Soares on 23/02/14.
 */
class ResearchLineTestDataAndOperations {
    static public void uploadResearchLine(filepath) {
        def cont = new XMLController()
        def xml = new File((String) filepath);
        def records = new XmlParser()
        cont.saveResearchLine(records.parse(xml));
        cont.response.reset()
    }
}
