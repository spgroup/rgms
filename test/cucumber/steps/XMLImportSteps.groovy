import pages.ArticlePages.ArticlesPage
import pages.BookChapterPage
import pages.Conferencia.ConferenciaPage
import pages.DissertationPage
import pages.LoginPage
import pages.OrientationPages.OrientationsPage
import pages.XMLImportPage
import pages.ferramenta.FerramentaPage
import rgms.publication.*
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