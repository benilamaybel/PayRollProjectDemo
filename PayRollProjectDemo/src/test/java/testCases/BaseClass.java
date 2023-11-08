package testCases;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
//import utilities.ITestResult;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import org.testng.annotations.Parameters;

import elementRepository.LoginPage;
import utilities.ScreenShotCapture;

public class BaseClass {
	WebDriver driver;
	ScreenShotCapture sc;
	public static Properties pro;
	static String inputExcelFileName;
	static String loginSheetName;
	static String clientSheetName;
	static String workerSheetName;
	static String timeSheetName;

	public static void testBasic() throws IOException {

		pro = new Properties();
		FileInputStream fp = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\main\\resources\\Properties\\config.properties");
		pro.load(fp);
	}

	@BeforeMethod(alwaysRun = true)
	@Parameters("browser")
	public void beforeMethod(String browserName) {
		// public void beforeMethod() {
		try {
			testBasic();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (browserName.equals("chrome")) {
			System.setProperty(pro.getProperty("ChromeDriver"),
					System.getProperty("user.dir") + "\\src\\main\\resources\\Driver\\chromedriver.exe");
			driver = new ChromeDriver();
		} else if (browserName.equals("edge")) {
			System.setProperty(pro.getProperty("EdgeDriver"),
					System.getProperty("user.dir") + "\\src\\main\\resources\\Driver\\msedgedriver.exe");
			driver = new EdgeDriver();
		}
/*
		System.setProperty(pro.getProperty("ChromeDriver"),
				System.getProperty("user.dir") + "\\src\\main\\resources\\Driver\\chromedriver.exe");
		driver = new ChromeDriver();*/
		driver.get(pro.getProperty("BaseURL"));
		inputExcelFileName = pro.getProperty("ExcelFileName");
		loginSheetName = pro.getProperty("LoginSheetName");
		clientSheetName = pro.getProperty("ClientSheetName");
		workerSheetName = pro.getProperty("WorkerSheetName");
		timeSheetName = pro.getProperty("TimeSheetName");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	}

	@AfterMethod(alwaysRun = true) // to run always
	public void afterMethod(ITestResult iTestResult) throws IOException { // ITestResult is a Listener class
		if (iTestResult.getStatus() == ITestResult.FAILURE) { // executes only when TC Fails
			sc = new ScreenShotCapture(); // Object creation for ScreenShotCapture class
			sc.captureFailureScreenShot(driver, iTestResult.getName()); // calls captureFailureScreenShot method; passes
																		// the name of Testcase
		}
		 driver.quit();
	}
}
