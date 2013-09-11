package rgms.publication

import rgms.member.Member

/**
 * Created with IntelliJ IDEA.
 * User: eduardoangelinramos
 * Date: 01/09/13
 * Time: 02:11
 * To change this template use File | Settings | File Templates.
 */
class BibtexGenerateFile {

    List<Publication> publications = new ArrayList<Publication>()
    Member member

    public BibtexFile(file) {
        publications = BibtexParse.generatePublications(file)
    }

    public List<Publication> getPublications() {
        return this.publications
    }

    public List getPublications(Class clazz) {
        List publicationsFiltered = new ArrayList()
        for (Publication publication : this.getPublications()) {
            if (publication.getClass().getName().equals(clazz.getName())) {
                publicationsFiltered.add(publication)
            }
        }
        return publicationsFiltered
    }
}
