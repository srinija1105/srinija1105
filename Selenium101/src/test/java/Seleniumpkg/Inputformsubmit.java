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

public class Inputformsubmit {

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
		// Verify if the submit button is enabled before filling the form
		driver.get("https://www.lambdatest.com/selenium-playground");
		// Thread.sleep(1000);
		driver.findElement(By.xpath("//a[normalize-space()='Input Form Submit']")).click();

	}

	@Test
	public void testFormSubmission() throws InterruptedException {

		boolean value = driver.findElement(By.xpath("//button[@type = 'submit']")).isEnabled();
		System.out.println("Is Submit button enabled: " + value);

		// Click the Submit button without entering data to trigger validation
		driver.findElement(By.xpath("//button[text() = 'Submit']")).click();
		// Thread.sleep(1000); // Sleep to let validation message appear

		// JavaScript executor to capture the validation message
		JavascriptExecutor js = (JavascriptExecutor) driver;

		WebElement nameField = driver.findElement(By.name("name")); // Name field locator
		String validationMessage = (String) js.executeScript("return arguments[0].validationMessage;", nameField);

		System.out.println("Captured validation message: " + validationMessage);
		Assert.assertEquals(validationMessage, "Please fill out this field.");

		// Fill the form fields with valid data
		driver.findElement(By.id("name")).sendKeys("Sriramyoga");
		driver.findElement(By.id("inputEmail4")).sendKeys("example@gmail.com");
		driver.findElement(By.name("password")).sendKeys("password1223");
		driver.findElement(By.xpath("//*[@id='company']")).sendKeys("Persistent");
		driver.findElement(By.xpath("//*[@id='websitename']")).sendKeys("Lambdatest");

		// Select country from dropdown
		WebElement countryDropdown = driver.findElement(By.name("country"));
		Select select = new Select(countryDropdown);
		select.selectByVisibleText("United States");

		// Fill remaining fields
		driver.findElement(By.name("city")).sendKeys("Arizona");
		driver.findElement(By.name("address_line1")).sendKeys("Flatno501");
		driver.findElement(By.name("address_line2")).sendKeys("Aquem");
		driver.findElement(By.id("inputState")).sendKeys("Goa");
		driver.findElement(By.id("inputZip")).sendKeys("567890");

		// Click Submit to submit the form
		driver.findElement(By.xpath("//button[text() = 'Submit']")).click();
		// Thread.sleep(1000); // Wait for the success message to appear

		// Verify the success message after form submission
		WebElement capturedElement = driver.findElement(By.xpath("//p[@class='success-msg hidden']"));
		String capturedText = capturedElement.getText();
		String originalText = "Thanks for contacting us, we will get back to you shortly.";

		Assert.assertEquals(capturedText, originalText, "Success message does not match expected text");
		boolean successMessage = capturedText.equals(originalText);
		System.out.println("SUCCESS MESSAGE VALIDATION: " + successMessage + "   CAPTURED MESSAGE IS: " + capturedText);
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