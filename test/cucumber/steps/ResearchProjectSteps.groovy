package steps

import org.apache.shiro.SecurityUtils
import org.apache.shiro.UnavailableSecurityManagerException
import pages.researchProject.ResearchProjectPage
import pages.researchProject.ResearchProjectPageCreatePage
import rgms.authentication.User
import rgms.member.Member
import rgms.researchProject.ResearchProject

import static cucumber.api.groovy.EN.*

/**
 * Created by Bruno Soares on 24/02/14.
 * Edited by Gert Müller and Vilmar Nepomuceno on 16/06/14.
 */
def initialSize = 0

def List<ResearchProject> oldProjects

// ------------------------------------------------------------------------------------------------------------------------------
// Scenario: new research project

Given(~'^the system has no research project named as "([^"]*)"$') { String projectName ->
    if(!checkIfResearchProjectNoExists(projectName)) {
        ResearchProject.findByProjectName(projectName).delete();
        assert checkIfResearchProjectNoExists(projectName);
    }
}

When(~'^I create a research project named as "([^"]*)" with all required data$') { String projectName ->
    ResearchProjectTestDadaAndOperations.createResearchProject(projectName);
    assert ResearchProject.findByProjectName(projectName) != null;
}

Then(~'^the research project "([^"]*)" is properly stored by the system$') { String projectName ->
    assert checkIfResearchProjectExists(projectName);
}
// ------------------------------------------------------------------------------------------------------------------------------


// ------------------------------------------------------------------------------------------------------------------------------
// Scenario: duplicated research project

Given(~'^the system has a research project named as "([^"]*)"$') { String projectName ->
    ResearchProject project = ResearchProject.findByProjectName(projectName);

    if(!project) {
        ResearchProjectTestDadaAndOperations.createResearchProject(projectName);
    }

    assert ResearchProject.findByProjectName(projectName) != null;
}

When(~'^I try to create a research project named as "([^"]*)"$') { String projectName ->
    oldProjects =  ResearchProject.findAll();
    ResearchProjectTestDadaAndOperations.createResearchProject(projectName);
}

Then(~'^the research project "([^"]*)" is not stored twice$') { String projectName ->
    assert ResearchProject.findAllByProjectName(projectName).size() == 1;
}
// ------------------------------------------------------------------------------------------------------------------------------


// ------------------------------------------------------------------------------------------------------------------------------
// Scenario: remove research project

Given(~'^I am logged into the system as administrator of the research group named as "([^"]*)"$') { String projectName ->
    assert checkIfLoggedUserIsAdminOfResearchProject(projectName);
}

When(~'^I remove the research project named as "([^"]*)"$') { String projectName ->
    ResearchProjectTestDadaAndOperations.deleteResearchProject(projectName);
}

Then(~'^the research project named as "([^"]*)" is properly removed by the system$') { String projectName ->
    assert checkIfResearchProjectNoExists(projectName);
}
// ------------------------------------------------------------------------------------------------------------------------------


// ------------------------------------------------------------------------------------------------------------------------------
// Scenario: new research project without funders

When(~'^I create a research project named as "([^"]*)" without funders$'){ String projectName ->
    ResearchProjectTestDadaAndOperations.createResearchProjectWithoutFunders(projectName);
    assert ResearchProject.findByProjectName(projectName) != null;
}
// ------------------------------------------------------------------------------------------------------------------------------


// ------------------------------------------------------------------------------------------------------------------------------
// Scenario: upload research project with a file
// XML import

Given(~'^the system has some research project stored$'){ ->
    initialSize = ResearchProject.findAll().size()
}

When(~'^I upload new research projects from the file "([^"]*)"$') { filename ->
    def path = new File(".").getCanonicalPath() + File.separator + "test" + File.separator + "files" + File.separator
    ResearchProjectTestDadaAndOperations.uploadOrientation(path + filename)
    TestDataAndOperations.logoutController(this)
}

Then(~'^the system has more research projects now$'){ ->
    finalSize = ResearchProject.findAll().size()
    assert initialSize < finalSize
}
// ------------------------------------------------------------------------------------------------------------------------------


// ------------------------------------------------------------------------------------------------------------------------------
// XML Import web

When (~'^I select the upload button at the research project page$'){ ->
    at ResearchProjectPage
    page.submitXML()
}

Then (~'^I\'m still on the research project page$'){ ->
    at ResearchProjectPage
}

Then (~'^the system shows an error message at the research project page$'){ ->
    assert page.hasInvalidXMLSubmited()
}
// ------------------------------------------------------------------------------------------------------------------------------


// ------------------------------------------------------------------------------------------------------------------------------
// Scenario: list research projects where I am a member

Given(~'^I am at the research project list page$') { ->
    at ResearchProjectPage;
}

When(~'^I select the option to show my research projects$') { ->
    page.myProjects();
}

Then(~'^the system shows a list with the research projects where I am a member$') { ->
    page.checkResearchGroupHasMeAsMember();
}
// ------------------------------------------------------------------------------------------------------------------------------


// ------------------------------------------------------------------------------------------------------------------------------
// Scenario: filter research projects by name

When(~'^I fill the project name filter field with "([^"]*)"$') { String filter ->
    page.fillFilterResearchProject(filter);
}

When(~'^select the option to filter the research projects$') { ->
    page.filter();
}

Then(~'^the system shows the research projects with the name "([^"]*)"$') { String filter ->
    page.checkResearchGroupListFilteredByName(filter);
}
// ------------------------------------------------------------------------------------------------------------------------------


