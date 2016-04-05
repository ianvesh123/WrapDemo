import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import junit.framework.AssertionFailedError;

public class TestWrap {
	public static WebDriver driver;
	
	@BeforeClass
	public static void setUp()
	{
		System.out.println("----- Running all three Tests -----");
		driver = new FirefoxDriver();
		driver.get("https://www-perf-aws.wrapdev.net/index/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20L, TimeUnit.SECONDS);
	}
	
	@Test
	public void testWrap() throws InterruptedException, ElementNotVisibleException, IOException
	{
		try
		{
			//Config File Load
			FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\Config.properties");
			Properties Config = new Properties();
			Config.load(fis);
			
			//User SignUp
			WebElement loginLink = driver.findElement(By.xpath(".//*[@id='wrap-header']/header/div/div[3]/div/div/nav/a[2]/span"));
			loginLink.click();
			Thread.sleep(10000);
			
			WebElement signupLink = driver.findElement(By.xpath(".//*[@id='pricingModel']/div/div[1]/div[4]/div[1]/div[2]/div[3]/a"));
			signupLink.click();
			
			String signupXpath = "/div[2]/div[1]/wm-plans-page/div/div/div/wm-plans-authorization/div/div/wm-auth-page/div/ng-transclude/wm-auth/div/div[3]/wm-signup/div/div/form/";
			WebElement emailAddress = driver.findElement(By.xpath(".//*[@id='wrap-theme']"+signupXpath+"input"));
			emailAddress.sendKeys(Config.getProperty("EMAILADDRESS"));
			driver.findElement(By.xpath(".//*[@id='wrap-theme']"+signupXpath+"button")).click();
			Thread.sleep(5000);
			
			WebElement userName = driver.findElement(By.xpath(".//*[@id='wrap-theme']"+signupXpath+"input[3]"));
			userName.sendKeys(Config.getProperty("USERNAME"));					
			WebElement password = driver.findElement(By.xpath(".//*[@id='wrap-theme']"+signupXpath+"input[4]"));
			password.sendKeys(Config.getProperty("PASSWORD"));
			driver.findElement(By.xpath(".//*[@id='wrap-theme']"+signupXpath+"button")).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath(".//*[@id='wrap-theme']"+signupXpath+"button")).click();
			Thread.sleep(5000);
			
			WebElement firstName = driver.findElement(By.xpath(".//*[@id='wrap-theme']"+signupXpath+"input[1]"));
			firstName.sendKeys(Config.getProperty("FIRSTNAME"));
			WebElement lastName = driver.findElement(By.xpath(".//*[@id='wrap-theme']"+signupXpath+"input[2]"));
			lastName.sendKeys(Config.getProperty("LASTNAME"));
			WebElement company = driver.findElement(By.xpath(".//*[@id='wrap-theme']"+signupXpath+"input[3]"));
			company.sendKeys(Config.getProperty("COMPANY"));
			driver.findElement(By.xpath(".//*[@id='wrap-theme']"+signupXpath+"button")).click();
			Thread.sleep(10000);
			
			System.out.println("---- Performing Signup Assertion ----");
			try
			{
				String successfulSignupValidation = driver.findElement(By.xpath(".//*[@id='wrap-theme']/wm-global-nav/header/div/div[3]/div/div/div[2]/a/div[2]")).getText();
				assertEquals(Config.getProperty("USERNAME"),successfulSignupValidation);
				System.out.println("Welcome ---> Successful Signup");
			}
			catch (AssertionFailedError afe)
			{
				System.out.println("Signup Failed");
			}

			//Wrap Creation - Photography -> 2nd Row, 2nd Template
			WebElement create = driver.findElement(By.xpath(".//*[@id='wrap-theme']/div[2]/div[1]/div/div/div[3]/div[2]/a/label"));
			create.click();
			Thread.sleep(6000);
			WebElement category = driver.findElement(By.xpath(".//*[@id='wrap-theme']/div[5]/div/div/div[2]/wm-template-selector/div[1]/div[2]/div[13]/a"));
			category.click();
			Thread.sleep(6000);
			WebElement wrapType = driver.findElement(By.xpath(".//*[@id='wrap-theme']/div[5]/div/div/div[2]/wm-template-selector/div[2]/div[7]/div/button[2]"));
			wrapType.click();
			Thread.sleep(30000);
			
			List<WebElement> exitIconListWrapCreation = driver.findElements(By.xpath(".//*[@id='wrap-theme']/div[5]/div/div/div/button[1]"));
			if (exitIconListWrapCreation.size() != 0)
			{
				exitIconListWrapCreation.get(0).click();
			}
			
			System.out.println("---- Performing Wrap Creation Assertion ----");
			
			try
			{
				String successfulWrapCreation = driver.findElement(By.xpath(".//*[@id='wrap-theme']/div[2]/div[1]/div/div/div/card-header/div/div[1]/div[2]/button[3]")).getText();
				assertEquals("PUBLISH",successfulWrapCreation);
				System.out.println("Wohoo ---> Wrap Created");
			}
			catch (AssertionFailedError afe)
			{
				System.out.println("Wrap Creation Failed");
			}

			//Wrap Publish
			WebElement publishIcon = driver.findElement(By.xpath(".//*[@id='wrap-theme']/div[2]/div[1]/div/div/div/card-header/div/div[1]/div[2]/button[3]"));
			publishIcon.click();
			Thread.sleep(30000);
			
			List<WebElement> exitIconListWrapPublish = driver.findElements(By.xpath(".//*[@id='wrap-theme']/div[5]/div/div/div/button[1]"));
			if (exitIconListWrapPublish.size() != 0)
			{
				exitIconListWrapPublish.get(0).click();
			}
			
			System.out.println("---- Performing Wrap Publish Assertion ----");
			
			try
			{
				String successfulWrapPublish = driver.findElement(By.xpath(".//*[@id='wrap-theme']/div[5]/div/div/div/div[1]/h4")).getText();
				assertEquals("Publish Successful",successfulWrapPublish);
				System.out.println("Congrats ---> Starting sharing your Wrap");
			}
			catch (AssertionFailedError afe)
			{
				System.out.println("Wrap Publish Failed");
			}
		}
		catch(ElementNotVisibleException e)
		{
			System.out.println(e);
		}
	}
	
	@AfterClass
	public static void tearDown()
	{
		System.out.println("---- Tests Done -----");
		driver.close();
	}
	
	public static void main(String args[])
	{
		JUnitCore junit = new JUnitCore();
		junit.run(TestWrap.class);
	}
}