package rgms

class Dissertacao extends Publicacao {
	
	String school
	int month
	String arquivo
	
	Member member

	
    static constraints = {
		title(nullable:false, blank:false)
		school(nullable:false, blank:false)
		year(nullable:false, blank:false)
		month(maxSize:12)
		arquivo(blank:true, maxSize:100000000)
    }
	#if($bibtex)
	public String setBib(){
		this.bibTex = "@masterthesis{"+this.retPrimeiroAutor()+this.year
		this.bibTex = this.bibTex +",author=\""+this.retListaAutor()
		this.bibTex = this.bibTex +"\",\n title=\""+this.title
		this.bibTex = this.bibTex +"\",\n school=\""+this.school
		this.bibTex = this.bibTex +"\",\n year=\""+this.year
		this.bibTex = this.bibTex +"\",\n month=\""+this.month+"\"}"
	}
	#end
}
