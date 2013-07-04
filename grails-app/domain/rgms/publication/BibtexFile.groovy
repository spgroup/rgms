package rgms.publication

import java.util.Collection;

import java.io.BufferedReader
import java.io.File;
import java.io.FileInputStream
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader
import java.io.Reader;
import java.util.ArrayList
import java.util.List

import org.jbibtex.BibTeXEntry;
import org.jbibtex.Value;
import org.jbibtex.BibTeXDatabase;
import org.jbibtex.BibTeXEntry;
import org.jbibtex.BibTeXParser;
import org.jbibtex.BibTeXString;
import org.jbibtex.Key;
import org.jbibtex.ParseException;

import rgms.member.Member

/**
 * 
 * @author Diogo Vinícius
 *
 */
class BibtexFile {

	List<Publication> publications = new ArrayList<Publication>()
	Member member
	
	public BibtexFile(file) {
		publications = BibtexParse.generatePublications(file)
	}
	
	public List<Publication> getPublications() {
		return this.publications
	}
	
	public List getPublications(Class clazz) {
		List publicationsFiltered = new ArrayList()
		for (Publication publication : this.getPublications()) {
			if (publication.getClass().getName().equals(clazz.getName())) {
				publicationsFiltered.add(publication)
			}
		}
		return publicationsFiltered
	}
	
}
