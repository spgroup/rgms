import pages.LoginPage
import pages.MemberListPage
import pages.MemberPage

import pages.PublicationsPage
import pages.ResearchGroupListPage
import pages.ResearchGroupPage

import static cucumber.api.groovy.EN.*

this.metaClass.mixin(cucumber.api.groovy.Hooks)
this.metaClass.mixin(cucumber.api.groovy.EN)

Given(~'^I am at the Member list page$') {->
    to LoginPage
    at LoginPage
    page.fillLoginData("admin", "adminadmin")
    at PublicationsPage
    to MemberListPage
    at MemberListPage
}

When(~'^I select the "([^"]*)" option at the Member list$') { String memberName ->
    page.selectMember(memberName)
}

And(~'^I can select the option Export to PDF at the Member show$') { ->
    at MemberPage
    page.checkPdf()
}

Then(~'^I can generate a PDF report about Member "([^"]*)"$') { String memberName ->
    page.comparePDF(memberName)
}
//-----------------------------------------------------------------------------------------------
/*Given(~'^I am at the Member list page$') { ->
    to LoginPage
    at LoginPage
    page.fillLoginData("admin", "adminadmin")
    at PublicationsPage
    to MemberListPage
    at MemberListPage
}

When(~'^I select the "([^"]*)" option at the Member list$') { String memberName ->
    page.selectMember(memberName)
}  */

And(~'^I can select the option Export to HTML at the Member show$') {  ->
    at MemberPage
    page.checkHtml()
}

Then(~'^I can generate a HTML report about Member "([^"]*)"$') { String memberName ->
    page.compareHTML(memberName)
}
//-------------------------------------------------------------------------------------------------
/*Given(~'^I am at the Member list page$') { ->
    to LoginPage
    at LoginPage
    page.fillLoginData("admin", "adminadmin")
    at PublicationsPage
    to MemberListPage
    at MemberListPage
}

When(~'^I select the "([^"]*)" option at the Member list$') { String memberName ->
    page.selectMember(memberName)
}   */

And(~'^I can select the option Export to XML at the Member show$') {  ->
    at MemberPage
    page.checkXml()
}

Then(~'^I can generate a XML report about Member "([^"]*)"$') { String memberName ->
    page.compareXML(memberName)
}
//-------------------------------------------------------------------------------------------------
Given(~'^I am at the Research Group list page and I select the "([^"]*)" group$') { String researchGroupName ->
    to LoginPage
    at LoginPage
    page.fillLoginData("admin", "adminadmin")
    at PublicationsPage
    to ResearchGroupListPage
    at ResearchGroupListPage
}

When(~'^I select the "([^"]*)" option at the Research Group list$') { String researchGroupName ->
    page.selectResearchGroup(researchGroupName)
}

And(~'^I can select the option Export to PDF at the Research Group show$') { ->
    at ResearchGroupPage
    page.checkPDF()
}

Then(~'^I can generate a PDF report about Research Group "([^"]*)"$') { String researchGroupName ->
    page.comparePDF(researchGroupName)
}
//-------------------------------------------------------------------------------------------------
/*Given(~'^I am at the Research Group list page and I select the "([^"]*)" group$') { String researchGroupName ->
    to LoginPage
    at LoginPage
    page.fillLoginData("admin", "adminadmin")
    at PublicationsPage
    to ResearchGroupListPage
    at ResearchGroupListPage
}
   /*
When(~'^I select the "([^"]*)" option at the Research Group list$') { String researchGroupName ->
    page.selectResearchGroup(researchGroupName)
}  */

And(~'^I can select the option Export to HTML at the Research Group show$') {  ->
    at ResearchGroupPage
    page.checkHtml()
}

Then(~'^I can generate a HTML report about Research Group "([^"]*)"$') { String researchGroupName ->
    page.compareHTML(researchGroupName)
}
//-------------------------------------------------------------------------------------------------
/*Given(~'^I am at the Research Group list page and I select the "([^"]*)" group$') { String researchGroupName ->
    to LoginPage
    at LoginPage
    page.fillLoginData("admin", "adminadmin")
    at PublicationsPage
    to ResearchGroupListPage
    at ResearchGroupListPage
}
     /*
When(~'^I select the "([^"]*)" option at the Research Group list$') { String researchGroupName ->
    page.selectResearchGroup(researchGroupName)
} */

And(~'^I can select the option Export to XML at the Research Group show$') {  ->
    at ResearchGroupPage
    page.checkXml()
}

Then(~'^I can generate a XML report about Research Group "([^"]*)"$') { String researchGroupName ->
    page.compareXML(researchGroupName)
}