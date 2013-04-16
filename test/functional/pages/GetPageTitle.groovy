package pages

import org.codehaus.groovy.grails.commons.ApplicationHolder

class GetPageTitle {
  static def messageSource = ApplicationHolder.application.mainContext.getBean('messageSource')
	
	String getMessage(String code) {
		messageSource.getMessage(code, null, "", request.getLocale());
 }

    String getMessageServerLocale(String code) {
        messageSource.getMessage(code, null, "default", Locale.getDefault())
    }
}
