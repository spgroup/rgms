package rgms

class PdfController {

	PdfService pdfService
	
    def index = { lista ->
		response.contentType = "application/pdf"
		
		response.outputStream << pdfService.createPDF(lista)
		}
}
