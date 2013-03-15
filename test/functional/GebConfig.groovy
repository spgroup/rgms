import org.openqa.selenium.chrome.ChromeDriver

autoClearCookies = false

driver = {
    def driver = new ChromeDriver()
    autoClearCookies = false
    driver
}