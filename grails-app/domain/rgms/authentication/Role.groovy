package rgms.authentication

import rgms.member.Member;

class Role {
    String name
    String description
    Date dateCreated
    Date lastUpdated
    def beforeInsert = {
        dateCreated = new Date()
    }
    def beforeUpdate = {
        lastUpdated = new Date()
    }

    static hasMany = [ users: Member, permissions: String ]
    static belongsTo = Member

    static constraints = {
        name(nullable: false, blank: false, unique: true)
        description(nullable: true)
    }

    static mapping = {
        cache true
    }

    String toString(){name}
}
