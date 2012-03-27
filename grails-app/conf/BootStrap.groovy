import org.apache.shiro.crypto.hash.Sha256Hash
import rgms.*

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

            admin = new Member(firstName:"Administrator", lastName:"User",
                username: 'admin', passwordHash: new Sha256Hash("adminadmin").toHex(),
                email:"jccmelo@gmail.com", status:"MSc Student", enabled:true)
            
            admin.save()
            adminRole.addToUsers(admin)
            adminRole.save()
            
            //feature record
            def hist = new Record(start:new Date(),status_H:"MSc Student")
            hist.save()
            
            admin.addToHistorics(hist)
            admin.save()
            
            print("Instancia de Admin = "+Member.findByUsername('admin').toString())
        }

    }

    def destroy = {

    }

}