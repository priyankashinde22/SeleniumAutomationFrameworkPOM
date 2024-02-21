package com.salesforce.automationtests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.salesforce.base.BaseTest;
import com.salesforce.pages.ForgotYourPassword.CheckYourEmailPage;
import com.salesforce.pages.ForgotYourPassword.ForgotYourPasswordPage;
import com.salesforce.pages.home.HomePage;
import com.salesforce.pages.login.LoginPage;
import com.salesforce.utilities.Constants;
import com.salesforce.utilities.ExtentReportsUtility;
import com.salesforce.utilities.PropertiesUtility;

public class LoginTests extends BaseTest {

	protected Logger LoginTestslog = LogManager.getLogger();
	protected ExtentReportsUtility extentReport = ExtentReportsUtility.getInstance();

	protected SoftAssert softobj = new SoftAssert();
	//protected WebDriverWait wait = new WebDriverWait(driver, 10);
	
	@Test

	public void salesforce_LoginTC2() {
		// SoftAssert softobj = new SoftAssert();

		String expHomePageTitle = "Home Page ~ Salesforce - Developer Edition";
		String userName = PropertiesUtility.readDataFromPropertyFile(Constants.APPLICATION_PROPERTIES, "username");
		String passWord = PropertiesUtility.readDataFromPropertyFile(Constants.APPLICATION_PROPERTIES, "password");

		// LoginPage loginPage = new LoginPage(driver);
		loginPage = new LoginPage(driver);
		loginPage.enterUserName(userName);
		loginPage.enterPassword(passWord);
		driver = loginPage.clickLoginButton();

		// HomePage homePage=new HomePage(driver);
		homePage = new HomePage(driver);
		String actHomePageTitle = homePage.getTitleOfThePage();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.titleIs(actHomePageTitle));
		
		LoginTestslog.info(actHomePageTitle);
		Assert.assertEquals(actHomePageTitle, expHomePageTitle);

