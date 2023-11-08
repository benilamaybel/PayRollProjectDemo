package elementRepository;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utilities.ExcelUtilities;
import utilities.GeneralUtilities;
import utilities.WaitUtilities;

public class TimeSheet {
	WebDriver driver;
	GeneralUtilities gu = new GeneralUtilities();
	WaitUtilities wu = new WaitUtilities();
	ExcelUtilities eu = new ExcelUtilities();
	static int inputTimeSheetNo;
	static String inputSearchClientString;
	static String inputClientValue;
	static String inputSearchWorkerString;
	static String inputWorkerValue;
	static String inputWeekEndDate;
	static String inputCategoryDropdown;
	static String inputDescriptionDropdown;
	static int inputUnits;
	static int inputPay;
	static int inputBill;


	public TimeSheet(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(linkText = "TimeSheet")
	WebElement timeSheetMenu;
	@FindBy(xpath = "//div[@class='col-lg-2 col-sm-4 left-menu no-padding']/ul//li[3]/a")
	WebElement createTimeSheetBtn;
	@FindBy(id = "file-logo")
	WebElement browseFile;
	@FindBy(xpath = "//input[@class='file-caption-name']")
	WebElement uploadedFileName;
	@FindBy(xpath = "//button[@class='btn btn-default btn-secondary fileinput-upload fileinput-upload-button']")
	WebElement uploadBtn;
	@FindBy(id = "timesheet-branch_id")
	WebElement branchField;
	@FindBy(id = "select2-timesheet-client_id-container")
	WebElement clientField;
	@FindBy(xpath = "//input[@class='select2-search__field']")
	WebElement searchClientDropdown;
	@FindAll({ @FindBy(xpath = "//ul[@id ='select2-timesheet-client_id-results']//li") })
	List<WebElement> clientList;
	@FindBy(id = "select2-timesheet-worker_id-container")
	WebElement workerField;
	@FindAll({ @FindBy(xpath = "//ul[@id='select2-timesheet-worker_id-results']//li") })
	List<WebElement> workerList;
	@FindBy(xpath = "//input[@class='select2-search__field']")
	WebElement searchWorkerDropdown;
	@FindBy(id = "timesheet-division_id")
	WebElement divisionField;
	@FindBy(id = "timesheet-date")
	WebElement weekEndDateField;
	@FindBy(id = "timesheet-category")
	WebElement categoryField;
	@FindBy(id = "timesheet-timesheet_number")
	WebElement timeSheetNoField;
	@FindBy(id = "rate-0-type_id")
	WebElement descriptionDropdown;
	@FindBy(id = "rate-0-units")
	WebElement unitsField;
	@FindBy(id = "rate-0-pay")
	WebElement payField;
	@FindBy(id = "rate-0-bill")
	WebElement billField;
	@FindBy(id = "rate-0-awr")
	WebElement awrField;
	@FindBy(xpath = "//button[text()='Save']")
	WebElement saveBtn;
	@FindBy(xpath = "//table[@id='w0']//tr[1]//td")
	WebElement timeSheetNoAfterSave;
	@FindBy(xpath = "//div[@class='col-sm-6 page-title']")
	WebElement pageTitle;
	@FindBy(xpath = "//button[@class='btn btn-warning btn-responsive playslip']")
	WebElement generatePayslip;
	@FindBy(xpath = "//button[@class='btn btn-warning btn-responsive invoice']")
	WebElement generateInvoice;
	@FindBy(xpath="//a[@class='dropdown-toggle']")
	WebElement userDropdown;
	@FindBy(xpath = "//a[@class='logout-btn']")
	WebElement logoutBtn;
	public void gotoTimeSheet() {
		wu.waitElementClickable(driver, timeSheetMenu);
		timeSheetMenu.click();
	}

	public void gotoCreateTimeSheet() {
		createTimeSheetBtn.click();
	}

	public WebElement browsefile() {
		return browseFile;
	}

	public void uploadTimeSheet(String fileName) {
		gu.uploadFile(browsefile(), System.getProperty("user.dir") + "\\src\\main\\resources\\Image\\" + fileName);
	}

	public String getFileUploaded() {
		String uploadedFile = uploadedFileName.getAttribute("title");
		return uploadedFile;
	}

	public void performUploadButtonClick() {
		uploadBtn.click();
	}

	public void getTimeSheetDataFromExcel(String excelFileName, String sheetName) {
		try {
			inputTimeSheetNo = eu.getIntegerData(excelFileName,sheetName, 0, 1);
			inputSearchClientString = eu.getStringData(excelFileName,sheetName, 1, 1);
			inputClientValue = eu.getStringData(excelFileName,sheetName, 2, 1);
			inputSearchWorkerString = eu.getStringData(excelFileName,sheetName, 3, 1);
			inputWorkerValue = eu.getStringData(excelFileName,sheetName, 4, 1);
			inputWeekEndDate = eu.getStringData(excelFileName,sheetName, 5, 1);
			inputCategoryDropdown = eu.getStringData(excelFileName,sheetName, 6, 1);
			inputDescriptionDropdown = eu.getStringData(excelFileName,sheetName, 7, 1);
			inputUnits = eu.getIntegerData(excelFileName,sheetName, 8, 1);
			inputPay = eu.getIntegerData(excelFileName,sheetName, 9, 1);
			inputBill = eu.getIntegerData(excelFileName,sheetName, 10, 1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public int enterTimeSheetDetails() {
		wu.fluentWaitElementClickable(driver, clientField);
		gu.selectDropdownByIndex(branchField, 1);
		clientField.click();
		searchClientDropdown.sendKeys(inputSearchClientString);
		gu.selectFromDynamicDropdown(driver, clientList, inputClientValue);
		workerField.click();
		searchWorkerDropdown.sendKeys(inputSearchWorkerString);
		gu.selectFromDynamicDropdown(driver, workerList, inputWorkerValue);
		gu.selectDropdownByIndex(divisionField, 1);
		weekEndDateField.sendKeys(inputWeekEndDate);
		gu.selectDropdownByVisibleText(categoryField, inputCategoryDropdown);
		timeSheetNoField.sendKeys(String.valueOf(inputTimeSheetNo));
		gu.scrollDown(driver);
		gu.selectDropdownByVisibleText(descriptionDropdown, inputDescriptionDropdown);
		wu.waitAlertDisplayed(driver);
		gu.acceptAlert(driver);
		unitsField.sendKeys(String.valueOf(inputUnits));
		payField.sendKeys(String.valueOf(inputPay));
		billField.sendKeys(String.valueOf(inputBill));
		awrField.click();
		gu.scrollDown(driver);
		saveBtn.click();
		return inputTimeSheetNo;
	}

	public String getTimeSheetNumberAfterSave() {
		return gu.getElementText(timeSheetNoAfterSave);
	}

	public String getPageTitle() {
		return gu.getElementText(pageTitle);
	}

	public void clickGeneratePayslip() {
		generatePayslip.click();
	}

	public void clickGenerateInvoice() {
		generateInvoice.click();
	}

	public String getTimeSheetAlertMessage() {
		wu.waitAlertDisplayed(driver);
		return gu.getAlertText(driver);
	}
	
	public void acceptTimeSheetAlert()
	{
		gu.acceptAlert(driver);
	}

	public void dismissTimeSheetAlert()
	{
		gu.dismissAlert(driver);
	}
	
	public void performLogout(WebDriver driver) {
		userDropdown.click();
		wu.waitElementClickable(driver, logoutBtn);
		logoutBtn.click();
	}

}
