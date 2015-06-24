import pages.ArticlePages.ArticlesPage
import pages.BookChapterPage
import pages.Conferencia.ConferenciaPage
import pages.DissertationPage
import pages.LoginPage
import pages.OrientationPages.OrientationsPage
import pages.XMLImportPage
import pages.ferramenta.FerramentaPage
import rgms.member.Orientation
import rgms.publication.*
import steps.ArticleTestDataAndOperations
import steps.OrientationTestDataAndOperations
import steps.TestDataAndOperationsPublication
import steps.TestDataDissertacao

import static cucumber.api.groovy.EN.*
import steps.TestDataAndOperations
import CommonSteps

import org.apache.shiro.util.ThreadContext
import org.apache.shiro.subject.Subject
import org.apache.shiro.SecurityUtils

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

Given(~'^the system has a dissertation entitled "([^"]*)" stored$') { String title->
    TestDataDissertacao.createDissertacao(title, "dissertation.txt", "University of Oxford")
    def dissertation = Dissertacao.findByTitle(title);
    assert dissertation != null
}

And(~'^the similarity tolerance is configured to "([^"]*)"$') { int similarityTolerance->
    TestDataDissertacao.setSimilarityTolerance(similarityTolerance)
}

When(~'^I upload the file "([^"]*)" which contains a dissertation entitled "([^"]*)"$') { String filename, title->

    String path = new File(".").getCanonicalPath() + File.separator + "test" +  File.separator + "functional" + File.separator + "steps" + File.separator + filename
    TestDataDissertacao.uploadDissertacaoWithSimilarityAnalisys(path)
    boolean result = TestDataDissertacao.verifyDissertationXML(title, path)
    assert result

}

Then(~'^the system outputs a list of imported dissertations which contains the dissertation entitled "([^"]*)"$') { String title->
    def dissertation = Dissertacao.findByTitle(title)
    assert dissertation != null
}

And(~'^no new dissertation entitled "([^"]*)" is stored by the system$') { String title->
    def dissertation = Dissertacao.findByTitle(title)
    assert dissertation == null
}

Given(~'^I am at the XMLImport Page$') {->
    to LoginPage
    at LoginPage
    page.fillLoginData("admin", "adminadmin")
    to XMLImportPage
    at XMLImportPage
}

And(~'^I select a xml file$') { ->
    page.selectFile()
}

When(~'^I click on "upload" without informing the tolerance level$') { ->
    page.uploadClick()
}

Then(~'^the system outputs an error message$') { ->
    //qualquer navegador por padrão mostra uma mensagem de erro quando o atributo "required" está configurado
    assert page.isRequiredEnabledOnToleranceSelect()
}

Given(~'^the system has a orientation entitled "([^"]*)" stored$') { String title->
    //TestDataDissertacao.createDissertacao(title, "dissertation.txt", "University of Oxford")
    OrientationTestDataAndOperations.createOrientation(title)
    //def dissertation = Dissertacao.findByTitle(title);
    def orientation = Orientation.findByTituloTese(title);
    assert orientation != null
}

When(~'^I upload the file "([^"]*)" which contains an orientation entitled "([^"]*)"$') { String filename, title->

    String path = new File(".").getCanonicalPath() + File.separator + "test" +  File.separator + "functional" + File.separator + "steps" + File.separator + filename
    OrientationTestDataAndOperations.uploadOrientationWithSimilarityAnalisys(path)
    boolean result = OrientationTestDataAndOperations.verifyOrientationXML(title, path)
    assert result
}

Then(~'^the system outputs a list of imported orientations which contains the orientation entitled "([^"]*)"$') { String title->
    def orientation = Orientation.findByTituloTese(title)
    assert orientation != null
}

And(~'^no new orientation entitled "([^"]*)" is stored by the system$') { String title->
    def orientation = Orientation.findByTituloTese(title)
    assert orientation == null
}

When(~'^I click on "upload" informing the tolerance level$') { ->
    page.setToleranceValue(10)
    page.uploadClick()
}

Then(~'^the system outputs a successful message$') { ->
    //se for um erro a mensagem é colocada em readErrorMessage
    assert page.readFlashMessage() != null
}

Then(~'^the system outputs a list of imported orientations which contains the orientations "([^"]*)" and "([^"]*)"$') { String title1, title2->
    def orientation1 = Orientation.findByTituloTese(title1)
    def orientation2 = Orientation.findByTituloTese(title2)
    assert orientation1 != null && orientation2 != null
}

And(~'^the new orientation entitled "([^"]*)" is stored by the system$') { String title->
    def orientation = Orientation.findByTituloTese(title)
    assert orientation != null
}

Given(~'^the system has a journal article entitled "([^"]*)" stored$') { String title->
    ArticleTestDataAndOperations.createArticle(title, "article.pdf")
    def periodico = Periodico.findByTitle(title);
    assert periodico != null
}

When(~'^I upload the file "([^"]*)" which contains a journal article entitled "([^"]*)"$') { String filename, title->

    String path = new File(".").getCanonicalPath() + File.separator + "test" +  File.separator + "functional" + File.separator + "steps" + File.separator + filename
    ArticleTestDataAndOperations.uploadArticleWithSimilarityAnalysis(path)
    boolean result = ArticleTestDataAndOperations.verifyJournalXML(title, path)
    assert result
}

Then(~'^the system does not store the journal article entitled "([^"]*)"$') { String title->
    def periodico = Periodico.findByTitle(title);
    assert periodico == null
}

And(~'^the journal article entitled "([^"]*)" still in the system$') { String title->
    def periodico = Periodico.findByTitle(title);
    assert periodico != null
}

Given(~'^the system has a journal article entitled "([^"]*)" stored with filename "([^"]*)"$') { String title, filename->
    ArticleTestDataAndOperations.createArticle(title, filename)
    def periodico = Periodico.findByTitle(title);
    assert periodico != null
}

Then(~'^the system store the journal article entitled "([^"]*)"$') { String title->
    def periodico = Periodico.findByTitle(title);
    assert periodico != null
}