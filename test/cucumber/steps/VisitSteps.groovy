import pages.PublicationsPage
import pages.visit.VisitEditPage
import rgms.visit.Visit
import rgms.visit.Visitor
import pages.LoginPage
import pages.visit.VisitPage
import pages.visit.VisitCreatePage
import pages.visit.VisitShowPage
import pages.visit.VisitConfirmPage
import rgms.tool.TwitterTool
import steps.TestDataAndOperationsVisit

import static cucumber.api.groovy.EN.*

import java.text.SimpleDateFormat

Given(~'^the system has no visitor named "([^"]*)"$') { String name ->
    assert Visitor.findByName(name) == null
}

When(~'^I create the visit for the visitor "([^"]*)" with initial date "([^"]*)"$') { String name, String date ->
    TestDataAndOperationsVisit.createVisit(name, date, date)
}

Then(~'^the visitor named "([^"]*)" is properly stored by the system$') { String name ->
    assert Visitor.findByName(name) != null
}

And(~'^the visit for the visitor "([^"]*)" with initial and final date equal to "([^"]*)" is properly stored by the system$') { String name, String date ->
    assert TestDataAndOperationsVisit.searchVisit(name, date, date) != null
}

When(~'^I create the visit for the visitor "([^"]*)" with initial date "([^"]*)" and final date "([^"]*)"$') { String name, String initialDate, String finalDate ->
    TestDataAndOperationsVisit.createVisit(name, initialDate, finalDate)
}

And(~'^the visit for the visitor "([^"]*)" with initial date "([^"]*)" and final date "([^"]*)" is properly stored by the system$') { String name, String initialDate, String finalDate ->
    assert TestDataAndOperationsVisit.searchVisit(name, initialDate, finalDate) != null
}

Given(~'^the system has visitor named "([^"]*)"$') { String name ->
    TestDataAndOperationsVisit.createVisitor(name)
    assert Visitor.findByName(name) != null
}

/**
 * @author carloscemb
 */
And(~'^a visit for the visitor "([^"]*)" with initial date "([^"]*)" and final date "([^"]*)"$') { String name, String initialDate, String finalDate ->
    TestDataAndOperationsVisit.createVisit(name, initialDate, finalDate)
    assert TestDataAndOperationsVisit.searchVisit(name, initialDate, finalDate) != null
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
    def visit = TestDataAndOperationsVisit.searchVisit(name, initialDate, finalDate)
    assert TestDataAndOperationsVisit.containsVisit(visit)
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
    visit = TestDataAndOperationsVisit.findVisit(name, initialDate, finalDate)
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
    TestDataAndOperationsVisit.removeVisit(name, initialDate, finalDate)
}

/**
 * @author carloscemb
 */
Then(~'^the visit of the visitor named "([^"]*)" with initial date "([^"]*)" and final date "([^"]*)" is properly removed by the system$') { String name, String initialDate, String finalDate ->
    assert TestDataAndOperationsVisit.searchVisit(name, initialDate, finalDate) == null
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
    def updatedVisit = TestDataAndOperationsVisit.editVisit(oldName, oldInitialDate, oldFinalDate, newName)
    assert updatedVisit != null
}

/**
 * @author carloscemb
 */
Then(~'^the visit of the visitor named "([^"]*)" with initial date "([^"]*)" and final date "([^"]*)" is properly updated by the system$') { String name, String initialDate, String finalDate ->
    assert TestDataAndOperationsVisit.searchVisit(name, initialDate, finalDate) == null
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
    assert TestDataAndOperationsVisit.searchVisit(name, initialDate, finalDate) == null
}

/**
 * @author penc
 */
When(~'^I try to edit the visit of the visitor named "([^"]*)" with initial date "([^"]*)" and final date "([^"]*)" changing the final date to "([^"]*)"$') { String name, String initialDate, String oldFinalDate, String newFinalDate ->
    TestDataAndOperationsVisit.editVisitChangeData(name, initialDate, oldFinalDate, newFinalDate)
}

