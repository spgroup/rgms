package steps

import rgms.member.*
import rgms.news.News
import rgms.news.NewsController
import rgms.publication.*
import org.apache.shiro.SecurityUtils
import steps.ThesisOrDissertationTestDataAndOperations

class TestDataAndOperations {

    static researchLines = [
            [name: "IA Avancada", description: ""],
            [name: "Redes Avancadas", description: "Redes de Computadores Avancadas"],
            [name: "Teoria da informacao - Complexidade no espaco", description: "P=NP"],
            [name: "Novo Padrao Arquitetural MVCE", description: "Nova arquitetura que promete revolucionar a web"],
            [name: "Modelo Cascata Renovado", description: "Altera��o do modelo original"]
    ]

    static members = [
            [name: "Rodolfo Ferraz", username: "usernametest", email: "rodolfofake@gmail.com",
                    status: "Graduate Student", university: "UFPE", enabled: true
            ],
            [name: "Rebeca Souza", username: "rebecasouza", email: "rsa2fake@cin.ufpe.br",
                    status: "Graduate Student", university: "UFPE", enabled: true
            ]]

    static records = [
            [status_H: "MSc Student", start: (new Date()), end: null],
            [status_H: "Graduate Student", start: (new Date()), end: null]
    ]

    static members = [
            [name: "Rodolfo Ferraz", username: "usernametest", email: "rodolfofake@gmail.com",
                    status: "Graduate Student", university: "UFPE", enabled: true
            ],
            [name: "Rebeca Souza", username: "rebecasouza", email: "rsa2fake@cin.ufpe.br",
                    status: "Graduate Student", university: "UFPE", enabled: true
            ],
            [name: "Invalid User", username: "userwithinvalidphone", email: "uwif@cin.ufpe.br",
                    status: "Graduate Student", university: "UFPE", enabled: true, phone: "invalidphone"
            ]]

    static researchgroups = [
            [name: "SPG",
                    description: "SW Productivity Research Group",
                    childOf: null]
            ,
            [name: "taes",
                    description: "grupo de estudos",
                    childOf: null]]

    static memberships = [
            [member: (new Member(members[0])),
                    researchGroup: (new ResearchGroup(researchgroups[0])),
                    dateJoined: (new Date(2012, 03, 01)),
                    dateLeft: (new Date(2012, 06, 01))]
            ,
            [member: (new Member(members[1])),
                    researchGroup: (new ResearchGroup(researchgroups[0])),
                    dateJoined: (new Date(2012, 03, 01)),
                    dateLeft: (new Date(2012, 06, 01))]]


    static public def openBibTexFile(String path){
        BibtexFileController bibtexFileController = new BibtexFileController()
        BibtexFile bibtexFile = bibtexFileController.transform(new File(path))
    }

    static public def findRecordByStatus(def status) {
        records.find { record ->
            record.status_H == status
        }
    }

    static public boolean recordIsAssociated(def status, def shallBe = true) {
        def recordId = Record.findByStatus_H(status).id
        RecordController.recordHasMembers(recordId) == shallBe
    }

   /* static public def findResearchLineByName(String name) {
        researchLines.find { researchLine ->
            researchLine.name == name
        }
    }*/

    static public def findMembershipByResearchGroupName(String groupname) {
        memberships.find { membership ->
            membership.researchGroup.name == groupname
        }
    }

    static public def findResearchGroupByGroupName(String groupname) {
        researchgroups.find { group ->
            group.name == groupname
        }
    }



    static public boolean memberCompatibleTo(member, username) {
        def testmember = findByUsername(username)
        def compatible = false
        if (testmember == null && member == null) {
            compatible = true
        } else if (testmember != null && member != null) {
            compatible = true
            testmember.each { key, data ->
                compatible = compatible && (member."$key" == data)
            }
        }
        return compatible
    }

    static public void createDissertacao(String title, filename, school) {
        def cont = new DissertacaoController()
        ThesisOrDissertationTestDataAndOperations.createThesisOrDissertation(title, filename, school, cont)
    }

