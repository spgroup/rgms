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

        Date yearDate = publicationDate.getAt(Calendar.YEAR)

        return "@inproceedings{" + members.toList()[0] + yearDate
        +",author=\"" + BibtexAux.organizeAuthors(members) + "\",\n title=\"" + title + "\",\n booktitle=\""
        +this.booktitle + "\",\n year=\"" + yearDate + "\",\n pages=\""
        +this.pages + "\",\n month=\"" + publicationDate.getAt(Calendar.MONTH) + "\"}"
    }
//#end

}