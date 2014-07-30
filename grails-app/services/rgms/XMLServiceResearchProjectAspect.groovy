//#if($researchProject)
package rgms

import rgms.researchProject.ResearchProject

/**
 * Created by Vilmar Santos on 26/07/2014.
 */
class XMLServiceResearchProjectAspect {

    public static final String PROJECT_PARTICIPATION_ACTIVITIES = "ATIVIDADES-DE-PARTICIPACAO-EM-PROJETO"
    public static final String PROJECT_PARTICIPATION = "PARTICIPACAO-EM-PROJETO"
    def mc = XMLService.metaClass
    def init() {

        mc.static.createResearchProjects = { Node xmlFile ->
            //Nesse ponto eu já estou com a lista de Atuacoes Profissionais do XML
            List<Node> pro_perf = ((Node) ((Node) xmlFile.children()[0]).children()[4]).children()
            //Navega ate atuacoes profissionais e extrai a lista de atuacoes

            processPerfilNodes(pro_perf)
        }

        mc.static.processPerfilNodes = { List<Node> pro_perf ->
            for (Node i : pro_perf) { //Atuaçao profissional
                for (Node j : i.children()) { //Atividades de pesquisa em projeto
                    if (((String) j.name()).equals(PROJECT_PARTICIPATION_ACTIVITIES)) {
                        processProjectParticipationActivities(j)
                    }
                }
            }
        }

        mc.static.processProjectParticipationActivities = { Node ppa ->
            for (Node k : ppa.children()) { //Participacao em projeto
                if (((String) k.name()).equals(PROJECT_PARTICIPATION)) {
                    for (Node l : k.children()) { //Projeto de pesquisa
                        saveResearchProject(l)
                    }
                }
            }
        }

         mc.static.saveResearchProject = {Node xmlFile ->
            String name = getAttributeValueFromNode(xmlFile, "NOME-DO-PROJETO")
                ResearchProject project = ResearchProject.findByProjectName(name)

                if (project == null) { //Se o projeto de pesquisa ainda nao existe no sistema hora de adiciona-lo
                    ResearchProject newProject = new ResearchProject()
                    newProject.projectName = name
                    newProject.description = getAttributeValueFromNode(xmlFile, "DESCRICAO-DO-PROJETO")
                    newProject.status = getAttributeValueFromNode(xmlFile, "SITUACAO")
                    newProject.startYear = getAttributeValueFromNode(xmlFile, "ANO-INICIO").toInteger()
                    newProject.endYear = getAttributeValueFromNode(xmlFile, "ANO-FIM").equals("") ? 0 : getAttributeValueFromNode(xmlFile, "ANO-FIM").toInteger()
                    fillProjectMembers(getNodeFromNode(xmlFile, "EQUIPE-DO-PROJETO"), newProject)
                    //#if($funder)
                    fillFunders(getNodeFromNode(xmlFile, "FINANCIADORES-DO-PROJETO"), newProject)
                    //#end
                    newProject.save(flush: false)
                }
        }

        mc.static.fillProjectMembers = { Node xmlFile, ResearchProject project ->

            for (Node node : xmlFile?.children()) { //Para cada integrante presente no projeto
                String name = (String) (node.attribute("NOME-COMPLETO"))
                Boolean responsavel = ((String) (node.attribute("FLAG-RESPONSAVEL"))).equals("SIM")
                if (responsavel) project.responsible = name
                project.addToMembers(name).save(validate: true)
            }
        }
    }
}
//#end
