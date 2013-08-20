package rgms.publication


class Dissertacao extends TeseOrDissertacao {

//#if($Bibtex)
    String generateBib() {
        return "@masterthesis{" + "author=\"" + BibtexAux.organizeAuthors(members) + "\",\n title=\"" + title + "\",\n school=\""
        +this.school + "\",\n year=\"" + publicationDate.getAt(Calendar.YEAR) + "\",\n address=\""
        +this.address + "\",\n month=\"" + publicationDate.getAt(Calendar.MONTH) + "\"}"
    }
//#end
}