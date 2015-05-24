//#if($Bibtex)
package rgms.publication

import rgms.member.Member;


class BibtexAux {

    static String organizeAuthors(Set<Member> members) {
        def returning = ""
        int count = 0;
        for (Member member : members.iterator()) {
            returning = returning + member.name
            count = count + 1

            /* Tomando cuidado para não adicionar um and depois do último autor*/
            if (count < members.size()) {
                returning = returning + " and "
            }
        }
        return returning.substring(0, returning.length())
    }
}
//#end
