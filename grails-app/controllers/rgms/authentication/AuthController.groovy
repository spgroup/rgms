package rgms.authentication

import org.apache.shiro.SecurityUtils
import org.apache.shiro.authc.AuthenticationException
import org.apache.shiro.authc.UsernamePasswordToken
import org.apache.shiro.web.util.SavedRequest
import org.apache.shiro.web.util.WebUtils
import rgms.member.Member

import java.security.SecureRandom
import org.apache.shiro.crypto.hash.Sha256Hash
import org.apache.shiro.web.util.WebUtils
import rgms.EmailService

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
        User user = User.findByUsername(params.username);
        if(user == null) {
            flash.message = message(code: "login.failed")
            redirect(uri: "/auth/login")
            return
        }
        Member member = user.author;
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
            if(!user.enabled){
                render "Please wait the administrator to unlock your access to system.\n\nThanks."
                return
            }
            //("ENTROU NO TRY")

            // print(member)
            log.info "Redirecting to '${targetUri}'."
            if (user.passwordChangeRequiredOnNextLogon) {
                redirect(action: newPassword)
            } else {
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
        if (checkPasswordWithConfirmation(params.password1,params.password2))
            redirect(action:'resetPassword',id:params.token)
        else {
            def resetRequest = (params.token ? PasswordResetRequest.findByToken(params.token) : null)
            def connectedUser = SecurityUtils.subject?.principal
            def user = resetRequest?.user ?: (connectedUser ? User.findByUsername(connectedUser) : null)
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
            def user = User.findByUsername(SecurityUtils.subject?.principal)
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
        def user = (params.username ? Member.findByUsername(params.username) : null);
        def memberInstance = null;
        if (user){
            memberInstance = user.author;
        }else{
            memberInstance = (params.email ? Member.findByEmail(params.email) : null);
            user = User.findByAuthor(memberInstance);
        }

        if (memberInstance) {
            flash.message = "An email is being sent to you with instructions on how to reset your password."
            def email = memberInstance.email
            def mailSender = grailsApplication.config.grails.mail.username
            def title = message(code: 'mail.title.reset')
            def resetRequest = new PasswordResetRequest(user:memberInstance,requestDate : new Date(),token:new BigInteger(130, new SecureRandom()).toString(32)).save(failOnError:true)
            def content = message(code: 'mail.body.reset', args: [memberInstance.name, createLink(absolute:true,controller:'auth',action:'resetPassword',id:resetRequest.token)])

            EmailService emailService = new EmailService()
            emailService.sendEmail(email, mailSender, title, content)

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

        if ( checkPasswordWithConfirmation(params.password1 , params.password2))
            return [memberInstance: new Member(params), userInstance: new User(params)]

        if (!grailsApplication.config.grails.mail.username) {
            throw new RuntimeException(message(code: 'mail.plugin.not.configured', 'default' : 'Mail plugin not configured'))
        }

        if(params.username == null){
            //  print("params NULL")
            return [memberInstance: new Member(params), userInstance: new User(params)]
        }

        def enabled = false

        def pwdHash = new Sha256Hash(params.password1).toHex()
        def userInstance = new User(username:params.username, passwordHash: pwdHash, passwordChangeRequiredOnNextLogon:false, enabled:enabled)
        def memberInstance = new Member(name:params.name, status:params.status, email:params.email, university:params.university, facebook_id:params.facebook_id, access_token:params.access_token)

        def name = memberInstance?.name
        def emailAddress = memberInstance?.email

        if (!memberInstance.save(flush: true)) {
            render(view: "register", model: [memberInstance: memberInstance])
            memberInstance.errors.each{
                  println it
            }
            return
        }else{
            userInstance.author = memberInstance;
            if (!userInstance.save(flush: true)){
                //flash.message = "Error creating user"
                render(view: "register", model: [memberInstance: memberInstance])
                userInstance.errors.each{
                      println it
                }
                return
            }
        }

//        sendMail {
//            to memberInstance.email
//            from grailsApplication.config.grails.mail.username
//            subject "[GRMS] Your account was successfully created!"
//            body "Hello ${memberInstance.firstName} ${memberInstance.lastName},\n\nYour account was successfully created!\n\nHere is your username: ${username} and password: ${password}\n\n${createLink(absolute:true,uri:'/')}\n\nBest Regards,\nAdministrator of the Research Group Management System".toString()
//        }

//        flash.message = message(code: 'default.created.message', args: [message(code: 'member.label', default: 'Member'), memberInstance.id])
//        redirect(action: "index", id: memberInstance.id)

        flash.message = message(code: 'default.created.message', args: [message(code: 'user.label', default: 'User'), memberInstance.name])
        redirect(uri: "/auth/login")
    }

    private sendRegistrationMailToAdmin(name) {
        def Admin = Member.findAllByName("Administrator")
        def emailAdmin = Admin?.email
        if (emailAdmin != null && !emailAdmin.empty) {
            // print("Email Admin : " + emailAdmin)

            def emailFrom = grailsApplication.config.grails.mail.username
            def subject = message(code: 'mail.title.authenticate')
            def content = message(code: 'mail.body.authenticade', args: [name, emailAddress, createLink(absolute: true, uri: '/member/list')])

            EmailService emailService = new EmailService();
            emailService.sendEmail(emailAdmin, emailFrom, subject, content)
        }
    }

    private checkPasswordWithConfirmation(p1, p2){
        if (p1!=p2) {
            flash.message = "Please enter same passwords."
            flash.status = "error"
            params.password1 = ""
            params.password2 = ""
        }
        return p1!=p2;
    }
}
