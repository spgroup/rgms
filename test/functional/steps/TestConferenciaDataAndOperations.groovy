package steps

import rgms.publication.Conferencia
import rgms.publication.ConferenciaController

class TestConferenciaDataAndOperations {

    static conferencias = [
            [title: "I International Conference on Software Engineering",
                    publicationDate: (new Date("12 October 2012")), researchLine: "Software Product Lines",
                    booktitle: "Software Engineering", pages: "20-120"],

            [title: "IV Conference on Software Product Lines",
                    publicationDate: (new Date("14 October 2012")), researchLine: "Software Product Lines",
                    booktitle: "Practices and Patterns", pages: "150-200"]

    ]


    static public def findByTitle(String title) {
        conferencias.find { conferencia ->
            conferencia.title == title
        }
    }

    static public boolean compatibleTo(conferencia, title) {
        def testConferencia = findByTitle(title)
        def compatible = false
        if (testConferencia == null && conferencia == null) {
            compatible = true
        } else if (testConferencia != null && conferencia != null) {
            compatible = true
            testConferencia.each { key, data ->
                compatible = compatible && (conferencia."$key" == data)
            }
        }
        return compatible
    }

    static public void createConferencia (String title, String filename){
        def cont = new ConferenciaController()
        def date = new Date()
        cont.params << TestConferenciaDataAndOperations.findByTitle(title) << [file:  filename]
        cont.request.setContent(new byte[1000])
        cont.create()
        cont.save()
        cont.response.reset()
    }

    static public void removeConferencia (String title){
        def cont = new ConferenciaController()
        def date = new Date()
        Conferencia.findByTitle(title).delete(flush:true)
    }
}
