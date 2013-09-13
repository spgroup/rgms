package rgms.authentication

import java.security.SecureRandom

class PasswordResetRequest {

    User user
    String token
    Date requestDate
        
    static beforeInsert = {
        requestDate = new Date()
        token = new BigInteger(130, new SecureRandom()).toString(32)
    }

    static constraints = {
    }
}
