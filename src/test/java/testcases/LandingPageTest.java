package testcases;

import java.io.IOException;
import java.util.Hashtable;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import base.BaseTest;
import pageobjects.AllLinksPage;
import utilities.DataUtil;

public class LandingPageTest extends BaseTest{
	
	
	@Test(dataProviderClass = DataUtil.class, dataProvider = "data")
	public void LandingPage(Hashtable<String, String> data) throws IOException {
		
		if (!DataUtil.isRunnable("LandingPage") || data.get("Runmode").equals("N")) {
			
			
			
			throw new SkipException("Test skipped since rumode is N");

		}	
	
	
	// opening chrome browser
	openBrowser(data.get("Browser"));
	
	
	// navigate to given url	
	navigate("appurl");
	
	AllLinksPage link = new AllLinksPage(driver);
	link.LinkTest();
	
	//Sign in Process
	link.clickOnSignin();	
	link.enterPhoneNumber(data.get("PhoneNumber"));
	link.enterPassword(data.get("Password"));
	link.clickOnLogin();
	String text = link.getAlertText();
	Assert.assertEquals(text, data.get("AlertMsg"));
	
	//Our Services
	link.clickOnOurSevices();
	//link.hooverOnOurServices();
	link.clickOnMedicine();
	
	
		
	}

}
