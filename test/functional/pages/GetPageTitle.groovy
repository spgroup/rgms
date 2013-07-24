package pages

import grails.plugin.remotecontrol.RemoteControl

import org.springframework.context.i18n.LocaleContextHolder as LCH
import org.codehaus.groovy.grails.commons.ApplicationHolder
class GetPageTitle {

RemoteControl remoteControl = new RemoteControl()

	String msg(String msgKey, args = null, locale = new Locale ("pt", "BR")) {
	//String msg(String msgKey, args = null, locale = LCH.getLocale()) {
		println locale
		if (args != null) {
			args = args as Object[]
		}
		return remoteControl.exec {
			ctx.messageSource.getMessage(msgKey, args, locale)
		}
	}

  static def messageSource = ApplicationHolder.application.mainContext.getBean('messageSource')
	
	String getMessage(String code) {
		messageSource.getMessage(code, null, "", request.getLocale());
 }

    String getMessageServerLocale(String code) {
        messageSource.getMessage(code, null, "default", Locale.getDefault())
    }
}
