package steps

import rgms.publication.Periodico
import rgms.publication.PeriodicoController
import rgms.member.Member
import rgms.member.MemberController

class TestDataAndOperations {
    static articles = [
            [journal: "Theoretical Computer Science", volume: 455, number: 1, pages: "2-30",
                    title: "A theory of software product line refinement",
                    publicationDate: (new Date("12 October 2012"))],
            [journal: "Science of Computer Programming", volume: 455, pages: "2-30",
                    title: "Algebraic reasoning for object-oriented programming",
                    publicationDate: (new Date("12 October 2012"))]
    ]

	
	static members = [
		[name: "Rodolfo Ferraz", username: "usernametest", email: "rcaferraz@gmail.com",
				status: "Graduate Student", university: "UFPE", enabled: true
				],
		[name: "Rebeca Souza", username: "rebecasouza", email: "rsa2@cin.ufpe.br",
				status: "Graduate Student", university: "UFPE", enabled: true
				]
]

    static public def findByTitle(String title) {
        articles.find { article ->
            article.title == title
        }
    }
	
	static public def findByUsername(String username) {
		members.find { member ->
			member.username == username
		}
	}


    static public boolean compatibleTo(article, title) {
        def testarticle = findByTitle(title)
        def compatible = false
        if (testarticle == null && article == null) {
            compatible = true
        } else if (testarticle != null && article != null) {
            compatible = true
            testarticle.each { key, data ->
                compatible = compatible && (article."$key" == data)
            }
        }
        return compatible
    }
	
	static public boolean memberCompatibleTo(member, username) {
		def testmember = findByUsername(username)
		def compatible = false
		if (testmember == null && member == null) {
			compatible = true
		} else if (testmember != null && member != null) {
			compatible = true
			testmember.each { key, data ->
				compatible = compatible && (member."$key" == data)
			}
		}
		return compatible
	}

    static public void createArticle(String title, filename) {
        def cont = new PeriodicoController()
        def date = new Date()
        cont.params << TestDataAndOperations.findByTitle(title) << [file: filename]
        cont.request.setContent(new byte[1000]) // Could also vary the request content.
        cont.create()
        cont.save()
        cont.response.reset()
    }

	static public void createMember(String username) {
		def cont = new MemberController()
		
		cont.params << TestDataAndOperations.findByUsername(username)
		cont.request.setContent(new byte[1000]) // Could also vary the request content.
		cont.create()
		cont.save()
		cont.response.reset()
	}

	
    static void clearArticles() {
        Periodico.findAll()*.delete flush: true // Could also delete the created files.
    }
}