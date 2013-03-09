package steps

import java.util.Date

import rgms.publication.Periodico
import rgms.publication.PeriodicoController
import rgms.member.Member
import rgms.member.MemberController
import rgms.member.ResearchGroup
import rgms.member.ResearchGroupController

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
		[name: "Rodolfo Ferraz", username: "usernametest", email: "rodolfofake@gmail.com",
				status: "Graduate Student", university: "UFPE", enabled: true
				],
		[name: "Rebeca Souza", username: "rebecasouza", email: "rsa2fake@cin.ufpe.br",
				status: "Graduate Student", university: "UFPE", enabled: true
				]
]
	
	static memberships = [
		[member: (new Member(members[0])),
			researchGroup: (new ResearchGroup(name: "taes", description: "grupo de estudos", childOf: null)),
			//researchGroup: (new ResearchGroup(researchgroups[0])),
			dateJoined: (new Date("12 October 2012")),
			dateLeft: (new Date("20 October 2012"))]
		,
		[member: (new Member(members[1])),
			researchGroup: (new ResearchGroup(name: "taes", description: "grupo de estudos", childOf: null)),
			//researchGroup: (new ResearchGroup(researchgroups[0])),
			dateJoined: (new Date("12 October 2012")),
			dateLeft: (new Date("20 October 2012"))]
		
]
	static researchgroups = [
		[name: "SWPRG",
			description: "SW Productivity Research Group",
			childOf: null]
		,
		[name: "taes",
			description: "grupo de estudos",
			childOf: null]
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

	static public def findByResearchGroupAndDateJoinedAndDateLeft(String username, String rgroup, String date1, String date2) {
		memberships.find { membership ->
			membership.member == username &&
			membership.researchGroup == rgroup &&
			membership.dateJoined == date1 &&
			membership.dateLeft == date2
		}
	}

	static public def findByGroupName(String groupname) {
		researchgroups.find { group ->
			group.name == groupname
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
	
	static public void createResearchGroup(String groupname) {
		def cont = new ResearchGroupController()
		cont.params << TestDataAndOperations.findByGroupName(groupname)
		cont.request.setContent(new byte[1000]) // Could also vary the request content.
		cont.create()
		cont.save()
		cont.response.reset()
	}
	
	static public void createMembership(String username, String rgroup, String date1, String date2) {
		def cont = new MemberController()
		
		cont.params << TestDataAndOperations.findByResearchGroupAndDateJoinedAndDateLeft(rgroup, date1, date2);
		cont.request.setContent(new byte[1000]) // Could also vary the request content.
		cont.create()
		cont.save()
		cont.response.reset()
	}
	
	static public void deleteMembership(String username, String rgroup, String date1, String date2) {
		def cont = new MemberController()
		
		cont.params << TestDataAndOperations.findByResearchGroupAndDateJoinedAndDateLeft(rgroup, date1, date2);
		cont.request.setContent(new byte[1000]) // Could also vary the request content.
		cont.delete()
		cont.save()
		cont.response.reset()
	}
	
	static public void deleteMember(String username) {
		def cont = new MemberController()
		def identificador = Member.findByUsername(username).id
		cont.params << [id: identificador]
		cont.request.setContent(new byte[1000]) // Could also vary the request content.
		cont.delete()
		//cont.save()
		cont.response.reset()
	}
	
    static void clearArticles() {
        Periodico.findAll()*.delete flush: true // Could also delete the created files.
    }
}