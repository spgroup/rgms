package rgms

class PublicacaoController {

	def index() { 
		//redirect (action: home)
<<<<<<< HEAD
<<<<<<< HEAD
		def listaPeriodico = Periodico.getAll();
		def listaFerramenta = Ferramenta.getAll();
		def listaConferencia = Conferencia.getAll();
		def listaGeral = []
		for(item in listaPeriodico){
			listaGeral.add(item)
		}
		for(item in listaFerramenta){
			listaGeral.add(item)
		}
		for(item in listaConferencia){
			listaGeral.add(item)
		}
		
		return [item:listaGeral]
	}
	
	def show(){

	}	
	
=======
=======
>>>>>>> 4dc8467add37a91b09033139da8c2e6bbaa43086
	}
	/*def home(){
		render "<a href='/rgms/periodico/list'> Periodico </a> <br> <a href='/rgms/conferencia/list'> Conferencia </a> <br> <a href='/rgms/ferramenta/list'> Ferramenta </a>"
	}*/
<<<<<<< HEAD
>>>>>>> 4dc8467add37a91b09033139da8c2e6bbaa43086
=======
>>>>>>> 4dc8467add37a91b09033139da8c2e6bbaa43086
}
