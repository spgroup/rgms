import org.apache.shiro.crypto.hash.Sha256Hash

import rgms.authentication.Role
import rgms.member.Member
import rgms.member.MemberController
import rgms.member.MemberControllerMixin
import rgms.member.Record
import rgms.member.ResearchGroup
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
        }		
    }

    def destroy = {

    }

}