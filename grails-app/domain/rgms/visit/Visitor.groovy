package rgms.visit

class Visitor {
	
	String name
	
	static hasMany = [visit:Visit]

	static constraints = {
		name(nullable:false,blank:false)
	}
	
	@Override
	public String toString() {
			return name;
	}
}