    static public void uploadFerramenta(filepath) {
        def cont = new FerramentaController()
        def xml = new File((String) filepath);
        def records = new XmlParser()
        cont.saveTools(records.parse(xml));
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

    static public void createArticle(String title, filename) {
        def cont = new PeriodicoController()
        def date = new Date()
        cont.params << TestDataAndOperations.findArticleByTitle(title) << [file: filename]
        cont.request.setContent(new byte[1000]) // Could also vary the request content.
        cont.create()
        cont.save()
        cont.response.reset()
    }

    static public void createFerramenta(String title, filename) {
        def cont = new FerramentaController()
        def date = new Date()
        cont.params << TestDataAndOperations.findFerramentaByTitle(title) << [file: filename]
        cont.request.setContent(new byte[1000]) // Could also vary the request content.
        cont.create()
        cont.save()
        cont.response.reset()
    }

    static public void createResearchGroup(String groupname) {
        def cont = new ResearchGroupController()
        cont.params << TestDataAndOperations.findResearchGroupByGroupName(groupname)
        cont.request.setContent(new byte[1000]) // Could also vary the request content.
        cont.create()
        cont.save()
        cont.response.reset()
    }


    static public void createMembership(String username, String rgroup, String date1, String date2) {
        //TODO Deveria pegar os dados dos parametros, mas
        //		esta dando problema na criacao. Entao para
        //		simplificar o entendimento do problema,
        //		os valores est?o fixos. O problema n?o
        //		est? nos par?metros passados.

        def cont = new MembershipController()

        cont.params << [member: (new Member(members[0])),
                researchGroup: (new ResearchGroup(name: "taes", description: "grupo de estudos", childOf: null)),
                dateJoined: (new Date(2012, 03, 01)), dateLeft: (new Date(2012, 06, 01))]
        cont.request.setContent(new byte[1000]) // Could also vary the request content.
        cont.create()
        cont.save()
        cont.response.reset()
    }

    static public void deleteMembership(String username, String rgroup, String date1, String date2) {
        java.text.DateFormat df = new java.text.SimpleDateFormat("dd-MM-yyyy");
        def cont = new MembershipController()
        def identificador = Membership.findByMemberAndResearchGroupAndDateJoinedAndDateLeft(Member.findByUsername(username),
                ResearchGroup.findByName(rgroup), df.parse(date1), df.parse(date2)).id
        cont.params << [id: identificador]
        cont.request.setContent(new byte[1000]) // Could also vary the request content.
        cont.delete()
        //cont.save()
        cont.response.reset()
    }

    /*static public void deleteResearchLine(def id) {
        def res = new ResearchLineController()
        res.params.id = id
        res.request.setContent(new byte[1000]) // Could also vary the request content.
        res.delete()
        res.response.reset()
    } */

    /*static public void updateResearchLine(String name, String description) {
        def res = new ResearchLineController()
        def research_line = ResearchLine.findByName(name)
        res.params.id = research_line.id
        res.params.name = research_line.name
        res.params.description = description
        res.request.setContent(new byte[1000]) // Could also vary the request content.
        res.update()
        res.response.reset()
    } */

 /*   static public void createResearchLine(String name) {
        def cont = new ResearchLineController()
        def research = TestDataAndOperations.findResearchLineByName(name)
        cont.params.name = research.name
        cont.params.description = research.description
        cont.request.setContent(new byte[1000]) // Could also vary the request content.
        cont.create()
        cont.save()
        cont.response.reset()
    } */

    static public void deleteRecord(def id) {
        def rec = new RecordController()
        rec.params.id = id
        rec.request.setContent(new byte[1000]) // Could also vary the request content.
        rec.delete()
        rec.response.reset()
    }

    static public void updateRecord(def status, String end) {
        def record = Record.findByStatus_H(status)
        def rec = new RecordController()
        rec.params.id = record.id
        rec.params.start = record.start
        rec.params.status_H = record.status_H
        rec.params.end = Date.parse("dd/mm/yyyy", end)
        rec.request.setContent(new byte[1000]) // Could also vary the request content.
        rec.update()
        rec.response.reset()
    }

    static public def createRecord(def status) {
        def cont = new RecordController()
        def record = TestDataAndOperations.findRecordByStatus(status)
        cont.params.status_H = record.status_H
        cont.params.start = record.start
        cont.params.end = record.end
        cont.create()
        cont.save()
        cont.response.reset()

    }

    static public def insertsRecord(String status) {
        def inserted = Record.findByStatus_H(status)
        if (!inserted) {
            def record = TestDataAndOperations.findRecordByStatus(status)
            Record r = new Record()
            r.setStatus_H(record.status_H)
            r.setStart(r.start)
            r.setEnd(r.end)
            r.save()
        }
    }


   /* static public def insertsResearchLine(String name) {
>>>>>>> HEAD~5
        def inserted = ResearchLine.findByName(name)
        if (!inserted) {
            //def research = TestDataAndOperations.findResearchLineByName(name)
            ResearchLine rl = new ResearchLine()
            rl.setName(name)
            rl.setDescription(description)
            rl.save()
        }
    }*/


    static public boolean containsMember(username, members) {
        def testmember = Member.findByUsername(username)
        def cont = new MemberController()
        def result = cont.list().memberInstanceList
        return result.contains(testmember)
    }

    static public Periodico editArticle(oldtitle, newtitle) {
        def article = Periodico.findByTitle(oldtitle)
        article.setTitle(newtitle)
        def cont = new PeriodicoController()
        cont.params << article.properties
        cont.update()
		def updatedarticle = Periodico.findByTitle(newtitle)
		return updatedarticle
    }

    static public ResearchGroup createAndGetResearchGroupByName(String name) {
        return createAndGetResearchGroupByNameWithTwitter(name,null)
    }

    static public ResearchGroup createAndGetResearchGroupByNameWithTwitter(String name, String twitter) {
        def researchGroupController = new ResearchGroupController()
        researchGroupController.params << findResearchGroupByGroupName(name)
        if(twitter != null)
            researchGroupController.params << [twitter: twitter]
        researchGroupController.create()
        researchGroupController.save()
        researchGroupController.response.reset()
        return ResearchGroup.findByName(name)
    }

    static public void requestNewsFromTwitter(ResearchGroup group) {
        def cont = new ResearchGroupController()
        cont.params << [id: group.id]
        cont.updateNewsFromTwitter()
        cont.response.reset()
    }

    //não funciona
    static public void editResearchGroupTwitter(researchGroup, String newTwitter) {
        def researchGroupController = new ResearchGroupController()
        researchGroupController.params << [twitter: newTwitter] << [id: researchGroup.getId()]
        researchGroupController.edit()
        researchGroupController.save()
        researchGroupController.response.reset()
    }

    //mapmf_tasj

    //orientation
    static orientations = [
            [tipo: "Mestrado", orientando: "Tomaz", tituloTese: "The Book is on the table", anoPublicacao: 2013, instituicao: "UFPE", orientador: (new Member(members[0]))]
    ]

    static public def findOrientationByTitle(String title) {
        orientations.find { orientation ->
            orientation.tituloTese == title
        }
    }


    static public void removeOrientation(String tituloTese) {

        def testOrientation = TestDataAndOperations.findOrientationByTitle(tituloTese)
        def cont = new OrientationController()
        cont.params << [id: testOrientation.id]
        cont.delete()
    }

 static public ResearchGroup editResearchGroupTwitterAcount(researchGroup, String newTwitter){
        def researchGroupController = new ResearchGroupController()
        researchGroupController.params << [twitter: newTwitter] << [id: researchGroup.getId()]
        researchGroupController.update()
        researchGroupController.response.reset()
        return researchGroup
    }

    static public String getTestFilesPath(String filename){
        new File(".").getCanonicalPath() + File.separator + "test" + File.separator + "functional" + File.separator + "steps" + File.separator + filename
    }
    //mapmf_tasj





    static public void createOrientation(String tituloTese) {

        def cont = new OrientationController()
        cont.params << [tipo: "Mestrado", orientando: "Tomaz", tituloTese: tituloTese, anoPublicacao: 2013, instituicao: "UFPE", orientador: (new Member(members[0]))]
        cont.request.setContent(new byte[1000]) // Could also vary the request content.
        cont.create()
        cont.save()
        cont.response.reset()
    }


    //article

    static public def path(){
        return new File(".").getCanonicalPath() + File.separator + "test" + File.separator + "files" + File.separator
      }
}
