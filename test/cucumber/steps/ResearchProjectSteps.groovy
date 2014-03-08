package steps

import pages.researchProject.ResearchProjectPage
import rgms.researchProject.ResearchProject

/**
 * Created by Bruno Soares on 24/02/14.
 */

import static cucumber.api.groovy.EN.*

def initialSize = 0

//Create Research Project
Given(~'^the system has no research project named as "([^"]*)"$'){ String projectName ->
    assert checkIfResearchProjectNoExists(projectName)
}

When(~'^I create a research project named as "([^"]*)"$'){ String projectName ->
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

Then(~'^the research project "([^"]*)" is not store twice$'){ String projectName ->
    assert ResearchProject.findAllByProjectName(projectName).size() == 1
}


//Remove Research Project
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
    TestDataAndOperations.loginController(this)
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

//Aux Functions
def checkIfResearchProjectNoExists(String projectName){
    ResearchProject project = ResearchProject.findByProjectName(projectName)
    project == null
}

def checkIfResearchProjectExists(String projectName){
    ResearchProject project = ResearchProject.findByProjectName(projectName)
    ResearchProject project2 = ResearchProjectTestDadaAndOperations.findResearchProjectByProjectName(projectName)
    project.equals(project2)
}