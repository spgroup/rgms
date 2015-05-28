import pages.Conferencia.ConferenciaCreatePage
import pages.Conferencia.ConferenciaPage
import pages.LoginPage
import pages.ResearchGroup.ResearchGroupCreatePage
import pages.member.MemberListPage
import pages.member.MemberPage
import pages.member.MemberCreatePage

import pages.PublicationsPage
import pages.Report.ReportHTMLPage
import pages.ResearchGroup.ResearchGroupPage
import pages.ResearchGroup.ResearchGroupListPage
import pages.ResearchGroup.ResearchGroupShowPage
import rgms.member.Member
import rgms.member.ResearchGroup
import rgms.member.ResearchGroupController

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

//#if ($createanewresearchgroup)
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

//#end

//---------------------------------------------------------------------------------------------------

//#if ($invalidvalueinfielderrorwhencreatinganewMember)
// invalid value in field error when creating a new Member
/*    Given(~'^I am at the Member list page$') { ->
        to MemberListPage
        at MemberListPage
    }
*/
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

//#end

//---------------------------------------------------------------------------------------------------

//created by marcio mendes github: marciojr

//if ($createanewMember)

    Given(~'^I am at the Member menu page$') { ->
        to LoginPage
        at LoginPage
        page.add("admin","adminadmin")
        to MemberPage
    }

When(~'^I select the "New Member" option$') {  ->
    at MemberPage
    MemberPage.select("create")
    to MemberCreatePage
}

And(~'^I can fill the Member "Name" with "([^"]*)"$') { String field, String name, String username, String email, String university ->
    at MemberCreatePage
    MemberCreatePage.fillSomeMemberDetails(name,username,email,university)
    // all fields was created and the input "create" was called on the fillSomeMem... methods
}

Then(~'^I should see the new user at the Member list$') { String MemberName ->
    at MemberPage
}

//end

//-----------------------------------------------------------------------------------------------------------------------------------------------------

// created by marcio mendes github: marciojr

//if ($export a existent research group report to html)

    Given(~'^I am at the Research Group List page$') { ->
        to LoginPage
        at LoginPage
        page.add("admin","adminadmin")
        to ResearchGroupListPage
    }

When(~'^I select the "([^"]*)" option at the Research Group List$') { String name ->
    at ResearchGroupListPage
    ResearchGroupListPage.selectResearchGroup(name)
}

And(~'^I select the option "([^"]*)" at the research group show$') { String name->
    at ResearchGroupListPage
    ResearchGroupShowPage.checkHtml(name) // check if there is the name created
}

Then(~'^I export a html report about resourch group "([^"]*)"$') { ->
    at ResearchGroupListPage
    ResearchGroupController.show() // show html using the own params.id
}

//end

//-----------------------------------------------------------------------------------------------------------------------------------------------------
//if ($missing field error when creating a new Member)

/*Given(~'^I am at the Member list page$'){ ->
    at MemberListPage
}

When(~'^I select the Novo Member option$') { ->
    to MemberCreatePage
}
*/
And(~'^I dont fill a field with * symbol$'){ ->
    assert (page.name.value()  != null &&
            page.username.value() != null &&
            page.email.value() != null &&
            page.university.value() != null)
}

And(~'^I can select Criar option$'){ ->
    page.select("create")
}

Then(~'^I can see a error message$'){ ->
    assert (page.readFlashMessage() != null)
}

//-----------------------------------------------------------------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------------------------------------------------------------
//if ($missing field error when creating a research group)


/*Given(~'^I am at the publications menu$'){ ->
    at PublicationsPage
}
*/
When(~'^I select the "Research Group" option at the publications menu') { ->
    to ResearchGroupListPage
}

And(~'^I select the new research group option at research group list page'){->
    to ResearchGroupCreatePage
}

/*And(~'^I dont fill a field with * symbol$'){ ->
    assert (page.name.value()  != null &&
            page.twitter.value() != null &&
            page.description.value() != null)
}

And(~'^I can select Criar option$'){ ->
    page.select("create")
}

Then(~'^I can see a error message$'){ ->
    assert (page.readFlashMessage() != null)
}*/
//-----------------------------------------------------------------------------------------------------------------------------------------------
//jp start here

Given(~'^I have a ResearchGroup registered in the system$') { ->
    researchGroup = ResearchGroup.getAll()
    assert researchGroup.size() == 1
}

And(~'^I am at ResearchGroupShowPage$') { ->
    at ResearchGroupShowPage
}

When(~'^I select XML Export option$') { ->
    page.clickXML()
}

Then(~'^I receive a download link to XML Report$') { ->

}




//-------------------------------------------------------------------------
def Login(String user, String password) {
    to LoginPage
    at LoginPage
    page.fillLoginData(user, password)
    at PublicationsPage
}
