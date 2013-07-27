import pages.PublicationsPage
import pages.VisitEditPage
import rgms.visit.Visit;
import rgms.visit.Visitor
import pages.LoginPage
import pages.VisitPage
import pages.VisitCreatePage
import pages.VisitShowPage
import rgms.tool.TwitterTool
import steps.TestDataAndOperations

import static cucumber.api.groovy.EN.*

Given(~'^que o visitante "([^"]*)" nao esteja cadastrado no  sistema$') { String visitante ->
    def visitor = Visitor.findByName(visitante)
    assert visitor == null
}

When(~'^eu agendar uma visita para o visitante "([^"]*)" And inserir apenas a data "([^"]*)"$') { String visitante, String data ->
    TestDataAndOperations.createVisit(visitante, data, data)
}

Then(~'^o cadastro do vistante "([^"]*)" e armazenado no sistema e tambem uma visita com de incio igual e fim igual  a "([^"]*)"$') { String visitante, String data ->
    def visitor = Visitor.findByName(visitante)
    assert visitor != null
    Date dia = Date.parse("dd/MM/yyyy", data)
    def visit = Visit.findByVisitorAndDataInicioAndDataFim(visitor, dia, dia)
    assert visit != null
}

When(~'^eu agendar uma visita para o visitante "([^"]*)" e inserir data inicio igual a "([^"]*)" e inserir a data fim igual a "([^"]*)"$') { String visitante, String dataInicio, String dataFim ->
    TestDataAndOperations.createVisit(visitante, dataInicio, dataFim)
}

Then(~'^o cadastro do vistante "([^"]*)" e armazenado no sistema e tambem uma visita com data de incio igual a "([^"]*)" e data fim igual a "([^"]*)"$') { String visitante, String dataInicio, String dataFim ->
    def visitor = Visitor.findByName(visitante)
    assert visitor != null
    Date dia_1 = Date.parse("dd/MM/yyyy", dataInicio)
    Date dia_2 = Date.parse("dd/MM/yyyy", dataFim)
    def visit = Visit.findByVisitorAndDataInicioAndDataFim(visitor, dia_1, dia_2)
    assert visit != null
}

Given(~'^que o visitante "([^"]*)" esteja cadastrado no  sistema$') { String visitante ->
    TestDataAndOperations.createVisitor(visitante)
    def visitor = Visitor.findByName(visitante)
    assert visitor != null
}

Then(~'^uma visita para o visitante "([^"]*)" com de incio igual e fim igual  a "([^"]*)" e armazenada no sistemas$') { String visitante, String data ->
    Visitor visitor = Visitor.findByName(visitante)
    Date dia = Date.parse("dd/MM/yyyy", data)
    def visit = Visit.findByVisitorAndDataInicioAndDataFim(visitor, dia, dia)
    assert visit != null
}

Then(~'^uma visita para o visitante "([^"]*)" com de incio igual a "([^"]*)" e data fim igual a "([^"]*)" e armazenada no sistemas$') { String visitante, String dataInicio, String dataFim ->
    Visitor visitor = Visitor.findByName(visitante)
    Date dia_1 = Date.parse("dd/MM/yyyy", dataInicio)
    Date dia_2 = Date.parse("dd/MM/yyyy", dataFim)
    def visit = Visit.findByVisitorAndDataInicioAndDataFim(visitor, dia_1, dia_2)
    assert visit != null
}

/**
 * @author carloscemb
 */
Given(~'^the system has a visit of the visitor named "([^"]*)" with initial date "([^"]*)" and final date "([^"]*)" stored$') { String visitante, String dataInicio, String dataFim ->
    TestDataAndOperations.createVisitor(visitante)
    def visitor = Visitor.findByName(visitante)
    assert visitor != null
    TestDataAndOperations.createVisit(visitante, dataInicio, dataFim)
    Date dia_1 = Date.parse("dd/MM/yyyy", dataInicio)
    Date dia_2 = Date.parse("dd/MM/yyyy", dataFim)
    def visit = Visit.findByVisitorAndDataInicioAndDataFim(visitor, dia_1, dia_2)
    assert visit != null
}

/**
 * @author carloscemb
 */
When(~"^I view the list of visits\$") {->
    def visits = Visit.findAll()
    assert visits != null
}

/**
 * @author carloscemb
 */
Then(~'^the list is returned with the visit of the visitor named "([^"]*)" with initial date "([^"]*)" and final date "([^"]*)"$') { String visitante, String dataInicio, String dataFim ->
    def visitor = Visitor.findByName(visitante)
    Date dia_1 = Date.parse("dd/MM/yyyy", dataInicio)
    Date dia_2 = Date.parse("dd/MM/yyyy", dataFim)
    def visit = Visit.findByVisitorAndDataInicioAndDataFim(visitor, dia_1, dia_2)
    assert TestDataAndOperations.containsVisit(visit)
}

/**
 * @author carloscemb
 */
Given(~'^I am logged as "([^"]*)" and at the visits page and the visit of the visitor named "([^"]*)" with initial date "([^"]*)" and final date "([^"]*)" is stored in the system$') { String userName, String visitante, String dataInicio, String dataFim ->
    to LoginPage
    at LoginPage
    page.fillLoginData(userName, "adminadmin")
    at PublicationsPage
    page.select("Visita")
    at VisitPage
    page.selectNewVisit()
    at VisitCreatePage
    page.fillVisitDetails()
    page.clickOnCreate()
    visit = TestDataAndOperations.findVisit(visitante, dataInicio, dataFim)
    assert visit != null
}

/**
 * @author carloscemb
 */
