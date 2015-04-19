import pages.Conferencia.ConferenciaCreatePage
import pages.Conferencia.ConferenciaPage
import pages.LoginPage
import pages.member.MemberListPage
import pages.member.MemberPage
import pages.member.MemberCreatePage

import pages.PublicationsPage
import pages.Report.ReportHTMLPage
import pages.ResearchGroup.ResearchGroupPage
import pages.ResearchGroup.ResearchGroupListPage
import pages.ResearchGroup.ResearchGroupShowPage

import static cucumber.api.groovy.EN.*

this.metaClass.mixin(cucumber.api.groovy.Hooks)
this.metaClass.mixin(cucumber.api.groovy.EN)

Given(~'^I am at the Member list page$') {->
    Login("admin","adminadmin")
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

Given(~'^I am at the Publications page$') {->
    to LoginPage
    at LoginPage
    page.fillLoginData("admin", "adminadmin")
    at PublicationsPage
}

When(~'^I select the Novo Member option$') { ->
    to MemberCreatePage
    at MemberCreatePage
}

Then(~'^I fill the Member details with "([^"]*)" "([^"]*)" "([^"]*)" "([^"]*)" and create a new one$') { String name, username, email, university ->
    at MemberCreatePage
    page.fillMemberDetails(name, username, email, university, "")
    to MemberListPage
    at MemberListPage
}

//-------------------------------------------------------------------------------------------------
Given(~'^I am at the Research Group list page$') { ->
    Login("admin","adminadmin")
    to ResearchGroupListPage
    at ResearchGroupListPage
}


And(~'^I select the "([^"]*)" option at the Research Group list$') { String researchGroupName ->
    to ResearchGroupListPage
    at ResearchGroupListPage
    page.selectResearchGroup(researchGroupName)
}


//-------------------------------------------------------------------------------------------------

And(~'^I can select the option Export to PDF at the Research Group show$') { ->
    at ResearchGroupShowPage
    page.checkPDF()
}

And(~'^I can generate a PDF report about Research Group "([^"]*)"$') { String researchGroupName ->
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
    at ResearchGroupShowPage
    page.checkHtml()
}

And(~'^I can generate a HTML report about Research Group "([^"]*)"$') { String researchGroupName ->
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
    at ResearchGroupShowPage
    page.checkXml()
}

And(~'^I can generate a XML report about Research Group "([^"]*)"$') { String researchGroupName ->
    page.compareXML(researchGroupName)
}


Then(~'^I can select the Member "([^"]*)" option$') { String memberId ->
    to ReportHTMLPage, memberid: memberId
    page.selectGenerateBibtex(memberId)
}

/*Then(~'^I can see the bibtex details$') {->
}*/


When(~'^I select the Conferencia option at the Publications menu$') {->
    page.select("Conferencia")
}

When(~'^I select the new Conferencia option at the Conferencia page$') {->
    at ConferenciaPage
    page.selectNewConferencia()
}

When(~'^I am at the Publications$') {->
    at PublicationsPage
}

When(~'^I select the home option at the Conferencia page$') {->
    at ConferenciaCreatePage
    page.selectHome()
}

When(~'^I can fill the Conferencia details$') {->
    at ConferenciaCreatePage
    page.fillConferenciaDetails()
}

//---------------------------------------------------------------------------------------------------

#if ($createanewresearchgroup)
Given(~'^I am at the publications menu page$') { ->
    to LoginPage
    at LoginPage
    page.add("admin","adminadmin")
    at PublicationsPage
}

When(~'^I select the "([^"]*)" option at the publications menu page$') { String option ->
    at PublicationsPage
    page.select(option)
}

And(~'^I select the "New Research Group" option at research group list page$') { ->
    at ResearchGroupListPage
    page.select("create")
}

Then(~'^I can fill the field "Nome" with value "([^"]*)"$') { String field, String name ->
    at ResearchGroupCreatePage
    page.fillResearchGroupName(name)
}

And(~'I can fill the field "Twitter" with value "([^"]*)"$') { String field, String twitter ->
    at ResearchGroupCreatePage
    page.fillResearchGroupTwitter(twitter)
}

And(~'^I can fill the field "Descrição" with value "([^"]*)"$') {  String field, String description ->
    at ResearchGroupCreatePage
    page.fillResearchGroupDescription(description)
}

And(~'^I can fill the field "Sigla" with value "([^"]*)"$') {  String field, String sigla ->
    at ResearchGroupCreatePage
    page.fillResearchGroupSigla(sigla)
}

And(~'^I select a member "([^"]*)" at member list') { String memberId ->
    at ResearchGroupCreatePage
    page.selectMember(memberId)
    page.clickOnCreate()
}

Then(~'^I should see the new research group named "([^"]*)" in Research Group list$') { String groupName ->
    at ResearchGroupPage
    assert page.findByName(groupName) != null       /* Checando se o grupo foi criado */
}

#end

//---------------------------------------------------------------------------------------------------

#if ($invalidvalueinfielderrorwhencreatinganewMember)
// invalid value in field error when creating a new Member
Given(~'^I am at the Member list page$') { ->
    to MemberListPage
    at MemberListPage
}

When(~'^I select the "([^"]*)" option$') { String option ->
    at MemberListPage
    page.getMenuOption(option)
}

And(~'^I can fill a field with an invalid value "([^"]*)"') { String value ->
    at MemberCreatePage
    page.fillMemberDetails(value)
}

And(~'^I select "([^"]*)" option') { String value ->
    at MemberCreatePage
    page.clickOnCreate()
}

Then(~'^I should see an error message'){ ->
    at MemberListPage
}

#end

//---------------------------------------------------------------------------------------------------

def Login(String user, String password) {
    to LoginPage
    at LoginPage
    page.fillLoginData(user, password)
    at PublicationsPage
}
