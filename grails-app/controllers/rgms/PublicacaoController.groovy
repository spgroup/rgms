package rgms

import java.awt.event.ItemEvent;

class PublicacaoController {

	def index() { 
		//redirect (action: home)
		def listaPeriodico = Periodico.getAll();
		def listaFerramenta = Ferramenta.getAll();
		def listaConferencia = Conferencia.getAll();
		def listaDissertacao = Dissertacao.getAll();
		def listaTese = Tese.getAll();
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
		for(item in listaDissertacao){
			listaGeral.add(item)
		}
		for(item in listaTese){
			listaGeral.add(item)
		}
		
		return [item:listaGeral]
	}
	
	def show(){

	}	
	
}
