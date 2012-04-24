package rgms

import java.io.ByteArrayOutputStream;

import java.io.FileNotFoundException;

import java.io.IOException;

 

import com.lowagie.text.Document;

import com.lowagie.text.DocumentException;

import com.lowagie.text.Paragraph;

import com.lowagie.text.pdf.PdfWriter;

 

class PdfService {

     

      boolean transactional = false

 

      def createPDF = { lista ->

 

            ByteArrayOutputStream b = null;

 

            Document document = new Document();

            try {

                  b = new ByteArrayOutputStream();

                  PdfWriter.getInstance(document, b);

                  document.open();
				  
				  document.add(new Paragraph("Gerando BibTex!\n\n"));
				  
				  for(item in lista){
					  document.add(new Paragraph(item.bibTex+"\n\n"));
					  }

            } catch (DocumentException de) {

                  System.err.println(de.getMessage());

            }

 

            document.close();

 

            byte[] pdf = b.toByteArray();

 

            return pdf;

      }

}