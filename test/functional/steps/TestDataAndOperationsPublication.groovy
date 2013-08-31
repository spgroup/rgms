package steps

import rgms.member.*

class TestDataAndOperationsPublication {

    static public boolean containsUser(members){
        def userData = Member.findByUsername('admin').id.toString()
        return members.contains(userData)
    }

}
