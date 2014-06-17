package steps

import rgms.authentication.User

class TestDataAndOperationsPublication {

    static public boolean containsUser(members){
        def userData = User.findByUsername('admin')?.author?.id.toString()
        return members.contains(userData)
    }

}
