package elementRepository;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

//import testCases.BaseClass;
import utilities.ExcelUtilities;
import utilities.GeneralUtilities;
import utilities.WaitUtilities;

public class TimeSheet {
	WebDriver driver;
	GeneralUtilities generalutility = new GeneralUtilities();
	WaitUtilities waitutility = new WaitUtilities();
	ExcelUtilities excelutility = new ExcelUtilities();
	static String inputTimeSheetNo;
	static String inputSearchClientString;
	static String inputClientValue;
	static String inputSearchWorkerString;
	static String inputWorkerValue;
	static String inputWeekEndDate;
	static String inputCategoryDropdown;
	static String inputDescriptionDropdown;
	static String inputUnits;
	static String inputPay;
	static String inputBill;
	public static String expectedTimeSheetPageTitle;
	public static String expectedPaySlipAlert;
	public static String expectedInvoiceAlert;
	public static String expectedPaySlipGenerateSuccessMessage;

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

	public void gotoTimeSheet() {
		waitutility.waitElementClickable(driver, timeSheetMenu);
		timeSheetMenu.click();
	}

	public void gotoCreateTimeSheet() {
		createTimeSheetBtn.click();
	}

	public void uploadTimeSheet(String filePath, String fileName) {
		// generalutility.uploadFile(browseFile, System.getProperty("user.dir") +
		// BaseClass.imageFilePath + fileName);
		generalutility.uploadFile(browseFile, System.getProperty("user.dir") + filePath + fileName);
	}

	public String getFileUploaded() {
		String uploadedFile = uploadedFileName.getAttribute("title");
		return uploadedFile;
	}

	public void performUploadButtonClick() {
		uploadBtn.click();
	}

	public void getTimeSheetDataFromExcel(String filePath, String excelFileName, String sheetName) {
		try {
			List<String> excelInputList = excelutility.getDataFromExcel(filePath, excelFileName, sheetName);
			inputTimeSheetNo = excelInputList.get(1);
			inputSearchClientString = excelInputList.get(3);
			inputClientValue = excelInputList.get(5);
			inputSearchWorkerString = excelInputList.get(7);
			inputWorkerValue = excelInputList.get(9);
			inputWeekEndDate = excelInputList.get(11);
			inputCategoryDropdown = excelInputList.get(13);
			inputDescriptionDropdown = excelInputList.get(15);
			inputUnits = excelInputList.get(17);
			inputPay = excelInputList.get(19);
			inputBill = excelInputList.get(21);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String enterTimeSheetDetails() {
		waitutility.fluentWaitElementClickable(driver, clientField);
		generalutility.selectDropdownByIndex(branchField, 1);
		clientField.click();
		searchClientDropdown.sendKeys(inputSearchClientString);
		generalutility.selectFromDynamicDropdown(driver, clientList, inputClientValue);
		workerField.click();
		searchWorkerDropdown.sendKeys(inputSearchWorkerString);
		generalutility.selectFromDynamicDropdown(driver, workerList, inputWorkerValue);
		generalutility.selectDropdownByIndex(divisionField, 1);
		weekEndDateField.sendKeys(inputWeekEndDate);
		generalutility.selectDropdownByVisibleText(categoryField, inputCategoryDropdown);
		timeSheetNoField.sendKeys(inputTimeSheetNo);
		generalutility.scrollDown(driver);
		generalutility.selectDropdownByVisibleText(descriptionDropdown, inputDescriptionDropdown);
		waitutility.waitAlertDisplayed(driver);
		generalutility.acceptAlert(driver);
		unitsField.sendKeys(inputUnits);
		payField.sendKeys(inputPay);
		billField.sendKeys(inputBill);
		awrField.click();
		generalutility.scrollDown(driver);
		saveBtn.click();
		return inputTimeSheetNo;
	}

	public String getTimeSheetNumberAfterSave() {
		return generalutility.getElementText(timeSheetNoAfterSave);
	}

	public String getPageTitle() {
		return generalutility.getElementText(pageTitle);
	}

	public void clickGeneratePayslip() {
		generatePayslip.click();
	}

	public void clickGenerateInvoice() {
		generateInvoice.click();
	}

	public String getTimeSheetAlertMessage() {
		waitutility.waitAlertDisplayed(driver);
		return generalutility.getAlertText(driver);
	}

	public void acceptTimeSheetAlert() {
		generalutility.acceptAlert(driver);
	}

	public void dismissTimeSheetAlert() {
		generalutility.dismissAlert(driver);
	}

	public void getExpectedDataFromExcel(String filePath, String fileName, String sheetName) {
		List<String> excelInputList;
		try {
			excelInputList = excelutility.getDataFromExcel(filePath, fileName, sheetName);
			expectedTimeSheetPageTitle = excelInputList.get(23);
			expectedPaySlipAlert = excelInputList.get(25);
			expectedPaySlipGenerateSuccessMessage = excelInputList.get(27);
			expectedInvoiceAlert = excelInputList.get(29);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
