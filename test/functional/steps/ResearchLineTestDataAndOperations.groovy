package steps

import rgms.publication.ResearchLineController
import rgms.publication.XMLController

/**
 * Created by Bruno Soares on 23/02/14.
 */
class ResearchLineTestDataAndOperations {

    static researchlines = [
         [name: "Estudos empiricos em Criptografia Quantica", description: "Esta area de estudo busca compreender como a evolução da computação quantica pode contribuir ou prejudicar o nível de segurança dos atuais sistemas criptograficos."]
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
}
