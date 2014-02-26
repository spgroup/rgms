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
        techReport.title = entry.getField(BibTeXEntry.KEY_TITLE).toUserString();
        techReport.institution = entry.getField(BibTeXEntry.KEY_INSTITUTION).toUserString()
        techReport.publicationDate = new Date();//TODO transformar o date para setar no objeto
        techReport.file = "file";//TODO tirar a obrigatoriedade. futuramente processar a url para importar
        techReport;
    }
}
