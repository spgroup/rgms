package rgms.publication

class Periodico extends Publication {

    String journal
    int volume
    int number
    String pages

    static constraints = {
        journal nullable: false, blank: false
        volume nullable: false, blank: false, min: 1
        number nullable: false, blank: false, min: 1
        pages nullable: false, blank: false
    }

    //#if($Bibtex)
    String generateBib() {
        return new BibtexExport().generateBibtexPeriodico(this)
    }
    //#end

    @Override
    public String toString() {

        return "Journal: " +   journal + " | Volume" + volume + " | Number" + number
    }

    @Override
    public boolean equals(Periodico other) {

        return volume == other.volume && number == other.number && journal == other.journal && pages == other.pages
    }

    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return super.hashCode();
    }
}
