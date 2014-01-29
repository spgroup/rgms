package pages

import geb.Page
import org.apache.shiro.SecurityUtils
import org.apache.shiro.subject.Subject
import org.apache.shiro.util.ThreadContext

class LoginPage extends Page {
    def titleName = /${(new GetPageTitle()).getMessageServerLocale("user.login.title")}/
	static url = "auth/login"

    static at = {
        title ==~ titleName && rememberMe != null
    }

    static content = {
        rememberMe { $("form input#rememberMe") }
        readFlashMessage() { $("div.message").text() }
        readErrorsMessage() { $("div.errors").text() }
    }


    def getLink(String linkName) { $("div#status a", text: linkName) }

    def submitForm = { $("form input[type='submit']").click() }


    def fillLoginDataOnly(String username, String password) {
        $("form").username = username
        $("form").password = password
    }

    def fillLoginData(String l, String p) {
        fillLoginDataOnly(l,p)
        $("form").signIn().click()
    }

    public void login(step){
        // save old metaclass
        def registry = GroovySystem.metaClassRegistry
        step.oldMetaClass = registry.getMetaClass(SecurityUtils)
        registry.removeMetaClass(SecurityUtils)

        // Mock login
        def subject = [getPrincipal: { "admin" },
                isAuthenticated: { true }
        ] as Subject
        ThreadContext.put(ThreadContext.SECURITY_MANAGER_KEY,
                [getSubject: { subject } as SecurityManager])
        SecurityUtils.metaClass.static.getSubject = { subject }
    }


}
