package rgms


class Publication {
	
    Date publicationDate
    String title

    static belongsTo = Member
    static hasMany = [members : Member]
    public String toString()
    {
        
        return title
    }
    static constraints = {
    }
}
