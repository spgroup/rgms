import pages.ArticlePages.ArticlesPage
import pages.BookChapterPage
import pages.Conferencia.ConferenciaPage
import pages.DissertationPage
import pages.LoginPage
import pages.OrientationPages.OrientationsPage
import pages.XMLImportPage
import pages.ferramenta.FerramentaPage
import rgms.publication.Publication
import steps.TestDataAndOperations

import static cucumber.api.groovy.EN.*

Given(~'^the system has some publications stored$') { ->

    TestDataAndOperations.loginController(this)

    initialSize = Publication.findAll().size()
}
When(~'^I upload the publications of "([^"]*)"$') { filename ->
    String path = "test" + File.separator + "functional" + File.separator + "steps" + File.separator + filename
    initialSize = Publication.findAll().size()
    TestDataAndOperations.uploadPublications(path)
    finalSize = Publication.findAll().size()
    assert initialSize < finalSize
}
Then(~'^the system has all the publications of the xml file$') { ->
    TestDataAndOperations.logoutController(this)

    //Book Chapters
    assert Publication.findByTitle("Refinement of Concurrent Object Oriented Programs") != null
    assert Publication.findByTitle("A RUP-Based Software Process Supporting Progressive Implementation") != null
    assert Publication.findByTitle("Transformation Laws for Sequential Object-Oriented Programming") != null
    assert Publication.findByTitle("Mapping Features to Aspects: A Model-Based Generative Approach") != null
    assert Publication.findByTitle("Recommending Mechanisms for Modularizing Mobile Software Variabilities") != null
    assert Publication.findByTitle("An Introduction to Software Product Line Refactoring") != null

    //Conferencias
    assert Publication.findByTitle("Latin American Conference On Computing (CLEI 1992)") != null
    assert Publication.findByTitle("Engineering Distributed Objects Workshop, 21st ACM International Conference on Software Engineering (ICSE 1999)") != null
    assert Publication.findByTitle("6th International Conference on Software Reuse (ICSR 2000)") != null

    //Dissertacoes
    assert Publication.findByTitle("De especificaÃ§Ãµes formais para protÃ³tipos funcionais") != null
    assert Publication.findByTitle("Semantics and Refinement for a Concurrent Object Oriented Language") != null

    //Ferramentas
    assert Publication.findByTitle("Sherlock") != null
    assert Publication.findByTitle("FOOPS Proof Assistant and Simulator") != null
    assert Publication.findByTitle("TaRGeT: Test and Requirements Generation Tool") != null
    assert Publication.findByTitle("JaTS: Java Transformation System") != null
    assert Publication.findByTitle("SGC") != null
    assert Publication.findByTitle("FLiP: Ferramenta para ExtraÃ§Ã£o de Linhas de Produtos de Software") != null

    //Periodicos
    assert Publication.findByTitle("A System For Translating Executable VDM Specifications Into Lazy ML") != null
    assert Publication.findByTitle("From VDM Specifications To Functional Prototypes") != null
    assert Publication.findByTitle("Basic laws of ROOL: an object-oriented language") != null
    assert Publication.findByTitle("Implementing distribution and persistence aspects with AspectJ") != null
    assert Publication.findByTitle("Developing adaptive J2ME applications using AspectJ") != null
    assert Publication.findByTitle("Algebraic reasoning for object-oriented programming") != null
    assert Publication.findByTitle("Refactoring Alloy Specifications") != null

    //Orientacoes
    assert Publication.findByTitle("Desenvolvimento de Software Como Um Processo ContÃ­nuo e ReversÃ­vel Usando Bon e Java") != null
    assert Publication.findByTitle("Design and Evaluation of an Object-Oriented Formal Specification Language") != null
    assert Publication.findByTitle("Um MÃ©todo para ImplementaÃ§Ã£o Orientada a Objetos Usando Java e Banco de Dados Relacional.") != null
    assert Publication.findByTitle("Progressive Development of Distributed Object-Oriented Applications") != null
    assert Publication.findByTitle("Um Processo de Software com Suporte para ImplementaÃ§Ã£o Progressiva") != null
}

