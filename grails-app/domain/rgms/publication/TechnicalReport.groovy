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
        return "Institution: " + institution + " | Publication: " + super.toString();
	}
	
	@Override
	public boolean equals(Object other) {

        if(!other || other.getClass() != this.getClass())return false

        //Para a interação assim que um valor diferente é encontrado
        for(elem in other?.properties){
            def thisValue = this."$elem.key"
            def otherValue = elem.value

            if (!thisValue.equals(otherValue)) return false
        }

        return true
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}
}