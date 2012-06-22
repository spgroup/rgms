package rgms

abstract class Publicacao {
	
	String author
	String title
	int year
	#if($bibtex)
	String bibTex
	#end

    static constraints = {
		author(nullable:false, blank:false)
		title(nullable:false, blank:false)
		year(nullable:false, blank:false)
    }
	
	public abstract String setBib();
	
	public String retPrimeiroAutor(){
		String[] quebraString = this.author.tokenize(",")
		String nomeAutor = quebraString[0]
		String[] quebraNovo = nomeAutor.split()
		String ultimoNome = quebraNovo[quebraNovo.length-1]
		return ultimoNome
	}
	
	public String retListaAutor(){
		
		String[] quebraString = this.author.tokenize(",")
		String lista =""
		//quebraString.each { if(quebraString.length()); lista}
		int valor=0
		for(i in quebraString){
			if(valor!=(quebraString.length-1)){
				lista = lista+i+" and "
				valor++
			}else{
				lista = lista+i
			}
		}
		
		return lista;
	}
}
