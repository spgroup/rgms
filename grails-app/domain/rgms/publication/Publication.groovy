package rgms.publication

import rgms.member.Member

abstract class Publication {

    String title
    Date publicationDate
    String file
    ResearchLine researchLine

    static belongsTo = Member
    static hasMany = [members: Member]

    static constraints = {
        title nullable: false, blank: false, size: 1..200
        publicationDate nullable: false
        file maxSize: 100000, nullable: true, blank: true
        researchLine nullable: true, blank: true
    }

    //#if($Bibtex)
    abstract String generateBib()
    //#end

    // código morto? não, usado pelo delete do controlador! não funcionando?! já que Member não tem o método removeFrom...
    def removeFromPublications() {
        this.members.each {
            it.removeFromPublications(this)
        }
    }

    def discardMembers() {
        this.members.each {
            it.discard()
        }
    }

    public String toString() {
        return title
    }

    static Set getPublicationsByMembershipList(membershipList) {
        def set = [] as Set
        for (membership in membershipList) {
            set.addAll(Publication.getPublicationsByMembership(membership))
        }
        return set
    }

    static Set getPublicationsByMembership(membership) {
        def publications = membership?.member.publications
        def query = !membership.dateLeft ?
                { it.publicationDate?.compareTo(membership.dateJoined) > 0 } :
                {
                    it.publicationDate?.compareTo(membership.dateJoined) > 0 &&
                            it.publicationDate?.compareTo(membership.dateLeft) < 0
                }
        def p = publications?.findAll(query)
        return p
    }

    @Override
    public boolean equals(Object obj) {
        if ((obj == null) || (obj.getClass() != this.getClass())) {
            return false
        }
        Publication p = (Publication) obj
        return this.title != null && this.title.equals(p.title)
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.title == null) ? 0 : this.title.hashCode());
        result = prime * result + ((this.id == null) ? 0 : this.id);
        return result;
    }

    public addMember(Member e) {
        if (!this.getMembers()) {
            this.setMembers(new LinkedHashSet<Member>())
            this.getMembers().add(e)
        } else if (!this.getMembers().contains(e)) {
            this.getMembers().add(e)
        }
    }

//	public String retPrimeiroAutor(){
//		String[] quebraString = this.author.tokenize(",")
//		String nomeAutor = quebraString[0]
//		String[] quebraNovo = nomeAutor.split()
//		String ultimoNome = quebraNovo[quebraNovo.length-1]
//		return ultimoNome
//	}

//	public String retListaAutor(){
//		
//		String[] quebraString = this.author.tokenize(",")
//		String lista =""
//		//quebraString.each { if(quebraString.length()); lista}
//		int valor=0
//		for(i in quebraString){
//			if(valor!=(quebraString.length-1)){
//				lista = lista+i+" and "
//				valor++
//			}else{
//				lista = lista+i
//			}
//		}
//		
//		return lista;
//	}
}
