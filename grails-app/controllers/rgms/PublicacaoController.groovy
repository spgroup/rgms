package rgms

import java.awt.event.ItemEvent;

class PublicacaoController {

	def index() { 
		def listaPeriodico = Periodico.getAll();
		def listaFerramenta = Ferramenta.getAll();
		def listaConferencia = Conferencia.getAll();
		#if($maisresul)
		def listaDissertacao = Dissertacao.getAll();
		def listaTese = Tese.getAll();
		#end
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
		#if($maisresul)
		for(item in listaDissertacao){
			listaGeral.add(item)
		}
		for(item in listaTese){
			listaGeral.add(item)
		}
		#end
		
		return [item:listaGeral]
	}
	
	/*
	 *  TESTE
	 * */
	
	def pdfPeriodico(){
		def listaPeriodico = Periodico.getAll();
		PdfController pdf = new PdfController()
		return pdf.index(listaPeriodico)
	}
	
	def pdfConferencia(){
		def listaConferencia = Conferencia.getAll();
		PdfController pdf = new PdfController()
		return pdf.index(listaConferencia)
	}
	
	def pdfFerramenta (){
		def listaFerramenta = Ferramenta.getAll();
		PdfController pdf = new PdfController()
		return pdf.index(listaFerramenta)
	}
	#if($maisresul)
	def pdfDissertacao(){
		def listaDissertacao = Dissertacao.getAll();
		PdfController pdf = new PdfController()
		return pdf.index(listaDissertacao)
	}
	
	def pdfTese(){
		def listaTese = Tese.getAll();
		PdfController pdf = new PdfController()
		return pdf.index(listaTese)
	}
	#end
	def pdftodasPublicacoes () {

			return pdf.index(Publicacao.index())
	}
	/*
	 * FIM TESTE
	 * */
	
	
	def upload = { publicacaoInstance ->
		def nomeOriginal = params.arquivo.originalFilename
		publicacaoInstance.arquivo = nomeOriginal
		
		def f = request.getFile("arquivo")
		
		if(!f.empty){
			f.transferTo(new File("web-app/uploads/${nomeOriginal}"))
		}else{
			flash.message = "nao foi possivel transferir o arquivo"
			}
	}
	
}