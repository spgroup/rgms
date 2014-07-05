import pages.ArticlePages.ArticlesPage
import pages.BookChapterPage
import pages.Conferencia.ConferenciaPage
import pages.DissertationPage
import pages.LoginPage
import pages.OrientationPages.OrientationsPage
import pages.ResearchLinePages.ResearchLinePage
import rgms.authentication.User
import rgms.publication.*
import rgms.researchProject.ResearchProject
import steps.ArticleTestDataAndOperations
import steps.ConferenciaTestDataAndOperations
import steps.ResearchProjectTestDadaAndOperations
import steps.TestDataAndOperationsResearchLine
import steps.XMLImportTestDataAndOperations

import pages.XMLImportPage

import javax.servlet.http.HttpServletResponse
import static cucumber.api.groovy.EN.*
import steps.TestDataAndOperations

XMLController xmlController
int publicationsTotal
String authorName = User.findByUsername('admin')?.author?.name

//#if($ResearchProject)
int researchProjectsTotal
//#end

Given(~'^the system has some publications stored$') { ->
    TestDataAndOperations.loginController(this)
    XMLImportTestDataAndOperations.initializePublicationDB()
    publicationsTotal = 4
    assert Publication.findAll().size() == publicationsTotal
}

Given(~'^the system has no journal article entitled "([^"]*)" with journal "([^"]*)" authored by me$'){ pubName, journalName ->
    assert XMLImportTestDataAndOperations.isANewJournal(authorName, pubName, journalName)
}

When(~'^I upload the file "([^"]*)" that contains a journal article entitled "([^"]*)" with journal "([^"]*)" authored by me$') { filename, pubName, journalName ->
    String path = XMLImportTestDataAndOperations.configureFileName(filename)
    assert XMLImportTestDataAndOperations.fileContainsJournal(path, authorName, pubName, journalName)
    xmlController = new XMLController()
    XMLImportTestDataAndOperations.uploadPublications(xmlController,path)
}

Then(~'^no new publication is stored by the system$'){ ->
    assert Publication.findAll().size() == publicationsTotal
}

Then(~'^the previously stored publications do not change$'){ ->
    // sempre vai conter as publicacoes conf1, conf2, journal1, journal2
    def conf1 = Conferencia.findByTitle(ConferenciaTestDataAndOperations.conferencias[0].title)
    assert ConferenciaTestDataAndOperations.conferenciaCompatibleTo(conf1, conf1.title)

    def conf2 = Conferencia.findByTitle(ConferenciaTestDataAndOperations.conferencias[1].title)
    assert ConferenciaTestDataAndOperations.conferenciaCompatibleTo(conf2, conf2.title)

    def journal1 = Periodico.findByTitle(ArticleTestDataAndOperations.articles[0].title)
    assert ArticleTestDataAndOperations.compatibleTo(journal1, journal1.title)

    def journal2 = Periodico.findByTitle(ArticleTestDataAndOperations.articles[1].title)
    assert ArticleTestDataAndOperations.compatibleTo(journal2, journal2.title)

    //caso do sistema já ter um dado periodico
    if(publicationsTotal == 5){
        def journal3 = Periodico.findByTitle(ArticleTestDataAndOperations.articles[2].title)
        assert ArticleTestDataAndOperations.compatibleTo(journal3, journal3.title)
    }
}

Then(~'^the system outputs a list of imported publications that contains the journal article entitled "([^"]*)" with status "([^"]*)"$'){ pubName, status ->
    assert xmlController.response.status == HttpServletResponse.SC_OK //A grails render is an HTTP 200 status
    assert xmlController.modelAndView.viewName == '/XML/home' //modelAndView is used only when is render
    assert xmlController.modelAndView.model.publications.journals.find{it["obj"].title == pubName && it["status"] == status}
}

Given(~'^the system has a journal article entitled "([^"]*)" with journal "([^"]*)" authored by me, among several publications$'){ pubName, journalName ->
    TestDataAndOperations.loginController(this)
    XMLImportTestDataAndOperations.initializePublicationDB()
    def p = XMLImportTestDataAndOperations.addJournalPublication(pubName, journalName)
    publicationsTotal = 5
    assert Publication.findAll().size() == publicationsTotal

    assert p.authors.contains(authorName)
}

