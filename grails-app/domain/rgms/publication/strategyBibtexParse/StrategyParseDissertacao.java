package rgms.publication.strategyBibtexParse;

import org.jbibtex.BibTeXEntry;
import rgms.publication.Dissertacao;
import rgms.publication.Publication;

import java.util.Date;

/**
 * @author Diogo Vinicius
 */
public class StrategyParseDissertacao implements StrategyParse {

    @Override
    public Publication execute(BibTeXEntry entry) {
        Dissertacao dissertacao = new Dissertacao();
        dissertacao.setTitle(entry.getField(BibTeXEntry.KEY_TITLE).toUserString());
        dissertacao.setSchool(entry.getField(BibTeXEntry.KEY_SCHOOL).toUserString());
        dissertacao.setAddress(entry.getField(BibTeXEntry.KEY_ADDRESS).toUserString());
        dissertacao.setPublicationDate(new Date());//TODO transformar o date para setar no objeto
        dissertacao.setFile("file");//TODO tirar a obrigatoriedade. futuramente processar a url para importar
        return dissertacao;
    }

}
