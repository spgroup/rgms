package rgms.publication

//#if($Dissertation)
class Dissertacao extends TeseOrDissertacao {


    //#if($Bibtex)
    String generateBib() {
        return new BibtexExport().generateBibtexDissertacao(this)
    }
    //#end
}
//#end