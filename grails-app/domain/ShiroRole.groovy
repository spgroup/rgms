class ShiroRole {
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

    static hasMany = [ users: ShiroUser, permissions: String ]
    static belongsTo = ShiroUser

    static constraints = {
        name(nullable: false, blank: false, unique: true)
        description(nullable: true)
    }

    static mapping = {
		cache true
		cache users : true
		cache permissions : true
	}

	String toString(){name}
}
