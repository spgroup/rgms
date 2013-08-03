package rgms.authentication

import org.apache.shiro.SecurityUtils
import org.apache.shiro.authc.AuthenticationException
import org.apache.shiro.authc.UsernamePasswordToken
import org.apache.shiro.web.util.SavedRequest
import org.apache.shiro.web.util.WebUtils
import java.security.SecureRandom
import org.apache.shiro.crypto.hash.Sha256Hash

import rgms.member.Member;
import rgms.member.PasswordResetRequest;

import java.security.SecureRandom

class AuthController {
    def shiroSecurityManager

    def index = {
        if(SecurityUtils.subject?.principal != null)
            render(view:  "/initial")
        else
            redirect(action: "login", params: params)
    }

    def login = {
        return [ username: params.username, rememberMe: (params.rememberMe != null), targetUri: params.targetUri ]
    }

    def signIn = {
        
//        def subject = SecurityUtils.getSubject();
        Member member = Member.findByUsername(params.username)
        if(member == null) {
            flash.message = message(code: "login.failed")
            redirect(uri: "/auth/login")
            return
        }
     //   print("ENTROU no signIn\nEnabled == "+member.enabled)


        
        def authToken = new UsernamePasswordToken(params.username, params.password as String)

        // Support for "remember me"
        if (params.rememberMe) {
            authToken.rememberMe = true
        }
        
        // If a controller redirected to this page, redirect back
        // to it. Otherwise redirect to the root URI.
        def targetUri = params.targetUri ?: "/"
        
        // Handle requests saved by Shiro filters.
        def savedRequest = WebUtils.getSavedRequest(request)
        if (savedRequest) {    // print("deu saved request: " + savedRequest.toString())
            targetUri = savedRequest.requestURI - request.contextPath
            if (savedRequest.queryString) targetUri = targetUri + '?' + savedRequest.queryString
        }
        
        try{
            // Perform the actual login. An AuthenticationException
            // will be thrown if the username is unrecognised or the
            // password is incorrect.
            SecurityUtils.subject.login(authToken)
            if(!member.enabled){
                render "Please wait the administrator to unlock your access to system.\n\nThanks."
                return
            }
            //("ENTROU NO TRY")
            
           // print(member)
            
            if (member.passwordChangeRequiredOnNextLogon) {
                log.info "Redirecting to '${targetUri}'."
                redirect(action: newPassword)
            } else {
                log.info "Redirecting to '${targetUri}'."
                render(view: "/initial")
//                redirect(uri: targetUri)
            }
        }
        catch (AuthenticationException ex){
            authErrorHandling(ex)
        }
    }

    private authErrorHandling(AuthenticationException ex) {
        // Authentication failed, so display the appropriate message
        // on the login page.
        log.info "Authentication failure for user '${params.username}'." + " Error:" + ex.toString()
        flash.message = message(code: "login.failed")

        // Keep the username and "remember me" setting so that the
        // user doesn't have to enter them again.
        def m = [username: params.username]
        if (params.rememberMe) {
            m["rememberMe"] = true
        }

        // Remember the target URI too.
        if (params.targetUri) {
            m["targetUri"] = params.targetUri
        }

        log.info "Authentication failure for user '${params.username}'." + " Error:" + ex.toString()
        flash.message = message(code: "login.failed")
        // Now redirect back to the login page.
        redirect(uri: "/auth/login")
//            redirect(action: "login", params: m) //action: "login"
    }

    def signOut = {
        // Log the user out of the application.
        SecurityUtils.subject?.logout()

        // For now, redirect back to the home page.
        redirect(uri: "/")
    }

