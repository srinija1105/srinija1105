package Seleniumframework;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import org.testng.annotations.Parameters;

public class Scenario1 {

	public String username = "sruthigundabolu";
	public String accesskey = "LT_Rt6CwJdzCMhPGo3EqE0HeOXvnpGicgUIM72al5vOcR4SXf3";
	public static RemoteWebDriver driver1 = null;
	public String gridURL = "@hub.lambdatest.com/wd/hub";
	boolean status = false;

	@BeforeClass
	public void setUp() throws Exception {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("browserName", "chrome");
		capabilities.setCapability("version", "70.0");
		capabilities.setCapability("platform", "win10"); // If this cap isn't specified, it will just get the any
															// available one
		capabilities.setCapability("build", "LambdaTestSampleApp");
		capabilities.setCapability("name", "LambdaTestJavaSample");
		try {
			driver1 = new RemoteWebDriver(new URL("https://" + username + ":" + accesskey + gridURL), capabilities);
		} catch (MalformedURLException e) {
			System.out.println("Invalid grid URL");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
		WebDriver driver;
		@Parameters("browser")
		@BeforeMethod
		public void setUp(@Optional("chrome") String browser) {
			
			switch (browser.toLowerCase()) {
			case "chrome":
				driver = new ChromeDriver();
				break;
			case "firefox":
				driver = new FirefoxDriver();
				break;
			case "edge":
				driver = new EdgeDriver();
				break;
			default:
				throw new IllegalArgumentException("Unsupported browser: " + browser);
			}

			
			driver.get("https://www.lambdatest.com/selenium-playground");
			driver.findElement(By.xpath("//a[normalize-space()='Simple Form Demo']")).click();
			String currentUrl = driver.getCurrentUrl();
			System.out.println("Current URL: " + currentUrl);
		}

		@Test
		public void testSimpleFormDemo() {
			String currentUrl = driver.getCurrentUrl();
			System.out.println("Current URL: " + currentUrl);

			// URL check
			Assert.assertTrue(currentUrl.contains("simple-form-demo"), "URL does not contain 'simple-form-demo'");

			// Form input and validation
			String searchText = "WelcometoLambdaTest";
			driver.findElement(By.id("user-message")).sendKeys(searchText);
			driver.findElement(By.id("showInput")).click();

			String displayedMessage = driver.findElement(By.id("message")).getText();
			Assert.assertEquals(displayedMessage, searchText, "Displayed message does not match input text");
			System.out.println("Displayed message with input text: " + displayedMessage);
		}

		/*
		 * @AfterMethod public void tearDown() { if (driver != null) { driver.quit(); }
		 * }
		 */
	}

