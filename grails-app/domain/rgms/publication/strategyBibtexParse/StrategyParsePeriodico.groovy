package rgms.publication.strategyBibtexParse

import org.jbibtex.BibTeXEntry
import rgms.publication.Periodico
import rgms.publication.Publication

/**
 * Created by Joao on 2/25/14.
 */
class StrategyParsePeriodico implements StrategyParse{

    @Override
    Periodico execute(BibTeXEntry entry) {
        Periodico periodico = new Periodico()
        periodico.setTitle(entry.getField(BibTeXEntry.KEY_TITLE).toUserString())
        periodico.setVolume(entry.getField(BibTeXEntry.KEY_VOLUME).toUserString().toInteger())
        periodico.setNumber(entry.getField(BibTeXEntry.KEY_NUMBER).toUserString().toInteger())
        periodico.setPages(entry.getField(BibTeXEntry.KEY_PAGES).toUserString())
        periodico.setJournal( entry.getField(BibTeXEntry.KEY_JOURNAL).toUserString())
        periodico.setPublicationDate(new Date());//TODO transformar o date para setar no objeto
        return periodico
    }
}
