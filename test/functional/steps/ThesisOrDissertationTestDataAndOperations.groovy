package steps

class ThesisOrDissertationTestDataAndOperations {

    static protected void createThesisOrDissertation(String title, filename, school, cont) {
        def date = new Date()
        cont.params << [title: title, publicationDate: new Date(2013, 03, 02),
                school: school, address: "Boa Viagem", file: filename]
        cont.request.setContent(new byte[1000]) // Could also vary the request content.
        cont.create()
        cont.save()
        cont.response.reset()
    }

}
