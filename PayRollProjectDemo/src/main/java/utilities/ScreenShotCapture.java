package utilities;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

public class ScreenShotCapture {
	public void captureFailureScreenShot(WebDriver driver, String name) throws IOException {
		TakesScreenshot scrShot = (TakesScreenshot) driver; // takes screenshot
		File screenShot = scrShot.getScreenshotAs(OutputType.FILE);// screenshot will store in temporary path
		System.out.println("In screenshot function!");
		File f1 = new File(System.getProperty("user.dir") + "\\OutputScreenshots");// Create folder using Java
		if (!f1.exists()) {
			System.out.println("creating directory");
			f1.mkdirs();
		}

		String timeStamp = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss").format(new Date());// date time capture using
																							// java

		File finalDestination = new File(
				System.getProperty("user.dir") + "\\OutputScreenshots\\" + name + "_" + timeStamp + ".png"); // permanently
																												// save
																												// the
																												// screenshot
																												// as
																												// file
																												// in
																												// workspace.

		FileHandler.copy(screenShot, finalDestination);

	}
}