/**
 * @author penc
 */
Then(~'^the visit of the visitor named "([^"]*)" with initial date "([^"]*)" and final date "([^"]*)" is not properly updated by the system because it is invalid$') { String name, String initialDate, String finalDate ->
    assert TestDataAndOperationsVisit.searchVisit(name, initialDate, finalDate) != null
}


/**
 * @author mclv
 */
Given(~'^the system has visits with initial or final date greater than or equal "([^"]*)"$') { String date ->
    TestDataAndOperationsVisit.createVisit("Person", date, date)
}

/**
 * @author mclv
 */
When(~'^I ask for the list of visits for the period from "([^"]*)" to today$') { String date ->
    def visits = TestDataAndOperationsVisit.searchVisit("Person", date, date)
    //def visits = Visit.findAllFromPeriod(date)
    assert visits != null
}

/**
 * @author mclv
 */
Then(~'^a visit with initial or final date greater than or equal "([^"]*)" is not lost$') { String date ->
    def visit = TestDataAndOperationsVisit.searchVisit("Person", date, date)
    assert TestDataAndOperationsVisit.containsVisit(visit)
}

/**
 * @author mclv
 */
Given(~'^I am at the Add Visit Page$') { ->
    to VisitCreatePage
    at VisitCreatePage
}

/**
 * @author mclv
 */
Given(~'^I have created a visitor named "([^"]*)"$') { String name ->
    at VisitCreatePage
    page.fillVisitDetails(name)
    page.clickOnCreate()
}

/**
 * @author mclv
 */
When(~'^I try to create the visit for the visitor "([^"]*)"$') { String name ->
    to VisitCreatePage
    at VisitCreatePage
    page.fillVisitDetails(name)
    page.clickOnCreate()
}

/**
 * @author mclv
 */
Then(~'^the Confirm Identification Page is open$') { ->
    at VisitConfirmPage
}

/**
 * @author mclv
 */
Given(~'^I have tried to create a visit for the visitor "([^"]*)" that already exists$') { String name ->
    to VisitCreatePage
    at VisitCreatePage
    page.fillVisitDetails(name)
    page.clickOnCreate()
}

/**
 * @author mclv
 */
Given(~'^I am at the Confirm Identification Page$') { ->
    at VisitConfirmPage
}

/**
 * @author mclv
 */
When(~'^I press the button "([^"]*)"$') { String button->
    at VisitConfirmPage
    if (button.equalsIgnoreCase("Yes"))
    	page.clickOnYes()
    else if (button.equalsIgnoreCase("No"))
    	page.clickOnNo()
}

/**
 * @author mclv
 */
Then(~'^the visit for the visitor "([^"]*)" is properly stored by the system$') { String name ->
    def date = new Date()  
    sdf = new SimpleDateFormat("dd/MM/yyyy")  
    String st = sdf.format(date)
    assert TestDataAndOperationsVisit.searchVisit(name, st, st) != null
}

//#if( $Twitter )

Given(~'^I am logged as "([^"]*)" and at the Add Visit Page$') { String userName ->
	to LoginPage
	at LoginPage
	page.fillLoginData(userName, "adminadmin")
	to VisitPage
}

When(~'^I try to create an visit$') { ->
    at VisitPage
    page.selectNewVisit()
    at VisitCreatePage
    page.fillVisitDetails()

}

When(~'^I share it in Twitter with "([^"]*)" and "([^"]*)"$') { String twitterLogin, String twitterPw ->
    at VisitShowPage
    page.clickOnTwitteIt(twitterLogin, twitterPw)

}


Then(~'^A tweet is added to my twitter account regarding the new visit "([^"]*)"$') { String visit ->
    page.addTwitter(visit)
    assert TwitterTool.consult(visit)
 }

Then(~'^The visit "([^"]*)" is created but no tweet should be post$') {String visit ->
   assert !TwitterTool.consult(null)
	 }

//#end
