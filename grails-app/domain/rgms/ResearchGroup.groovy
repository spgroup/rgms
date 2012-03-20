package rgms

class ResearchGroup {
    String name
    String description
    static hasMany = [members : Member]
    
    public String toString()
    {
        return this.name
    }
    static constraints = {
        name(maxSize:50,blank:false)
        description(maxSize:1000,blank:false)
        
    }
}
