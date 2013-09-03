package rgms.publication


class Tese extends TeseOrDissertacao {

    //#if($Bibtex)
    String generateBib() {
        return new BibtexExport().generateBibtexTese(this)
    }
    //#end
}
