package rgms.publication


class Dissertacao extends TeseOrDissertacao {

    String title

    //#if($Bibtex)
    String generateBib() {
        return new BibtexExport().generateBibtexDissertacao(this)
    }


    List findByTitle(String title){
        return new ArrayList<Dissertacao>()
    }

    //#end
}