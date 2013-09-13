package steps

import rgms.authentication.User
import rgms.member.*

class TestDataAndOperationsPublication {

    static public boolean containsUser(members){
        def userData = User.findByUsername('admin')?.author?.id.toString()
        return members.contains(userData)
    }

}
