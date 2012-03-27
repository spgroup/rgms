package rgms

class Member {
	String name
	String email
	String university
	String affiliation
	String phone
	String website
	String city
	String country
	Boolean active
    static hasMany = [memberships : Membership, publications: Publication]
	
    public String toString()
    {
        
        return this.name
    }
    static constraints = {
    	name(blank:false)
    	email(email:true,blank:false)
    	affiliation(blank:false,inList: ["Estudante", "Pesquisador", "Outro"])
    	university(blank:false)
    	phone()
    	website(url:true)
    	city()
    	country()
    	active()
    }
}
