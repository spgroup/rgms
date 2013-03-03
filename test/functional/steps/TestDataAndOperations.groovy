package steps

import java.text.SimpleDateFormat

import rgms.member.Member
import rgms.member.MembershipController
import rgms.member.ResearchGroup
import rgms.member.ResearchGroupController
import rgms.publication.Periodico
import rgms.publication.PeriodicoController
import rgms.publication.PublicationController

class TestDataAndOperations {
    static articles = [
            [journal: "Theoretical Computer Science", volume: 455, number: 1, pages: "2-30",
                    title: "A theory of software product line refinement",
                    publicationDate: (new Date("12 October 2012"))],
            [journal: "Science of Computer Programming", volume: 455, pages: "2-30",
                    title: "Algebraic reasoning for object-oriented programming",
                    publicationDate: (new Date("12 October 2012"))]
    ]

    static public def findByTitle(String title) {
        articles.find { article ->
            article.title == title
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

    static public void createArticle(String title, filename) {
        def cont = new PeriodicoController()
        def date = new Date()
        cont.params << TestDataAndOperations.findByTitle(title) << [file: filename]
        cont.request.setContent(new byte[1000]) // Could also vary the request content.
        cont.create()
        cont.save()
        cont.response.reset()
    }
	
	static public void createResearchGroup(String name, description) {
		def researchGroupController = new ResearchGroupController()
		researchGroupController.params << [name: name] << [description: description]
		researchGroupController.request.setContent(new byte[1000]) // Could also vary the request content.
		researchGroupController.create()
		researchGroupController.save()
		researchGroupController.response.reset()
	}

	static public void createResearchGroupMembershipWithMember(String name, String memberUsername) {
		def researchGroup = ResearchGroup.findByName(name) 
		def member = Member.findByUsername(memberUsername)
		def memberShipController = new MembershipController()
		memberShipController.params << [researchGroup: researchGroup] << [member: member]
		memberShipController.create()
		memberShipController.save()
		memberShipController.response.reset()
	}

	static public void createMemberPublication(String username, String title, String date) {
		def member = Member.findByUsername(username)
		def publicationController =	 new PublicationController()
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");  
		java.sql.Date dateFormat = new java.sql.Date(format.parse(date).getTime());
		publicationController.params << [title: title] << [publicationDate: date]
		publicationController.create()
		publicationController.save()
		publicationController.response.reset()
	}

    static void clearArticles() {
        Periodico.findAll()*.delete flush: true // Could also delete the created files.
    }
}