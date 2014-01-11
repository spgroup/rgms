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
    static members = [
            [name: "Rodolfo Ferraz", username: "usernametest", email: "rodolfofake@gmail.com",
                    status: "Graduate Student", university: "UFPE", enabled: true
            ],
            [name: "Rebeca Souza", username: "rebecasouza", email: "rsa2fake@cin.ufpe.br",
                    status: "Graduate Student", university: "UFPE", enabled: true
            ]]

    static public def findByUsername(String username) {
        members.find { member ->
            member.username == username
        }
    }

    static public void createMember(String username, String phone) {
        def cont = new MemberController()
        if (phone.equals("")) {
            cont.params << findByUsername(username)
        }else{
            cont.params << [username: username, phone: phone]
        }
        cont.create()
        cont.save()
        cont.response.reset()
    }

    static public void editEmail(String username, String email){
        def cont = new MemberController()
        cont.params << [username: username, email: email]
        cont.create()
        cont.save()
        cont.response.reset()
    }

    static public void createMemberWithEmail(String username, String email){
        createMember(username,"")
        editEmail(username,email)
    }


    static public void deleteMember(String username) {
        def cont = new MemberController()
        def identificador = User.findByUsername(username)?.author?.id
        cont.params << [id: identificador]
        cont.request.setContent(new byte[1000]) // Could also vary the request content.
        cont.delete()
        //cont.save()
        cont.response.reset()
    }
    static public boolean containsMember(username) {
        def cont = new MemberController()
        def result = cont.list().userMemberInstanceList
        for(i in result){
            if(i.user.username == username && i.member != null)
                return true;
        }
        return false;
    }
}

