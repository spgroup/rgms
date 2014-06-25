package steps

import rgms.publication.Tese
import rgms.publication.TeseController

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
}
