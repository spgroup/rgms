package rgms

class Member {
    String firstName
    String lastName
    String username
    String passwordHash
    String email
    String additionalInfo
    String status
    Boolean enabled
    
    Date dateCreated
    Date lastUpdated
    def beforeInsert = {
        dateCreated = new Date()
    }
    def beforeUpdate = {
        lastUpdated = new Date()
    }
    boolean passwordChangeRequiredOnNextLogon
    static hasMany = [ roles: Role, permissions: String, historics: Record]
    
    static constraints = {
        firstName(nullable: false, blank: false)
        lastName(nullable: false, blank: false)
        username(unique:true,nullable: false, blank: false,size: 5..20)
        email(unique:true,email: true)
        additionalInfo(nullable:true)
        status(nullable: false, inList: ["Graduate Student", "MSc Student", "PhD Student", "Professor", "Researcher"])
        enabled(blank: false)
    }

    static mapping = {
        cache true
        cache roles : true
        cache permissions : true
        
        historics fetch: 'join'
        
        //        historics joinTable: [name: 'USER_HIST',column: 'HIST_ID',key: 'id']
    }

    String toString(){username}
}
