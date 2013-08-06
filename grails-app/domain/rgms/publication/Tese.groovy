package rgms.publication


class Tese extends TeseOrDissertacao {

//#if($Bibtex)
    String generateBib() {
        return "@phdthesis{" + "author=\"" + BibtexAux.organizeAuthors(members) + "\",\n title=\"" + title + "\",\n school=\"" +
                this.school + "\",\n year=\"" + publicationDate.getAt(Calendar.YEAR) + "\",\n address=\"" +
                this.address + "\"}"
    }
//#end
}
