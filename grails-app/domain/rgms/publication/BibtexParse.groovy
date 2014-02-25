package rgms.publication

import org.jbibtex.*
import rgms.publication.strategyBibtexParse.*

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

        entries.each { BibTeXEntry entry ->
            //TODO settar todos os atributos de acordo com a classe a ser instanciada
            //Para pegar os valores do objeto 'entry' basta seguir o modelo da linha abaixo
            //String value = entry.getField(BibTeXEntry.KEY_TITLE).toUserString();
            switch (entry.getType()) {
                //#if($Periodico)
                case BibTeXEntry.TYPE_ARTICLE:
                    publications.add(new StrategyParsePeriodico().execute(entry))
                    break
                //#end

                //#if($BookChapter)
                case BibTeXEntry.TYPE_BOOK:
                    publications.add(new StrategyParseBookChapter().execute(entry))
                    break
                //#end


                case BibTeXEntry.TYPE_BOOKLET:
                    break

                //#if($Conferencia)
                case BibTeXEntry.TYPE_INPROCEEDINGS:
                case BibTeXEntry.TYPE_CONFERENCE:
                    publications.add(new StrategyParseConference().execute(entry))
                    break

                //#end

                case BibTeXEntry.TYPE_INBOOK:
                    break

                case BibTeXEntry.TYPE_INCOLLECTION:
                    break

                case BibTeXEntry.TYPE_MANUAL:
                    break

                //#if($Dissertation)
                case BibTeXEntry.TYPE_MASTERSTHESIS:
                    publications.add(new StrategyParseDissertacao().execute(entry))
                    break

                case BibTeXEntry.TYPE_MISC:
                    break
                //#end

                //#if($ImportBibtex && $TesePublication)
                case BibTeXEntry.TYPE_PHDTHESIS:
                    publications.add(new StrategyParseTese().execute(entry))
                    break
                //#end

                case BibTeXEntry.TYPE_PROCEEDINGS:
                    break

                case BibTeXEntry.TYPE_TECHREPORT:
                    publications.add(new StrategyParseTechnicalReport().execute(entry))
                    break

                case BibTeXEntry.TYPE_UNPUBLISHED:
                    break

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
