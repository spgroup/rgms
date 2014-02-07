package steps

class ThesisOrDissertationTestDataAndOperations {

    static protected void createThesisOrDissertation(String title, filename, school, cont) {
        def data = [title: title, publicationDate: new Date(2013, 03, 02),
                school: school, address: "Boa Viagem", file: filename]
        createThesisOrDissertation(cont,data)
    }

    static protected void createThesisOrDissertation(cont,data) {
        cont.params << data
        cont.request.setContent(new byte[1000]) // Could also vary the request content.
        cont.create()
        cont.save()
        cont.response.reset()
    }

    static protected void deleteThesisOrDissertation(cont,test) {
        cont.params << [id: test.id]
        cont.delete()

    }
}
