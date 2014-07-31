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
import steps.ResearchLineTestDataAndOperations
import steps.ResearchProjectTestDadaAndOperations
import steps.TestDataAndOperationsPublication
import steps.TestDataAndOperationsResearchLine
import steps.XMLImportTestDataAndOperations

import pages.XMLImportPage

import javax.servlet.http.HttpServletResponse
import static cucumber.api.groovy.EN.*
import steps.TestDataAndOperations

XMLController xmlController
int publicationsTotal
String authorName = XMLImportTestDataAndOperations.getUser()
String user = "paulo"

//#if($ResearchProject)
int researchProjectsTotal
//#end

Given(~'^the system has some publications stored$') { ->
    TestDataAndOperations.loginController(this,user)
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

    File importedFile = new File("xmlimported.xml")
    importedFile.setText("")
    assert importedFile.length()==0
    xmlController = new XMLController()
    XMLImportTestDataAndOperations.uploadPublications(xmlController,path)
    assert importedFile.length()>0
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

    def journal1 = Periodico.findByTitle(ArticleTestDataAndOperations.articles[4].title)
    assert ArticleTestDataAndOperations.compatibleTo(journal1, journal1.title)

    def journal2 = Periodico.findByTitle(ArticleTestDataAndOperations.articles[5].title)
    assert ArticleTestDataAndOperations.compatibleTo(journal2, journal2.title)

    //caso do sistema já ter um dado periodico
    if(publicationsTotal == 5){
        def journal3 = Periodico.findByTitle(ArticleTestDataAndOperations.articles[6].title)
        assert ArticleTestDataAndOperations.compatibleTo(journal3, journal3.title)
    }
}

Then(~'^the system outputs a list of imported publications that contains the journal article entitled "([^"]*)" with status "([^"]*)"$'){ pubName, status ->
    assert xmlController.response.status == HttpServletResponse.SC_OK //A grails render is an HTTP 200 status
    assert xmlController.modelAndView.viewName == '/XML/home' //modelAndView is used only when is render
    assert xmlController.modelAndView.model.publications.journals.find{it["obj"].title == pubName && it["status"] == status}
}

Given(~'^the system has a journal article entitled "([^"]*)" with journal "([^"]*)" authored by me, among several publications$'){ pubName, journalName ->
    TestDataAndOperations.loginController(this, user)
    XMLImportTestDataAndOperations.initializePublicationDB()
    XMLImportTestDataAndOperations.addJournalPublication(pubName, journalName)
    publicationsTotal = 5
    assert Periodico.findByTitleAndJournal(pubName, journalName).authors.contains(authorName)
}

When(~'^I upload the file "([^"]*)" that contains a conference article entitled "([^"]*)" from "([^"]*)" authored by me$'){ filename, pubName, confName ->
    String path = XMLImportTestDataAndOperations.configureFileName(filename)
    assert XMLImportTestDataAndOperations.fileContainsConference(path, authorName, pubName, confName)
    assert !Conferencia.findByBooktitleAndTitle(pubName, confName)?.authors?.contains(authorName)

    File importedFile = new File("xmlimported.xml")
    importedFile.setText("")
    assert importedFile.length()==0
    xmlController = new XMLController()
    XMLImportTestDataAndOperations.uploadPublications(xmlController,path)
    assert importedFile.length()>0
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

    File importedFile = new File("xmlimported.xml")
    importedFile.setText("")
    assert importedFile.length()==0
    xmlController = new XMLController()
    XMLImportTestDataAndOperations.uploadPublications(xmlController, path)
    assert importedFile.length()>0
}

Then(~'^the system outputs a list of imported publications that does not contain the journal article entitled "([^"]*)"$'){ pubName ->
    assert xmlController.response.status == HttpServletResponse.SC_OK //A grails render is an HTTP 200 status
    assert xmlController.modelAndView.viewName == '/XML/home' //modelAndView is used only when is render
    assert !xmlController.modelAndView.model.publications.journals.find{it["obj"].title == pubName}
}

Given(~'^the system has a journal article entitled "([^"]*)" with journal "([^"]*)" and pages "([^"]*)" that is authored by me, among several publications$'){ pubName, journalName, pages ->
    TestDataAndOperations.loginController(this, user)
    XMLImportTestDataAndOperations.initializePublicationDB()
    XMLImportTestDataAndOperations.addJournalPublication(pubName, journalName)
    publicationsTotal = 5
    def journal = Periodico.findByTitleAndJournal(pubName, journalName)
    assert journal.pages == pages
    assert journal.authors.contains(authorName)
}

