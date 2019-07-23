package chatBot.AutoMate;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;



/**
 * Hello world!
 *
 */
public class App 
{
	public WebDriver driver;
	String gecoDriverPath;
	@Parameters("browser")
	@BeforeMethod
	public void setup(@Optional("chrome") String browser, Method caller) {
		

			boolean isDebug = java.lang.management.ManagementFactory
					.getRuntimeMXBean().getInputArguments().toString()
					.indexOf("-agentlib:jdwp") > 0;
			try {
				if (isDebug) {
					Runtime.getRuntime().exec(
							"taskkill /F /IM chromedriver.exe");	
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				LoggingClass.getLogger().info(
						"Starting Test Case:");
				// setting capabilities

				/* Getting homepage url */
				
				if (browser.equalsIgnoreCase("chrome")) {
						Proxy proxy = new Proxy();
						proxy.setProxyType(Proxy.ProxyType.AUTODETECT);
						ChromeOptions chromeOptions = new ChromeOptions();
						chromeOptions.addArguments("--disable-notifications");
						chromeOptions.setCapability(
								CapabilityType.ACCEPT_SSL_CERTS, true);
						chromeOptions.setCapability(
								CapabilityType.SUPPORTS_JAVASCRIPT, true);
						chromeOptions.setCapability(
								CapabilityType.TAKES_SCREENSHOT, true);
						chromeOptions
								.setCapability(CapabilityType.PROXY, proxy);
						chromeOptions
								.setCapability("acceptInsecureCerts", true);
						chromeOptions.setCapability("marionette", true);
						try {
							/*
							 * WebDriverManager.chromedriver().setup(); driver =
							 * new ChromeDriver(chromeOptions);
							 */
							gecoDriverPath = new File(".").getCanonicalPath()
									+ File.separator
									+ "src"
									+ File.separator
									+ "test"
									+ File.separator
									+ "resource"
									+ File.separator
									+ "lib"
									+ File.separator
									+ "chromedriver.exe";
							System.setProperty("webdriver.chrome.driver",
									gecoDriverPath);
							driver = new ChromeDriver(chromeOptions);
						} catch (Exception e) {
							gecoDriverPath = new File(".").getCanonicalPath()
									+ File.separator
									+ "src"
									+ File.separator
									+ "test"
									+ File.separator
									+ "resource"
									+ File.separator
									+ "lib"
									+ File.separator
									+ "chromedriver.exe";
							System.setProperty("webdriver.chrome.driver",
									gecoDriverPath);
							driver = new ChromeDriver(chromeOptions);
						}
					}
				} catch (Exception e) {
				}
				driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
				driver.manage().timeouts()
						.pageLoadTimeout(250, TimeUnit.SECONDS);

				/* Navigating to home page */
				driver.get("https://www.irctc.co.in/nget/train-search");

				/* Maximize the window */
				driver.manage().window().fullscreen();
				driver.manage().deleteAllCookies();
				
			} 
	

	@AfterMethod
	public final void tearDown(ITestResult result) throws IOException {


		// Logging the test result
		LoggingClass.getLogger().info(
				this.getClass().getSimpleName() + " The test result for "
						+ result.getInstanceName() + " is "
						+ (boolean) (result.getStatus() == 1));
		// Capture screen shot in case test has failed.
		try{
			if (!result.isSuccess()) {
				LoggingClass.getLogger().info(
						this.getClass().getSimpleName() + " The test result for "
								+ result.getInstanceName() + " is "
								+ (boolean) (result.getStatus() == 1));
			}}catch(Exception e){
				
			}
			 finally {

			// Closing the browser and closing driver
			// Runtime.getRuntime().exec("taskkill /F /IM firefox.exe");
			/*
			 * extent.endTest(BaseTestClass.test); extent.flush();
			 */
			
				driver.close();
			
			/*
			 * try { stopRecording(); } catch (Exception e) { // TODO
			 * Auto-generated catch block e.printStackTrace(); }
			 */
		}

	}
}
