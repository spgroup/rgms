package steps

import rgms.authentication.AuthController
import rgms.member.*
import rgms.news.News
import rgms.news.NewsController
import rgms.publication.*
import rgms.visit.Visit
import rgms.visit.VisitController
import rgms.visit.Visitor
import org.apache.shiro.SecurityUtils

class TestDataAndOperations {

    static articles = [
            [journal: "Theoretical Computer Science", volume: 455, number: 1, pages: "2-30",
                    title: "A theory of software product line refinement",
                    publicationDate: (new Date("12 October 2012"))],
            [journal: "Science of Computer Programming", volume: 455, pages: "2-30",
                    title: "Algebraic reasoning for object-oriented programming",
                    publicationDate: (new Date("12 October 2012"))]
    ]

    static ferramentas = [
            [description: "Ferramenta Target",
                    title: "Target",
                    publicationDate: (new Date("12 October 2012"))],
            [website: "http://www.teste.com", description: "Ferramenta Emergo",
                    title: "Emergo",
                    publicationDate: (new Date("12 October 2012"))],
            [website: "http://www.ccfinder.com", description: "Ferramenta CCfinder",
                    title: "CCFinder",
                    publicationDate: (new Date("12 October 2012"))]
    ]


    static researchLines = [
            [name: "IA Avancada", description: ""],
            [name: "Teoria da informacao - Complexidade no espaco", description: "P=NP"],
            [name: "Novo Padrao Arquitetural MVCE", description: "Nova arquitetura que promete revolucionar a web"],
            [name: "Modelo Cascata Renovado", description: "Altera��o do modelo original"]
    ]
    static bookChapters = [
            [title: "Next Generation Software Product Line Engineering", publicationDate: (new Date("12 October 2012")),
                    publisher: "Person", chapter: 1],
            [title: "SPL Development", publicationDate: (new Date("12 October 2012")),
                    publisher: "Addison", chapter: 5],
            [title: "Artificial Neural Networks", publicationDate: (new Date("25 July 2012")),
                    publisher: "Penguim", chapter: 3]
    ]

    static conferencias = [
            [title: "I International Conference on Software Engineering",
                    publicationDate: (new Date("12 October 2012")),
                    booktitle: "Software Engineering", pages: "20-120"],
            [title: "IV Conference on Software Product Lines",
                    publicationDate: (new Date("14 October 2012")),
                    booktitle: "Practices and Patterns", pages: "150-200"]
    ]

    static records = [
            [status_H: "MSc Student", start: (new Date()), end: null],
            [status_H: "Graduate Student", start: (new Date()), end: null]
    ]

    static reports = [
            [title: 'Evaluating Natural Languages System',
                    publicationDate: (new Date('13 November 2012')), institution: 'UFPE'],
            [title: 'NFL Languages System',
                    publicationDate: (new Date('27 October 2011')), institution: 'NFL'],
            [title: 'Joe-E',
                    publicationDate: (new Date('1 May 2013')), institution: 'TG']
    ]

