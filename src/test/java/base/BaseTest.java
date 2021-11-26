package base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;

import extentreports.TestListeners;
import utilities.ExcelReader;

public class BaseTest extends TestListeners {

	public WebDriver driver;

	public SoftAssert softAssert;
	public static Properties prop = new Properties();
	public FileInputStream fis;
	public static ExcelReader excel;
	public String url;

	@BeforeClass
	public void setUp() {

		excel = new ExcelReader(".\\src\\test\\resources\\testdata\\testdata.xlsx");
		// prop= new Properties();
		try {
			fis = new FileInputStream(".\\src\\test\\resources\\properties\\config.properties");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			prop.load(fis);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@AfterMethod
	public void afterClass() {
		if (driver != null)
			driver.quit();

	}

	public void openBrowser(String browserType) throws IOException {

		extentTest.get().log(Status.INFO, "Opening " + browserType + " Browser");
		// public RemoteWebDriver driver = null;
		String username = "ajay.skiva";
		String accessKey = "Mrzl5zl5j1kDfdOM45oUh29rMcxxJbezct6pvCG8ecZsbd3lpS";

		// To run on grid
		if (prop.get("grid_run").equals("Y")) {

			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability("platform", "Windows 10");
			capabilities.setCapability("browserName", "chrome");
			capabilities.setCapability("version", "92.0"); // If this cap isn't specified, it will just get the any
															// available one
			capabilities.setCapability("resolution", "1024x768");
			capabilities.setCapability("build", "First Test");
			capabilities.setCapability("name", "Sample Test");
			capabilities.setCapability("network", true); // To enable network logs
			capabilities.setCapability("visual", true); // To enable step by step screenshot
			capabilities.setCapability("video", true); // To enable video recording
			capabilities.setCapability("console", true); // To capture console logs

			try {
				driver = new RemoteWebDriver(
						new URL("https://" + username + ":" + accessKey + "@hub.lambdatest.com/wd/hub"), capabilities);
			} catch (MalformedURLException e) {
				System.out.println("Invalid grid URL");
			}
			/*
			 * //remote web driver DesiredCapabilities cap=new DesiredCapabilities();
			 * if(browserType.equals("firefox")){
			 * 
			 * cap.setBrowserName("firefox"); cap.setJavascriptEnabled(true);
			 * cap.setPlatform(org.openqa.selenium.Platform.WINDOWS);
			 * 
			 * }else if(browserType.equals("chrome")){ cap.setBrowserName("chrome");
			 * cap.setPlatform(org.openqa.selenium.Platform.WINDOWS); }
			 * 
			 * try { // hit the hub for GRID 3 driver = new RemoteWebDriver(new
			 * URL("http://localhost:4444/wd/hub"), cap); //driver = new RemoteWebDriver(new
			 * URL("http://localhost:4444"), cap); } catch (Exception e) {
			 * e.printStackTrace(); }
			 */
		}

		else { // local machine

			if (browserType.equals("chrome")) {
				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + "\\drivers\\chromedriver.exe");
				System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "null");
				ChromeOptions options = new ChromeOptions();
				options.setPageLoadStrategy(PageLoadStrategy.EAGER);
				driver = new ChromeDriver(options);

			} else if (browserType.equals("firefox")) {
				System.setProperty("webdriver.gecko.driver",
						System.getProperty("user.dir") + "\\drivers\\geckodriver.exe");
				System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "null");
				driver = new FirefoxDriver();

			} else if (browserType.equals("ie")) {
				System.setProperty("webdriver.ie.driver",
						System.getProperty("user.dir") + "\\drivers\\IEDriverServer.exe");
				driver = new InternetExplorerDriver();

			} else if (browserType.equals("Edge")) {
				System.setProperty(EdgeDriverService.EDGE_DRIVER_EXE_PROPERTY, "null");
				System.setProperty(EdgeDriverService.EDGE_DRIVER_LOG_PROPERTY, "null");
				driver = new EdgeDriver();
			}

			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}

	}

	public void navigate(String urlKey) {

		extentTest.get().log(Status.INFO, "Navigate to " + urlKey);

		driver.get(prop.getProperty(urlKey));

	}

	public void closeBrowser() {
		extentTest.get().log(Status.INFO, "Closing Browser");

	}
	
	public void wait(int time) {
		try {
			Thread.sleep(time * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void verifyLink(String urlLink) {
		// Sometimes we may face exception "java.net.MalformedURLException". Keep the
		// code in try catch block to continue the broken link analysis
		try {
			// Use URL Class - Create object of the URL Class and pass the urlLink as
			// parameter
			URL link = new URL(urlLink);
			// Create a connection using URL object (i.e., link)
			HttpURLConnection httpConn = (HttpURLConnection) link.openConnection();
			// Set the timeout for 2 seconds
			httpConn.setConnectTimeout(2000);
			// connect using connect method
			httpConn.connect();
			// use getResponseCode() to get the response code.
			if (httpConn.getResponseCode() == 200) {
				System.out.println(urlLink + " - " + httpConn.getResponseMessage());
				extentTest.get().log(Status.INFO,urlLink + " - " + httpConn.getResponseMessage());
				System.out.println(urlLink + " is directed to " + url);
				extentTest.get().log(Status.INFO, urlLink + " is directed to " + url);
				System.out.println("----------------------------------------------------");
			}
			if (httpConn.getResponseCode() == 404) {
				System.out.println(urlLink + " - " + httpConn.getResponseMessage());
				extentTest.get().log(Status.INFO, urlLink + " - " + httpConn.getResponseMessage());
				
			}
		}
		// getResponseCode method returns = IOException - if an error occurred
		// connecting to the server.
		catch (Exception e) {
			// e.printStackTrace();
		}

	}
}
