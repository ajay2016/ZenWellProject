package pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.Status;

import base.BaseTest;

public class AllLinksPage extends BaseTest {

	public AllLinksPage(WebDriver driver) {
		PageFactory.initElements(driver, this);

	}

	@FindBy(tagName = "a")
	public List<WebElement> links;
	
	@FindBy(xpath = "//*[@href = '/auth/login/']")
	public WebElement singin;
	
	@FindBy(name = "phone_number")
	public WebElement phno;
	
	@FindBy(name = "password")
	public WebElement pword;
	
	@FindBy(xpath = "//button[text()=\"Login\"]")
	public WebElement login;
	
	@FindBy(xpath = "//*[@role='alert']")
	public WebElement alertmsg;
	
	//Menu Links
	@FindBy(xpath = "//a[@id=\"nav-link--page\"]")
	public WebElement ourserve;
	@FindBy(xpath = "//a[@href='/medicines/']")
	public WebElement med;

	public void LinkTest() {
		System.out.println("Total links on the page " + links.size());
		extentTest.get().log(Status.INFO, "Total links on the page " + links.size());
		for (int i = 0; i < links.size(); i++) {
			WebElement element = links.get(i);
			url = element.getAttribute("href");
			verifyLink(url);

		}

	}
	
	public void clickOnSignin() {
		
		extentTest.get().log(Status.INFO, "Click On Signin");
		singin.click();
	}
	
	public void enterPhoneNumber(String number) {
		extentTest.get().log(Status.INFO, "Enter PhoneNumber: "+number);
		phno.sendKeys(number);
	}
	
	public void enterPassword(String pwd) {
		extentTest.get().log(Status.INFO, "Enter Password: "+pwd);
		pword.sendKeys(pwd);
	}
	
	public void clickOnLogin() {
		extentTest.get().log(Status.INFO, "Click On Login");
		login.click();
	}
	
	public void clickOnOurSevices() {
		extentTest.get().log(Status.INFO, "Click On OurSevices");
		ourserve.click();
		
	}
	
	public void hooverOnOurServices() {
		Actions act = new Actions(driver);
		act.moveToElement(ourserve).click().perform();
	}
	
	public void clickOnMedicine() {
		extentTest.get().log(Status.INFO, "Click On Medicine");
		med.click();
	}
	
	public String getAlertText() {
		String text = alertmsg.getText();
		return text;
	}
}
