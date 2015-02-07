package steps

import rgms.publication.Ferramenta
import rgms.publication.FerramentaController
//#if($XMLUpload)
import rgms.publication.XMLController
//#end

class FerramentaTestDataAndOperations {
    static ferramentas = [
            [description: "Ferramenta Target",
                    title: "Target",
                    publicationDate: (new Date("12 October 2012"))],
            [website: "http://www.teste.com", description: "Ferramenta Emergo",
                    title: "Emergo",
                    publicationDate: (new Date("12 October 2012"))],
            [website: "http://www.ccfinder.com", description: "Ferramenta CCfinder",
                    title: "CCFinder",
                    publicationDate: (new Date("12 October 2012"))],
            [website: "http://www.tool.com", description: "Ferramenta Tool",
                    title: "Tool",
                    publicationDate: (new Date("12 October 2012"))],
            [website: "http://www.new.com", description: "Ferramenta New",
                    title: "New",
                    publicationDate: (new Date("12 October 2012"))],
            [website: "http://www.tooldelete.com", description: "Ferramenta ToolDelete",
                    title: "ToolDelete",
                    publicationDate: (new Date("12 October 2012"))]
    ]

    static public def findFerramentaByTitle(String title) {
        ferramentas.find { ferramenta ->
            ferramenta.title == title
        }
    }

    static public Ferramenta editFerramenta(oldtitle, newtitle) {
        def ferramenta = Ferramenta.findByTitle(oldtitle)
        ferramenta.setTitle(newtitle)
        def cont = new FerramentaController()
        cont.params << ferramenta.properties
        cont.update()
        def updatedferramenta = Ferramenta.findByTitle(newtitle)
        return updatedferramenta
    }

    static public void removeFerramenta(String title){
        def cont = new FerramentaController()
        cont.params << [id: Ferramenta.findByTitle(title).id]
        cont.delete()
        cont.response.reset()
    }

    static public void createFerramenta(String new_title, new_filename) {
        def cont = new FerramentaController()
        def date = new Date()
        cont = find_and_set_content(cont, new_title, new_filename)
        save_controller(cont)
    }

    private static void save_controller(FerramentaController cont) {
        cont.create()
        cont.save()
        cont.response.reset()
    }

    static public void createFerramentaWithWebsite(String new_title, new_filename, new_website) {
        def cont = new FerramentaController()
        def date = new Date()
        cont = find_and_set_content(cont, new_title, new_filename)
        cont.params << [website: new_website]
        save_controller(cont)
    }

    private static FerramentaController find_and_set_content(FerramentaController cont, String title, filename) {
        cont.params << findFerramentaByTitle(title) << [file: filename]
        cont.request.setContent(new byte[1000]) // Could also vary the request content.
        return cont;
    }


    static public void uploadFerramenta(filepath) {
        def cont = new XMLController()
        def xml = new File((String) filepath);
        def records = new XmlParser()
        cont.saveTools(records.parse(xml));
        save_controller(cont);
    }

    static public Ferramenta getFerramenta(title){
        return Ferramenta.findByTitle(title)
    }
}
