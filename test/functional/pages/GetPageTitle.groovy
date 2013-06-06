package pages

import grails.plugin.remotecontrol.RemoteControl

class GetPageTitle {

	RemoteControl remoteControl = new RemoteControl()

	String msg(String msgKey, args = null, locale = new Locale ("en", "US")) {
		if (args != null) {
			args = args as Object[]
		}
		return remoteControl.exec {
			ctx.messageSource.getMessage(msgKey, args, locale)
		}
	}
	def messageSource

	String getMessage(String code) {
		messageSource.getMessage(code, null, "", request.getLocale());
	}
}