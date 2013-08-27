package rgms.publication


class Dissertacao extends TeseOrDissertacao {


    //#if($Bibtex)
    String generateBib() {
        return new BibtexExport().generateBibtexDissertacao(this)
    }
    //#end
}