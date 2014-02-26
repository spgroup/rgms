package rgms.publication.strategyBibtexParse

import org.jbibtex.BibTeXEntry
import rgms.publication.Periodico

/**
 * Created by Joao on 2/25/14.
 */
class StrategyParsePeriodico implements StrategyParse{

    @Override
    Periodico execute(BibTeXEntry entry) {
        Periodico periodico = new Periodico()
        periodico.title = entry.getField(BibTeXEntry.KEY_TITLE).toUserString()
        periodico.volume = entry.getField(BibTeXEntry.KEY_VOLUME).toUserString().toInteger()
        periodico.number = entry.getField(BibTeXEntry.KEY_NUMBER).toUserString().toInteger()
        periodico.pages = entry.getField(BibTeXEntry.KEY_PAGES).toUserString()
        periodico.journal = entry.getField(BibTeXEntry.KEY_JOURNAL).toUserString()
        periodico.publicationDate = new Date();//TODO transformar o date para setar no objeto
        periodico
    }
}
