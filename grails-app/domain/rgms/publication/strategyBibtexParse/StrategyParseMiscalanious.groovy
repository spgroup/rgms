package rgms.publication.strategyBibtexParse

import org.jbibtex.BibTeXEntry
import rgms.publication.Miscellaneous
import rgms.publication.Publication

/**
 * Created by Joao on 2/26/14.
 */
class StrategyParseMiscellaneous implements StrategyParse{
    @Override
    Miscellaneous execute(BibTeXEntry entry) {
        Miscellaneous misc = new Miscellaneous()
        misc.title = entry.getField(BibTeXEntry.KEY_TITLE)?.toUserString()
        misc.publicationDate = new Date(year: entry.getField(BibTeXEntry.KEY_YEAR)?.toUserString()?.toInteger(),month:entry.getField(BibTeXEntry.KEY_MONTH)?.toUserString()?.toInteger() );
        misc.author = entry.getField(BibTeXEntry.KEY_AUTHOR)?.toUserString()
        misc.howPublished = entry.getField(BibTeXEntry.KEY_HOWPUBLISHED)?.toUserString()
        misc.note = entry.getField(BibTeXEntry.KEY_NOTE)?.toUserString()
        return misc
    }
}
