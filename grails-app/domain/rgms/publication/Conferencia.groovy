package rgms.publication

import rgms.member.Member

class Conferencia extends Publication {

    String booktitle
    String pages

    static constraints = {
        booktitle nullable: false, blank: false
        pages nullable: false, blank: false
    }

    //#if($Bibtex)
    String generateBib() {
        return new BibtexExport().generateBibtexConferencia(this)
    }
    //#end

    //#if($managingAuthors)
    void addAuthor(String authorName) {
        Member member = Member.findByName(authorName)
        if (!member == null)
        {
            if (!members.contains(member))
                members.add(member)
        }

        if (authors == null)
            authors = new ArrayList<String>()

        if (!authors.contains(authorName))
            authors.add(authorName)
    }

    void removeAuthor(String authorName){
        Member member = Member.findByName(authorName)
        if (!member == null)
        {
            if (members.contains(member))
                members.remove(member)
        }

        if (authors.contains(authorName))
            authors.remove(authorName)
    }
    //#end
}