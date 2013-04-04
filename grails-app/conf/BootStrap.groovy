import org.apache.shiro.crypto.hash.Sha256Hash

import rgms.authentication.Role
import rgms.member.Member
import rgms.member.MemberController
import rgms.member.MemberControllerMixin
import rgms.member.Record
import rgms.publication.Periodico
import rgms.member.ResearchGroup
import rgms.publication.Dissertacao;
import rgms.publication.ResearchLine
import rgms.publication.TechnicalReport

class BootStrap {

	def init = { servletContext ->

		def adminRole = Role.findByName("Administrator")

		if(!adminRole) {

			adminRole = new Role(name: 'Administrator')
			adminRole.addToPermissions("*:*")
			adminRole.save()
		}

		def admin = Member.findByUsername('admin')

		if(!admin) {

			admin = new Member(name:"Jefferson Almeida",username: 'admin', passwordHash: new Sha256Hash("adminadmin").toHex(),
					email:"jra@cin.ufpe.br", status:"MSc Student", enabled:true, university:"UFPE")


			adminRole.addToUsers(admin)
			adminRole.save()




			//#if($History)
			//feature record
			def hist = new Record(start:new Date(),status_H:"MSc Student")
			hist.save()
			admin.addToHistorics(hist)
			//#end

			admin.save()

			print("Instancia de Admin = "+Member.findByUsername('admin').toString())

			ResearchLine rl2 = new ResearchLine()
			rl2.setName("Teoria da informacao - Complexidade no espaco")
			rl2.setDescription("P=NP")
			rl2.save()


			ResearchLine rl3 = new ResearchLine()
			rl3.setName("Empirical Software Engineering")
			rl3.setDescription("We are investigating processes, methods, techniques and tools for supporting empirical studies in software engineering. The main objective is to develop a infrastructure that support researchers to define, plan, execute, analyze and store results of empirical studies in general. At this moment we call such structure Testbed")
			rl3.save()

			print("Instancia de Admin = "+Member.findByUsername('admin').toString())
			ResearchLine rl = new ResearchLine()
			rl.setName("Teoria da informacao - Complexidade no espaco")
			rl.setDescription("P=NP")
			rl.save()

			ResearchGroup r2 = new ResearchGroup()
			ResearchGroup r3 = new ResearchGroup()
			ResearchGroup r4 = new ResearchGroup()
			r2.name = "testehugo1"
			r2.description = "testehugo1"
			r3.name = "testehugo12"
			r3.description = "testehugo12"
			r4.name = "testehugo123"
			r4.description = "testehugo123"
			r2.save()
			r3.save()
			r4.save()

			Periodico p1 = new Periodico()
			p1.setJournal("Theoretical Computer Science")
			p1.setVolume(455)
			p1.setNumber(1)
			p1.setPages("2-30")
			p1.setTitle("A theory of software product line refinement")
			p1.setPublicationDate(new Date("12 October 2012"))
			p1.setFile("TCS.pdf")
			if( !p1.save() ) {
				p1.errors.each { println it }
			}

			TechnicalReport t1 = new TechnicalReport()
			t1.setTitle('Evaluating Natural Languages System')
			t1.setPublicationDate(new Date("13 November 2012"))
			t1.setInstitution('UFPE')
			t1.setFile("EVLS.pdf")
			if( !t1.save() ) {
				t1.errors.each { println it }
			}

			def dissertation = new Dissertacao(title: "Nova Dissertacao", publicationDate: new Date(2013, 03, 02),
					school: "UFPE", address: "Boa Viagem", ResearchLine: rl, file:"guiDissertacao.txt")

			dissertation.save()

			def dissertation2 = new Dissertacao(title: "Dissertacao Teste4", publicationDate: new Date(2013, 03, 02),
					school: "UFPE", address: "Boa Viagem", file:"gui3Dissertacao.txt")

			dissertation2.save()

		}
	}

	def destroy = {

	}

}
