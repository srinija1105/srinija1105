package Seleniumframework;

import javax.swing.text.Highlighter.Highlight;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class Scenario2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WebDriver driver = new ChromeDriver();
		driver.get("https://www.lambdatest.com/selenium-playground/drag-drop-range-sliders-demo");
		driver.manage().window().maximize();
	//	WebElement draganddroppage = driver.findElement(By.xpath("//a[@href=https://www.lambdatest.com/selenium-playground/drag-drop-range-sliders-demo]"));
	//	draganddroppage.click();
		WebElement slider = driver.findElement(By.xpath("(//div[@id='slider4']|//input[ @type='range'])[3]"));
		System.out.println(slider.isDisplayed());
		Actions a = new Actions(driver);
		a.clickAndHold(slider).moveByOffset(140, 0).build().perform();
		
	}

}
