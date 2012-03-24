package rgms

class ResearchGroup {
    String name
    String description
	String macaco
    static hasMany = [members : Membership]
    
    public String toString()
    {
        return this.name
    }
    static constraints = {
        name(maxSize:50,blank:false)
        description(maxSize:1000,blank:false)
        
    }
}
