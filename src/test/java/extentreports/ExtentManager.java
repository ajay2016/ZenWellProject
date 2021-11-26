package extentreports;

import java.io.File;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {
	
	private static ExtentReports extent;
	
	public static ExtentReports createInstance() {
		
		//filename and path
		String fileName = getReportName();
		String directory = System.getProperty("user.dir")+"/reports/";
		new File(directory).mkdirs();
		String path = directory + fileName;
		
		// Initialization
				ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(path);
				htmlReporter.config().setEncoding("UTF-8");
				htmlReporter.config().setDocumentTitle("Test Reports");
				htmlReporter.config().setReportName("Automation Report");
				htmlReporter.config().setTheme(Theme.DARK);

				extent = new ExtentReports();
				// Attach a ExtentReporter reporter, allowing it to access all started tests,
				// nodes and logs
				extent.attachReporter(htmlReporter);
				// Adds any applicable system information to all started reporters
				extent.setSystemInfo("Automation Tester", "AJ");
				extent.setSystemInfo("Organization", "Atoz");
				extent.setSystemInfo("Build", "3.0");
				
				return extent;
	}
	
	public static String getReportName() {

		Date d = new Date();
		String fileName = "Automation Report" + "_" + d.toString().replace(":", "_").replace(" ", "_") + ".html";
		return fileName;

	}
}
