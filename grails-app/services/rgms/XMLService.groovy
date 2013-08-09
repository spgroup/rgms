package rgms


import org.springframework.web.multipart.MultipartHttpServletRequest
import org.springframework.web.multipart.commons.CommonsMultipartFile
import javax.*

class XMLService {

    /*
        saveEntity - closure que salva a classe de domínio que está usando a importação
     */
    static boolean Import(Closure saveEntity, Closure returnWithMessage, String flashMessage,
        javax.servlet.http.HttpServletRequest request)
    {
        boolean errorFound = false

        try {
            Node xmlFile = parseReceivedFile(request)
            saveEntity(xmlFile)
        }
        //If file is not XML or if no file was uploaded
        catch (SAXParseException) {
            flashMessage = 'default.xml.parserror.message'
            errorFound = true
        }
        //If XML structure is not according to Lattes, it'll perform an invalid cast
        catch (NullPointerException) {

            flashMessage = 'default.xml.structure.message'
            errorFound = true
        }
        catch (Exception) {
            flashMessage = 'default.xml.unknownerror.message'
            errorFound = true
        }

        returnWithMessage(flashMessage)
        return !errorFound
    }

    private static Node parseReceivedFile(MultipartHttpServletRequest request) {
        MultipartHttpServletRequest mpr = (MultipartHttpServletRequest) request;
        CommonsMultipartFile f = (CommonsMultipartFile) mpr.getFile("file");
        File file = new File("xmlimported.xml");
        f.transferTo(file)
        def records = new XmlParser()
        if (file.length()>0){
            records.parse(file)
        }
    }

    static String getAttributeValueFromNode(Node n, String attribute) {
        n.attribute attribute
    }
}
