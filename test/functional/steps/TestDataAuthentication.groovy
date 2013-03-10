package steps

import org.apache.shiro.crypto.hash.Sha256Hash
import rgms.member.Member

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
        Member user = null;
        while( user == null ||
                !user.validate() ||
                Member.findByUsername(user.username) != null){
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
            user = new Member(name: nome, username: username, passwordHash: new Sha256Hash(password).toHex(),
                        email: email, university: university, status: status, enabled: false)
        }
        def userMapping = user.properties as LinkedHashMap
        userMapping << [password: "12345"]
        users << userMapping;
        return userMapping
    }

}