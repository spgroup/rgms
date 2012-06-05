package rgms

import java.awt.event.ItemEvent;

class PublicacaoController {

	def index() { 
		def listaPeriodico = Periodico.getAll();
		def listaFerramenta = Ferramenta.getAll();
		def listaConferencia = Conferencia.getAll();
		
		/**Velocity**/
		#if($maisresul)
			def listaDissertacao = Dissertacao.getAll();
			def listaTese = Tese.getAll();
		#end
		/**Fim Velocity**/
		
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
		
		/**Velocity**/
		#if($maisresul)
			for(item in listaDissertacao){
				listaGeral.add(item)
			}
			for(item in listaTese){
				listaGeral.add(item)
			}
		#end
		/**Fim Velocity**/
		
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
	
	/**Velocity**/
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
	/**Fim Velocity**/
	
	def pdftodasPublicacoes () {
		def listaPeriodico = Periodico.getAll();
		def listaFerramenta = Ferramenta.getAll();
		def listaConferencia = Conferencia.getAll();
		
		/**Velocity**/
		#if($maisresul)
			def listaDissertacao = Dissertacao.getAll();
			def listaTese = Tese.getAll();
		#end
		/**Fim Velocity**/
		
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
		/**Velocity**/
		#if($maisresul)
			for(item in listaDissertacao){
				listaGeral.add(item)
			}
			for(item in listaTese){
				listaGeral.add(item)
			}
		#end
		/**Fim Velocity**/
			PdfController pdf = new PdfController()
			return pdf.index(listaGeral)
	}
	/*
	 * FIM TESTE
	 * */
	
	/**Velocity**/
	#if($bibtex)
	def bibTex = { publicacaoInstance ->
				Periodico periodico = new Periodico()
				Conferencia conferencia = new Conferencia()
				Ferramenta ferramenta = new Ferramenta()
				
				/**Velocity**/
				#if($maisresul)
					Dissertacao dissertacao = new Dissertacao()
					Tese tese = new Tese()
				#end
				/**Fim Velocity**/
				
				String[] quebraString = publicacaoInstance.author.tokenize(",")
				String nomeAutor = quebraString[0]
				String[] quebraNovo = nomeAutor.split()
				String ultimoNome = quebraNovo[quebraNovo.length-1]
				String ano = Integer.toString(publicacaoInstance.year)
				String num1 = ano.charAt(ano.length()-1)
				String num2 = ano.charAt(ano.length()-2)
				
				String puts = ""
				int valor=0
				for(i in quebraString){
					if(valor!=(quebraString.length-1)){
						puts = puts+i+" and "
						valor++
					}else{
						puts = puts+i
					}
				}
				
				
				if(publicacaoInstance.class == periodico.class){
					
					String bibtexPeriodico = "@article{"+ultimoNome+num2+num1+",author=\""+puts+"\",\n title=\""+publicacaoInstance.title+"\",\n journal=\""+publicacaoInstance.journal+"\",\n year=\""+publicacaoInstance.year+"\",\n volume=\""+publicacaoInstance.volume+"\",\n number=\""+publicacaoInstance.number+"\",\n pages=\""+publicacaoInstance.pageInitial+"-"+publicacaoInstance.pageFinal+"\"}"
					return bibtexPeriodico
				}
				if(publicacaoInstance.class == conferencia.class){
					String bibtexConferencia = "@inproceedings{"+ultimoNome+num2+num1+",author=\""+puts+"\",\n title=\""+publicacaoInstance.title+"\",\n booktitle=\""+publicacaoInstance.conference+"\",\n year=\""+publicacaoInstance.year+"\",\n pages=\""+publicacaoInstance.pageInitial+"-"+publicacaoInstance.pageFinal+"\",\n month=\""+publicacaoInstance.month+"\"}"
					return bibtexConferencia
					}
				if(publicacaoInstance.class == ferramenta.class){
					String bibtexFerramenta = "@misc{"+ultimoNome+num2+num1+",author=\""+puts+"\",\n title=\""+publicacaoInstance.title+"\",\n year=\""+publicacaoInstance.year+"\",\n note=\""+publicacaoInstance.descricao+"\"}"
					return bibtexFerramenta
					}
				
				/**Velocity**/
				#if($maisresul)
					if(publicacaoInstance.class == dissertacao.class){
						String bibtexDissertacao = "@masterthesis{"+ultimoNome+num2+num1+",author=\""+puts+"\",\n title=\""+publicacaoInstance.title+"\",\n school=\""+publicacaoInstance.school+"\",\n year=\""+publicacaoInstance.year+"\",\n month=\""+publicacaoInstance.month+"\"}"
						return bibtexDissertacao
						}
					if(publicacaoInstance.class == tese.class){
						String bibtexTese = "@phdthesis{"+ultimoNome+num2+num1+",author=\""+puts+"\",\n title=\""+publicacaoInstance.title+"\",\n school=\""+publicacaoInstance.school+"\",\n year=\""+publicacaoInstance.year+"\",\n month=\""+publicacaoInstance.month+"\"}"
						return bibtexTese
						}
				#end
				/**Fim Velocity**/
		}
	#end
	/**Fim Velocity**/
	
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