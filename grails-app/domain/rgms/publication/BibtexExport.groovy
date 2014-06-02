//#if($Bibtex)
package rgms.publication
/**
 * Created with IntelliJ IDEA.
 * User: eduardoangelinramos
 * Date: 27/08/13
 * Time: 14:08
 * To change this template use File | Settings | File Templates.
 */
class BibtexExport {

    public String generateBibtexBookChapter(BookChapter bookChapter) {

        return "@book{" + "author=\"" + BibtexAux.organizeAuthors(bookChapter.members) + "\",\n title=\"" + bookChapter.title + "\",\n publisher=\"" + bookChapter.publisher + "\",\n year=\"" + bookChapter.publicationDate.getAt(Calendar.YEAR) + "\",\n chapter=\"" + bookChapter.chapter + "\"}"
    }

    public String generateBibtexBook(Book book) {

        return "@book{" + "member=\"" + BibtexAux.organizeAuthors(book.members) + "\",\n authors=\"" + book.authors + "\",\n title=\"" + book.title + "\",\n publisher=\"" + book.publisher + "\",\n volume=\"" + book.volume + "\",\n pages=\"" + book.pages + "\"}"
    }


    public String generateBibtexConferencia(Conferencia conferencia) {

        int yearDate = conferencia.publicationDate.getAt(Calendar.YEAR)

        return "@inproceedings{" + conferencia.members.toList()[0] + yearDate + ",author=\"" + BibtexAux.organizeAuthors(conferencia.members) + "\",\n title=\"" + conferencia.title + "\",\n booktitle=\"" + conferencia.booktitle + "\",\n year=\"" + yearDate + "\",\n pages=\"" + conferencia.pages + "\",\n month=\"" + conferencia.publicationDate.getAt(Calendar.MONTH) + "\"}"
    }

    public String generateBibtexDissertacao(Dissertacao dissertacao) {

        return "@masterthesis{" + "author=\"" + BibtexAux.organizeAuthors(dissertacao.members) + "\",\n title=\"" + dissertacao.title + "\",\n school=\"" + dissertacao.school + "\",\n year=\"" + dissertacao.publicationDate.getAt(Calendar.YEAR) + "\",\n address=\"" + dissertacao.address + "\",\n month=\"" + dissertacao.publicationDate.getAt(Calendar.MONTH) + "\"}"
    }

    public String generateBibtexFerramenta(Ferramenta ferramenta) {

        return "@misc{" + "author=\"" + BibtexAux.organizeAuthors(ferramenta.members) + "\",\n title=\"" + ferramenta.title + "\",\n website=\"" + ferramenta.website + "\",\n year=\"" + ferramenta.publicationDate.getAt(Calendar.YEAR) + "\",\n description=\"" + ferramenta.description + "\",\n month=\"" + ferramenta.publicationDate.getAt(Calendar.MONTH) + "\"}"
    }

    //#if($Article)
    public String generateBibtexPeriodico(Periodico periodico) {

        return "@article{" + "author=\"" + BibtexAux.organizeAuthors(periodico.members) + "\",\n title=\"" + periodico.title + "\",\n journal=\"" + periodico.journal + "\",\n year=\"" + periodico.publicationDate.getAt(Calendar.YEAR) + "\",\n volume=\"" + periodico.volume + "\",\n month=\"" + periodico.publicationDate.getAt(Calendar.MONTH) + "\",\n number=\"" + periodico.number + "\",\n pages=\"" + periodico.pages + "\"}"
    }
    //#end

    public String generateBibtexTechnicalReport(TechnicalReport technicalReport) {

        return "@techreport{" + "author=\"" + BibtexAux.organizeAuthors(technicalReport.members) + "\",\n title=\"" + technicalReport.title + "\",\n institution=\"" + technicalReport.institution + "\",\n year=\"" + technicalReport.publicationDate.getAt(Calendar.YEAR) + "\"}"
    }

    public String generateBibtexTese(Tese tese) {

        return "@phdthesis{" + "author=\"" + BibtexAux.organizeAuthors(tese.members) + "\",\n title=\"" + tese.title + "\",\n school=\"" + tese.school + "\",\n year=\"" + tese.publicationDate.getAt(Calendar.YEAR) + "\",\n address=\"" + tese.address + "\"}"
    }
}

//#end
