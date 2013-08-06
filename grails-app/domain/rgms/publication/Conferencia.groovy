package rgms.publication


class Conferencia extends Publication {

    String booktitle
    String pages

    static constraints = {
        booktitle nullable: false, blank: false
        pages nullable: false, blank: false
    }

//#if($Bibtex)
    String generateBib() {
        return "@inproceedings{" + members.toList()[0] + publicationDate.getAt(Calendar.YEAR)
        +",author=\"" + BibtexAux.organizeAuthors(members) + "\",\n title=\"" + title + "\",\n booktitle=\""
        +this.booktitle + "\",\n year=\"" + publicationDate.getAt(Calendar.YEAR) + "\",\n pages=\""
        +this.pages + "\",\n month=\"" + publicationDate.getAt(Calendar.MONTH) + "\"}"
    }
//#end

}