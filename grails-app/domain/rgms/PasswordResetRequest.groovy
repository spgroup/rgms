package rgms

import java.security.SecureRandom

class PasswordResetRequest {

    Member user
    String token
    Date requestDate
        
    static beforeInsert = {
        requestDate = new Date()
        token = new BigInteger(130, new SecureRandom()).toString(32)
    }

    static constraints = {
    }
}
