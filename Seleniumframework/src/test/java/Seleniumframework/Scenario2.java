package Seleniumframework;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Scenario2 {

    WebDriver driver;

    // Setup method to initialize WebDriver
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
        driver.findElement(By.xpath("//a[normalize-space()='Drag & Drop Sliders']")).click();
        
    }
    @Test
    public void testSliderDragAndDrop() {
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

    // Cleanup method to quit the WebDriver
   /* @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
    */
}