When(~'^I upload the file "([^"]*)" that contains a journal article entitled "([^"]*)" with journal "([^"]*)" and pages "([^"]*)" authored by me$'){
    filename, pubName, journalName, pages ->
    String path = XMLImportTestDataAndOperations.configureFileName(filename)
    assert XMLImportTestDataAndOperations.fileContainsJournalWithPages(path, authorName, pubName, journalName, pages)

    File importedFile = new File("xmlimported.xml")
    importedFile.setText("")
    assert importedFile.length()==0
    xmlController = new XMLController()
    XMLImportTestDataAndOperations.uploadPublications(xmlController,path)
    assert importedFile.length()>0
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
    TestDataAndOperations.loginController(this, user)
    XMLImportTestDataAndOperations.initializeResearchProjectDB()
    researchProjectsTotal = 2
    assert ResearchProject.findAll().size() == researchProjectsTotal
}

When(~'^I upload the file "([^"]*)" that contains a research project named as "([^"]*)"$'){ filename, projectName ->
    String path = XMLImportTestDataAndOperations.configureFileName(filename)
    assert XMLImportTestDataAndOperations.fileContainsResearchProject(path, projectName, authorName)

    File importedFile = new File("xmlimported.xml")
    importedFile.setText("")
    assert importedFile.length()==0
    xmlController = new XMLController()
    XMLImportTestDataAndOperations.uploadPublications(xmlController,path)
    assert importedFile.length()>0
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
    TestDataAndOperations.loginController(this, user)
    XMLImportTestDataAndOperations.initializeResearchProjectDB()
    researchProjectsTotal = 2
    def project = ResearchProject.findByProjectName(projectName)
    assert project.members.contains(authorName) || project.responsible==authorName
}

When(~'^I upload the file "([^"]*)" that also contains a research project named as "([^"]*)" with the same details information$'){
    filename, projectName ->
        String path = XMLImportTestDataAndOperations.configureFileName(filename)
        assert XMLImportTestDataAndOperations.fileContainsResearchProject(path, projectName, authorName)

        File importedFile = new File("xmlimported.xml")
        importedFile.setText("")
        assert importedFile.length()==0
        xmlController = new XMLController()
        XMLImportTestDataAndOperations.uploadPublications(xmlController,path)
        assert importedFile.length()>0
}

Then(~'^the system outputs a list of imported research projects that does not contain the one named as "([^"]*)"$'){ projectName ->
    assert xmlController.response.status == HttpServletResponse.SC_OK //A grails render is an HTTP 200 status
    assert xmlController.modelAndView.viewName == '/XML/home' //modelAndView is used only when is render
    assert !xmlController.modelAndView.model.publications.researchProjects.find{it["obj"].projectName == projectName}
}

Given(~'^the system has a research project named as "([^"]*)" with status "([^"]*)", among several research projects$'){
    projectName, projectStatus ->
        TestDataAndOperations.loginController(this, user)
        XMLImportTestDataAndOperations.initializeResearchProjectDB()
        researchProjectsTotal = 2
        def project = ResearchProject.findByProjectNameAndStatus(projectName, projectStatus)
        assert project.members.contains(authorName) || project.responsible==authorName
}

When(~'^I upload the file "([^"]*)" that also contains a research project named as "([^"]*)" with status "([^"]*)"$'){
    filename, projectName, projectStatus ->
        String path = XMLImportTestDataAndOperations.configureFileName(filename)
        assert XMLImportTestDataAndOperations.fileContainsResearchProjectWithStatus(path, projectName, authorName, projectStatus)

        File importedFile = new File("xmlimported.xml")
        importedFile.setText("")
        assert importedFile.length()==0
        xmlController = new XMLController()
        XMLImportTestDataAndOperations.uploadPublications(xmlController,path)
        assert importedFile.length()>0
}
//#end


// #if($ResearchLine)
Given(~'^ the system has some research lines stored$'){
    TestDataAndOperations.loginController(this)
    ResearchLineTestDataAndOperations.createResearchLine(0)
    ResearchLineTestDataAndOperations.createResearchLine(1)
    ResearchLineTestDataAndOperations.createResearchLine(2)
    initialSize = ResearchLine.findAll().size()
}

Given(~'^ the system has no research line named as "([^"]*)" associated with me $') { String nameOfResearch ->
    xmlImport = new ResearchLineController()
    def autor =  xmlImport.findByActor(this)
    List listaDeVerificacao = xmlImport.findResearchByActor(autor, nameOfResearch)
    assert listaDeVerificacao.size() == 0
}