    def unauthorized = {
        render "You do not have permission to access this page."
    }
    def lostPassword = {
    }
    def updatePassword = {
    }
    def newPassword = {
        render(view:'resetPassword')
    }
    def doResetPassword = {
        if (params.password1!=params.password2) {
            flash.message = "Please enter same passwords."
            flash.status = "error"
            redirect(action:'resetPassword',id:params.token)
        } else {
            def resetRequest = (params.token ? PasswordResetRequest.findByToken(params.token) : null)
            def connectedUser = SecurityUtils.subject?.principal
            def user = resetRequest?.user ?: (connectedUser ? Member.findByUsername(connectedUser) : null)
            if (user) {
                user.passwordHash = new Sha256Hash(params.password1).toHex()
                user.passwordChangeRequiredOnNextLogon = false
                if (user.save()){
                    resetRequest?.delete()
                    flash.message = "Password successfully updated"
                    redirect(uri:'/')
                }
            } else {
                flash.status = "error"
                flash.message = "Unknown user"
                redirect(action:'resetPassword',id:params.token)
            }
        }
    }
    def putErrorAndRedirect(message,status,action) {
        flash.message = message
        flash.status = status
        redirect(action: action)
    }
    def doUpdatePassword = {
        if (params.password1!=params.password2) {
            putErrorAndRedirect("Please enter same passwords.","error",'updatePassword')
        } else {
            def user = Member.findByUsername(SecurityUtils.subject?.principal)
            if (user) {
                if (user.passwordHash == new Sha256Hash(params.oldpassword).toHex()){
                    user.passwordHash = new Sha256Hash(params.password1).toHex()
                    if (user.save()){
                        flash.message = "Password successfully updated"
                        redirect(uri:'/')
                    } else {
                        putErrorAndRedirect("Password update failed.","error",'updatePassword')
                    }
                } else {
                    putErrorAndRedirect("Incorrect old password .","error",'updatePassword')
                }
            } else {
                putErrorAndRedirect("Unknown user.","error",'updatePassword')
            }
        }
    }
    def resetPassword = {
        if (params.id){
            def resetRequest = PasswordResetRequest.findByToken(params.id)
            if (resetRequest) {
                [resetRequest:resetRequest]
            } else {
                flash.message = "Not a valid request."
                redirect(uri:'/')
            }
        }
    }
    def sendPasswordResetRequest = {
        def memberInstance = (params.email ? Member.findByEmail(params.email) : (params.username ? Member.findByUsername(params.username) : null))
        if (memberInstance) {
            flash.message = "An email is being sent to you with instructions on how to reset your password."
            def resetRequest = new PasswordResetRequest(user:memberInstance,requestDate : new Date(),token:new BigInteger(130, new SecureRandom()).toString(32)).save(failOnError:true)
            def mailSender = grailsApplication.config.grails.mail.username
            sendMail {
                to memberInstance.email
                from mailSender
                subject "[GRMS] Reset your password"
                body "Hello ${memberInstance.name},\n\nYou have requested resetting your password. Please ignore this message if it's not you who have made the request.\n\nIn order to reset your password, please follow this link :\n\n ${createLink(absolute:true,controller:'auth',action:'resetPassword',id:resetRequest.token)}\n\nBest Regards".toString()
            }
        } else {
            flash.message = "No such user, please try again."
        }
        redirect(uri:'/')
    }
    
//    def doRegister = {
//        return [name: params.name, username: params.username, passwordtargetUri: params.targetUri ]
//    }
    
    def register = {

//#if($contextualInformation)
        /**
         * @author penc
         */
         params.university   = params.university ?: grailsApplication.getConfig().getProperty("defaultUniversity");
//#end

        //("ENTROU no register")

        if (params.password1 != params.password2) {
            flash.message = "Please enter same passwords."
            flash.status = "error"
            params.password1 = ""
            params.password2 = ""
            return [memberInstance: new Member(params)]
        }

        def memberInstance = new Member(params)
        
        if (!grailsApplication.config.grails.mail.username) {
            throw new RuntimeException(message(code: 'mail.plugin.not.configured', 'default' : 'Mail plugin not configured'))
        }
        
        if(params.username == null){
          //  print("params NULL")
            return [memberInstance: memberInstance]
        }

        def enabled = false
        
        def pwdHash = new Sha256Hash(params.password1).toHex()
		
        memberInstance = new Member(username:params.username,name:params.name, status:params.status, passwordHash: pwdHash, email:params.email, passwordChangeRequiredOnNextLogon:false, enabled:enabled, university:params.university, facebook_id:params.facebook_id, access_token:params.access_token)
        
		def name = memberInstance?.name
        def emailAddress = memberInstance?.email
        
        if (!memberInstance.save(flush: true)) {
            //flash.message = "Error creating user"
            render(view: "register", model: [memberInstance: memberInstance])
            memberInstance.errors.each{
              //  println it
            }
            return
        }

//        sendMail {
//            to memberInstance.email
//            from grailsApplication.config.grails.mail.username
//            subject "[GRMS] Your account was successfully created!"
//            body "Hello ${memberInstance.firstName} ${memberInstance.lastName},\n\nYour account was successfully created!\n\nHere is your username: ${username} and password: ${password}\n\n${createLink(absolute:true,uri:'/')}\n\nBest Regards,\nAdministrator of the Research Group Management System".toString()
//        }
        
//        flash.message = message(code: 'default.created.message', args: [message(code: 'member.label', default: 'Member'), memberInstance.id])
//        redirect(action: "index", id: memberInstance.id)

        flash.message = "User successfully created";
        render(view: "register")
    }

    private sendRegistrationMailToAdmin(name) {
        def Admin = Member.findAllByName("Administrator")
        def emailAdmin = Admin?.email
        if (emailAdmin != null && !emailAdmin.empty) {
           // print("Email Admin : " + emailAdmin)

            sendMail {
                to emailAdmin
                from grailsApplication.config.grails.mail.username
                subject "[GRMS] You received a request to authenticate an account."
                body "Hello Administrator,\n\nYou received a request to authenticate an account.\n\nWho requested was ${name}. His/Her email address is ${emailAddress}\n\n${createLink(absolute: true, uri: '/member/list')}\n\nBest Regards,\nResearch Group Management System".toString()
            }
        }
    }
}
