package rgms

import org.springframework.web.multipart.MultipartHttpServletRequest
import org.springframework.web.multipart.commons.CommonsMultipartFile

class XMLService {
    //Métodos públicos
    static boolean Import(Closure saveClosure, Closure returnWithMessage, Node xmlFile, String flashMessage)
    {
        boolean errorFound = false

        try
        {
            saveClosure(xmlFile)
        }
        catch (SAXParseException) { //Se o arquivo nÃ£o for XML ou nÃ£o passaram nenhum
            flashMessage = 'default.xml.parserror.message'
            errorFound = true
        }
        catch (NullPointerException) //Se a estrutura do XML estÃ¡ errada (cast em NÃ³ nulo)
        {
            flashMessage = 'default.xml.structure.message'
            errorFound = true
        }
        catch (Exception)
        {
            flashMessage = 'default.xml.unknownerror.message'
            errorFound = true
        }

        returnWithMessage(flashMessage)
        return !errorFound
    }

    static Node parseReceivedFile(MultipartHttpServletRequest request)
    {
        MultipartHttpServletRequest mpr = (MultipartHttpServletRequest) request;
        CommonsMultipartFile f = (CommonsMultipartFile) mpr.getFile("file");
        File file = new File("xmlimported.xml");
        f.transferTo(file)
        def records = new XmlParser()
        if (file.length()>0){
            records.parse(file)
        }
    }

    static String getAttributeValueFromNode(Node n, String attribute)
    {
        n.attribute attribute
    }
    //Métodos públicos
}