When(~'^I upload the file "([^"]*)" that contains a conference article entitled "([^"]*)" from "([^"]*)" authored by me$'){ filename, pubName, confName ->
    String path = XMLImportTestDataAndOperations.configureFileName(filename)
    assert XMLImportTestDataAndOperations.fileContainsConference(path, authorName, pubName, confName)

    assert !Conferencia.findByBooktitleAndTitle(pubName, confName)?.authors?.contains(authorName)
    xmlController = new XMLController()
    XMLImportTestDataAndOperations.uploadPublications(xmlController, path)
}

Then(~'^the system outputs a list of imported publications that contains the conference article entitled "([^"]*)" with status "([^"]*)"$'){ pubName, status ->
    assert xmlController.response.status == HttpServletResponse.SC_OK //A grails render is an HTTP 200 status
    assert xmlController.modelAndView.viewName == '/XML/home' //modelAndView is used only when is render
    assert xmlController.modelAndView.model.publications.conferences.find{it["obj"].booktitle == pubName && it["status"] == status}
}

When(~'^I upload the file "([^"]*)" that also contains a journal article entitled "([^"]*)" with the same details information$'){ filename, pubName ->
    String path = XMLImportTestDataAndOperations.configureFileName(filename)
    assert XMLImportTestDataAndOperations.fileContainsJournal(path, authorName, pubName, null)

    Periodico dbPub = ArticleTestDataAndOperations.findArticleByTitleAndAuthor(pubName, authorName)
    assert ArticleTestDataAndOperations.compatibleTo(dbPub, pubName)

    xmlController = new XMLController()
    XMLImportTestDataAndOperations.uploadPublications(xmlController, path)
}

Then(~'^the system outputs a list of imported publications that does not contain the journal article entitled "([^"]*)"$'){ pubName ->
    assert xmlController.response.status == HttpServletResponse.SC_OK //A grails render is an HTTP 200 status
    assert xmlController.modelAndView.viewName == '/XML/home' //modelAndView is used only when is render
    assert !xmlController.modelAndView.model.publications.journals.find{it["obj"].title == pubName}
}

Given(~'^the system has a journal article entitled "([^"]*)" with journal "([^"]*)" and pages "([^"]*)" that is authored by me, among several publications$'){ pubName, journalName, pages ->
    TestDataAndOperations.loginController(this)
    XMLImportTestDataAndOperations.initializePublicationDB()
    def journal = XMLImportTestDataAndOperations.addJournalPublication(pubName, journalName)
    publicationsTotal = 5
    assert Publication.findAll().size() == publicationsTotal

    assert journal.authors.contains(authorName)
    assert journal.pages == pages
}

When(~'^I upload the file "([^"]*)" that contains a journal article entitled "([^"]*)" with journal "([^"]*)" and pages "([^"]*)" authored by me$'){
    filename, pubName, journalName, pages ->
    String path = XMLImportTestDataAndOperations.configureFileName(filename)
    assert XMLImportTestDataAndOperations.fileContainsJournalWithPages(path, authorName, pubName, journalName, pages)
    xmlController = new XMLController()
    XMLImportTestDataAndOperations.uploadPublications(xmlController, path)
}

When(~'^I click on "([^"]*)" at the "([^"]*)" Page without selecting a xml file$'){ option, xmlPage ->
    to XMLImportPage
    at XMLImportPage
    page.uploadWithoutFile()
}

Then(~'^the system outputs an error message$') { ->
    at XMLImportPage
    assert page.hasErrorUploadFile()
}

//#if ($ResearchProject)
Given(~'^the system has some research projects stored$'){ ->
    TestDataAndOperations.loginController(this)
    XMLImportTestDataAndOperations.initializeResearchProjectDB()
    researchProjectsTotal = 2
    assert ResearchProject.findAll().size() == researchProjectsTotal
}

When(~'^I upload the file "([^"]*)" that contains a research project named as "([^"]*)"$'){ filename, projectName ->
    String path = XMLImportTestDataAndOperations.configureFileName(filename)
    assert XMLImportTestDataAndOperations.fileContainsResearchProject(path, projectName, authorName)
    xmlController = new XMLController()
    XMLImportTestDataAndOperations.uploadPublications(xmlController,path)
}

