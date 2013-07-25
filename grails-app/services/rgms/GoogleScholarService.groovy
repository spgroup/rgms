package rgms

import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method
import rgms.publication.Publication

class GoogleScholarService {

    static def findCitations(List<Publication> publications) {
        for (publication in publications) {
            String publicationTitle = (publication.title).replace(" ", "+")
            String result
            def http = new HTTPBuilder()
            http.request('http://scholar.google.com.br/', Method.GET, ContentType.TEXT) { req ->
                uri.path = '/scholar'
                uri.query = [hl: "pt-BR", q: publicationTitle]
                headers.'User-Agent' = "Mozilla/5.0 Firefox/3.0.4"
                headers.Accept = 'application/json'

                response.success = { resp, reader ->
                    assert resp.statusLine.statusCode == 200
                    result = reader.text
                }

                response.'404' = {
                    println 'Not found'
                }
            }
            Publication.metaClass.citations = countCitations(result)
        }
    }

    private static String countCitations(String result) {
        String citations = ""
        try {
            def allCitations = result.split("Citado por")
            String wantedCitation = allCitations[1]
            citations = (wantedCitation.split("<"))[0]
            return citations
        } catch (e) {
            citations = "N/E"
            return citations
        }
    }
}
