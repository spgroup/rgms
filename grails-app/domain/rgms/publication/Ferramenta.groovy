package rgms.publication


class Ferramenta extends Publication {

    String website
    String description

    static constraints = {
        website nullable: false, blank: false
        description nullable: false, blank: false
    }

//#if($Bibtex)
    String generateBib() {
        return "@misc{" + "author=\"" + BibtexAux.organizeAuthors(this.members) + "\",\n title=\"" + this.title +
                "\",\n website=\"" + this.website + "\",\n year=\"" + this.publicationDate.getAt(Calendar.YEAR) +
                "\",\n description=\"" + this.description + "\",\n month=\"" +
                this.publicationDate.getAt(Calendar.MONTH) + "\"}"
    }
//#end
}
