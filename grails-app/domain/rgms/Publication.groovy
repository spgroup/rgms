package rgms


class Publication {
	
    Date publicationDate
    String title
    ResearchLine researchLine

    static belongsTo = Member
    static hasMany = [members : Member]
    
    static constraints = {
        title()
        publicationDate()
        researchLine(nullable:true)
    }
    
    public String toString()
    {        
        return title
    }
}
