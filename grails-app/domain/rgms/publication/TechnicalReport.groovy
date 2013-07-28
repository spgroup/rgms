package rgms.publication

class TechnicalReport extends Publication {

	String institution
	
    static constraints = {
		institution nullable: false, blank: false
    }

    //#if($Bibtex)
    String generateBib() {
        return "@techreport{"
        + "author=\"" + BibtexAux.organizeAuthors(super.members) + "\",\n title=\"" + super.title + "\",\n institution=\""
        + this.institution + "\",\n year=\"" + super.publicationDate.getAt(Calendar.YEAR) + "\"}"
    }
    //#end
	
    @Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}
}