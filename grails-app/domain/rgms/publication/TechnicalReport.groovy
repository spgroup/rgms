package rgms.publication

class TechnicalReport extends Publication {

	String institution
	
    static constraints = {
		institution nullable: false, blank: false
    }

    //#if($Bibtex)
    String generateBib() {
        return "@techreport{"
        + "author=\"" + BibtexAux.organizeAuthors(members) + "\",\n title=\"" + title + "\",\n institution=\""
        + this.institution + "\",\n year=\"" + publicationDate.getAt(Calendar.YEAR) + "\"}"
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