// ------------------------------------------------------------------------------------------------------------------------------
// Scenario: remove research project that does not exist

Given(~'^I am logged into the system as administrator$') { ->
    TestDataAndOperations.loginController(this);
}
// ------------------------------------------------------------------------------------------------------------------------------


// ------------------------------------------------------------------------------------------------------------------------------
// Scenario: edit existing research project

def projectDataUpdate = "Descrição teste";

When(~'^I edit the research project "([^"]*)" in the system$') { String projectName ->
    to ResearchProjectPage;
    page.selectReseachGroup(projectName);
    at ResearchProjectPageShowPage;
    page.selectEditReseachGroup();
    at ResearchProjectPageEditPage;
    page.fillUpdateDescription(projectDataUpdate);
    page.saveUpdates();
}

Then(~'^the data of the research project named "([^"]*)" is updated in the system$') { String projectName ->
    def check = false;
    def rp = ResearchProject.findByProjectName(projectName);

    if(rp!=null && rp.description==projectDataUpdate) {
        check = true;
    }

    assert check;
}
// ------------------------------------------------------------------------------------------------------------------------------


// ------------------------------------------------------------------------------------------------------------------------------
Then(~'^no research project stored is affected'){ ->
    assert checkIfNoResearchProjectAffected(oldProjects)
}

Then(~'^the research project "([^"]*)" is not stored by the system because it is invalid$'){ String projectName ->
    assert ResearchProject.findByProjectName(projectName) == null
}

When(~'^I try to create a research project named as "([^"]*)" with description field blank$'){ String projectName ->
    oldProjects =  ResearchProject.findAll()
    ResearchProjectTestDadaAndOperations.createResearchProject(projectName)
}

When(~'^I create a research project named as "([^"]*)" with member field duplicated$'){ String projectName ->
    ResearchProjectTestDadaAndOperations.createResearchProject(projectName)
    assert ResearchProject.findByProjectName(projectName) != null;
}

Then(~'^the research project "([^"]*)" does not have duplicated members$'){String projectName ->
    ResearchProject project  = ResearchProject.findByProjectName(projectName);
    boolean check = true
    Iterator i = project.members.iterator()
    while (i.hasNext() && check) {
        String m = i.next()
        project.members.remove(m)
        if (project.members.contains(m)) {
            check = false
        }
    }

    assert check
}

Given (~'^I go to new research project page$'){ ->
    to ResearchProjectPage
    page.selectNewReseachGroup()
    at ResearchProjectPageCreatePage

}

When(~'^I create a research project named as "([^"]*)" with all required data filled on the web$'){ String projectName ->
    page.fillResearchProject(projectName)
    page.createResearchProject()
    assert ResearchProject.findByProjectName(projectName) != null
}

Then (~'^I\'m still on the new research project page$'){ ->
    at ResearchProjectPageCreatePage
}

Then (~'^the system shows an error message at the new research project page$') { ->
    at ResearchProjectPageCreatePage
    page.checkHasErrorMsg()
}

Then(~'^it is shown in the research project list with name "([^"]*)"$'){ String projectName ->
    to ResearchProjectPage
    page.checkResearchGroupAtList(projectName)
}

When(~'^I try to create a research project named as "([^"]*)" with description field blank on the web$'){ String projectName ->
    // updated
    oldProjects =  ResearchProject.findAll()
    page.fillResearchProjectWithBlankDescription(projectName)
    page.createResearchProject()
}

Given(~'^the system has a research project named as "([^"]*)" created on web$') { String projectName ->
    ResearchProject project = ResearchProject.findByProjectName(projectName);

    if(!project) {
        page.fillResearchProject(projectName)
        page.createResearchProject()
        to ResearchProjectPage
        page.selectNewReseachGroup()
        at ResearchProjectPageCreatePage
    }

    assert ResearchProject.findByProjectName(projectName) != null;
}

When(~'^I try to create a research project named as "([^"]*)" on the web$'){ String projectName ->
    // updated
    oldProjects =  ResearchProject.findAll()
    at ResearchProjectPageCreatePage
    page.fillResearchProject(projectName)
    page.createResearchProject()
}

Then(~'^the research project "([^"]*)" is not shown duplicated in the research project list$'){ projectName ->
    to ResearchProjectPage
    page.checkResearchGroupDuplicatedAtList(projectName)
}

// ------------------------------------------------------------------------------------------------------------------------------
// Aux Functions

def checkIfResearchProjectNoExists(String projectName){
    ResearchProject project = ResearchProject.findByProjectName(projectName)
    project == null
}

def checkIfResearchProjectExists(String projectName){
    ResearchProject project = ResearchProject.findByProjectName(projectName)
    ResearchProject project2 = ResearchProjectTestDadaAndOperations.findResearchProjectByProjectName(projectName)
    project.equals(project2)
}

def checkIfNoResearchProjectAffected(oldProjects) {
    boolean check = true;
    List<ResearchProject> beforeProjects = oldProjects
    List<ResearchProject> afterProjects = ResearchProject.findAll()

    if(!beforeProjects.equals(afterProjects)) {
        check = false
    }

    check
}

def checkIfLoggedUserIsAdminOfResearchProject(String projectName) {
    ResearchProject project = ResearchProject.findByProjectName(projectName);
    Member author = null;

    try {
        if(SecurityUtils.subject?.principal!=null) {
            User user = User.findByUsername(SecurityUtils.subject.principal);
            author = user.getAuthor();
        }
    } catch(UnavailableSecurityManagerException e) {
        return false;
    }

    return author.name == project.responsible;
}