package rgms.publication

/**
 * Created by Joao on 2/26/14.
 */
class Miscellaneous extends Publication{

    String author
    String howPublished
    String note

    static constraints = {
        author nullable: true
        howPublished nullable: true
        note nullable: true
    }


    @Override
    String generateBib() {
        return new BibtexExport().generateBibtexMiscellaneous(this)
    }
}
