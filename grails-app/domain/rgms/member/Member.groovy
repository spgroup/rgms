package rgms.member

import rgms.authentication.Role;
import rgms.publication.Publication;
import rgms.publication.ResearchLine;

class Member {
    
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
    Boolean active
    Boolean enabled
    String access_token
    String facebook_id
    
   //static hasMany = [roles: Role, permissions: String, #if($History) historics: Record,#end memberships : Membership, publications: Publication]
   static hasMany = [roles: Role, permissions: String, historics: Record, memberships : Membership, publications: Publication, researchLines: ResearchLine]
   static belongsTo = ResearchLine
    
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
        additionalInfo(nullable:true, size: 0..255)
        status(nullable: false, inList: ["Graduate Student", "MSc Student", "PhD Student", "Professor", "Researcher"])
    	university(blank:false)
    	phone(nullable: true,matches: "[0-9 ]+" )
    	website(nullable:true, url:true)
    	city(nullable: true, matches: "[a-z A-Z]+")
    	country(nullable: true, matches: "[a-z A-Z]+")
    	active(nullable: true)
        enabled(blank: false)
        access_token(nullable: true)
        facebook_id(nullable: true)
    }

    static mapping = {
        cache true
        cache roles : true
        cache permissions : true
        
        //#if($History)
        historics fetch: 'join'
        //#end
        
        //        historics joinTable: [name: 'USER_HIST',column: 'HIST_ID',key: 'id']
    }

    String toString(){return this.name}
    
  
}
