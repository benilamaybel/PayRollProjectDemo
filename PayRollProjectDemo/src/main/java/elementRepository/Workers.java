package elementRepository;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utilities.ExcelUtilities;
import utilities.GeneralUtilities;
import utilities.WaitUtilities;

public class Workers {
	WebDriver driver;
	GeneralUtilities gu = new GeneralUtilities();;
	WaitUtilities wu = new WaitUtilities();;
	ExcelUtilities eu = new ExcelUtilities();

	public Workers(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(linkText = "Workers")
	WebElement workersBtn;
	@FindBy(id = "workersearch-first_name")
	WebElement workerNameElement;
	@FindBy(xpath = "//button[@class='btn btn-primary']")
	WebElement searchBtn;
	@FindBy(xpath = "//div[@class='col-sm-6 page-title']")
	WebElement title;
	@FindBy(linkText = "Bank Details")
	WebElement bankDetailsBtn;
	@FindBy(id = "worker-start_date")
	WebElement bankStartDate;
	@FindBy(xpath = "//button[@class='btn btn-success']")
	WebElement saveBtn;
	@FindBy(xpath = "//table//tbody//tr[1]//td[8]//a[@aria-label='Delete']")
	WebElement firstDeleteItem;

	public int searchWorker(WebDriver driver, String workerName) {
		return gu.findTableElement(driver, workerName);
	}

	public void navigateToWorkersMenu() {
		workersBtn.click();
	}

	public void enterWorkerName(String workerName) {
		gu.enterText(workerNameElement, workerName);
	}

	public void searchWorker() {
		searchBtn.click();
	}

	public String getWorkerTitle() {
		return title.getText();
	}

	public void navigateToBankDetailsSubMenu() {
		bankDetailsBtn.click();
	}

	public void clearBankStartDate() {
		gu.clearField(bankStartDate);
	}

	public void enterBankStartDate(String setDate) {
		gu.enterText(bankStartDate, setDate);
	}

	public void saveWorker() {
		saveBtn.click();
	}

	public String verifyPageTitle(WebDriver driver, String expectedText) {
		wu.waitTextDisplayed(driver, title, expectedText);
		String actualTitleText = title.getText();
		return actualTitleText;
	}

	public String getBankDate() {
		String date = bankStartDate.getAttribute("value");
		return date;
	}

	public String getSearchStringFromExcel(String excelFileName, String sheetName) {
		String SearchString = "";
		try {
			SearchString = eu.getStringData(excelFileName, sheetName, 1, 1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SearchString;
	}

	public String getWorkerNameFromExcel(String excelFileName, String sheetName) {
		String workerName = "";
		try {
			workerName = eu.getStringData(excelFileName, sheetName, 0, 1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return workerName;
	}

	public String getSetDateFromExcel(String excelFileName, String sheetName) {
		String setDate = "";
		try {
			setDate = eu.getStringData(excelFileName, sheetName, 2, 1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return setDate;
	}

	public String verifyDeleteFirstRecord() {
		firstDeleteItem.click();
		wu.waitAlertDisplayed(driver);
		return gu.getAlertText(driver);
	}
}