And(~'^I select the upload button at the XML import page$') {->
    at XMLImportPage
    page.uploadWithoutFile()
}
Then(~'^I\'m still on XML import page$') {->
    at XMLImportPage
}
And(~'^the publications are not stored by the system$') {->
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

    to FerramentaPage
    at FerramentaPage
    page.checkIfFerramentaListIsEmpty()

    to OrientationsPage
    at OrientationsPage
    page.checkIfOrientationListIsEmpty()
}

/**
 * @author Kamilla Cardoso
 * #if($ResearchLine)
 * Scenario: new research line
 */
Given(~'^ the system has some research lines stored$'){ ->
    TestDataAndOperations.loginController(this)
    initialSize = ResearchLine.findAll().size()
}
And(~'^ the system has no research line named as "([^"]*)" associated with me $'){ String nameResearch ->
    //myIdentification = TestDataAuthentication.getProperties().get(this)

    //Se existe uma linha de pesquisa de nome (nameResearch), mas ela não é existe na listas de pesquisas do usuário atual, ou seja,
    // o usuário logado não à possui
    assert ResearchLine.findByName(nameResearch) != PublicationController.getLoggedMember().researchLines.contains(nameResearch)
}

When(~'^ I upload the file "([^"]*)" which contains a research line named as "([^"]*)" $'){ file, researchLineName ->
    TestDataAndOperations.uploadPublications(file)
    TestDataAndOperationsResearchLine.findResearchLineByName(researchLineName)
}

Then(~'^ the system outputs a list of imported research lines which contains the one named as "([^"]*)" with status "([^"]*)" $'){  research_Line, status ->
    //lista linha de pesquisa
    assert Publication.findAllByResearchLineInListAndTitle(ResearchLine.findAll(), research_Line).size() > 1
    //deve conter apenas uma linha de pesquisa
    TestDataAndOperationsResearchLine.deleteResearchLine(ResearchLine.findByName(research_Line).getMembers())
    //status definido na descrição deve ser "stable"
    assert ResearchLine.findByName(research_Line).getDescription() == status
}

And(~'^ no new research line is stored by the system$'){ ->
    finalSize = ResearchLine.findAll().size()
}
And(~'^ the previously stored research lines do not change$'){
    assert inicialSize == finalSize
}
//#end


/**
 * @author Kamilla Cardoso
 * #if($ResearchLine)
 * Scenario: import xml file that contains a research line
 */
Given(~'^ the system has no research line named as "([^"]*)" $'){ nameResearch ->
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
 * Scenario import invalid file
 */
Given(~'^The system has some publications stored $'){ ->
    inicial = Publication.findAll().size()
}

When(~'^ I upload the file "([^"]*)" $') { String typeFile ->
    TestDataAndOperations.uploadPublications(typeFile)
    currentTypeFile = Publication.findByFile(typeFile).getFile().hasProperty(".xml")
    assert currentTypeFile == false
    //Publication.findByFile(typeFile).removeFromMembers()


    //Vai verificar que a publicacao era inconsistente ao tipo, e será removido o arquivo do sistema
    //Como este arquivo pode ter pessistido informacoes entao deve-se deletar todas as informacoes juntamente com o arquivo

    Publication.findAllByFile(typeFile).remove(this)
    //Publication.removeFromPublications()
}

Then(~'^ no publication is stored by the system $') { ->
    finalS = Publication.findAll().size()
}

And(~'^ And previusly stored publications do not change  $'){->
    assert inicial == finalS
    TestDataAndOperations.logoutController(this)
}



/**
 * @author Kamilla Cardoso
 * Scenario invalid file web
 */
Given(~'^I am at the "Import XML File" Page$'){ ->
    to LoginPage
    at LoginPage
    page.add("admin", "adminadmin")
    at XMLImportPage
}

When(~'^I select the "([^"]*)" button$'){ String uploadButton ->
    at XMLImportPage
    page.selectButton(uploadButton)
}

And(~' I upload the file "([^"]*)"$'){ String file ->
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

    to FerramentaPage
    at FerramentaPage
    page.checkIfFerramentaListIsEmpty()

    to OrientationsPage
    at OrientationsPage
    page.checkIfOrientationListIsEmpty()
}

And(~'^ the previously stored publications do not change$'){
    to XMLImportPage
    at XMLImportPage
}
