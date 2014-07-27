package steps

import rgms.authentication.User
import rgms.member.MemberController
import rgms.member.Member
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
            [name: "Rodolfo",  email: "rodolfofake@gmail.com",
                    status: "Graduate Student", university: "UFPE", enabled: true
            ],
            [name: "Rebeca Souza",  email: "rsa2fake@cin.ufpe.br",
                    status: "Graduate Student", university: "UFPE", enabled: true
            ],
            [name: "Rubens Lopes", username: "rlfs", email: "rlfsfake@cin.ufpe.br",
                    status: "Graduate Student", university: "UFPE", enabled: true
            ]]

//#if ($memberListAndPageImprovement)

    static newMembers = [
            [name: "Rodolfo",  email: "rodolfofake@gmail.com",
             status: "Graduate Student", university: "UFPE", enabled: false
            ],
            [name: "Rebeca Souza",  email: "rsa2fake@cin.ufpe.br",
             status: "Graduate Student", university: "UFPE", enabled: false
            ],
            [name: "Rubens Lopes",  email: "rlfsfake@cin.ufpe.br",
             status: "Graduate Student", university: "UFPE", enabled: false
            ],
            [name: "Alvaro Joao",  email: "ajsss@cin.ufpe.br",
             status: "Graduate Student", university: "UFPE", enabled: false
            ]
            ]

    static public def sendEmailToMember(String username) {
        Member.sendEmail()
    }

    static public def orderMembers(int number,String attribute) {
        int countMember = newMembers.sort{member ->
            member.name.hashCode()
        }.size()
        assert countMember,number
    }

    static public def orderNewMembersFirst(int number) {
      int countNewMember = newMembers.find{member ->
            member.enabled==false
        }.size()

        assert countNewMember,number
    }



    //#end

    static public def findByEmail(String email) {
        members.find { member ->
            member.email == email
        }
    }

    //TODO evitar duplicação, depois de resolver toda a confusão conceitual entre user vs member,
    // inclusive na feature Member talvez não seja necessário ter mais o método acima
    static public def findByName(String name) {
        members.find { member ->
            member.name == name
        }
    }

    static public void createMember(String email, String phone) {
        def cont = new MemberController()
        if (phone.equals("")) {
            cont.params << findByEmail(email)
        } else {
            cont.params << [username: email, phone: phone]
        }
        cont.create()
        cont.save()
        cont.response.reset()
    }

    //TODO evitar duplicação, depois de resolver toda a confusão conceitual entre user vs member
    /*
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
    */
}
