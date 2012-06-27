package rgms

class Periodico extends Publicacao{

	String journal
	int volume
	int number
	int pageInitial
	int pageFinal
	String arquivo
	
	static hasMany = [members:Member]
	
	static constraints = {
		arquivo(blank:true, maxSize:100000000)
	}
	
	#if($bibtex)
	public String setBib(){
		this.bibTex = "@article{"+this.retPrimeiroAutor()+this.year
		this.bibTex = this.bibTex +",author=\""+this.retListaAutor()
		this.bibTex = this.bibTex +"\",\n title=\""+this.title
		this.bibTex = this.bibTex +"\",\n journal=\""+this.journal
		this.bibTex = this.bibTex +"\",\n year=\""+this.year
		this.bibTex = this.bibTex +"\",\n volume=\""+this.volume
		this.bibTex = this.bibTex +"\",\n number=\""+this.number
		this.bibTex = this.bibTex +"\",\n pages=\""+this.pageInitial+"-"+this.pageFinal+"\"}"
	}
	#end
}
