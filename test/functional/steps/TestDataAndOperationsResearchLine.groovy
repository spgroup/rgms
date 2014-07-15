package steps

import rgms.publication.ResearchLine
import rgms.publication.ResearchLineController

/**
 * Created with IntelliJ IDEA.
 * User: Flavia
 * Date: 29/08/13
 * Time: 00:46
 * To change this template use File | Settings | File Templates.
 */
class TestDataAndOperationsResearchLine {
    static researchLines = [
            [name: "IA Avancada", description: ""],
            [name: "Redes Avancadas", description: "Redes de Computadores Avancadas"],
            [name: "Teoria da informacao - Complexidade no espaco", description: "P=NP"],
            [name: "Novo Padrao Arquitetural MVCE", description: "Nova arquitetura que promete revolucionar a web"],
            [name: "Modelo Cascata Renovado", description: "Altera��o do modelo original"]
    ]

    static public def insertsResearchLine(String name, description) {
        def inserted = ResearchLine.findByName(name)
        if (!inserted) {
           // def research = TestDataAndOperationsResearchLine.findResearchLineByName(name)
            ResearchLine rl = new ResearchLine()
            rl.setName(name)
            rl.setDescription(description)
            rl.save()
        }

    }

    static public def findResearchLineByName(String name) {
        researchLines.find { researchLine ->
            researchLine.name == name
        }
    }

    static public void deleteResearchLine(def id) {
        def res = new ResearchLineController()
        res.params.id = id
        res.request.setContent(new byte[1000]) // Could also vary the request content.
        res.delete()
        res.response.reset()
    }

    static public void updateResearchLine(String name, String description) {
        def res = new ResearchLineController()
        def research_line = ResearchLine.findByName(name)
        res.params.id = research_line.id
        res.params.name = research_line.name
        res.params.description = description
        res.request.setContent(new byte[1000]) // Could also vary the request content.
        res.update()
        res.response.reset()
    }

    static public void createResearchLine(String name) {
        def cont = new ResearchLineController()
        def research = TestDataAndOperationsResearchLine.findResearchLineByName(name)
        cont.params.name = research.name
        cont.params.description = research.description
        cont.request.setContent(new byte[1000]) // Could also vary the request content.
        cont.create()
        cont.save()
        cont.response.reset()
    }

    static public void listAllResearchLine(){
        def cont2 = new ResearchLineController()
        cont2.request.setContent(new byte[1000]) // Could also vary the request content.
        cont2.findAllResearchLine()
        cont2.response.reset()
    }

}
