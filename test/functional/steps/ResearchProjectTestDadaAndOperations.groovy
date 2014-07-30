package steps

import org.springframework.mock.web.MockMultipartFile
import org.springframework.mock.web.MockMultipartHttpServletRequest
import rgms.XMLService
import rgms.publication.XMLController
import rgms.researchProject.ResearchProject
import rgms.researchProject.ResearchProjectController

/**
 * Created by Bruno Soares on 24/02/14.
 */
class ResearchProjectTestDadaAndOperations {

    static researchProjects =[
            [projectName:"Implementação Progressiva de Aplicações Orientadas a Objetos Complexas",
                    description:"Neste projeto pretendemso definir e validar um método para a implementação de aplicações orientadas a objetos complexas. Em particular, este método deve suportar uma abordagem progressiva para implementação orientada a objetos, de forma que aspectos de distribuição, concorrência, e persistência não sejam inicialmente considerados pelo processo de implementação, mas sejam gradualmente introduzidos, preservando os requisitos funcionais da aplicação.",
                    status:"CONCLUIDO",
                    responsible: "Paulo Henrique Monteiro Borba",
                    startYear: 2000,
                    endYear: 2003,
                    funders: FunderTestDataAndOperations.funder[0],
                    members: ["Rubens Lopes da Silva"]
            ],
            [projectName:"Implementação Progressiva de Aplicações Orientadas a Aspectos",
                    description:"Neste projeto pretendemso definir e validar um método para a implementação de aplicações orientadas a Aspectos. Em particular, este método deve suportar uma abordagem progressiva para implementação orientada a aspectos, de forma que aspectos de distribuição, concorrência, e persistência não sejam inicialmente considerados pelo processo de implementação, mas sejam gradualmente introduzidos, preservando os requisitos funcionais da aplicação.",
                    status:"CONCLUIDO",
                    responsible: "Paulo Henrique Monteiro Borba",
                    startYear: 2001,
                    endYear: 2004,
                    members: ["Bruno Soares da Silva","Dyego Felipe Oliveira de Penha", "Pedro Henrique Torres Gonçalves"]
            ],
            [projectName:"Implementação Progressiva de Aplicações Orientadas a Aspectos Complexas",
             description:"",
             status:"CONCLUIDO",
             responsible: "Paulo Henrique Monteiro Borba",
             startYear: 2001,
             endYear: 2004,
             members: ["Bruno Soares da Silva"]
            ],
            [projectName:"Implementação Progressiva de Aplicações Orientadas a Objetos Complexas members duplicated",
               description:"Neste projeto pretendemso definir e validar um método para a implementação de aplicações orientadas a objetos complexas. Em particular, este método deve suportar uma abordagem progressiva para implementação orientada a objetos, de forma que aspectos de distribuição, concorrência, e persistência não sejam inicialmente considerados pelo processo de implementação, mas sejam gradualmente introduzidos, preservando os requisitos funcionais da aplicação.",
               status:"CONCLUIDO",
               responsible: "Paulo Henrique Monteiro Borba",
               startYear: 2000,
               endYear: 2003,
               members: ["Rubens Lopes da Silva", "Rubens Lopes da Silva"]
            ],
            [projectName:"Implementação Progressiva de Aplicações Orientadas a Aspectos Complexas web",
             description:"Neste projeto pretendemso definir e validar um método para a implementação de aplicações orientadas a Aspectos Complexas web. Em particular, este método deve suportar uma abordagem progressiva para implementação orientada a objetos, de forma que aspectos de distribuição, concorrência, e persistência não sejam inicialmente considerados pelo processo de implementação, mas sejam gradualmente introduzidos, preservando os requisitos funcionais da aplicação.",
             status:"CONCLUIDO",
             responsible: "Paulo Henrique Monteiro Borba",
             startYear: 2001,
             endYear: 2004,
            ],
            [projectName:"",
             description:"Neste projeto pretendemso definir e validar um método para a implementação de aplicações orientadas a Aspectos Complexas web. Em particular, este método deve suportar uma abordagem progressiva para implementação orientada a objetos, de forma que aspectos de distribuição, concorrência, e persistência não sejam inicialmente considerados pelo processo de implementação, mas sejam gradualmente introduzidos, preservando os requisitos funcionais da aplicação.",
             status:"CONCLUIDO",
             responsible: "Paulo Henrique Monteiro Borba",
             startYear: 2001,
             endYear: 2004,
            ]
    ]

    static private def findResearchProjectByProjectName(String name) {
        researchProjects.find { orientation ->
            orientation.projectName == name
        }
    }

    private static void createResearchProjectDefault(String name) {
        def cont = new ResearchProjectController()
        cont.params << findResearchProjectByProjectName(name)
        cont.request.setContent(new byte[1000]) // Could also vary the request content.
        cont.create()
        cont.save()
        cont.response.reset()
    }

    public static void createResearchProjectWithoutFunders(String name) {
        def cont = new ResearchProjectController()
        def rp = findResearchProjectByProjectName(name);
        rp.funders = null;
        cont.params << rp;
        cont.request.setContent(new byte[1000]);
        cont.create();
        cont.save();
        cont.response.reset();
    }

    public static void createResearchProject(String name){
        createResearchProjectDefault(name)
    }

    private static ResearchProject getIfResearchProjectExists(String projectName){
        return ResearchProject.findByProjectName(projectName)
    }

    public static void deleteResearchProject(String name){
        def cont = new ResearchProjectController()
        ResearchProject project = getIfResearchProjectExists(findResearchProjectByProjectName(name).projectName)
        if(project) {
            cont.delete(project.id)
        }
    }

    static public void uploadOrientation(filePath) {
        def cont = new XMLController()
        def mockRequest = new MockMultipartHttpServletRequest()
        def uploadedFile = new File(filePath)
        mockRequest.addFile(new MockMultipartFile("file", uploadedFile.bytes))
        cont.metaClass.request = mockRequest
        cont.uploadXMLResearchProject()
    }
}
