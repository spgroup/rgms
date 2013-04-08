package rgms

import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap
import org.springframework.web.multipart.MultipartHttpServletRequest
import org.springframework.web.multipart.commons.CommonsMultipartFile
import org.xml.sax.SAXParseException
import rgms.member.Member
import rgms.member.Orientation
import rgms.publication.Periodico
import rgms.publication.Publication

class XMLService {
    //Métodos públicos
    static boolean Import(Closure saveClosure, Closure returnWithMessage, Node xmlFile, String flashMessage)
    {
        boolean errorFound = false

        try
        {
            saveClosure(xmlFile)
        }
        catch (SAXParseException exception) { //Se o arquivo nÃ£o for XML ou nÃ£o passaram nenhum
            flashMessage = 'default.xml.parserror.message'
            errorFound = true
        }
        catch (NullPointerException e) //Se a estrutura do XML estÃ¡ errada (cast em NÃ³ nulo)
        {
            flashMessage = 'default.xml.structure.message'
            errorFound = true
        }
        catch (Exception e)
        {
            flashMessage = 'default.xml.unknownerror.message'
            errorFound = true
        }

        returnWithMessage(flashMessage)
        if (errorFound) return false
        else return true
    }

    static Node parseReceivedFile(MultipartHttpServletRequest request)
    {
        MultipartHttpServletRequest mpr = (MultipartHttpServletRequest) request;
        CommonsMultipartFile f = (CommonsMultipartFile) mpr.getFile("file");
        File file = new File("xmlimported.xml");
        f.transferTo(file)
        def records = new XmlParser()

        records.parse(file)
    }

    static String getAttributeValueFromNode(Node n, String attribute)
    {
        n.attribute attribute
    }
    //Métodos públicos
}
