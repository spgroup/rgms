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
 */
def initialSize = 0

//Create Research Project
Given(~'^the system has no research project named as "([^"]*)"$'){ String projectName ->

    if (!checkIfResearchProjectNoExists(projectName)) {
        ResearchProject.findByProjectName(projectName).delete()
    }
}

When(~'^I create a research project named as "([^"]*)" with all required data$'){ String projectName ->
    ResearchProjectTestDadaAndOperations.createResearchProject(projectName)
}

Then(~'^the research project "([^"]*)" is properly stored by the system$'){ String projectName ->
    assert checkIfResearchProjectExists(projectName)
}

//Duplicated Research Project
Given(~'^the system has a research project named as "([^"]*)"$'){ String projectName ->
    ResearchProject project = ResearchProject.findByProjectName(projectName)
    if(!project){
        ResearchProjectTestDadaAndOperations.createResearchProject(projectName)
    }
    assert ResearchProject.findByProjectName(projectName) != null
}

When(~'^I try to create a research project named as "([^"]*)"$'){ String projectName ->
    // updated
    ResearchProjectTestDadaAndOperations.oldProjects =  ResearchProject.findAll()
    ResearchProjectTestDadaAndOperations.createResearchProject(projectName)
}

Then(~'^the research project "([^"]*)" is not stored twice$'){ String projectName ->
    assert ResearchProject.findAllByProjectName(projectName).size() == 1
}

//Remove Research Project

Given(~'^I am logged into the system as administrator of the research group named as "([^"]*)"$') { String projectName ->
    assert checkIfLoggedUserIsAdminOfResearchProject(projectName);
}

When(~'^I remove the research project named as "([^"]*)"$'){ String projectName ->
    ResearchProjectTestDadaAndOperations.deleteResearchProject(projectName)
}

Then(~'^the research project named as "([^"]*)" is properly removed by the system$'){ String projectName ->
    assert checkIfResearchProjectNoExists(projectName)
}

//new research project without funders
When(~'^I create a research project named as "([^"]*)" without funders$'){ String projectName ->
    ResearchProjectTestDadaAndOperations.createResearchProject(projectName)
}

//XML import
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

//XML Import web
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
// filter research projects by name

When(~'^I fill the project name field$') { ->

}

When(~'^I select the option "Filtrar Projetos de Pesquisa"$') { ->
    page.select("Filtrar Projetos de Pesquisa");
}

Then(~'^the system shows the research projects listed by the research projects name$') { ->

}

// ------------------------------------------------------------------------------------------------------------------------------
// remove research project that does not exist

Given(~'^I am logged into the system as administrator$') { ->
    to LoginPage;
    at LoginPage;
    page.fillLoginData("admin", "adminadmin");
}

When(~'^I try to remove a research project named "([^"]*)"$') { String projectName ->

}

Then(~'^nothing happens to the research projects stored$') { ->

}
// ------------------------------------------------------------------------------------------------------------------------------

// ------------------------------------------------------------------------------------------------------------------------------
// list research projects where I am a member

Given(~'^I am at the research project list page$') { ->
    at ResearchProjectPage;
}

When(~'^I select the "Meus Projetos de Pesquisa" option at research project menu$') { ->
    page.select("Meus Projetos de Pesquisa");
}

Then(~'^the system shows a list with the research projects where I am a member$') { ->

}

// ------------------------------------------------------------------------------------------------------------------------------
// edit existing research project
Then(~'^the data of the research project named "([^"]*)" is updated in the system$') { String projectName ->

}
// ------------------------------------------------------------------------------------------------------------------------------

// NOVOS Vilmar
Then(~'^no research project stored is affected'){ ->
    assert checkIfNoResearchProjectAffected()
}

Given(~'^I am logged in the system$'){ ->
    TestDataAndOperations.loginController(this)
}

Then(~'^the research project "([^"]*)" is not stored by the system because it is invalid$'){ String projectName ->
    assert ResearchProject.findByProjectName(projectName) == null
}