Then(~'^my resulting visits list contains the visit of the visitor named "([^"]*)" with initial date "([^"]*)" and final date "([^"]*)"$') { String visitante, String dataInicio, String dataFim ->
    to VisitPage
    at VisitPage
    page.checkVisitAtList(visitante, dataInicio, dataFim, 0)
}

/**
 * @author carloscemb
 */
When(~'^I delete the visit of the visitor named "([^"]*)" with initial date "([^"]*)" and final date "([^"]*)"$') { String visitante, String dataInicio, String dataFim ->
    TestDataAndOperations.removeVisit(visitante, dataInicio, dataFim)
}

/**
 * @author carloscemb
 */
Then(~'^the visit of the visitor named "([^"]*)" with initial date "([^"]*)" and final date "([^"]*)" is properly removed by the system$') { String visitante, String dataInicio, String dataFim ->
    def visitor = Visitor.findByName(visitante)
    Date dia_1 = Date.parse("dd/MM/yyyy", dataInicio)
    Date dia_2 = Date.parse("dd/MM/yyyy", dataFim)
    def visit = Visit.findByVisitorAndDataInicioAndDataFim(visitor, dia_1, dia_2)
    assert visit == null
}

/**
 * @author carloscemb
 */
When(~'^I select to view the visit of the visitor named "([^"]*)" with initial date "([^"]*)" and final date "([^"]*)" in resulting list$') { String visitante, String dataInicio, String dataFim ->
    to VisitPage
    at VisitPage
    page.selectViewVisit(visitante, dataInicio, dataFim)
}

/**
 * @author carloscemb
 */
Then(~'the visit details are showed and I can select the option to remove$') {->
    at VisitShowPage
    page.select('input', 'delete')
}

/**
 * @author carloscemb
 */
When(~'^I edit the visit of the visitor named "([^"]*)" with initial date "([^"]*)" and final date "([^"]*)" to the visitor named "([^"]*)"$') { String oldVisitante, String oldDataInicio, String oldDataFim, String newVisitante ->
    def updatedVisit = TestDataAndOperations.editVisit(oldVisitante, oldDataInicio, oldDataFim, newVisitante)
    assert updatedVisit != null
}

/**
 * @author carloscemb
 */
Then(~'^the visit of the visitor named "([^"]*)" with initial date "([^"]*)" and final date "([^"]*)" is properly updated by the system$') { String visitante, String dataInicio, String dataFim ->
    def visitor = Visitor.findByName(visitante)
    Date dia_1 = Date.parse("dd/MM/yyyy", dataInicio)
    Date dia_2 = Date.parse("dd/MM/yyyy", dataFim)
    def visit = Visit.findByVisitorAndDataInicioAndDataFim(visitor, dia_1, dia_2)
    assert visit == null
}

/**
 * @author carloscemb
 */
When(~'^I change the visitor name$') {->
    at VisitShowPage
    page.select('a', 'edit')
    at VisitEditPage
    page.edit()
}

/**
 * @author carloscemb
 */
Then(~'I can select the "([^"]*)" option visit$') { String option ->
    at VisitEditPage
    page.select(option)
}

/**
 * @author penc
 */
Then(~'^the visit for the visitor "([^"]*)" with initial date "([^"]*)" and final date "([^"]*)" is not stored by the system because it is invalid$') { String visitante, String dataInicio, String dataFim ->
    Visitor visitor = Visitor.findByName(visitante)
    Date dia_1 = Date.parse("dd/MM/yyyy", dataInicio)
    Date dia_2 = Date.parse("dd/MM/yyyy", dataFim)
    def visit = Visit.findByVisitorAndDataInicioAndDataFim(visitor, dia_1, dia_2)
    assert visit == null
}

/**
 * @author penc
 */
When(~'^I try to edit the visit of the visitor named "([^"]*)" with initial date "([^"]*)" and final date "([^"]*)" changing the final date to "([^"]*)"$') { String visitante, String dataInicio, String oldDataFim, String newDataFim ->
    TestDataAndOperations.editVisitChangeData(visitante, dataInicio, oldDataFim, newDataFim)
}

/**
 * @author penc
 */
Then(~'^the visit of the visitor named "([^"]*)" with initial date "([^"]*)" and final date "([^"]*)" is not properly updated by the system because it is invalid$') { String visitante, String dataInicio, String dataFim ->
    def visitor = Visitor.findByName(visitante)
    Date dia_1 = Date.parse("dd/MM/yyyy", dataInicio)
    Date dia_2 = Date.parse("dd/MM/yyyy", dataFim)
    def visit = Visit.findByVisitorAndDataInicioAndDataFim(visitor, dia_1, dia_2)
    assert visit != null
}

//#if( $Twitter )

Given(~'^I am logged as "([^"]*)" and at the Add Visit Page$') { String userName ->
    to LoginPage
    at LoginPage
    page.fillLoginData(userName, "adminadmin")
    at PublicationsPage
    page.select("Visita")
    at VisitPage
}

When(~'^I try to create an visit  and I click on Share it in Twitter with "([^"]*)" and "([^"]*)"$') { String twitterLogin, String twitterPw ->
    at VisitPage
    page.selectNewVisit()
    at VisitCreatePage
    page.fillVisitDetails()
    page.clickOnCreate()
    at VisitShowPage
    page.clickOnTwitteIt(twitterLogin, twitterPw)
}

When(~'^I try to create an visit$') { ->
    page.selectNewVisit()
    at VisitCreatePage
    page.fillVisitDetails()
    page.clickOnCreate()
}

Then(~'^A twitter is added to my twitter account regarding the new visit "([^"]*)"$') { String visita ->
    page.addTwitter(visita)
    assert TwitterTool.consult(visita)
}

Then(~'^No twitter should be post$') { ->
    assert !TwitterTool.consult(null)
}

//#end