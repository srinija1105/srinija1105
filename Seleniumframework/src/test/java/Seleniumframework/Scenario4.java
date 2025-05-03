package Seleniumframework;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Scenario4 {

    private RemoteWebDriver driver;
    private String username = "sruthigundabolu";
    private String accesskey = "LT_Rt6CwJdzCMhPGo3EqE0HeOXvnpGicgUIM72al5vOcR4SXf3";
    private String gridURL = "https://" + username + ":" + accesskey + "@hub.lambdatest.com/wd/hub";

    @BeforeMethod
    public void setUp() throws MalformedURLException {
    	
    	ChromeOptions options = new ChromeOptions();
    	options.setCapability("browserName", "chrome");

    	// Optional metadata can be wrapped under "LT:Options" or similar depending on provider (like LambdaTest)
    	Map<String, Object> ltOptions = new HashMap<>();
    	ltOptions.put("build", "LambdaTestSampleApp");
    	ltOptions.put("name", "LambdaTestJavaSample");
    	ltOptions.put("platformName", "Windows 10");
    	ltOptions.put("browserVersion", "latest");

    	options.setCapability("LT:Options", ltOptions);  // if testing on LambdaTest


        driver = new RemoteWebDriver(new URL(gridURL), options);

        // Log session ID
        System.out.println("Session ID: " + driver.getSessionId());

        driver.get("https://www.lambdatest.com/selenium-playground");
        driver.findElement(By.linkText("Simple Form Demo")).click();
        System.out.println("Current URL: " + driver.getCurrentUrl());
    }

    @Test
    public void testSimpleFormDemo() {
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("simple-form-demo"), "URL does not contain 'simple-form-demo'");

        String input = "WelcometoLambdaTest";
        driver.findElement(By.id("user-message")).sendKeys(input);
        driver.findElement(By.id("showInput")).click();

        String output = driver.findElement(By.id("message")).getText();
        Assert.assertEquals(output, input, "Displayed message mismatch");

        System.out.println("Displayed message with input text: " + output);
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        try {
            // ✅ Report test status to LambdaTest
            String status = result.isSuccess() ? "passed" : "failed";
            ((JavascriptExecutor) driver).executeScript("lambda-status=" + status);

            System.out.println("🔗 View test at: https://automation.lambdatest.com/logs/?sessionID=" + driver.getSessionId());
        } catch (Exception e) {
            System.out.println("Error in tearDown: " + e.getMessage());
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}