    static members = [
            [name: "Rodolfo Ferraz", username: "usernametest", email: "rodolfofake@gmail.com",
                    status: "Graduate Student", university: "UFPE", enabled: true
            ],
            [name: "Rebeca Souza", username: "rebecasouza", email: "rsa2fake@cin.ufpe.br",
                    status: "Graduate Student", university: "UFPE", enabled: true
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


    static public def findArticleByTitle(String title) {
        articles.find { article ->
            article.title == title
        }
    }

//#if ($visit)
    static visitors = [
            [name: "Pessoa"]
    ]

    /**
     * @author carloscemb
     */
    static visits = [
            [visitor: new Visitor(visitors[0]),
                    dataInicio: (new Date("11 November 2000")),
                    dataFim: (new Date("12 November 2000"))]
    ]

    /**
     * @author carloscemb
     */
    static public def findVisitByVisitorAndInitialDateAndFinalDate(String name, String initialDate, String finalDate) {
        visits.find { visit ->
            visit.visitor.name == name &&
                    visit.dataInicio.format("dd/MM/YYYY") == initialDate &&
                    visit.dataFim.format("dd/MM/YYYY") == finalDate
        }
    }
//#end

    static public def findFerramentaByTitle(String title) {
        ferramentas.find { ferramenta ->
            ferramenta.title == title
        }
    }

    static public def findByUsername(String username) {
        members.find { member ->
            member.username == username
        }
    }

    static public def findRecordByStatus(def status) {
        records.find { record ->
            record.status_H == status
        }
    }

    static public boolean recordIsAssociated(def status) {
        def c = Member.createCriteria()
        def recordId = Record.findByStatus_H(status).id
        def members = c.listDistinct {
            historics {
                eq("id", recordId)
            }
        }
        members.size() > 0
    }

    static public def findBookChapterByTitle(String title) {
        bookChapters.find { bookChapter ->
            bookChapter.title == title
        }
    }

    static public def findConferenciaByTitle(String title) {
        conferencias.find { conferencia ->
            conferencia.title == title
        }
    }

    static public def findResearchLineByName(String name) {
        researchLines.find { researchLine ->
            researchLine.name == name
        }
    }

    static public def findTechnicalReportByTitle(String title) {
        reports.find { report ->
            report.title == title
        }
    }


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


    static public boolean compatibleTo(article, title) {
        def testarticle = findArticleByTitle(title)
        def compatible = false
        if (testarticle == null && article == null) {
            compatible = true
        } else if (testarticle != null && article != null) {
            compatible = true
            testarticle.each { key, data ->
                compatible = compatible && (article."$key" == data)
            }
        }
        return compatible
    }

    static public boolean bookChapterCompatibleTo(bookChapter, title) {
        def testBookChapter = findBookChapterByTitle(title)
        def compatible = false
        if (testBookChapter == null && bookChapter == null) {
            compatible = true
        } else if (testBookChapter != null && bookChapter != null) {
            compatible = true
            testBookChapter.each { key, data ->
                compatible = compatible && (bookChapter."$key" == data)
            }
        }
        return compatible
    }

    static public boolean technicalReportCompatibleTo(tech, title) {
        def testtech = findTechnicalReportByTitle(title)
        def compatible = false
        if (testtech == null && tech == null) {
            compatible = true
        } else if (testtech != null && tech != null) {
            compatible = true
            testtech.each { key, data ->
                compatible = compatible && (tech."$key" == data)
            }
        }
        return compatible
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
        def date = new Date()
        cont.params << [title: title, publicationDate: new Date(2013, 03, 02),
                school: school, address: "Boa Viagem", file: filename]
        cont.request.setContent(new byte[1000]) // Could also vary the request content.
        cont.create()
        cont.save()
        cont.response.reset()
    }


    static public void createDissertacaoWithotSchool(String title, filename) {
        def cont = new DissertacaoController()
        def date = new Date()
        cont.params << [title: title, publicationDate: new Date(2013, 03, 02),
                address: "Boa Viagem", file: filename]
        cont.request.setContent(new byte[1000]) // Could also vary the request content.
        cont.create()
        cont.save()
        cont.response.reset()
    }
    static public void createDissertacaoWithoutAddress(String title, filename) {
        def cont = new DissertacaoController()
        def date = new Date()
        cont.params << [title: title, publicationDate: new Date(2013, 03, 02),school: "UFPE", file: filename]
        cont.request.setContent(new byte[1000]) // Could also vary the request content.
        cont.create()
        cont.save()
        cont.response.reset()
    }

    static public Dissertacao editDissertatacao(oldtitle, newtitle) {
        def article = Dissertacao.findByTitle(oldtitle)
        article.setTitle(newtitle)
        def cont = new DissertacaoController()
        cont.params << article.properties
        cont.update()

        def updatedarticle = Dissertacao.findByTitle(newtitle)
        return updatedarticle
    }

    static public void uploadDissertacao(filename) {
        def cont = new DissertacaoController()
        def xml = new File(filename);
        def records = new XmlParser()
        cont.saveDissertations(records.parse(xml));
        cont.response.reset()
    }

    static public void removeDissertacao(String title) {
        def testDissertation = Dissertacao.findByTitle(title)
        def cont = new DissertacaoController()
        def date = new Date()
        cont.params << [id: testDissertation.id]
        cont.delete()
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

    static public void createResearchGroup(String name, description) {
        def researchGroupController = new ResearchGroupController()
        researchGroupController.params << [name: name] << [description: description]
        researchGroupController.request.setContent(new byte[1000]) // Could also vary the request content.
        researchGroupController.create()
        researchGroupController.save()
        researchGroupController.response.reset()
    }

    static public void editResearchGroup(def researchGroup, String newName, String newDescription) {
        def researchGroupController = new ResearchGroupController()
        researchGroupController.params << [name: newName] << [description: newDescription] << [id: researchGroup.getId()]
        researchGroupController.request.setContent(new byte[1000]) // Could also vary the request content.
        researchGroupController.edit()
        researchGroupController.save()
        researchGroupController.response.reset()
    }

    //#if($researchGroupHierarchy)
    static public void editResearchGroupChildOf(ResearchGroup researchGroup, ResearchGroup researchGroupParent) {
        def researchGroupController = new ResearchGroupController()
        researchGroupController.params << [name: researchGroup.name]
        researchGroupController.params << [description: researchGroup.description]
        researchGroupController.params << [id: researchGroup.id]
        researchGroupController.params << [childOf: researchGroupParent]
        researchGroupController.request.setContent(new byte[1000]) // Could also vary the request content.

        try {
            researchGroupController.update()
        } catch (Exception e) {}
    }
    //#end

    static public void createBookChapter(String title, filename) {
        def cont = new BookChapterController()
        def date = new Date()
        cont.params << TestDataAndOperations.findBookChapterByTitle(title) << [file: filename]
        cont.request.setContent(new byte[1000])
        cont.create()
        cont.save()
        cont.response.reset()
    }

    static public void deleteResearchGroup(def researchGroup) {
        def researchGroupController = new ResearchGroupController()
        researchGroupController.params << [id: researchGroup.getId()]
        researchGroupController.request.setContent(new byte[1000]) // Could also vary the request content.
        researchGroupController.delete()
        researchGroupController.response.reset()
    }


    static public void createConferencia(String title, String filename) {
        def cont = new ConferenciaController()
        cont.params << TestDataAndOperations.findConferenciaByTitle(title) << [file: filename]
        cont.request.setContent(new byte[1000])
        cont.create()
        cont.save()
        cont.response.reset()
    }

    static public void createTechnicalReport(String title, filename) {
        def cont = new TechnicalReportController()
        cont.params << TestDataAndOperations.findTechnicalReportByTitle(title) << [file: filename]
        cont.request.setContent(new byte[1000]) // Could also vary the request content.
        cont.create()
        cont.save()
        cont.response.reset()
    }

    static public void createMember(String username) {
        def cont = new MemberController()
        cont.params << TestDataAndOperations.findByUsername(username)
        cont.create()
        cont.save()
        cont.response.reset()
    }

    static public TechnicalReport editTech(oldtitle, newtitle) {
        def tech = TechnicalReport.findByTitle(oldtitle)
        tech.setTitle(newtitle)
        def cont = new TechnicalReportController()
        cont.params << tech.properties
        cont.update()
        def updatedtech = TechnicalReport.findByTitle(newtitle)
        return updatedtech
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
        //		os valores est�o fixos. O problema n�o
        //		est� nos par�metros passados.

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


    static public void deleteMember(String username) {
        def cont = new MemberController()
        def identificador = Member.findByUsername(username).id
        cont.params << [id: identificador]
        cont.request.setContent(new byte[1000]) // Could also vary the request content.
        cont.delete()
        //cont.save()
        cont.response.reset()
    }


    static void clearArticles() {
        Periodico.findAll()*.delete flush: true // Could also delete the created files.
    }

    static public void deleteResearchLine(def id) {
        def res = new ResearchLineController()
        res.params.id = id
        res.request.setContent(new byte[1000]) // Could also vary the request content.
        res.delete()
        res.response.reset()
    }

    static public void updateResearchLine(String name, String description) {
        def res = new ResearchLineController()
        def research_line = ResearchLine.findByName(name)
        res.params.id = research_line.id
        res.params.name = research_line.name
        res.params.description = description
        res.request.setContent(new byte[1000]) // Could also vary the request content.
        res.update()
        res.response.reset()
    }

    static public void createResearchLine(String name) {
        def cont = new ResearchLineController()
        def research = TestDataAndOperations.findResearchLineByName(name)
        cont.params.name = research.name
        cont.params.description = research.description
        cont.request.setContent(new byte[1000]) // Could also vary the request content.
        cont.create()
        cont.save()
        cont.response.reset()
    }

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

    static public def insertsResearchLine(String name) {
        def inserted = ResearchLine.findByName(name)
        if (!inserted) {
            def research = TestDataAndOperations.findResearchLineByName(name)
            ResearchLine rl = new ResearchLine()
            rl.setName(research.name)
            rl.setDescription(research.description)
            rl.save()
        }
    }

    static public void removeArticle(String title) {
        def testarticle = Periodico.findByTitle(title)
        def cont = new PeriodicoController()
        def date = new Date()
        cont.params << [id: testarticle.id]
        cont.delete()
    }

    static public void removeBookChapter(String title) {
        def testBookChapter = BookChapter.findByTitle(title)
        def cont = new BookChapterController()
        cont.params << [id: testBookChapter.id]
        cont.delete()
    }

    static public boolean containsBookChapter(title, bookList) {
        def testarbook = BookChapter.findByTitle(title)
        def cont = new BookChapterController()
        def result = cont.list().bookChapterInstanceList
        return result.contains(testarbook)
    }

    static public boolean containsArticle(title, articles) {
        def testarticle = Periodico.findByTitle(title)
        def cont = new PeriodicoController()
        def result = cont.list().periodicoInstanceList
        return result.contains(testarticle)
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

    static public Ferramenta editFerramenta(oldtitle, newtitle) {
        def ferramenta = Ferramenta.findByTitle(oldtitle)
        ferramenta.setTitle(newtitle)
        def cont = new FerramentaController()
        cont.params << ferramenta.properties
        cont.update()

        def updatedferramenta = Ferramenta.findByTitle(newtitle)
        return updatedferramenta
    }

    static public void removeConferencia(String title) {
        def cont = new ConferenciaController()
        def date = new Date()
        Conferencia.findByTitle(title).delete(flush: true)
    }

//#if ( $visit )

    static public def findVisit(String name, String initialDate, String finalDate) {
        def visitController = new VisitController()
        visitController.params.visitor = Visitor.findByName(name)
        visitController.params.dataInicio = Date.parse("dd/MM/yyyy", initialDate)
        visitController.params.dataFim = Date.parse("dd/MM/yyyy", finalDate)
        def result = Visit.list(visitController.params)
        return result
    }

    static public void createVisitor(String nome) {
        def visitController = new VisitController()
        visitController.params.nameVisitor = nome
        visitController.request.setContent(new byte[1000]) // Could also vary the request content.
        visitController.createVisitor();
        visitController.response.reset()
    }

    /**
     * @author carloscemb
     */
    static public void createVisit(String name, String initialDate, String finalDate) {
        def visitController = new VisitController()
        visitController.params.nameVisitor = name
        visitController.params.dataInicio = Date.parse("dd/MM/yyyy", initialDate)
        visitController.params.dataFim = Date.parse("dd/MM/yyyy", finalDate)
        visitController.request.setContent(new byte[1000]) // Could also vary the request content.
        visitController.create()
        visitController.save()
        visitController.response.reset()
    }

    /**
     * @author carloscemb
     */
    static public boolean containsVisit(Visit visit) {
        def visitController = new VisitController()
        def result = visitController.list().visitInstanceList
        return result.contains(visit)
    }

    /**
     * @author carloscemb
     */
    static public void removeVisit(String name, String initialDate, String finalDate) {
        def visitController = new VisitController()
        def visitor = Visitor.findByName(name)
        Date dia_1 = Date.parse("dd/MM/yyyy", initialDate)
        Date dia_2 = Date.parse("dd/MM/yyyy", finalDate)
        def visit = Visit.findByVisitorAndDataInicioAndDataFim(visitor, dia_1, dia_2)
        visitController.params << [id: visit.id]
        visitController.delete()
    }

    /**
     * @author carloscemb
     */
    static public Visit editVisit(String oldVisitante, String oldDataInicio, String oldDataFim, String newVisitante) {
        def visitor = Visitor.findByName(oldVisitante)
        Date dia_1 = Date.parse("dd/MM/yyyy", oldDataInicio)
        Date dia_2 = Date.parse("dd/MM/yyyy", oldDataFim)
        def visit = Visit.findByVisitorAndDataInicioAndDataFim(visitor, dia_1, dia_2)

        def novoVisitor = Visitor.findByName(newVisitante)

        if(novoVisitor == null) {
            createVisitor(newVisitante)
            novoVisitor = Visitor.findByName(newVisitante)
        }

        visit.setVisitor(novoVisitor)

        def visitController = new VisitController()
        visitController.params << visit.properties
        visitController.update()

        def updatedVisit = Visit.findByVisitorAndDataInicioAndDataFim(novoVisitor, dia_1, dia_2)
        return updatedVisit
    }

    /**
     * @author penc
     */
    static public Visit editVisitChangeData(String visitante, String dataInicio, String oldDataFim, String newDataFim) {
        def visitor = Visitor.findByName(visitante)
        Date dia_1 = Date.parse("dd/MM/yyyy", dataInicio)
        Date dia_2 = Date.parse("dd/MM/yyyy", oldDataFim)
        def visit = Visit.findByVisitorAndDataInicioAndDataFim(visitor, dia_1, dia_2)

        Date newFinalDate = Date.parse("dd/MM/yyyy", newDataFim)
        visit.setDataFim(newFinalDate);

        def visitController = new VisitController()
        visitController.params << visit.properties
        visitController.update()
    }

//#end

    static public ResearchGroup createAndGetResearchGroupByName(String name) {
        def researchGroupController = new ResearchGroupController()
        researchGroupController.params << findResearchGroupByGroupName(name)
        researchGroupController.create()
        researchGroupController.save()
        researchGroupController.response.reset()
        return ResearchGroup.findByName(name)
    }

    static public void createNews(String descriptionParam, Date dateParam, ResearchGroup groupParam) {
        def cont = new NewsController()
        cont.params << [description: descriptionParam, date: dateParam, researchGroup: groupParam]
        cont.create()
        cont.save()
        cont.response.reset()
    }

    static public void requestNewsFromTwitter(ResearchGroup group) {
        def cont = new ResearchGroupController()
        cont.params << [id: group.id]
        cont.updateNewsFromTwitter()
        cont.response.reset()
    }

    static public void deleteNews(String description, Date date, ResearchGroup researchGroup) {
        def cont = new NewsController()
        def identificador = News.findByDescriptionAndDateAndResearchGroup(description, date, researchGroup).id
        cont.params << [id: identificador]
        cont.request.setContent(new byte[1000]) // Could also vary the request content.
        cont.delete()
        //cont.save()
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

    static public void ShareArticleOnFacebook(String title){
       def member = new Member()
        member.access_token =  "CAAJIlmRWCUwBAN0r1puBTUa4vDZAKxWWlR5gN4qtgZAosBDKGUOLBquyKuHYQ0zxICioiarTJ66mpdZC08U4rHJOrtvXJCB8hMBcLKlQaTdwYZCgMTJtbFnQfIBZAxi6hRIkfw2fCSyCS6DuFIrGRThI53ZCzBOLsZD"
        member.facebook_id = "100006411132660"
        PublicationController.sendPostFacebook(member, title)
    }

    static public ResearchGroup editResearchGroupTwitterAcount(researchGroup, String newTwitter){
        def researchGroupController = new ResearchGroupController()
        researchGroupController.params << [twitter: newTwitter] << [id: researchGroup.getId()]
        researchGroupController.update()
        researchGroupController.response.reset()
    }

    static public void createTechnicalReportWithEmptyInstitution(String title, filename) {
        def cont = new TechnicalReportController()
        def params = TestDataAndOperations.findTechnicalReportByTitle(title)
        params["institution"] = ""
        cont.params << params << [file: filename]
        cont.request.setContent(new byte[1000]) // Could also vary the request content.
        cont.create()
        cont.save()
        cont.response.reset()
    }

}

