package steps

import rgms.authentication.User
import rgms.member.Member
import rgms.member.MemberController

/**
 * Created with IntelliJ IDEA.
 * User: jucemberg
 * Date: 01/09/13
 * Time: 13:48
 * To change this template use File | Settings | File Templates.
 */

class MemberTestDataAndOperations {

    //TODO member não tem username!
    static members = [
            [name: "Rodolfo", username: "usernametest", email: "rodolfofake@gmail.com",
                    status: "Graduate Student", university: "UFPE", enabled: true
            ],
            [name: "Rebeca Souza", username: "rebecasouza", email: "rsa2fake@cin.ufpe.br",
                    status: "Graduate Student", university: "UFPE", enabled: true
            ],
            [name: "Rubens Lopes", username: "rlfs", email: "rlfsfake@cin.ufpe.br",
                    status: "Graduate Student", university: "UFPE", enabled: true
            ]]

    static public def findByUsername(String username) {
        members.find { member ->
            member.username == username
        }
    }

    //TODO evitar duplicação, depois de resolver toda a confusão conceitual entre user vs member,
    // inclusive na feature Member talvez não seja necessário ter mais o método acima
    static public def findByName(String name) {
        members.find { member ->
            member.name == name
        }
    }
	
	static public void createDefaultMember(String name = "name",String username = "username", String email = "email@gmail.com", String phone = "3333-3333", String university = "UFPE", String website = null, String country = "Brasil", String status = null) {
		def cont = new MemberController()
        cont.params << [name: name] << [username: username] << [email: email] << [university: university] << [phone: phone] << [website: website] << [country: country] << [status: status]
        cont.create()
        cont.save()
        cont.response.reset()
	}

    static public void createMember(String name = null,String username = null, String email = null, String phone = null, String university = null,
                                    String website = null, String country = null, String status = null) {
        def cont = new MemberController()
        cont.params << [name: name] << [username: username] << [email: email] << [university: university] << [phone: phone] << [website: website] << [country: country] << [status: status]
        cont.create()
        cont.save()
        cont.response.reset()
    }

    //TODO evitar duplicação, depois de resolver toda a confusão conceitual entre user vs member
    static public void createMemberWithEmail(String name, String email) {
        def cont = new MemberController()
        cont.params << findByName(name) << [email: email]
        cont.create()
        cont.save()
        cont.response.reset()
    }

    static public void deleteMember(String username) {
        def cont = new MemberController()
        def identificador = User.findByUsername(username)?.author?.id
        cont.params << [id: identificador]
        cont.request.setContent(new byte[1000]) // Could also vary the request content.
        cont.delete()
        cont.response.reset()
    }

    static public boolean containsMember(username) {
        def cont = new MemberController()
        def result = cont.list().userMemberInstanceList
        for (i in result) {
            if (i.user.username == username && i.member != null)
                return true;
        }
        return false;
    }

    static public Member editMember(String emailKey, String option, String value){
        def cont = new MemberController()
        def member = Member.findByEmail(emailKey)
        if(option == "name")
            member.name = value
        else if(option == "email")
            member.email = value
        else if(option == "phone")
            member.phone = value
        else if(option == "university")
            member.university = value
        else if(option == "website")
            member.website = value
        else if(option == "country")
            member.country = value
        else if(option == "additionalInfo")
            member.additionalInfo = value
        else if(option == "city")
            member.city = value
        else if(option == "status")
            member.status = value
        else if(option == "active")
            member.active = (boolean)value
        cont.params << member.properties
        cont.update()

        return member
    }
}
