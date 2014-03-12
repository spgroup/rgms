package steps

import rgms.publication.ResearchLine
import rgms.publication.ResearchLineController
import rgms.publication.XMLController

/**
 * Created by Bruno Soares on 23/02/14.
 */
class ResearchLineTestDataAndOperations {

    static researchlines = [
         [name: "Estudos empiricos em Criptografia Quantica", description: "Esta area de estudo busca compreender como a evolução da computação quantica pode contribuir ou prejudicar o nível de segurança dos atuais sistemas criptograficos."],
         [name: "Desenvolvimento Progressivo de Sistemas Complexos Orientados a Objetos", description: "O foco desta linha de pesquisa é a definição e aperfeiçoamento do processo de implementação (ou codificação) de aplicações orientadas a objetos complexas, de forma que tais aplicações possam ser mais facilmente implementadas, testadas, e adaptadas. Com isso pretendemos ajudar a aumentar a produtividade, reduzindo tempo e custos de desenvolvimento, dos engenheiros de software que usem ou venham a usar um processo de desenvolvimento orientado a objetos. Além disso, esperamos possibilitar a implementação de aplicações com níveis de confiabilidade, extensibilidade, e reusabilidade adequados para as necessidades de um mercado cada vez mais globalizado e competitivo."]
    ]

    static public void uploadResearchLine(filepath) {
        def cont = new XMLController()
        def xml = new File((String) filepath);
        def records = new XmlParser()
        cont.saveResearchLine(records.parse(xml));
        cont.response.reset()
    }

    static public void createResearchLine(int position){
        def cont = new ResearchLineController()
        cont.params << researchlines[position]
        cont.request.setContent(new byte[1000]) // Could also vary the request content.
        cont.create()
        cont.save()
        cont.response.reset()
    }

    static public def findResearchLineByName(String name) {
        researchlines.find { orientation ->
            orientation.name == name
        }
    }

    static public boolean researchLineCompatibleTo(ResearchLine line, String name){
        def testResearchline = findResearchLineByName(name)
        def compatible = false
        if (testResearchline == null && name == null) {
            compatible = true
        } else if (testResearchline != null && name != null) {
            compatible = true
            testResearchline.each { key, data ->
                compatible = compatible && (line."$key" == data)
            }
        }
        return compatible
    }
}
