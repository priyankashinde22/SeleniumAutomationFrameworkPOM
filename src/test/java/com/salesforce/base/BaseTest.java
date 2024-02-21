package com.salesforce.base;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.Wait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.google.common.io.Files;
import com.salesforce.pages.ForgotYourPassword.CheckYourEmailPage;
import com.salesforce.pages.ForgotYourPassword.ForgotYourPasswordPage;
import com.salesforce.pages.home.HomePage;
import com.salesforce.pages.login.LoginPage;
import com.salesforce.utilities.Constants;
import com.salesforce.utilities.PropertiesUtility;

import io.github.bonigarcia.wdm.WebDriverManager;

@Listeners(com.salesforce.utilities.SalesforcebaseListenerUtility.class)
public class BaseTest{

	protected static WebDriver driver = null;
	protected Logger basetestlog = LogManager.getLogger();
	protected LoginPage loginPage;
	protected HomePage homePage;
	protected ForgotYourPasswordPage forgtpwdPage;
	protected CheckYourEmailPage chckuremailPage;
	

	@BeforeMethod
	@Parameters("browserName")
	public void setUpBeforeMethod(@Optional("chrome") String name) {

		basetestlog.info("-----SetupBeforeMethod executed----");
		launchBrowser(name);
		String url = PropertiesUtility.readDataFromPropertyFile(Constants.APPLICATION_PROPERTIES, "url");
		goToUrl(url);

	}

	
	@AfterMethod
	public void tearDownAfterTestMethod() {

		basetestlog.info("-----tearDownAfterTestMethod executed----");

		closeBrowser();

	}

	public void launchBrowser(String browserName)
	{

		switch (browserName.toLowerCase()) {
		case "chrome":
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			basetestlog.info("browser instance chrome created.");
			
			driver.manage().window().maximize();
			basetestlog.info("window is maximized");
			break;

		case "firefox":
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			basetestlog.info("browser instance firefox created.");
			
			driver.manage().window().maximize();
			break;

		case "edge":
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			basetestlog.info("browser instance edge created.");
			
			driver.manage().window().maximize();
			break;

		case "opera":
			WebDriverManager.operadriver().setup();
			driver = new OperaDriver();
			basetestlog.info("browser instance opera created.");
			
			driver.manage().window().maximize();
			break;

		case "safari":
			// Safari does not require a separate driver setup, as it is included with macOS
			driver = new SafariDriver();
			basetestlog.info("browser instance safari created.");
			

			break;

		default:
			basetestlog.info("Unsupported browser: " + browserName);
			
		}

		// return driver;
	}
	
	public void goToUrl(String url) {
		driver.get(url);
		basetestlog.info(url + "is entered");
		

	}


	

//
//	public void salesforceLogout() throws InterruptedException {
//		WebElement userNavArrow = driver.findElement(By.id("userNav-arrow"));
//		clickElement(userNavArrow, "User-nav arrow");
//		Thread.sleep(4000);
//		WebElement logOut = driver.findElement(By.linkText("Logout"));
//		clickElement(logOut, "Logout link");
//		Thread.sleep(4000);
//		String expTitle = "Login | Salesforce";
//		String actTitle = getTitle(driver, "Login Page");
//		if (expTitle.equals(actTitle)) {
//			basetestlog.info("Salesforce application login page is displayed");
//		} else {
//			basetestlog.info("Salesforce application login page is not displayed");
//		}
//
//	}
	
	public void closeBrowser() {
		driver.close();
		basetestlog.info("browser instance closed");
		driver = null;
	}
	

	public void quitBrowser() {
		driver.quit();
		basetestlog.info("all browser closed");
		driver=null;
		
	}
	
	public void takescreenshot(String filepath) {
		 TakesScreenshot screenCapture=(TakesScreenshot)driver;
		 File src=screenCapture.getScreenshotAs(OutputType.FILE);
		 File destination=new File(filepath);
		 try {
			Files.copy(src, destination);
			basetestlog.info("captured the screen");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			basetestlog.info("went wrong when capturing the screen");
			
		}
	}
	
	public void takescreenshot(WebElement element,String filepath) {
		File src = element.getScreenshotAs(OutputType.FILE);
		 File destination=new File(filepath);
		 try {
			Files.copy(src, destination);
			basetestlog.info("captured element screenshot");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			basetestlog.info("went wrong when capturing the screen");
			
		}
		
		
	}
	

}
