//#if($Bibtex)
package rgms.publication

import rgms.member.Member;


class BibtexAux {

    static String organizeAuthors(List<Member> members) {
  	def returning = ""
		for (Member member : members) {
			returning = returning + member.name + " and "
		}
		return returning.substring(0, returning.length() - 5)
	}
}
//#end
