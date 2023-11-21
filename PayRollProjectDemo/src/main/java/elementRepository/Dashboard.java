package elementRepository;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utilities.ExcelUtilities;
import utilities.GeneralUtilities;
import utilities.WaitUtilities;

public class Dashboard {
	WebDriver driver;
	GeneralUtilities generalutility = new GeneralUtilities();
	WaitUtilities waitutility = new WaitUtilities();
	ExcelUtilities excelutility = new ExcelUtilities();

	@FindBy(xpath = "//a[@class='dropdown-toggle']")
	WebElement userDropdown;
	@FindBy(xpath = "//a[@class='logout-btn']")
	WebElement logoutBtn;

	public Dashboard(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//div[@class='col-lg-12 col-sm-12']//p[1]")
	WebElement dashboardText;

	public String verifyDashboardText() {
		return generalutility.getElementText(dashboardText);
	}

	public void performLogout(WebDriver driver) {
		userDropdown.click();
		waitutility.waitElementClickable(driver, logoutBtn);
		logoutBtn.click();
	}

	public String getExpectedDataFromExcel(String filePath, String fileName, String sheetName) {
		String expectedLogoutText = "";
		try {
			List<String> excelInputList;
			excelInputList = excelutility.getDataFromExcel(filePath, fileName, sheetName);
			expectedLogoutText = excelInputList.get(1);
		} catch (Exception e) {
			System.out.println("An Exception Occurred!!!" + e);
		}
		return expectedLogoutText;
	}
}
