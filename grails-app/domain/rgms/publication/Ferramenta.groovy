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
        return "@misc{" + super.members.get(0) + super.publicationDate.getAt(Calendar.YEAR) + ",author=\"" + BibtexAux.organizeAuthors(super.members) + "\",\n title=\"" + super.title + "\",\n website=\"" + this.website + "\",\n year=\"" + super.publicationDate.getAt(Calendar.YEAR) + "\",\n description=\"" + this.description + "\",\n month=\"" + super.publicationDate.getAt(Calendar.MONTH) + "\"}"
    }
//#end

    @Override
    String toString(){
        return website
    }

    @Override
    boolean equals(Object obj){
        Ferramenta f = (Ferramenta) obj
        return website.equals(f.website)
    }

    @Override
    int hashCode(){
        if(website == null){
            return "".hashCode()
        }
        else{
            return website.hashCode()
        }
    }
}
