package rgms.publication

import org.jbibtex.*
import rgms.publication.strategyBibtexParse.StrategyParseDissertacao
import rgms.publication.strategyBibtexParse.StrategyParseTese

/**
 *
 * @author Diogo Vin�cius
 *
 */
class BibtexParse {

    public static List<Publication> generatePublications(File file) {
        List<Publication> publications = new ArrayList<Publication>()
        BibTeXDatabase bibtexDatabase = parseBibTeX(file)
        Collection<BibTeXEntry> entries = bibtexDatabase.getEntries().values();

        for (BibTeXEntry entry : entries) {
            //TODO settar todos os atributos de acordo com a classe a ser instanciada
            //Para pegar os valores do objeto 'entry' basta seguir o modelo da linha abaixo
            //String value = entry.getField(BibTeXEntry.KEY_TITLE).toUserString();
            Publication publicationTemp = new Publication() {
                @Override
                String generateBib() {
                    return null  //To change body of implemented methods use File | Settings | File Templates.
                }
            }
            Date date = new Date()
            date.year = entry.getField(BibTeXEntry.KEY_YEAR).toUserString().toInteger()
           // date.month = entry.getField(BibTeXEntry.KEY_MONTH).toUserString().toInteger()
            publicationTemp.title = entry.getField(BibTeXEntry.KEY_TITLE).toUserString()
            publicationTemp.publicationDate = date
            publicationTemp.members = entry.getField(BibTeXEntry.KEY_AUTHOR).toUserString().toSet()
            if (entry.getType().equals(BibTeXEntry.TYPE_ARTICLE)) {
                Periodico periodico = new Periodico()
                periodico.title = publicationTemp.title
                periodico.publicationDate = publicationTemp.publicationDate
                periodico.members = publicationTemp.members
                periodico.volume = entry.getField(BibTeXEntry.KEY_VOLUME).toUserString().toInteger()
                periodico.number = entry.getField(BibTeXEntry.KEY_NUMBER).toUserString().toInteger()
                periodico.pages = entry.getField(BibTeXEntry.KEY_PAGES).toUserString().toInteger()
                periodico.journal = entry.getField(BibTeXEntry.KEY_JOURNAL).toUserString()
                publications.add(periodico)

            } else if (entry.getType().equals(BibTeXEntry.TYPE_BOOK)) {
                BookChapter bookchapter = new BookChapter()
                bookchapter.chapter = entry.getField(BibTeXEntry.KEY_CHAPTER).toUserString().toInteger()
                bookchapter.publisher = entry.getField(BibTeXEntry.KEY_PUBLISHER).toUserString()
                bookchapter.title = publicationTemp.title
                bookchapter.publicationDate = publicationTemp.publicationDate
                bookchapter.members = publicationTemp.members
                publications.add(bookchapter)
            } else if (entry.getType().equals(BibTeXEntry.TYPE_BOOKLET)) {

            } else if (entry.getType().equals(BibTeXEntry.TYPE_CONFERENCE)) {



            } else if (entry.getType().equals(BibTeXEntry.TYPE_INBOOK)) {

            } else if (entry.getType().equals(BibTeXEntry.TYPE_INCOLLECTION)) {

            } else if (entry.getType().equals(BibTeXEntry.TYPE_INPROCEEDINGS)) {
                Conferencia conferencia = new Conferencia()
                conferencia.booktitle = entry.getField(BibTeXEntry.KEY_BOOKTITLE).toUserString()
                conferencia.pages = entry.getField(BibTeXEntry.KEY_PAGES).toUserString().toInteger()
                conferencia.title = publicationTemp.title
                conferencia.publicationDate = publicationTemp.publicationDate
                conferencia.members = publicationTemp.members
                publications.add(conferencia)

            } else if (entry.getType().equals(BibTeXEntry.TYPE_MANUAL)) {

            } else if (entry.getType().equals(BibTeXEntry.TYPE_MASTERSTHESIS)) {

                Dissertacao dissertacao = new Dissertacao()
                dissertacao.school = entry.getField(BibTeXEntry.KEY_SCHOOL).toUserString()
                dissertacao.address = entry.getField(BibTeXEntry.KEY_ADDRESS).toUserString()
                dissertacao.title = publicationTemp.title
                dissertacao.publicationDate = publicationTemp.publicationDate
                dissertacao.members = publicationTemp.members
                publications.add(dissertacao)

            } else if (entry.getType().equals(BibTeXEntry.TYPE_MISC)) {
                Ferramenta ferramenta = new Ferramenta()
                ferramenta.website = entry.getField((BibTeXEntry.KEY_URL)).toUserString()
                ferramenta.description = entry.getField(BibTeXEntry.KEY_NOTE).toUserString()
                ferramenta.title = publicationTemp.title
                ferramenta.publicationDate = publicationTemp.publicationDate
                ferramenta.members = publicationTemp.members
                publications.add(ferramenta)

            }
            //#if($ImportBibtex && $TesePublication)
            else if (entry.getType().equals(BibTeXEntry.TYPE_PHDTHESIS)) {
                publications.add(new StrategyParseTese().execute(entry))
            }
            //#end
            else if (entry.getType().equals(BibTeXEntry.TYPE_PROCEEDINGS)) {

            } else if (entry.getType().equals(BibTeXEntry.TYPE_TECHREPORT)) {
                publications.add(new TechnicalReport())

            } else if (entry.getType().equals(BibTeXEntry.TYPE_UNPUBLISHED)) {

            }

        }

        return publications

    }

    private static BibTeXDatabase parseBibTeX(File file) throws IOException, ParseException {
        Reader reader = new FileReader(file);

        try {
            BibTeXParser parser = new BibTeXParser() {

                @Override
                public void checkStringResolution(Key key, BibTeXString string) {

                    if (string == null) {
                        System.err.println("Unresolved string: \"" + key.getValue() + "\"");
                    }
                }

                @Override
                public void checkCrossReferenceResolution(Key key, BibTeXEntry entry) {

                    if (entry == null) {
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
