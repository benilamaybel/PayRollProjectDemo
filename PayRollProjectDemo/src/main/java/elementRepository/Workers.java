package elementRepository;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utilities.ExcelUtilities;
import utilities.GeneralUtilities;
import utilities.WaitUtilities;

public class Workers {
	WebDriver driver;
	GeneralUtilities generalutility = new GeneralUtilities();;
	WaitUtilities waitutility = new WaitUtilities();;
	ExcelUtilities excelutility = new ExcelUtilities();

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
		return generalutility.findTableElement(driver, workerName);
	}

	public void navigateToWorkersMenu() {
		workersBtn.click();
	}

	public void enterWorkerName(String workerName) {
		generalutility.enterText(workerNameElement, workerName);
	}

	public void clickSearch() {
		searchBtn.click();
	}

	public String getWorkerTitle() {
		return title.getText();
	}

	public void navigateToBankDetailsSubMenu() {
		bankDetailsBtn.click();
	}

	public void clearBankStartDate() {
		generalutility.clearField(bankStartDate);
	}

	public void enterBankStartDate(String setDate) {
		generalutility.enterText(bankStartDate, setDate);
	}

	public void saveWorker() {
		saveBtn.click();
	}

	public void clickOnEditWorker(WebElement editElement) {
		generalutility.scrollDown(driver);
		waitutility.fluentWaitElementClickable(driver, editElement);
		editElement.click();
	}

	public String verifyPageTitle(WebDriver driver, String expectedText) {
		String actualTitleText = "";
		try {
			waitutility.waitTextDisplayed(driver, title, expectedText);
			actualTitleText = title.getText();
		} catch (Exception e) {
			actualTitleText = title.getText();
			System.out.println("Exception Captured !" + e);
		}
		return actualTitleText;
	}

	public String getBankDate() {
		String date = bankStartDate.getAttribute("value");
		return date;
	}

	public String getSearchStringFromExcel(String excelFileName, String sheetName) {
		String SearchString = "";
		try {
			List<String> excelInputList = excelutility.getDataFromExcel(excelFileName, sheetName);
			SearchString = excelInputList.get(3);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SearchString;
	}

	public String getWorkerNameFromExcel(String excelFileName, String sheetName) {
		String workerName = "";
		try {
			List<String> excelInputList = excelutility.getDataFromExcel(excelFileName, sheetName);
			workerName = excelInputList.get(1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return workerName;
	}

	public String getSetDateFromExcel(String excelFileName, String sheetName) {
		String setDate = "";
		try {
			List<String> excelInputList = excelutility.getDataFromExcel(excelFileName, sheetName);
			setDate = excelInputList.get(5);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return setDate;
	}

	public String verifyDeleteFirstRecord() {
		firstDeleteItem.click();
		waitutility.waitAlertDisplayed(driver);
		return generalutility.getAlertText(driver);
	}
}