Then(~'^no new research project is stored by the system$'){ ->
    assert ResearchProject.findAll().size() == researchProjectsTotal
}

Then(~'^the previously stored research projects do not change$'){ ->
    def project1 = ResearchProject.findByProjectName(ResearchProjectTestDadaAndOperations.researchProjects[0].projectName)
    assert ResearchProjectTestDadaAndOperations.compatibleTo(project1, project1.projectName)
    def project2 = ResearchProject.findByProjectName(ResearchProjectTestDadaAndOperations.researchProjects[1].projectName)
    assert ResearchProjectTestDadaAndOperations.compatibleTo(project2, project2.projectName)
}

Then(~'^the system outputs a list of imported research projects that contains the one named as "([^"]*)" with status "([^"]*)"$'){
    projectName, status ->
    assert xmlController.response.status == HttpServletResponse.SC_OK //A grails render is an HTTP 200 status
    assert xmlController.modelAndView.viewName == '/XML/home' //modelAndView is used only when is render
    assert xmlController.modelAndView.model.publications.researchProjects.find{it["obj"].projectName == projectName && it["status"] == status}
}

Given(~'^the system has a research project named as "([^"]*)", among several research projects$'){ projectName ->
    TestDataAndOperations.loginController(this)
    XMLImportTestDataAndOperations.initializeResearchProjectDB()
    researchProjectsTotal = 2
    assert ResearchProject.findAll().size() == researchProjectsTotal

    def project = ResearchProject.findByProjectName(projectName)
    assert project
    assert project.members.contains(authorName) || project.responsible==authorName
}

When(~'^I upload the file "([^"]*)" that also contains a research project named as "([^"]*)" with the same details information$'){
    filename, projectName ->
        String path = XMLImportTestDataAndOperations.configureFileName(filename)
        assert XMLImportTestDataAndOperations.fileContainsResearchProject(path, projectName, authorName)
        xmlController = new XMLController()
        XMLImportTestDataAndOperations.uploadPublications(xmlController, path)
}

Then(~'^the system outputs a list of imported research projects that does not contain the one named as "([^"]*)"$'){ projectName ->
    assert xmlController.response.status == HttpServletResponse.SC_OK //A grails render is an HTTP 200 status
    assert xmlController.modelAndView.viewName == '/XML/home' //modelAndView is used only when is render
    assert !xmlController.modelAndView.model.publications.researchProjects.find{it["obj"].projectName == projectName}
}

Given(~'^the system has a research project named as "([^"]*)" with status "([^"]*)", among several research projects$'){
    projectName, projectStatus ->
        TestDataAndOperations.loginController(this)
        XMLImportTestDataAndOperations.initializeResearchProjectDB()
        researchProjectsTotal = 2
        assert ResearchProject.findAll().size() == researchProjectsTotal

        def project = ResearchProject.findByProjectNameAndStatus(projectName, projectStatus)
        assert project
        assert project.members.contains(authorName) || project.responsible==authorName
}

When(~'^I upload the file "([^"]*)" that also contains a research project named as "([^"]*)" with status "([^"]*)"$'){
    filename, projectName, projectStatus ->
        String path = XMLImportTestDataAndOperations.configureFileName(filename)
        assert XMLImportTestDataAndOperations.fileContainsResearchProjectWithStatus(path, projectName, authorName, projectStatus)
        xmlController = new XMLController()
        XMLImportTestDataAndOperations.uploadPublications(xmlController, path)
}
//#end

