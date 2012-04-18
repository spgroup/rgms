package rgms

class Periodico {

	String author
	String title
	String journal
	int year
	String bibTex
	int volume
	int number
	int pageInitial
	int pageFinal
	String arquivo
	
	static hasMany = [members:Member]
	
	static constraints = {
		arquivo(blank:true, maxSize:100000000)
	}
}
