package rgms.publication

class ResearchLine {
    
    String name
    String description      
	     
    static hasMany = [publications : Publication]
    
	static constraints = {
        name(blank:false, unique:true)
        description(maxSize:256,blank:false)        
    }
	 
    String toString() {
        return name;
    }
}