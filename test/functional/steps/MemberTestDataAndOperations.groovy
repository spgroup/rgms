package steps

import org.codehaus.groovy.grails.plugins.web.taglib.ValidationTagLib
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

    static public void createMember(String username, String phone, String university) {
        def cont = new MemberController()
        if (phone.equals("") && university.equals("")) {
            cont.params << findByUsername(username)
        } else if(university.equals("")){
            cont.params << [name: "Rodolfo", username: username, email: "rodolfofake@gmail.com",
                             status: "Graduate Student", university: "UFPE", enabled: true, phone: phone
                            ]
        }
        else
        {
            cont.params << [name: "Rodolfo", username: username, email: "rodolfofake@gmail.com",
                            status: "Graduate Student", university: university, enabled: true, phone: "123456"
            ]
        }
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


    static public void createMemberWithoutPhone(String name, String username, String email, String university, String status, String country, String website) {
        def cont = new MemberController()
        setMemberParams(cont, name, username,email, university, status, country, "", website)
        //cont.params << [name: name, username: username, email: email, status: status, university: university, enabled: true, website: website, country:country]
        createAndSaveCont(cont);
    }

    static public void createMemberWithoutWebsite(String name, String username, String email, String university, String status, String country, String phone) {
        def cont = new MemberController()
        setMemberParams(cont, name, username,email, university, status, country, phone, "")
        //cont.params << [name: name, username: username, email: email, status: status, university: university, enabled: true, phone:phone, country:country]
        createAndSaveCont(cont);
    }

    static private void createAndSaveCont(cont)
    {
        cont.create()
        cont.save()
        cont.response.reset()
    }

    static private setMemberParams(cont, String name, String username, String email, String university, String status, String country, String phone, String website)
    {
        if(!isNullOrEmpty(name))
        {
            cont.params << [name: name]
        }
        if(!isNullOrEmpty(username))
        {
            cont.params << [username: username]
        }
        if(!isNullOrEmpty(email))
        {
            cont.params << [email: email]
        }
        if(!isNullOrEmpty(university))
        {
            cont.params << [university: university]
        }
        if(!isNullOrEmpty(status))
        {
            cont.params << [status: status]
        }
        if(!isNullOrEmpty(country))
        {
            cont.params << [nacountryme: country]
        }
        if(!isNullOrEmpty(phone))
        {
            cont.params << [phone: phone]
        }
        if(!isNullOrEmpty(website))
        {
            cont.params << [website: website]
        }

        cont.params << [enabled: true]

    }

    static private boolean isNullOrEmpty(param)
    {
        return (param==null || param=="")

    }







}
