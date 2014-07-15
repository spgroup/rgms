package steps

import rgms.authentication.User
import rgms.member.*
import rgms.publication.PublicationController
import rgms.publication.XMLController

class TestDataAndOperationsPublication {

    static public boolean containsUser(members){
        def userData = User.findByUsername('admin')?.author?.id.toString()
        return members.contains(userData)
    }

    static public void uploadPublication(filepath) {
        def cont = new XMLController()
        def xml = new File((String) filepath);
        def records = new XmlParser()
        cont.upload(records.parse(xml))
        cont.response.reset()
    }


}
