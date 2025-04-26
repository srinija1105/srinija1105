package Seleniumframework;

import java.util.stream.Stream;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Scenario1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		WebDriver driver = new ChromeDriver();
		driver.get("https://www.lambdatest.com/selenium-playground/simple-form-demo");
		System.out.println(driver.getCurrentUrl());
		String currenturl = driver.getCurrentUrl();
	
		if (currenturl.contains("simple-form-demo"))
				{
			System.out.println("Url contains simple-form-demo");
				}
		else
		{
			System.out.println("Url doesnot contains simple-form-demo");
			}
		String searchtext  = "WelcometoLambdaTest";
		driver.findElement(By.id("user-message")).sendKeys(searchtext);
		driver.findElement(By.id("showInput")).click();
		String Textmessage = driver.findElement(By.id("message")).getText();
		if (Textmessage.equals(searchtext)) 
		{
		
			System.out.println("testpass");	
		}
	   else
	   {
		System.out.println("test fail");
		}
		System.out.println(driver.findElement(By.tagName("iframe")).getSize());
	}

}
