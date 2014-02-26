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
        conf.title = entry.getField(BibTeXEntry.KEY_TITLE).toUserString()
        conf.booktitle = entry.getField(BibTeXEntry.KEY_BOOKTITLE).toUserString()
        conf.pages = entry.getField(BibTeXEntry.KEY_PAGES).toUserString()
        conf.publicationDate = new Date()//TODO transformar o date para setar no objeto
        conf.file = "file"//TODO tirar a obrigatoriedade. futuramente processar a url para importar
        conf
    }
}
