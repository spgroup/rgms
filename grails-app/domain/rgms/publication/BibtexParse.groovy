package rgms.publication

import org.jbibtex.BibTeXDatabase
import org.jbibtex.BibTeXEntry;
import org.jbibtex.BibTeXParser
import org.jbibtex.BibTeXString
import org.jbibtex.Key
import org.jbibtex.ParseException
import org.jbibtex.Value

/**
 *
 * @author Diogo Vinícius
 *
 */
class BibtexParse {

	public static List<Publication> generatePublications(File file) {
		List<Publication> publications = new ArrayList<Publication>()
		BibTeXDatabase bibtexDatabase = parseBibTeX(file)
		Collection<BibTeXEntry> entries = bibtexDatabase.getEntries().values();
		
		for(BibTeXEntry entry : entries){
			//TODO settar todos os atributos de acordo com a classe a ser instanciada
			//Para pegar os valores do objeto 'entry' basta seguir o modelo da linha abaixo
			//String value = entry.getField(BibTeXEntry.KEY_TITLE).toUserString(); 
			if (entry.getType().equals(BibTeXEntry.TYPE_ARTICLE)) {
				
			}
			else if (entry.getType().equals(BibTeXEntry.TYPE_BOOK)) {
				publications.add(new BookChapter())
			}
			else if (entry.getType().equals(BibTeXEntry.TYPE_BOOKLET)) {
				
			}
			else if (entry.getType().equals(BibTeXEntry.TYPE_CONFERENCE)) {
				publications.add(new Conferencia())
			}
			else if (entry.getType().equals(BibTeXEntry.TYPE_INBOOK)) {
				
			}
			else if (entry.getType().equals(BibTeXEntry.TYPE_INCOLLECTION)) {
				
			}
			else if (entry.getType().equals(BibTeXEntry.TYPE_INPROCEEDINGS)) {
				
			}
			else if (entry.getType().equals(BibTeXEntry.TYPE_MANUAL)) {
				
			}
			else if (entry.getType().equals(BibTeXEntry.TYPE_MASTERSTHESIS)) {
				Dissertacao dissertacao = new Dissertacao()
				dissertacao.setTitle(entry.getField(BibTeXEntry.KEY_TITLE).toUserString())
				dissertacao.setSchool(entry.getField(BibTeXEntry.KEY_SCHOOL).toUserString())
				dissertacao.setAddress(entry.getField(BibTeXEntry.KEY_ADDRESS).toUserString())
				dissertacao.setPublicationDate(new Date())//TODO transformar o date para setar no objeto
				dissertacao.setFile("file")//TODO tirar a obrigatoriedade. futuramente processar a url para importar 
				publications.add(dissertacao)
			}
			else if (entry.getType().equals(BibTeXEntry.TYPE_MISC)) {
				
			}
			else if (entry.getType().equals(BibTeXEntry.TYPE_PHDTHESIS)) {
				Tese tese = new Tese()
				tese.setTitle(entry.getField(BibTeXEntry.KEY_TITLE).toUserString())
				tese.setSchool(entry.getField(BibTeXEntry.KEY_SCHOOL).toUserString())
				tese.setAddress(entry.getField(BibTeXEntry.KEY_ADDRESS).toUserString())
				tese.setPublicationDate(new Date())//TODO transformar o date para setar no objeto
				tese.setFile("file")//TODO settar corretamente este atributo
				publications.add(tese)
			}
			else if (entry.getType().equals(BibTeXEntry.TYPE_PROCEEDINGS)) {
				
			}
			else if (entry.getType().equals(BibTeXEntry.TYPE_TECHREPORT)) {
				publications.add(new TechnicalReport())
			}
			else if (entry.getType().equals(BibTeXEntry.TYPE_UNPUBLISHED)) {
				
			}
			
		}
		
		return publications
		
	}
	
	private static BibTeXDatabase parseBibTeX(File file) throws IOException, ParseException {
		Reader reader = new FileReader(file);

		try {
				BibTeXParser parser = new BibTeXParser(){

						@Override
						public void checkStringResolution(Key key, BibTeXString string){

								if(string == null){
										System.err.println("Unresolved string: \"" + key.getValue() + "\"");
								}
						}

						@Override
						public void checkCrossReferenceResolution(Key key, BibTeXEntry entry){

								if(entry == null){
										System.err.println("Unresolved cross-reference: \"" + key.getValue() + "\"");
								}
						}
				};

				return parser.parse(reader);
		} finally {
				reader.close();
		}
}
}
