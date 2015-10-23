package steps

import rgms.publication.Tese
import rgms.publication.TeseController
import rgms.publication.XMLController

class ThesisTestDataAndOperations {

    static public void createTese(String title, filename, school) {
        def cont = new TeseController()
        ThesisOrDissertationTestDataAndOperations.createThesisOrDissertation(title, filename, school, cont)
    }

    static public void deleteTeseByTitle(String title) {
        def cont = new TeseController()
        ThesisOrDissertationTestDataAndOperations.deleteThesisOrDissertation(title, cont)
    }

    static public Tese editThesis(title, newtitle) {
        def thesis = Tese.findByTitle(title)
        thesis.setTitle(newtitle)
        def controller = new TeseController()
        controller.params << thesis.properties
        controller.update()

        def updatedThesis = Tese.findByTitle(newtitle)
        return updatedThesis
    }

    static public void uploadThesis(filename) {
        def controller = new XMLController()
        def xmlFile = new File(filename);
        def xmlParser = new XmlParser()
        controller.saveThesis(xmlParser.parse(xmlFile))
        controller.response.reset()
    }
}
