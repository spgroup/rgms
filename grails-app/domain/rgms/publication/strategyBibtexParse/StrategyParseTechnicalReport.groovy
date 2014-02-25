package rgms.publication.strategyBibtexParse

import org.jbibtex.BibTeXEntry
import rgms.publication.TechnicalReport

/**
 * Created by Joao on 2/25/14.
 */
class StrategyParseTechnicalReport implements StrategyParse {

    @Override
    public TechnicalReport execute(BibTeXEntry entry) {
        TechnicalReport techReport = new TechnicalReport();
        techReport.setTitle(entry.getField(BibTeXEntry.KEY_TITLE).toUserString());
        techReport.setInstitution(entry.getField(BibTeXEntry.KEY_INSTITUTION).toUserString())
        techReport.setPublicationDate(new Date());//TODO transformar o date para setar no objeto
        techReport.setFile("file");//TODO tirar a obrigatoriedade. futuramente processar a url para importar
        return techReport;
    }
}
