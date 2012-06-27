package rgms

class Conferencia extends Publicacao{

	String conference
	int pageInitial
	int pageFinal
	int month
	String arquivo

	static hasMany = [members:Member]
	
	static constraints = {
		author(nullable:false, blank:false)
		title(nullable:false, blank:false)
		conference(nullable:false, blank:false)
		year(nullable:false, blank:false)
		month(maxSize:12)
		arquivo(blank:true, maxSize:100000000)
	}
	
	#if($bibtex)
	public String setBib(){
		this.bibTex = "@inproceedings{"+this.retPrimeiroAutor()+this.year
		this.bibTex = this.bibTex +",author=\""+this.retListaAutor()
		this.bibTex = this.bibTex +"\",\n title=\""+this.title
		this.bibTex = this.bibTex +"\",\n booktitle=\""+this.conference
		this.bibTex = this.bibTex +"\",\n year=\""+this.year
		this.bibTex = this.bibTex +"\",\n pages=\""+this.pageInitial+"-"+this.pageFinal
		this.bibTex = this.bibTex +"\",\n month=\""+this.month+"\"}"
	}
	#end
}