/**
 * @author Kamilla Cardoso
 * #if($ResearchLine)
 * Scenario: new research line
 *
Given(~'^ The system has some research lines stored$'){ ->
    TestDataAndOperations.loginController(this)
    initialSize = ResearchLine.findAll().size()
}

Given(~'^ The system has no research line named as "([^"]*)" associated with me $'){ String nameResearch ->
    //Se existe uma linha de pesquisa de nome (nameResearch), mas ela não é existe na lista de pesquisas do usuário atual,
    // o usuário logado não à possui
    assert ResearchLine.findByName(nameResearch) != PublicationController.getLoggedMember().researchLines.contains(nameResearch)
}

When(~'^ I upload the file "([^"]*)" which contains a research line named as "([^"]*)" $'){ file, researchLineName ->
    TestDataAndOperations.uploadPublications(file)
    TestDataAndOperationsResearchLine.findResearchLineByName(researchLineName)
}

Then(~'^ The system outputs a list of imported research lines which contains the one named as "([^"]*)" with status "([^"]*)" $'){  research_Line, status ->
    //lista a quantidades de linha de pesquisas armazenadas para o nome especifico
    assert Publication.findAllByResearchLineInListAndTitle(ResearchLine.findAll(), research_Line).size() > 1
    //deve conter apenas uma linha de pesquisa, entao a linha de pesquisa armazenada recentemente é removida
    TestDataAndOperationsResearchLine.deleteResearchLine(ResearchLine.findByName(research_Line).getMembers())
    //status definido na descrição deve ser "stable" para a linha de pesquisa que se manteve armazenada
    assert ResearchLine.findByName(research_Line).getDescription() == status
}

Then(~'^ No new research line is stored by the system$'){ ->
    finalSize = ResearchLine.findAll().size()
}

Then(~'^ The previously stored research lines do not change$'){
    assert initialSize == finalSize
}
//#end

/**
 * @author Kamilla Cardoso
 * #if($ResearchLine)
 * Scenario: import xml file that contains a research line
 *
Given(~'^ The system has no research line named as "([^"]*)" $'){ nameResearch ->
    assert ResearchLine.findByName(nameResearch) == null
    inicialSize = ResearchLineController.findAll().size()
}

When(~'^ I upload the file "([^"]*)" which contains a research line named as "([^"]*)" $') { file, research_name ->
    TestDataAndOperations.uploadPublications(file)
    assert ResearchLine.findByName(research_name) == research_name
    finalSize = ResearchLineController.findAll().size()
    assert inicialSize < finalSize
}

Then(~'^ the research line named as "([^"]*)" is stored &'){ research->
    assert ResearchLine.findByName(research) != null
}
//#end

/**
 * @author Kamilla Cardoso
 * Scenario: import invalid file
 *
Given(~'^The system has some publications stored $'){ ->
    inicial = Publication.findAll().size()
}

When(~'^ I upload the file "([^"]*)" $') { String typeFile ->
    TestDataAndOperations.uploadPublications(typeFile)
    currentTypeFile = Publication.findByFile(typeFile).getFile().hasProperty(".xml")
    assert currentTypeFile == false

    //Verifica se o arquivo possuia tipo invalido, caso seja e removido do sistema
    //Como este arquivo pode ter persistido informacoes entao deve-se deletar todas as informacoes juntamente com o arquivo

    Publication.findAllByFile(typeFile).remove(this)
}

Then(~'^ no publication is stored by the system $') { ->
    finalS = Publication.findAll().size()
}

Then(~'^ And previusly stored publications do not change  $'){->
    assert inicial == finalS
    TestDataAndOperations.logoutController(this)
}

/**
 * @author Kamilla Cardoso
 * Scenario: invalid file web
 *
Given(~'^I am at the "Import XML File" page$'){ ->
    to LoginPage
    at LoginPage
    page.add("admin", "adminadmin")
    at XMLImportPage
}

When(~'^I select the "([^"]*)" button$'){ String uploadButton ->
    at XMLImportPage
    page.selectButton(uploadButton)
}

When(~' I upload the file "([^"]*)"$'){ String file ->
    at XMLImportPage
    page.uploadFile(file)
}

Then(~'^ the system outputs an error message$'){ ->
    at XMLImportPage
    assert page.invalidXML()
}

And(~'^ no new publication is stored by the system$'){ ->
    to ArticlesPage
    at ArticlesPage
    page.checkIfArticlesListIsEmpty()

    to BookChapterPage
    at BookChapterPage
    page.checkIfBookChapterListIsEmpty()

    to ConferenciaPage
    at ConferenciaPage
    page.checkIfConferenciaListIsEmpty()

    to DissertationPage
    at DissertationPage
    page.checkIfDissertationListIsEmpty()

    to ResearchLinePage
    at ResearchLinePage
    page.checkIfFerramentaListIsEmpty()

    to OrientationsPage
    at OrientationsPage
    page.checkIfOrientationListIsEmpty()
}

And(~'^ the previously stored publications do not change$'){
    to XMLImportPage
    at XMLImportPage
}
*/

