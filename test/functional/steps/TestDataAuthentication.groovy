package steps

import org.apache.shiro.crypto.hash.Sha256Hash
import rgms.authentication.User
import rgms.member.Member
import twitter4j.auth.BasicAuthorization

class TestDataAuthentication{
    static users = [
        /*new Member(status: "Graduate Student", email: "testando@testando.com", username: "user186", active: true,
                city: "Recife", country: "Brasil", enabled: true, name: "Dom Pedro I",
                university: "Instituto Sem Lugar", phone: "3333-5555", passwordHash: new Sha256Hash("12345"))*/
        [status: "Graduate Student", email: "testando@testando.com", username: "user186", active: true,
                city: "Recife", country: "Brasil", enabled: true, name: "Dom Pedro I",
                university: "Instituto Sem Lugar", phone: "3333-5555", password: "12345"]
    ]

    static public def findByUsername(String username){
        users.find { user ->
            user.username == username
        }
    }

    static public def generateUnregisteredUser(){
        User user = null;
        Member author = null;
        while( user == null ||
                !user.validate() ||
                User.findByUsername(user.username) != null){
            println "user: " + user
            println "validate: " + user?.validate()
            println "errors: " + user?.errors
            Random numero = new Random();
            String nome = "algum"+(numero.nextInt()%100);
            String username = "algum"+(numero.nextInt()%100);
            String password = "12345"+(numero.nextInt()%100);
            String email = "asd"+(numero.nextInt()%100)+"@hotmail.com";
            String university = "Federal University of Pernambuco";
            String status = "Graduate Student";
			//String facebook_id = "12312fdsfsd".toString();
			String access_token = "134gdsf";
            author = new Member(name: nome, email: email, university: university, status: status,  access_token: access_token, facebook_id: "teste")
            user = new User( username: username, passwordHash: new Sha256Hash(password).toHex(), enabled: false, author: author)
        }
        def userMapping = user.properties as LinkedHashMap
        userMapping << author.properties as LinkedHashMap
        userMapping << [password: "12345"]
        println userMapping
        users << userMapping;
        return userMapping
    }

}