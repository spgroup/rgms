package rgms.authentication

import rgms.member.Member

/**
 * Created with IntelliJ IDEA.
 * User: Marcos
 * Date: 02/09/13
 * Time: 08:34
 * To change this template use File | Settings | File Templates.
 */
class User {

    String username;
    String passwordHash;
    Member author;
    boolean enabled;
    boolean passwordChangeRequiredOnNextLogon

    static hasMany = [roles: Role, permissions: String, ]

    static constraints = {
        username(unique:true,nullable: false, blank: false,size: 5..20)
        enabled(blank: false)
        author(unique: true, blank:false, nullable: false)
    }

    static mapping = {
        cache roles : true
        cache permissions : true

    }
}
