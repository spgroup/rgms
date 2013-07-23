package rgms.publication.strategyBibtexParse;

import org.jbibtex.BibTeXEntry;
import rgms.publication.Publication;
import rgms.publication.Tese;

import java.util.Date;

/**
 * @author Diogo Vinicius
 */
public class StrategyParseTese implements StrategyParse {

    @Override
    public Publication execute(BibTeXEntry entry) {
        Tese tese = new Tese();
        tese.setTitle(entry.getField(BibTeXEntry.KEY_TITLE).toUserString());
        tese.setSchool(entry.getField(BibTeXEntry.KEY_SCHOOL).toUserString());
        tese.setAddress(entry.getField(BibTeXEntry.KEY_ADDRESS).toUserString());
        tese.setPublicationDate(new Date());//TODO transformar o date para setar no objeto
        tese.setFile("file");//TODO settar corretamente este atributo
        return tese;
    }

}
