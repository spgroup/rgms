package rgms.publication

class TechnicalReport extends Publication {

	String institution
	
    static constraints = {
		institution nullable: false, blank: false
    }

    //#if($Bibtex)
    String generateBib() {
        return new BibtexExport().generateBibtexTechnicalReport(this)
    }
    //#end

    @Override
	public String toString() {

		return "Institution: " + institution + super.toString();
	}
	
	@Override
	public boolean equals(TechnicalReport other) {

		return institution == other.institution && super.equals(other);
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}
}