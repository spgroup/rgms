package steps

import java.util.Date

import rgms.publication.Periodico
import rgms.publication.PeriodicoController
import rgms.member.Member
import rgms.member.MemberController
import rgms.member.MembershipController
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
	
	static researchgroups = [
		[name: "SWPRG",
			description: "SW Productivity Research Group",
			childOf: null]
		,
		[name: "taes",
			description: "grupo de estudos",
			childOf: null]
]
	
	static memberships = [
		[member: (new Member(members[0])),
			researchGroup: (new ResearchGroup(researchgroups[0])),
			dateJoined: (new Date(2012, 03, 01)),
			dateLeft: (new Date(2012, 06, 01))]
		,
		[member: (new Member(members[1])),
			researchGroup: (new ResearchGroup(researchgroups[0])),
			dateJoined: (new Date(2012, 03, 01)),
			dateLeft: (new Date(2012, 06, 01))]
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

	static public def findMembershipByResearchGroupName(String groupname) {
		memberships.find { membership ->
			membership.researchGroup.name == groupname
		}
	}

	static public def findResearchGroupByGroupName(String groupname) {
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
		cont.params << TestDataAndOperations.findResearchGroupByGroupName(groupname)
		cont.request.setContent(new byte[1000]) // Could also vary the request content.
		cont.create()
		cont.save()
		cont.response.reset()
	}
	
	static public void createMembership(String username, String rgroup, String date1, String date2) {
		//TODO Deveria pegar os dados dos parametros, mas
		//		esta dando problema na criacao. Entao para
		//		simplificar o entendimento do problema,
		//		os valores estão fixos. O problema não
		//		está nos parâmetros passados.
		
		def cont = new MembershipController()
		
		cont.params << [member: (new Member(members[0])),
						researchGroup: (new ResearchGroup(name: "taes", description: "grupo de estudos", childOf: null)),
						dateJoined: (new Date(2012, 03, 01)), dateLeft: (new Date(2012, 06, 01))]
		cont.request.setContent(new byte[1000]) // Could also vary the request content.
		cont.create()
		cont.save()
		cont.response.reset()
	}
	
	static public void deleteMembership(String username, String rgroup, String date1, String date2) {
		java.text.DateFormat df = new java.text.SimpleDateFormat("dd-MM-yyyy");
		def cont = new MembershipController()
		def identificador = Membership.findByMemberAndResearchGroupAndDateJoinedAndDateLeft(Member.findByUsername(username),
										ResearchGroup.findByName(rgroup), df.parse(date1), df.parse(date2)).id
		cont.params << [id: identificador]
		cont.request.setContent(new byte[1000]) // Could also vary the request content.
		cont.delete()
		//cont.save()
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