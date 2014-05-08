import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.remote.DesiredCapabilities

autoClearCookies = false

def forcedLocale = "en"

driver = {
	//DesiredCapabilities capabilities = new DesiredCapabilities()
	// available capabilities can be find in %USER_HOME%\AppData\Local\Google\Chrome\User Data\default\preferences
	//def prefs = ["intl.accept_languages":forcedLocale] // Map
	//capabilities.setCapability("chrome.prefs",prefs);
	//def driver = { new ChromeDriver(capabilities) }
    def driver = new ChromeDriver()
    autoClearCookies = false
    driver
}