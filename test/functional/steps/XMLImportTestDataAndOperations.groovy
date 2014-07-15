package steps

import org.springframework.mock.web.MockMultipartFile
import org.springframework.mock.web.MockMultipartHttpServletRequest
import rgms.XMLService
import rgms.authentication.User
import rgms.member.Orientation
import rgms.publication.Conferencia
import rgms.publication.Periodico
import rgms.publication.ResearchLine
import rgms.publication.ResearchLineController
import rgms.publication.XMLController
import rgms.researchProject.ResearchProject

/**
 * Created by Thaís Burity on 02/06/2014.
 */
class XMLImportTestDataAndOperations {

    static configureFileName(filename){
        String path = "test" + File.separator + "files" + File.separator + filename
    }

    static addJournalPublication(pubName, journalName){
        Periodico p = new Periodico(ArticleTestDataAndOperations.findArticleByTitle(pubName))
        p.journal = journalName
        p.save(flush: true)
    }

    static public void uploadPublications(controller, filename) {
        def mockRequest = new MockMultipartHttpServletRequest()
        controller.metaClass.request = mockRequest
        def uploadedFile = new File(filename)
        mockRequest.addFile(new MockMultipartFile("file", uploadedFile.bytes))
        controller.upload()
    }

    private static getRootNode(filename){
        def xml = new File(filename)
        def cv = new XmlParser().parse(xml)
    }

    static extractJournalsFromFile(filename, name){
        def xmlFile = getRootNode(filename)
        def publishedArticles = xmlFile.depthFirst().findAll{ it.name() == 'ARTIGO-PUBLICADO' }
        def journalArticles = []

        for (int i = 0; i < publishedArticles?.size(); ++i) {
            def newJournal = XMLService.saveNewJournal(publishedArticles, i, name)
            if(newJournal) journalArticles += newJournal
        }

        return journalArticles
    }

    static extractConferencesFromFile(String filename, String name){
        def xmlFile = getRootNode(filename)
        def conferencePublications = xmlFile.depthFirst().findAll{ it.name() == 'TRABALHO-EM-EVENTOS' }
        def conferences = []

        for (Node currentNode : conferencePublications) {
            def newConference = XMLService.saveNewConferencia(currentNode, name);
            if(newConference) conferences += newConference
        }

        return conferences
    }

    static boolean checkJournal(journalArticles, pubName, journalName) {
        def result = false
        if(journalArticles){
            def searched
            if(journalName){
                searched = journalArticles.findAll{ it.title == pubName && it.journal == journalName }
            }
            else{
                searched = journalArticles.findAll{ it.title == pubName }
            }
            if (searched) result = true
        }
        return result
    }

    static boolean checkJournalWithPages(journalArticles, pubName, journalName, pages) {
        def result = false
        if(journalArticles){
            def searched = journalArticles.findAll{
                it.title==pubName && it.journal == journalName && it.pages==pages
            }
            if (searched) result = true
        }
        return result
    }

    static boolean checkConference(conferences, pubName, confName) {
        def result = false
        if(conferences) {
            def searched = conferences.findAll { it.booktitle==pubName && it.title==confName }
            if (searched) result = true
        }
        return result
    }

    static isANewJournal(authorName, title, journal){
        def periodic = Periodico.findByTitleAndJournal(title, journal)
        !periodic || !(periodic?.authors?.contains(authorName))
    }

    static fileContainsJournal(path, authorName, pubName, journalName){
        def journalArticles = extractJournalsFromFile(path, authorName)
        checkJournal(journalArticles, pubName, journalName)
    }

    static fileContainsJournalWithPages(path, authorName, pubName, journal, pages){
        def journalArticles = extractJournalsFromFile(path, authorName)
        checkJournalWithPages(journalArticles, pubName, journal, pages)
    }

    static fileContainsConference(path, authorName, pubName, confName){
        def conferences = extractConferencesFromFile(path, authorName)
        checkConference(conferences, pubName, confName)
    }

    static void initializePublicationDB(){
        //salvar 2 artigos de conferencia
        Conferencia conf = new Conferencia(ConferenciaTestDataAndOperations.conferencias[0])
        conf.save(flush: true)
        conf = new Conferencia(ConferenciaTestDataAndOperations.conferencias[1])
        conf.save(flush: true)

        //salvar 2 artigos de periodico
        Periodico journal = new Periodico(ArticleTestDataAndOperations.articles[0])
        journal.save(flush: true)
        journal = new Periodico(ArticleTestDataAndOperations.articles[1])
        journal.save(flush: true)
    }

    //#if($ResearchLine)
    static void initializeResearchLineDB(){
        ResearchLine rl = new ResearchLine(ResearchLineTestDataAndOperations.researchlines[0])
        rl.save(flush: true)
        rl = new ResearchLine(ResearchLineTestDataAndOperations.researchlines[1])
        rl.save(flush: true)
    }
    //#end

    //#if($ResearchProject)
    static void initializeResearchProjectDB() {
        ResearchProject rp = new ResearchProject(ResearchProjectTestDadaAndOperations.researchProjects[0])
        rp.funders = null
        rp.save(flush: true)
        rp = new ResearchProject(ResearchProjectTestDadaAndOperations.researchProjects[1])
        rp.save(flush: true)
    }

    static fileContainsResearchProject(path, projectName, authorName){
        def projects = extractResearchProjectsFromFile(path, authorName)?.find{
            it.projectName == projectName
        }
        return projects
    }

    static fileContainsResearchProjectWithStatus(path, projectName, authorName, status){
        def projects = extractResearchProjectsFromFile(path, authorName)?.find{
            it.projectName==projectName && it.status==status
        }
        return projects
    }

    static extractResearchProjectsFromFile(filename, authorName){
        def xmlFile = getRootNode(filename)

        def author = xmlFile.depthFirst().find{it.name() == 'DADOS-GERAIS'}.'@NOME-COMPLETO'
        if(author != authorName) return null

        def projects = xmlFile.depthFirst().findAll{ it.name() == 'PROJETO-DE-PESQUISA' }
        def projectsList = []

        for (Node project: projects) {
            def newProject = XMLService.saveResearchProject(project)
            if(newProject){
                projectsList += newProject
            }
        }
        return projectsList
    }
    //#end

    //#if($Orientation)
    static void initializeOrientationDB() {
        Orientation o = new Orientation(OrientationTestDataAndOperations.findOrientationByTitle(OrientationTestDataAndOperations.orientations[0].tituloTese))
        o.save(flush: true)
    }
    //#end

    //#if(ResearchLine)
    static checkResearchLineFromFile(fileName, researchName){
        getRootNode(fileName)
        def researchLine = fileName.depthFirst().findAll{ it.name() == 'TITULO-DA-LINHA-DE-PESQUISA' }
        def List researchs = new ArrayList<List>()
        def newResearch

        for (int i = 0; i < researchLine?.size(); ++i) {
            newResearch = XMLService.checkContResearch(researchLine, i, researchName)
            researchs.add(newResearch)
        }

        return researchs.size()
    }

    static extractSpecificResearchLineFromFile(fileName, researchLineName){
        getRootNode(fileName)
        List specificResearchs = fileName.depthFirst().findAll{ it.name() == 'TITULO-DA-LINHA-DE-PESQUISA' }
        for (research in specificResearchs) {
            if(research.equals(researchLineName)){
                TestDataAndOperationsResearchLine.createResearchLine(researchLineName)
                return true
            }
        }
        return false
    }


}
