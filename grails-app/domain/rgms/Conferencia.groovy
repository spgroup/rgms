package rgms

class Conferencia {

	String author
	String title
	String conference
	int year

	int pages
	int month

	static constraints = {
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
		author(nullable:false, blank:false)
		title(nullable:false, blank:false)
		conference(nullable:false, blank:false)
		year(nullable:false, blank:false)
		pages()
		month(maxSize:12)
=======
>>>>>>> 4dc8467add37a91b09033139da8c2e6bbaa43086
=======
>>>>>>> 4dc8467add37a91b09033139da8c2e6bbaa43086
=======
>>>>>>> 4dc8467add37a91b09033139da8c2e6bbaa43086
	}
}
