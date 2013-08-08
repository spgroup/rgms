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

Given(~'^the system has no visitor named "([^"]*)"$') { String name ->
    def visitor = Visitor.findByName(name)
    assert visitor == null
}

When(~'^I create the visit for the visitor "([^"]*)" with initial date "([^"]*)"$') { String name, String date ->
    TestDataAndOperations.createVisit(name, date, date)
}

Then(~'^the visitor named "([^"]*)" is properly stored by the system$') { String name ->
    def visitor = Visitor.findByName(name)
    assert visitor != null
}

And(~'^the visit for the visitor "([^"]*)" with initial and final date equal to "([^"]*)" is properly stored by the system$') { String name, String date ->
    Date day = Date.parse("dd/MM/yyyy", date)
    def visitor = Visitor.findByName(name)
    def visit = Visit.findByVisitorAndDataInicioAndDataFim(visitor, day, day)
    assert visit != null
}

When(~'^I create the visit for the visitor "([^"]*)" with initial date "([^"]*)" and final date "([^"]*)"$') { String name, String initialDate, String finalDate ->
    TestDataAndOperations.createVisit(name, initialDate, finalDate)
}

And(~'^the visit for the visitor "([^"]*)" with initial date "([^"]*)" and final date "([^"]*)" is properly stored by the system$') { String name, String initialDate, String finalDate ->
    def visitor = Visitor.findByName(name)
    def visit = Visit.findByVisitorAndDataInicioAndDataFim(visitor, Date.parse("dd/MM/yyyy", initialDate), Date.parse("dd/MM/yyyy", finalDate))
    assert visit != null
}

Given(~'^the system has visitor named "([^"]*)"$') { String name ->
    TestDataAndOperations.createVisitor(name)
    def visitor = Visitor.findByName(name)
    assert visitor != null
}

/**
 * @author carloscemb
 */
