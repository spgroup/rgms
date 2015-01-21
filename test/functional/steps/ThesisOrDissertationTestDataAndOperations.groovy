package steps

import rgms.publication.Dissertacao
import rgms.publication.Tese
import rgms.publication.TeseController

class ThesisOrDissertationTestDataAndOperations {

    static teses = [
            [title: "TCS-01", publicationDate: "01/02/2014", file: "TCS-01.pdf",
             researchLine: "Computation", school: "UFPE"
            ],
            [title: "TCS-02", publicationDate: "01/06/2014", file: "TCS-02.pdf",
             researchLine: "Statistics", school: "UFPE"
            ],
            [title: "TCS-03", publicationDate: "01/03/2014", file: "TCS-03.pdf",
             researchLine: "Algebra", school: "UFPE"
            ],
            [title: "TCS-04", publicationDate: "01/04/2014", file: "TCS-04.pdf",
             researchLine: "Mathmatics", school: "UFPE"
            ],
    ]

    static protected void createThesisOrDissertation(String title, filename, school, cont) {
        cont.params << [title: title, publicationDate: new Date(2013, 03, 02),
                school: school, address: "Boa Viagem", file: filename]
        cont.request.setContent(new byte[1000]) // Could also vary the request content.
        cont.create()
        cont.save()
        cont.response.reset()
    }

    static protected void deleteThesisOrDissertation(String title, cont) {
        def test
        if (cont instanceof TeseController) {
            test = Tese.findByTitle(title)
        } else {
            test = Dissertacao.findByTitle(title)
        }
        cont.params << [id: test.id]
        cont.delete()

    }
    
    static public def findByTitle(String title){
                teses.find{ tese ->
                        tese.title == title
                    }
            }
}