When(~'^I upload the file "([^"]*)" which contains a research line named as "([^"]*)" $'){ String research, file ->
    TestDataAndOperations.uploadPublications(file)
    String path = "test" + File.separator + "files" + File.separator + file
    xmlImport2 = XMLImportTestDataAndOperations.checkResearchLineFromFile(file, research)
    assert xmlImport2 != 0
}

Then(~'^the system outputs a list of imported research lines which contains the one named as "([^"]*)" with status "([^"]*)" $'){ String researchOfLine, status ->

    xmlImport2 = new ResearchLineController()
    //O metodo retorna todos as research que estao com status stable
    lista = xmlImport2.findAllResearchLine()
    assert xmlImport2.checkIfResearchLineExists(researchOfLine,lista)
}

Then(~'^ no new research line is stored by the system $'){
    finalSize = ResearchLine.findAll().size()
    assert initialSize == finalSize

}

Then(~'^ the previously stored research lines do not change $'){
    ResearchLineController controller = new XMLController()
    def lista = ResearchLine.findAll()
    status = controller.statusChanged(lista) //se true é porque modificou, se false é porque nada foi modificado
    assert status == false
}
//#end


Given(~'^ the system has some research lines stored $'){
    ResearchLineTestDataAndOperations.createResearchLine(0)
    ResearchLineTestDataAndOperations.createResearchLine(1)
    initialSize = ResearchLine.findAll().size()
}
Given(~'^ the system has no research line named as "([^"]*)" associated with me $'){ nameOfResearch ->
    xmlImport = new ResearchLineController()
    def autor =  xmlImport.findByActor(this)
    List listaDeVerificacao = xmlImport.findResearchByActor(autor, nameOfResearch)
    assert listaDeVerificacao.size() == 0
}

Given(~'^the file "([^"]*)", which contains a research line named as "([^"]*)", is uploaded $'){ String theFile, researchLineName ->
    TestDataAndOperations.uploadPublications(theFile)
    status = XMLImportTestDataAndOperations.extractSpecificResearchLineFromFile(theFile, researchLineName)
    assert status == true
}

When(~'^ I confirm the import of the research line named as "([^"]*)" with status "([^"]*)" $'){ String nameOfResearch, status ->
    ResearchLineController controller = new ResearchLineController()
    statusController =  controller.checkSavedResearchByDescription(nameOfResearch, status)
    assert statusController == true

}
Then(~'^ the research line named as "([^"]*)" is stored by the system $'){ String research ->
    list = ResearchLine.findAll()
    ResearchLineController controller = new ResearchLineController()
    statusSave = controller.checkIfResearchLineExists(research,list)
    assert statusSave == true
}

Then(~'^ the research line named as "([^"]*)" with status "([^"]*)" is removed from the list of imported research lines $'){ String nameOfResearch, status ->
    ResearchLineController cont = new ResearchLineController()
    check = cont.checkDeletedResearchByDescription(nameOfResearch, status)
    assert check == true

}
Then(~'^ the previously stored research lines do not change $'){

    ResearchLineController controller = new XMLController()
    def lista = ResearchLine.findAll()
    status = controller.statusChanged(lista) //se true é porque modificou, se false é porque nada foi modificado
    assert status == false
}
//#end


Given(~'^The system has some publications stored $'){ ->
    Publication.findOrSaveById(0)
    Publication.findOrSaveById(1)
    Publication.findOrSaveById(2)
    inicial = Publication.findAll().size()
}

When(~'^ I upload the file "([^"]*)" $') { String typeFile ->
    TestDataAndOperationsPublication.uploadPublication(typeFile)
    PublicationController controllerP = new PublicationController()
    status = controllerP.checkTypeFile(typeFile)
    assert status == false
}

Then(~'^ no publication is stored by the system $') { ->
    finalS = Publication.findAll().size()
    assert inicial == finalS
}

Then(~'^ And previusly stored publications do not change  $'){->
    PublicationController contr = new PublicationController()
    def lista = Publication.findAll()
    result = contr.statusChanged(lista)
    assert result == false
    TestDataAndOperations.logoutController(this)
}


Given(~'^I am at the "Import XML File" page$'){ ->
    to LoginPage
    at LoginPage
    page.add("admin", "adminadmin")
    to XMLImportPage
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

Then(~'^ no new publication is stored by the system$'){ ->
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

Then(~'^ the previously stored publications do not change$'){
    to XMLImportPage
    at XMLImportPage
}