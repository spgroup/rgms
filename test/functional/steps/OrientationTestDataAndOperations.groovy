package steps

import rgms.member.Member
import rgms.member.Orientation
import rgms.member.OrientationController
import rgms.publication.XMLController

/**
 * Created with IntelliJ IDEA.
 * User: tasj
 * Date: 30/08/13
 * Time: 15:31
 * To change this template use File | Settings | File Templates.
 */
class OrientationTestDataAndOperations {

    static members = [
            [name: "Rodolfo Ferraz", username: "usernametest", email: "rodolfofake@gmail.com",
                    status: "Graduate Student", university: "UFPE", enabled: true
            ],
            [name: "Rebeca Souza", username: "rebecasouza", email: "rsa2fake@cin.ufpe.br",
                    status: "Graduate Student", university: "UFPE", enabled: true
            ],
            [name: "Rubens Lopes", username: "rlfs", email: "rlfsfake@cin.ufpe.br",
                    status: "Graduate Student", university: "UFPE", enabled: true
            ]]

    static orientations = [
            [tipo: "Mestrado", orientando: "Tomaz", tituloTese: "The Book is on the table", anoPublicacao: 2013, instituicao: "UFPE", orientador: (new Member(members[0]))]
    ]

    static public def findOrientationByTitle(String title) {
        orientations.find { orientation ->
            orientation.tituloTese == title
        }
    }

    static public void createOrientation(String tituloTese) {

        def cont = new OrientationController()
        def memberCreater = new Member(members[0])
        memberCreater.create()
        memberCreater.save()
        def member = Member.findByName(memberCreater.name)
        cont.params << [tipo: "Mestrado", orientando: "Tomaz", tituloTese: tituloTese, anoPublicacao: 2013, instituicao: "UFPE", orientador: member]
        cont.request.setContent(new byte[1000]) // Could also vary the request content.
        cont.create()
        cont.save()
        cont.response.reset()
    }

    static public void createOrientationWithMenber(String tituloTese, member) {

        def cont = new OrientationController()
        cont.params << [tipo: "Mestrado", orientando: "Tomaz", tituloTese: tituloTese, anoPublicacao: 2013, instituicao: "UFPE", orientador: new Member(member)]
        cont.request.setContent(new byte[1000]) // Could also vary the request content.
        cont.create()
        cont.save()
        cont.response.reset()
    }

    static public void removeOrientation(String tituloTese) {
        def testOrientation = Orientation.findByTituloTese(tituloTese)
        def cont = new OrientationController()
        cont.params << [id: testOrientation.id]
        cont.delete()
    }

    static public boolean OrientationCompatibleTo(orientation, title) {
        def testOrientation = findOrientationByTitle(title)
        def compatible = false
        if (testOrientation == null && orientation == null) {
            compatible = true
        } else if (testOrientation != null && orientation != null) {
            compatible = true
            testOrientation.each { key, data ->
                compatible = compatible && (orientation."$key" == data)
            }
        }
        return compatible
    }

    static public void uploadOrientation(filepath) {
        def cont = new XMLController()
        def xml = new File((String) filepath);
        def records = new XmlParser()
        cont.saveOrientations(records.parse(xml));
        cont.response.reset()
    }
}
