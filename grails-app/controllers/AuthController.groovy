import org.apache.shiro.SecurityUtils
import org.apache.shiro.authc.AuthenticationException
import org.apache.shiro.authc.UsernamePasswordToken
import org.apache.shiro.web.util.SavedRequest
import org.apache.shiro.web.util.WebUtils
import java.security.SecureRandom
import org.apache.shiro.crypto.hash.Sha256Hash

class AuthController {
    def shiroSecurityManager

    def index = { redirect(action: "login", params: params) }

    def login = {
        return [ username: params.username, rememberMe: (params.rememberMe != null), targetUri: params.targetUri ]
    }

    def signIn = {
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
        if (savedRequest) {
            targetUri = savedRequest.requestURI - request.contextPath
            if (savedRequest.queryString) targetUri = targetUri + '?' + savedRequest.queryString
        }
        
        try{
            // Perform the actual login. An AuthenticationException
            // will be thrown if the username is unrecognised or the
            // password is incorrect.
            SecurityUtils.subject.login(authToken)
			def user = ShiroUser.findByUsername(params.username)
			if (user?.passwordChangeRequiredOnNextLogon) {
				log.info "Redirecting to '${targetUri}'."
				redirect(action: newPassword)
			} else {
				log.info "Redirecting to '${targetUri}'."
				redirect(uri: targetUri)
			}
        }
        catch (AuthenticationException ex){
            // Authentication failed, so display the appropriate message
            // on the login page.
            log.info "Authentication failure for user '${params.username}'."
            flash.message = message(code: "login.failed")

            // Keep the username and "remember me" setting so that the
            // user doesn't have to enter them again.
            def m = [ username: params.username ]
            if (params.rememberMe) {
                m["rememberMe"] = true
            }

            // Remember the target URI too.
            if (params.targetUri) {
                m["targetUri"] = params.targetUri
            }

            // Now redirect back to the login page.
            redirect(action: "login", params: m)
        }
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
			def resetRequest = (params.token ? ShiroPasswordResetRequest.findByToken(params.token) : null)
			def connectedUser = SecurityUtils.subject?.principal
			def user = resetRequest?.user ?: (connectedUser ? ShiroUser.findByUsername(connectedUser) : null)
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
    def doUpdatePassword = {
		if (params.password1!=params.password2) {
			flash.message = "Please enter same passwords."
			flash.status = "error"
			redirect(action:'updatePassword')
		} else {
			def user = ShiroUser.findByUsername(SecurityUtils.subject?.principal)
			if (user) {
				if (user.passwordHash == new Sha256Hash(params.oldpassword).toHex()){
					user.passwordHash = new Sha256Hash(params.password1).toHex()
					if (user.save()){
						flash.message = "Password successfully updated"
						redirect(uri:'/')
					} else {
						flash.message = "Password update failed."
						flash.status = "error"
						redirect(action:'updatePassword')
					}
				} else {
					flash.message = "Incorrect old password ."
					flash.status = "error"
					redirect(action:'updatePassword')
				}
			} else {
				flash.message = "Unknown user."
				flash.status = "error"
				redirect(action:'updatePassword')
			}
		}
	}
    def resetPassword = {
        if (params.id){
			def resetRequest = ShiroPasswordResetRequest.findByToken(params.id)
			if (resetRequest) {
				[resetRequest:resetRequest]
			} else {
				flash.message = "Not a valid request."
				redirect(uri:'/')
			}
		}
    }
    def sendPasswordResetRequest = {
		def shiroUserInstance = (params.email ? ShiroUser.findByEmail(params.email) : (params.username ? ShiroUser.findByUsername(params.username) : null))
		if (shiroUserInstance) {
			flash.message = "An email is being sent to you with instructions on how to reset your password."
			def resetRequest = new ShiroPasswordResetRequest(user:shiroUserInstance,requestDate : new Date(),token:new BigInteger(130, new SecureRandom()).toString(32)).save(failOnError:true)
			def mailSender = grailsApplication.config.grails.mail.username
			sendMail {
			   to shiroUserInstance.email
			   from mailSender
			   subject "Reset your password"
			   body "Hello ${shiroUserInstance.firstName} ${shiroUserInstance.lastName},\n\nYou have requested resetting your password. Please ignore this message if it's not you who have made the request.\n\nIn order to reset your password, please follow this link :\n\n ${createLink(absolute:true,controller:'auth',action:'resetPassword',id:resetRequest.token)}\n\nBest Regards".toString()
			}
		} else {
			flash.message = "No such user, please try again."
		}
		redirect(uri:'/')
    }
}
