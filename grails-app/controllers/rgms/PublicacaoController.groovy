package rgms

class PublicacaoController {

	def index() { 
		//redirect (action: home)
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
	
}
