package steps

/**
 * Created by Bruno Soares on 24/02/14.
 */

import rgms.researchProject.Funder
import rgms.researchProject.FunderController

class FunderTestDataAndOperations {

    static funder = [
            [name: "Conselho Nacional de Desenvolvimento Cientifico e Tecnologico", code: "002200000000", nature: "BOLSA"]
    ]

    static public void createFunderWithCode(String codigo){
        def cont = new FunderController()
        createFunderAux (cont, codigo, "Test Funder", "BOLSA")
    }

    static public void createFunder(){
        def cont = new FunderController()
        createFunderAux(cont,funder[0].code,funder[0].name,funder[0].nature)
    }

    static public void removeFunder(String codigo) {
        def funder = funder.findByCode(codigo)
    }

    private static void createFunderAux(FunderController cont, String code,String name, String nature){
        cont.params << [code: code, name: name, nature: nature]
        cont.request.setContent(new byte[1000]) // Could also vary the request content.
        cont.create()
        cont.save()
        cont.response.reset()
    }
}
