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

    //cscbb
    static public boolean exist(String usernameToCompare, String username, Member member) {
        return (usernameToCompare == username && member != null)
    }

    //cscbb
    static public boolean containsMember(username) {
        def cont = new MemberController()
        def result = cont.list().userMemberInstanceList
        for (i in result) {
            if(exist(i.user.username, username, i.member))
                   return true
            //if (i.user.username == username && i.member != null)
            //    return true;
        }
        return false;
    }

    //cscbb
    static public void changeValue(def member, String option, String value) {
        switch(option) {
            case 'name':
                member.name = value
                break
            case 'email':
                member.email = value
                break
            case 'phone':
                member.phone = value
                break
            case 'university':
                member.university = value
                break
            case 'website':
                member.website = value
                break
            case 'country':
                member.country = value
                break
            case 'additionalInfo':
                member.additionalInfo = value
                break
            case 'city':
                member.city = value
                break
            case 'status':
                member.status = value
                break
            case 'active':
                member.active = (boolean)value
                break
            default:
                break
        }
    }
//cscbb
    static public Member editMember(String emailKey, String option, String value){
        def cont = new MemberController()
        def member = Member.findByEmail(emailKey)
        changeValue(member, option, value)
        cont.params << member.properties
        cont.update()

        return member
    }
}
