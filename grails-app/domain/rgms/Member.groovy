package rgms

class Member {
//    String firstName
//    String lastName
    String name
    String username
    String passwordHash
    String email
    String additionalInfo
    String status
    String university
    String phone
    String website
    String city
    String country
    Boolean enabled
    Boolean active
    static hasMany = [memberships : Membership, publications: Publication,roles: Role, permissions: String, historics: Record]
    
    Date dateCreated
    Date lastUpdated
    def beforeInsert = {
        dateCreated = new Date()
    }
    def beforeUpdate = {
        lastUpdated = new Date()
    }
    boolean passwordChangeRequiredOnNextLogon
    
    
    static constraints = {
        name(nullable: false, blank: false)
        username(unique:true,nullable: false, blank: false,size: 5..20)
        email(unique:true,email: true, nullable: false)
        additionalInfo(nullable:true)
        status(nullable: false, inList: ["Graduate Student", "MSc Student", "PhD Student", "Professor", "Researcher"])
    	university(blank:false)
    	phone(nullable: true)
    	website(nullable:true, url:true)
    	city(nullable: true)
    	country(nullable: true)
    	active(nullable: true)
        enabled(blank: false)
    }

    static mapping = {
        cache true
        cache roles : true
        cache permissions : true
        
        historics fetch: 'join'
        
        //        historics joinTable: [name: 'USER_HIST',column: 'HIST_ID',key: 'id']
    }

    String toString(){return this.name}
}