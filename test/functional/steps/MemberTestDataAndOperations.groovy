package steps

import rgms.authentication.User
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

    static public void createMember(String new_username) {
        def cont = new MemberController()
        cont.params << findByUsername(new_username)
        save_controller(cont)
    }

    static public void createMemberWithPhone(String new_username, String new_phone) {
        def cont = new MemberController()
        if (if_empty(new_phone)) {
            cont.params << findByUsername(new_username)
        } else {
            cont.params << [username: new_username, phone: new_phone]
        }
        save_controller(cont)
    }

    private static boolean if_empty(String text) {
        text.equals("")
    }

    static public void createMemberWithEmail(String new_username, String new_email) {
        def cont = new MemberController()
        if (if_empty(new_email)) {
            cont.params << findByUsername(new_username)
        } else {
            cont.params << [username: new_username, email: new_email]
        }
        save_controller(cont)
    }

    private static void save_controller(MemberController cont) {
        cont.create()
        cont.save()
        cont.response.reset()
    }

    static public void deleteMember(String username) {
        def cont = new MemberController()
        def identificador = User.findByUsername(username)?.author?.id
        cont.params << [id: identificador]
        cont.request.setContent(new byte[1000]) // Could also vary the request content.
        save_controller(cont)
    }

    static public boolean containsMember(String username) {
        def cont = new MemberController()
        def result = cont.list().userMemberInstanceList
        for (userMember in result) {
            if (userMember.user.username == username && userMember.member != null)
                return true;
        }
        return false;
    }

}
