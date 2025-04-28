package Seleniumframework;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Scenario3 {
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

	// Setup method to initialize WebDriver and open the URL
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

		driver.manage().window().maximize();
		driver.get("https://www.lambdatest.com/selenium-playground");
		driver.findElement(By.xpath("//a[normalize-space()='Input Form Submit']")).click();
	}

	// Test method to perform form submission and validation
	@Test
	public void testFormSubmission() throws InterruptedException {
		// Verify if the submit button is enabled before filling the form
		boolean value = driver.findElement(By.xpath("//button[@type = 'submit']")).isEnabled();
		System.out.println("Is Submit button enabled: " + value);

		// Click the Submit button without entering data to trigger validation
		driver.findElement(By.xpath("//button[text() = 'Submit']")).click();
		Thread.sleep(1000); // Sleep to let validation message appear

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
		Thread.sleep(1000); // Wait for the success message to appear

		// Verify the success message after form submission
		WebElement capturedElement = driver.findElement(By.xpath("//p[@class='success-msg hidden']"));
		String capturedText = capturedElement.getText();
		String originalText = "Thanks for contacting us, we will get back to you shortly.";

		Assert.assertEquals(capturedText, originalText, "Success message does not match expected text");
		boolean successMessage = capturedText.equals(originalText);
		System.out.println("SUCCESS MESSAGE VALIDATION: " + successMessage + "   CAPTURED MESSAGE IS: " + capturedText);
	}
}
