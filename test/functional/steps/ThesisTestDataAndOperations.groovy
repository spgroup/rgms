package steps

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

}
