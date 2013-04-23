package pages

class GetPageTitle {
  def messageSource
	
	String getMessage(String code) {
		messageSource.getMessage(code, null, "", request.getLocale());
 }
}