		String expLoggedUName = "priyanka Abcd";
		String actLoggedUName = homePage.getTextFromElement();
		LoginTestslog.info(actLoggedUName);
		softobj.assertEquals(actLoggedUName, expLoggedUName, "Logged User name is not matched with expected");
		softobj.assertAll();

	}

	@Test
	public void errorMessageLoginTC1() {

		String expTitle = "Login | Salesforce";

		String userName = "Priyanka Shinde";
		String passWord = "";

		loginPage = new LoginPage(driver);
		String actTitle = loginPage.getTitleOfThePage();
		LoginTestslog.info(actTitle);
		Assert.assertEquals(actTitle, expTitle);
		extentReport.logTestInfo("using hard assert compare actual and expected");
		loginPage.enterUserName(userName);
		loginPage.enterPassword(passWord);
		driver = loginPage.clickLoginButton();
		
		String expErrMsg = "Please enter your password.";
		String actErrMsg = loginPage.getTextFromElement();
		
		LoginTestslog.info(actErrMsg);
		softobj.assertEquals(actErrMsg, expErrMsg);

	}
	
	@Test
	public void checkRememberMeTC3() throws InterruptedException {
		String expLoginTitle = "Login | Salesforce";
		String userName = PropertiesUtility.readDataFromPropertyFile(Constants.APPLICATION_PROPERTIES, "username");
		String passWord = PropertiesUtility.readDataFromPropertyFile(Constants.APPLICATION_PROPERTIES, "password");
		loginPage = new LoginPage(driver);
		String actLoginTitle = loginPage.getTitleOfThePage();
		Thread.sleep(3000);
		
		LoginTestslog.info(actLoginTitle);
		Assert.assertEquals(actLoginTitle, expLoginTitle);
		extentReport.logTestInfo("using hard assert compare actual and expected");
		
		loginPage.enterUserName(userName);
		loginPage.enterPassword(passWord);
		loginPage.selectCheckBox();
		driver = loginPage.clickLoginButton();
		Thread.sleep(3000);
		
		String expHomePageTitle = "Home Page ~ Salesforce - Developer Edition";
		homePage = new HomePage(driver);
		String actHomePageTitle = homePage.getTitleOfThePage();
	//	WebDriverWait wait = new WebDriverWait(driver, 10);
		//wait.until(ExpectedConditions.titleIs(actHomePageTitle));
		
		Thread.sleep(3000);
		
		LoginTestslog.info(actHomePageTitle);
		Assert.assertEquals(actHomePageTitle, expHomePageTitle);

		String expLoggedUName = "priyanka Abcd";
		String actLoggedUName = homePage.getTextFromElement();
		LoginTestslog.info(actLoggedUName);
		softobj.assertEquals(actLoggedUName, expLoggedUName, "Logged User name is not matched with expected");
		softobj.assertAll();
		
		//salesforceLogout();
		homePage.clickUserNavArrowElement();
		Thread.sleep(3000);
		
		driver=homePage.clickOnLogOut();
		
		Thread.sleep(3000);
		
		loginPage = new LoginPage(driver);
		String actTitle = loginPage.getTitleOfThePage();
		softobj.assertEquals(actTitle, expLoginTitle, "title is not matched with expected");
		softobj.assertAll();
		
		boolean flag =loginPage.isSelectedCheckBox();
		
		softobj.assertTrue(flag, "Checkbox Remember me is not selected");
		
		String user= loginPage.getValueFrmTextbox();
		softobj.assertAll();
		Thread.sleep(3000);
		softobj.assertEquals(user, "priyanka@house.com","Testcase is failed");
		softobj.assertAll();
		LoginTestslog.info("Testcase is Passed");

	}
	
	@Test
	
	
	public void forgetPasswordTC4A() throws InterruptedException {
		
		String expTitle = "Login | Salesforce";

		loginPage = new LoginPage(driver);
		String actTitle = loginPage.getTitleOfThePage();
		LoginTestslog.info(actTitle);
		Assert.assertEquals(actTitle, expTitle);
		driver=loginPage.clickOnLink();
		Thread.sleep(3000);
		
		forgtpwdPage=new ForgotYourPasswordPage(driver);
		
		String expforgtpwdPagetitle ="Forgot Your Password | Salesforce";
		String actforgtpwdPagetitle = forgtpwdPage.getPageTitle();
		softobj.assertEquals(actforgtpwdPagetitle, expforgtpwdPagetitle);
		softobj.assertAll();
		
		String userName = PropertiesUtility.readDataFromPropertyFile(Constants.APPLICATION_PROPERTIES, "username"); 
		
		forgtpwdPage.enterUserName(userName);
		driver=forgtpwdPage.clickContinueButton();
		Thread.sleep(3000);
		String expEmailTitle = "Check Your Email | Salesforce";
		chckuremailPage=new CheckYourEmailPage(driver);
		
		String expmessage="We’ve sent you an email with a link to finish resetting your password.";
		
		String actmessgae=chckuremailPage.getTextFromElement();
		softobj.assertEquals(actmessgae, expmessage);
		softobj.assertAll();

		String actemailPageTitle=chckuremailPage.getTitleOfThePage();
		softobj.assertEquals(actemailPageTitle, expEmailTitle,"Test case is Failed");
		softobj.assertAll();
		
		LoginTestslog.info("Testcase is Passed");
		
		

}
	
	
	@Test
	public void forgetPasswordTC4B() throws InterruptedException {
		
		String expTitle = "Login | Salesforce";
		String userName = "123";
		String passWord = "22131";
		loginPage = new LoginPage(driver);
		String actTitle = loginPage.getTitleOfThePage();
		LoginTestslog.info(actTitle);
		Assert.assertEquals(actTitle, expTitle);
		
		loginPage.enterUserName(userName);
		loginPage.enterPassword(passWord);
	
		driver = loginPage.clickLoginButton();
		Thread.sleep(3000);

		String expErrMsg = "Please check your username and password. If you still can't log in, contact your Salesforce administrator.";
		String actErrMsg = loginPage.getTextFromElement();
		LoginTestslog.info(actErrMsg);
		softobj.assertEquals(actErrMsg, expErrMsg,"Test case is Failed");
		softobj.assertAll();

		LoginTestslog.info("Testcase is Passed");



		

}


	
	

}
