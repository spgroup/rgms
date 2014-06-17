package steps

import rgms.publication.XMLController
import rgms.researchProject.ResearchProject
import rgms.researchProject.ResearchProjectController

/**
 * Created by Bruno Soares on 24/02/14.
 */
class ResearchProjectTestDadaAndOperations {

    static ResearchProjectController cont = new ResearchProjectController()

    static researchProjects =[
            [projectName:"Implementação Progressiva de Aplicações Orientadas a Objetos Complexas",
                    description:"Neste projeto pretendemso definir e validar um método para a implementação de aplicações orientadas a objetos complexas. Em particular, este método deve suportar uma abordagem progressiva para implementação orientada a objetos, de forma que aspectos de distribuição, concorrência, e persistência não sejam inicialmente considerados pelo processo de implementação, mas sejam gradualmente introduzidos, preservando os requisitos funcionais da aplicação.",
                    status:"CONCLUIDO",
                    responsible: "Paulo Henrique Monteiro Borba",
                    startYear: 2000,
                    endYear: 2003,
                    funders: FunderTestDataAndOperations.funder[0],
                    members: ["Rubens Lopes da Silva"] as Set
            ],
            [projectName:"Implementação Progressiva de Aplicações Orientadas a Aspectos",
                    description:"Neste projeto pretendemso definir e validar um método para a implementação de aplicações orientadas a Aspectos. Em particular, este método deve suportar uma abordagem progressiva para implementação orientada a aspectos, de forma que aspectos de distribuição, concorrência, e persistência não sejam inicialmente considerados pelo processo de implementação, mas sejam gradualmente introduzidos, preservando os requisitos funcionais da aplicação.",
                    status:"CONCLUIDO",
                    responsible: "Paulo Henrique Monteiro Borba",
                    startYear: 2001,
                    endYear: 2004,
                    members: ["Bruno Soares da Silva","Dyego Felipe Oliveira de Penha", "Pedro Henrique Torres Gonçalves"] as Set
            ]
    ]

    static def findResearchProjectByProjectName(String name) {
        researchProjects.find { orientation ->
            orientation.projectName == name
        }
    }

    private static void createResearchProjectDefault(String name) {
        cont.params << findResearchProjectByProjectName(name)
        cont.request.setContent(new byte[1000]) // Could also vary the request content.
        cont.create()
        cont.save()
        cont.response.reset()
    }

    public static void createResearchProject(String name){
        createResearchProjectDefault(name)
    }

    private static ResearchProject getIfResearchProjectExists(String projectName){
        return ResearchProject.findByProjectName(projectName)
    }

    public static void deleteResearchProject(String name){
        ResearchProject project = getIfResearchProjectExists(findResearchProjectByProjectName(name).projectName)
        if(project) {
            cont.delete(project.id)
        }
    }

    static public void uploadOrientation(filepath) {
        def cont = new XMLController()
        def xml = new File((String) filepath);
        def records = new XmlParser()
        cont.saveReseachProject(records.parse(xml));
        cont.response.reset()
    }

    static public boolean compatibleTo(project, projectName) {
        def testProject = findResearchProjectByProjectName(projectName)
        testProject.funders = null //desconsiderar provisoriamente
        def compatible = false
        if (testProject == null && project == null) {
            compatible = true
        } else if (testProject != null && project != null) {
            compatible = true
            testProject.each { key, data ->
                compatible = compatible && (project."$key" == data)
            }
        }
        return compatible
    }
}
