package rgms.publication.strategyBibtexParse

import org.jbibtex.BibTeXEntry
import rgms.publication.BookChapter;

/**
 * Created by Joao on 2/25/14.
 */
class StrategyParseBookChapter implements StrategyParse {

    @Override
    public BookChapter execute(BibTeXEntry entry) {
        BookChapter bChap = new BookChapter()
        bChap.title = entry.getField(BibTeXEntry.KEY_TITLE).toUserString()
        bChap.publisher = entry.getField(BibTeXEntry.KEY_PUBLISHER).toUserString()
        bChap.chapter = entry.getField(BibTeXEntry.KEY_CHAPTER).toUserString().toInteger()
        bChap.publicationDate = new Date();//TODO transformar o date para setar no objeto
        bChap.file = "file";//TODO tirar a obrigatoriedade. futuramente processar a url para importar
        bChap
    }

}
