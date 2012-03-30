class ShiroUser {
    String firstName
    String lastName
    String username
    String passwordHash
	String email
	String additionalInfo
	Date dateCreated
	Date lastUpdated
	def beforeInsert = {
	   dateCreated = new Date()
	}
	def beforeUpdate = {
	   lastUpdated = new Date()
	}
   boolean passwordChangeRequiredOnNextLogon
    static hasMany = [ roles: ShiroRole, permissions: String ]

    static constraints = {
        firstName(nullable: false, blank: false)
        lastName(nullable: false, blank: false)
        username(unique:true,nullable: false, blank: false,size: 5..20)
        email(unique:true,email: true)
        additionalInfo(nullable:true)
    }

    static mapping = {
		cache true
		cache roles : true
		cache permissions : true
	}

	String toString(){username}
}
