package rgms.publication.strategyBibtexParse;

import org.jbibtex.BibTeXEntry;

import rgms.publication.Publication;

/**
 * 
 * @author Diogo Vinicius
 *
 */
public interface StrategyParse {

	//
	/**
	 * Para pegar os valores do objeto 'entry' basta seguir o modelo da linha abaixo </br>
	 * <code>String value = entry.getField(BibTeXEntry.KEY_TITLE).toUserString();</code> 
	 * @param entry
	 * @return Publication
	 */
	public abstract Publication execute(BibTeXEntry entry);
	
}