When(~'^I try to create a research project named as "([^"]*)" with description field blank$'){ String projectName ->
    ResearchProjectTestDadaAndOperations.oldProjects =  ResearchProject.findAll()
    ResearchProjectTestDadaAndOperations.createResearchProject(projectName)
}

When(~'^I create a research project named as "([^"]*)" with member field duplicated$'){ String projectName ->
    ResearchProjectTestDadaAndOperations.createResearchProject(projectName)
}

Then(~'^the research project "([^"]*)" does not have duplicated members$'){String projectName ->
    ResearchProject project  = ResearchProject.findByProjectName(projectName);
    boolean check = true
    // Não vai ter nunca porque é um SET
    Iterator i = project.members.iterator()
    while (i.hasNext()) {
        String m = i.next()
        project.members.remove(m)
        if (project.members.contains(m)) {
            check = false
            break
        }
    }

    assert check
}

Given (~'^I am at new research project page$'){ ->
    to ResearchProjectPage
    page.selectNewReseachGroup()
    at ResearchProjectPageCreatePage

}

When(~'^I can create a research project named as "([^"]*)" with all required data$'){ String projectName ->
    page.fillResearchProjectDetails(projectName)
    page.createResearchProject()
}

Then (~'^I\'m still on the new research project page$'){ ->
    at ResearchProjectPageCreatePage
}

Then(~'^it is shown in the research project list with name "([^"]*)"$'){ String projectName ->
    to ResearchProjectPage
    page.checkResearchGroupAtList(projectName)
}

When(~'^I can try to create a research project named as "([^"]*)" with description field blank$'){ String projectName ->
    // updated
    ResearchProjectTestDadaAndOperations.oldProjects =  ResearchProject.findAll()
    page.fillResearchProjectDetailsWithBlankDescription(projectName)
    page.createResearchProject()
}


When(~'^I can try to create a research project named as "([^"]*)"$'){ String projectName ->
    // updated
    ResearchProjectTestDadaAndOperations.oldProjects =  ResearchProject.findAll()
    at ResearchProjectPageCreatePage
    page.fillResearchProjectDetails(projectName)
    page.createResearchProject()
}

Then(~'^the research project "([^"]*)" is not shown duplicated in the research project list$'){ projectName ->
    to ResearchProjectPage
    page.checkResearchGroupDuplicatedAtList(projectName)
}

Then(~'^the system shows an warning message at the new research project page$'){ ->
    at ResearchProjectPageCreatePage
    page.checkHasErrorMsg()
}

//Aux Functions
def checkIfResearchProjectNoExists(String projectName){
    ResearchProject project = ResearchProject.findByProjectName(projectName)
    project == null
}

def checkIfResearchProjectExists(String projectName){
    ResearchProject project = ResearchProject.findByProjectName(projectName)
    Console.println(projectName)
    if (project == null) {
        Console.println("Project NUll")
    }
    ResearchProject project2 = ResearchProjectTestDadaAndOperations.findResearchProjectByProjectName(projectName)
    project.equals(project2)
}

def checkIfNoResearchProjectAffected() {
    boolean check = true;
    List<ResearchProject> beforeProjects = ResearchProjectTestDadaAndOperations.oldProjects
    List<ResearchProject> afterProjects = ResearchProject.findAll()

    for(int i = 0; i < beforeProjects.size();i++) {
        if (!beforeProjects.get(i).equals(afterProjects.get(i))) {
            check = false
            if (!check) break
        }
    }

    check
}

def checkIfLoggedUserIsAdminOfResearchProject(String projectName) {
    ResearchProject project = ResearchProject.findByProjectName(projectName);
    Member author = null;

    try {
        if(SecurityUtils.subject?.principal != null) {
            User user = User.findByUsername(SecurityUtils.subject.principal);
            author = user.getAuthor();
        }
    } catch(UnavailableSecurityManagerException e) {
        return false;
    }

    return author.name == project.responsible;
}