import org.apache.shiro.crypto.hash.Sha256Hash

import rgms.authentication.Role
import rgms.member.Member
import rgms.member.MemberController
import rgms.member.MemberControllerMixin
import rgms.member.Record
import rgms.publication.ResearchLine

class BootStrap {

    def init = { servletContext ->
      
        def adminRole = Role.findByName("Administrator")

        if(!adminRole){

            adminRole = new Role(name: 'Administrator')
            adminRole.addToPermissions("*:*")
            adminRole.save()
        }

        def admin = Member.findByUsername('admin')

        if(!admin){

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
        }

		ResearchLine rl = new ResearchLine()
		rl.setName("Empirical Software Engineering")
		rl.setDescription("We are investigating processes, methods, techniques and tools for supporting empirical studies in software engineering. The main objective is to develop a infrastructure that support researchers to define, plan, execute, analyze and store results of empirical studies in general. At this moment we call such structure Testbed")
		rl.save()
    }

    def destroy = {

    }

}