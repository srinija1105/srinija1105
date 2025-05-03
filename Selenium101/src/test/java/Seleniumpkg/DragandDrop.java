package Seleniumpkg;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class DragandDrop {

	private RemoteWebDriver driver;
	private String username = "geetaserina";
	private String accesskey = "LT_Ap3myytqJdEnMshyGoTmfsIlc7TIQ1orVePzk1xUWzzQn3p";
	private String gridURL = "https://" + username + ":" + accesskey + "@hub.lambdatest.com/wd/hub";

	@BeforeMethod
	@Parameters("browser")
	public void setUp(@Optional("chrome") String browser) throws MalformedURLException {
		if (browser.equalsIgnoreCase("chrome")) {
			ChromeOptions options = new ChromeOptions();
			options.setCapability("browserName", "chrome");
			// add other capabilities here
			Map<String, Object> ltOptions = new HashMap<>();
			ltOptions.put("build", "LambdaTestFinal");
			ltOptions.put("name", "LambdaTestJava");
			ltOptions.put("platformName", "Windows 10");
			ltOptions.put("browserVersion", "latest");
			options.setCapability("LT:Options", ltOptions);
			driver = new RemoteWebDriver(new URL(gridURL), options);

		} else if (browser.equalsIgnoreCase("firefox")) {
			FirefoxOptions options = new FirefoxOptions();
			options.setCapability("browserName", "firefox");
			Map<String, Object> ltOptions = new HashMap<>();
			ltOptions.put("build", "LambdaTest");
			ltOptions.put("name", "LambdaTestJava");
			ltOptions.put("platformName", "Windows 10");
			ltOptions.put("browserVersion", "latest");
			options.setCapability("LT:Options", ltOptions);
			driver = new RemoteWebDriver(new URL(gridURL), options);
		}

		// Log session ID
		System.out.println("Session ID: " + driver.getSessionId());
		// driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(200));
		driver.manage().window().maximize(); // Optional to maximize the window
		driver.get("https://www.lambdatest.com/selenium-playground");
		// Thread.sleep(1000);
		driver.findElement(By.xpath("//a[normalize-space()='Drag & Drop Sliders']")).click();

	}

	@Test
	public void testSliderDragAndDrop() throws InterruptedException {

		Thread.sleep(1000);
		WebElement slider = driver.findElement(By.xpath("(//div[@id='slider4']|//input[ @type='range'])[3]"));
		Assert.assertTrue(slider.isDisplayed(), "Slider is not displayed");

		Actions actions = new Actions(driver);
		actions.clickAndHold(slider).moveByOffset(215, 0).release().build().perform();

		// Capture the slider value after drag
		String capture = driver.findElement(By.id("rangeSuccess")).getText();

		// Assert that the captured value is the expected one after dragging
		Assert.assertEquals(capture, "95", "Slider value did not change to 95");
		System.out.println(" Slider Default value 15 changed to " + capture);
	}

	@AfterMethod
	public void tearDown(ITestResult result) {
		try {
			// ✅ Report test status to LambdaTest
			String status = result.isSuccess() ? "passed" : "failed";
			((JavascriptExecutor) driver).executeScript("lambda-status=" + status);

			System.out.println(
					"🔗 View test at: https://automation.lambdatest.com/logs/?sessionID=" + driver.getSessionId());
		} catch (Exception e) {
			System.out.println("Error in tearDown: " + e.getMessage());
		} finally {
			if (driver != null) {
				driver.quit();
			}
		}
	}

}
