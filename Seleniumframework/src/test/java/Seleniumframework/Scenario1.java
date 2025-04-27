package Seleniumframework;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import org.testng.annotations.Parameters;

public class Scenario1 {

    WebDriver driver;

    @Parameters("browser")
    @BeforeMethod
    public void setUp(@Optional("chrome")String browser) {
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
        driver.findElement(By.xpath("//a[normalize-space()='Simple Form Demo']")).click();
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

    /*@AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
    */
}




