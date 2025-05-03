package Seleniumframework;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class CrossBrowserDemo {

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
            Map<String, Object> ltOptions = new HashMap<>();
            ltOptions.put("build", "LambdaTest");
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
        } else if (browser.equalsIgnoreCase("edge")) {
            EdgeOptions options = new EdgeOptions();
            options.setCapability("browserName", "MicrosoftEdge");
            Map<String, Object> ltOptions = new HashMap<>();
            ltOptions.put("build", "LambdaTest");
            ltOptions.put("name", "LambdaTestJava");
            ltOptions.put("platformName", "Windows 10");
            ltOptions.put("browserVersion", "latest");
            options.setCapability("LT:Options", ltOptions);
            driver = new RemoteWebDriver(new URL(gridURL), options);
        } else {
            throw new IllegalArgumentException("Browser not supported: " + browser);
        }

        // Log session ID
        System.out.println("Session ID: " + driver.getSessionId());
        driver.manage().window().maximize(); // Optional to maximize the window
        driver.get("https://www.lambdatest.com/selenium-playground");
        driver.findElement(By.linkText("Simple Form Demo")).click();
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
