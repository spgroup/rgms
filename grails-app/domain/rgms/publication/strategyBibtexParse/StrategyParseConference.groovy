package rgms.publication.strategyBibtexParse

import org.jbibtex.BibTeXEntry
import rgms.publication.Conferencia

/**
 * Created by Joao on 2/25/14.
 */
class StrategyParseConference implements StrategyParse{
    @Override
    Conferencia  execute(BibTeXEntry entry) {
        Conferencia conf = new Conferencia()
        conf.setTitle(entry.getField(BibTeXEntry.KEY_TITLE).toUserString())
        conf.setBooktitle(entry.getField(BibTeXEntry.KEY_BOOKTITLE).toUserString())
        conf.setPages(entry.getField(BibTeXEntry.KEY_PAGES).toUserString())
        conf.setPublicationDate(new Date());//TODO transformar o date para setar no objeto
        conf.setFile("file");//TODO tirar a obrigatoriedade. futuramente processar a url para importar
        return conf
    }
}
