package rgms.member

import rgms.publication.Publication
import rgms.publication.ResearchLine

class Member {
    
    String name
    String email
    String additionalInfo
    String status
    String university
    String phone
    String website
    String city
    String country
    Boolean active
    String access_token
    String facebook_id
    
   //static hasMany = [roles: Role, permissions: String, #if($History) historics: Record,#end memberships : Membership, publications: Publication]
   static hasMany = [historics: Record, memberships : Membership, publications: Publication, researchLines: ResearchLine]
   static belongsTo = ResearchLine
    
    Date dateCreated
    Date lastUpdated
    def beforeInsert = {
        dateCreated = new Date()
    }
    def beforeUpdate = {
        lastUpdated = new Date()
    }
    
    static constraints = {
        name(nullable: false, blank: false)
        email(unique:true,email: true, nullable: false)
        additionalInfo(nullable:true)
        status(nullable: false, inList: ["Graduate Student", "MSc Student", "PhD Student", "Professor", "Researcher"])
    	university(blank:false)
    	phone(nullable: true)
    	website(nullable:true)
    	city(nullable: true)
    	country(nullable: true)
    	active(nullable: true)
        access_token(nullable: true)
        facebook_id(nullable: true)
    }

    static mapping = {
        cache true
        cache permissions : true
        
        //#if($History)
        historics fetch: 'join'
        //#end
        
        //        historics joinTable: [name: 'USER_HIST',column: 'HIST_ID',key: 'id']
    }
    boolean equals(Member other) {
        def compatible = (other != null)
        other?.properties.each {key, value  ->
            compatible = compatible && (this."$key".equals(value))
        }
        return compatible

    }

    String toString(){return this.name}
    
  
}
