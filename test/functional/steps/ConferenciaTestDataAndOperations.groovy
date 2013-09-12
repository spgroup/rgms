package steps

import rgms.publication.Conferencia
import rgms.publication.ConferenciaController
import rgms.publication.XMLController

/**
 * Created with IntelliJ IDEA.
 * User: Diogo
 * Date: 26/08/13
 * Time: 19:57
 * To change this template use File | Settings | File Templates.
 */
class ConferenciaTestDataAndOperations {

    static conferencias = [
            [title: "I International Conference on Software Engineering",
                    publicationDate: (new Date("12 October 2012")),
                    booktitle: "Software Engineering", pages: "20-120"],
            [title: "IV Conference on Software Product Lines",
                    publicationDate: (new Date("14 October 2012")),
                    booktitle: "Practices and Patterns", pages: "150-200"],
            [title: "V Conference on Software Product Lines",
                    publicationDate: (new Date("16 October 2012")),
                    booktitle: "Practices and Patterns", pages: "50-100"]
    ]


    static public void createConferencia(String title, String filename) {
        def cont = new ConferenciaController()
        cont.params << ConferenciaTestDataAndOperations.findConferenciaByTitle(title) << [file: filename]
        cont.request.setContent(new byte[1000])
        cont.create()
        cont.save()
        cont.response.reset()
    }

    static public boolean conferenciaCompatibleTo(conferencia, title) {
        def testConferencia = findConferenciaByTitle(title)
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

    static public void removeConferencia(String title) {
        def cont = new ConferenciaController()
        def date = new Date()
        Conferencia.findByTitle(title).delete(flush: true)
    }

    static public def findConferenciaByTitle(String title) {
        conferencias.find { conferencia ->
            conferencia.title == title
        }
    }

    static public void uploadConferencias(filename) {
        def cont = new XMLController()
        def xml = new File(filename);
        def records = new XmlParser()
        cont.saveConferencias(records.parse(xml));
        cont.response.reset()
    }

}
