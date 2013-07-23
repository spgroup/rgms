package rgms.publication

import rgms.member.Member

/**
 *
 * @author Diogo Vin�cius
 *
 */
class BibtexFile {

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