Given(~'^the system has a visit of the visitor named "([^"]*)" with initial date "([^"]*)" and final date "([^"]*)" stored$') { String name, String initialDate, String finalDate ->
    TestDataAndOperations.createVisitor(name)
    def visitor = Visitor.findByName(name)
    assert visitor != null
    TestDataAndOperations.createVisit(name, initialDate, finalDate)
    def visit = Visit.findByVisitorAndDataInicioAndDataFim(visitor, Date.parse("dd/MM/yyyy", initialDate), Date.parse("dd/MM/yyyy", finalDate))
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
Then(~'^the list is returned with the visit of the visitor named "([^"]*)" with initial date "([^"]*)" and final date "([^"]*)"$') { String name, String initialDate, String finalDate ->
    def visitor = Visitor.findByName(name)
    def visit = Visit.findByVisitorAndDataInicioAndDataFim(visitor, Date.parse("dd/MM/yyyy", initialDate), Date.parse("dd/MM/yyyy", finalDate))
    assert TestDataAndOperations.containsVisit(visit)
}

/**
 * @author carloscemb
 */
Given(~'^I am logged as "([^"]*)" and at the visits page\$') { String userName ->
    to LoginPage
    at LoginPage
    page.fillLoginData(userName, "adminadmin")
    at PublicationsPage
    page.select("Visita")
    at VisitPage
}

/**
 * @author carloscemb
 */
Given(~'^the visit of the visitor named "([^"]*)" with initial date "([^"]*)" and final date "([^"]*)" is stored in the system$') { String name, String initialDate, String finalDate ->
at VisitPage
page.selectNewVisit()
at VisitCreatePage
page.fillVisitDetails()
visit = TestDataAndOperations.findVisit(name, initialDate, finalDate)
assert visit != null
}

/**
 * @author carloscemb
 */
Then(~'^my resulting visits list contains the visit of the visitor named "([^"]*)" with initial date "([^"]*)" and final date "([^"]*)"$') { String name, String initialDate, String finalDate ->
    to VisitPage
    at VisitPage
    page.checkVisitAtList(name, initialDate, finalDate, 0)
}

/**
 * @author carloscemb
 */
When(~'^I delete the visit of the visitor named "([^"]*)" with initial date "([^"]*)" and final date "([^"]*)"$') { String name, String initialDate, String finalDate ->
    TestDataAndOperations.removeVisit(name, initialDate, finalDate)
}

/**
 * @author carloscemb
 */
Then(~'^the visit of the visitor named "([^"]*)" with initial date "([^"]*)" and final date "([^"]*)" is properly removed by the system$') { String name, String initialDate, String finalDate ->
    def visitor = Visitor.findByName(name)
    Date day_1 = Date.parse("dd/MM/yyyy", initialDate)
    Date day_2 = Date.parse("dd/MM/yyyy", finalDate)
    def visit = Visit.findByVisitorAndDataInicioAndDataFim(visitor, day_1, day_2)
    assert visit == null
}

/**
 * @author carloscemb
 */
When(~'^I select to view the visit of the visitor named "([^"]*)" with initial date "([^"]*)" and final date "([^"]*)" in resulting list$') { String name, String initialDate, String finalDate ->
    to VisitPage
    at VisitPage
    page.selectViewVisit(name, initialDate, finalDate)
}

/**
 * @author carloscemb
 */
Then(~'the visit details are showed and I can select the option to remove$') {->
    at VisitShowPage
    page.deleteVisit()
}

/**
 * @author carloscemb
 */
When(~'^I edit the visit of the visitor named "([^"]*)" with initial date "([^"]*)" and final date "([^"]*)" to the visitor named "([^"]*)"$') { String oldName, String oldInitialDate, String oldFinalDate, String newName ->
    def updatedVisit = TestDataAndOperations.editVisit(oldName, oldInitialDate, oldFinalDate, newName)
    assert updatedVisit != null
}

/**
 * @author carloscemb
 */
Then(~'^the visit of the visitor named "([^"]*)" with initial date "([^"]*)" and final date "([^"]*)" is properly updated by the system$') { String name, String initialDate, String finalDate ->
    def visitor = Visitor.findByName(name)
    Date day_1 = Date.parse("dd/MM/yyyy", initialDate)
    Date day_2 = Date.parse("dd/MM/yyyy", finalDate)
    def visit = Visit.findByVisitorAndDataInicioAndDataFim(visitor, day_1, day_2)
    assert visit == null
}

/**
 * @author carloscemb
 */
When(~'^I change the visitor name$') {->
    at VisitShowPage
    page.editVisit()
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
Then(~'^the visit for the visitor "([^"]*)" with initial date "([^"]*)" and final date "([^"]*)" is not stored by the system because it is invalid$') { String name, String initialDate, String finalDate ->
    Visitor visitor = Visitor.findByName(name)
    Date day_1 = Date.parse("dd/MM/yyyy", initialDate)
    Date day_2 = Date.parse("dd/MM/yyyy", finalDate)
    def visit = Visit.findByVisitorAndDataInicioAndDataFim(visitor, day_1, day_2)
    assert visit == null
}

/**
 * @author penc
 */
When(~'^I try to edit the visit of the visitor named "([^"]*)" with initial date "([^"]*)" and final date "([^"]*)" changing the final date to "([^"]*)"$') { String name, String initialDate, String oldFinalDate, String newFinalDate ->
    TestDataAndOperations.editVisitChangeData(name, initialDate, oldFinalDate, newFinalDate)
}

/**
 * @author penc
 */
Then(~'^the visit of the visitor named "([^"]*)" with initial date "([^"]*)" and final date "([^"]*)" is not properly updated by the system because it is invalid$') { String name, String initialDate, String finalDate ->
    def visitor = Visitor.findByName(name)
    Date day_1 = Date.parse("dd/MM/yyyy", initialDate)
    Date day_2 = Date.parse("dd/MM/yyyy", finalDate)
    def visit = Visit.findByVisitorAndDataInicioAndDataFim(visitor, day_1, day_2)
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
    at VisitShowPage
    page.clickOnTwitteIt(twitterLogin, twitterPw)
}

When(~'^I try to create an visit$') { ->
    page.selectNewVisit()
    at VisitCreatePage
    page.fillVisitDetails()
}

Then(~'^A twitter is added to my twitter account regarding the new visit "([^"]*)"$') { String visita ->
    page.addTwitter(visita)
    assert TwitterTool.consult(visita)
}

Then(~'^No twitter should be post$') { ->
    assert !TwitterTool.consult(null)
}

//#end