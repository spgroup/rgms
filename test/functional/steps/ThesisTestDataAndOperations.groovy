package steps

import rgms.publication.Tese
import rgms.publication.TeseController

class ThesisTestDataAndOperations {

    static public void createTese(String title, filename, school) {
        ThesisOrDissertationTestDataAndOperations.createThesisOrDissertation(title, filename, school, new TeseController())
    }

    static public void deleteTeseByTitle(String title) {
        ThesisOrDissertationTestDataAndOperations.deleteThesisOrDissertation(new TeseController(),Tese.findByTitle(title))
    }

}
