package elementRepository;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utilities.ExcelUtilities;
import utilities.GeneralUtilities;
import utilities.WaitUtilities;

public class Clients {

	WebDriver driver;
	GeneralUtilities generalutility = new GeneralUtilities();
	WaitUtilities waitutility = new WaitUtilities();
	ExcelUtilities excelutility = new ExcelUtilities();
	static String inputClientName;
	static String inputClientId;
	static String inputRefNo;
	static String inputInvoiceContract;
	static String inputDeliveryDropdown;
	static String inputPhoneNo;
	static String inputClientAddress;
	static String inputFax;
	static String inputSettlementDays;
	static String inputEmail;
	static String inputVatRateDropdown;
	static String inputPostCode;
	static String inputCompanyReg;
	static String inputInvoiceOrderDropdown;

	public Clients(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(linkText = "Clients")
	WebElement clientBtn;
	@FindBy(id = "clientsearch-client_name")
	WebElement searchClientName;
	@FindBy(id = "clientsearch-id")
	WebElement searchClientId;
	@FindBy(xpath = "//button[@class='btn btn-primary']")
	WebElement searchBtn;
	@FindBy(linkText = "Create Client")
	WebElement createClientBtn;
	@FindBy(id = "client-branch_id")
	WebElement branchDropdown;
	@FindBy(id = "client-invoice_order")
	WebElement invoiceOrderDropdown;
	@FindBy(id = "client-division_id")
	WebElement divisionDropdown;
	@FindBy(id = "client-invoice_delivery_method")
	WebElement deliveryDropdown;
	@FindBy(id = "client-master_document")
	WebElement masterDocumentDropdown;
	@FindBy(id = "client-vat_rate")
	WebElement vatrateDropdown;
	@FindBy(id = "client-your_ref")
	WebElement refNo;
	@FindBy(id = "client-invoice_contact")
	WebElement invoiceContract;
	@FindBy(id = "client-client_name")
	WebElement clientName;
	@FindBy(id = "client-phone")
	WebElement phoneNo;
	@FindBy(id = "client-client_address")
	WebElement clientAddress;
	@FindBy(id = "client-fax")
	WebElement fax;
	@FindBy(id = "client-settilement_days")
	WebElement settlementDays;
	@FindBy(id = "client-email")
	WebElement email;
	@FindBy(id = "client-postcode")
	WebElement postCode;
	@FindBy(id = "client-company_reg")
	WebElement companyReg;
	@FindBy(id = "client-direct_client")
	public WebElement directClient;
	@FindBy(xpath = "//button[@class='btn btn-success']")
	WebElement saveBtn;
	@FindBy(xpath = "//div[@class='col-sm-6 page-title']")
	public WebElement savedClient;

	public void navigateToCreateClient() {
		createClientBtn.click();
	}

	public void navigateToClient() {
		clientBtn.click();
	}

	public void enterClientName(String text) {
		searchClientName.sendKeys(text);
	}

	public String getClientName(String filePath, String fileName, String sheetName) {
		try {
			List<String> list = excelutility.getDataFromExcel(filePath, fileName, sheetName);
			inputClientName = list.get(1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return inputClientName;
	}

	public String getClientId(String filePath, String fileName, String sheetName) {
		try {
			List<String> list = excelutility.getDataFromExcel(filePath, fileName, sheetName);
			inputClientId = list.get(3);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return inputClientId;
	}

	public void enterClientId(String clientId) {
		searchClientId.sendKeys(clientId);
	}

	public void clickSearch() {
		searchBtn.click();
	}

	public String generateClientName(String clientName) {
		String generatedClientName = clientName + generalutility.generateCurrentDateAndTime();
		return generatedClientName;
	}

	public void getClientDetailsFromExcel(String filePath, String fileName, String sheetName) {
		try {
			List<String> list = excelutility.getDataFromExcel(filePath, fileName, sheetName);
			inputClientName = list.get(1);
			inputClientId = list.get(3);
			inputRefNo = list.get(5);
			inputInvoiceContract = list.get(7);
			inputDeliveryDropdown = list.get(9);
			inputPhoneNo = list.get(11);
			inputClientAddress = list.get(13);
			inputFax = list.get(15);
			inputSettlementDays = list.get(17);
			inputEmail = list.get(19);
			inputVatRateDropdown = list.get(21);
			inputPostCode = list.get(23);
			inputCompanyReg = list.get(25);
			inputInvoiceOrderDropdown = list.get(27);
		} catch (Exception e) {
			System.out.println("An Exception Occurred!!!" + e);
		}

	}

	public String enterClientDetails() {
		generalutility.selectDropdownByIndex(branchDropdown, 1);
		generalutility.enterText(refNo, String.valueOf(inputRefNo)); // Convert int to String and pass it to enterText
																		// Function
		generalutility.selectDropdownByValue(invoiceOrderDropdown, inputInvoiceOrderDropdown);
		generalutility.selectDropdownByIndex(divisionDropdown, 1);
		generalutility.enterText(invoiceContract, String.valueOf(inputInvoiceContract));
		generalutility.selectDropdownByVisibleText(deliveryDropdown, inputDeliveryDropdown);
		String generatedClientName = generateClientName(inputClientName);
		generalutility.enterText(clientName, generatedClientName);
		generalutility.enterText(phoneNo, inputPhoneNo);
		generalutility.selectDropdownByIndex(masterDocumentDropdown, 1);
		generalutility.enterText(clientAddress, inputClientAddress);
		generalutility.enterText(fax, String.valueOf(inputFax));
		generalutility.enterText(settlementDays, String.valueOf(inputSettlementDays));
		generalutility.scrollDown(driver);
		generalutility.enterText(email, inputEmail);
		generalutility.selectDropdownByValue(vatrateDropdown, inputVatRateDropdown);
		generalutility.enterText(postCode, inputPostCode);
		generalutility.enterText(companyReg, String.valueOf(inputCompanyReg));
		waitutility = new WaitUtilities();
		waitutility.waitElementClickable(driver, directClient);
		directClient.click();
		return generatedClientName;
	}

	public void saveClient() {
		saveBtn.click();
	}

	public String getSavedClientName(WebDriver driver, String generatedClientName) {
		waitutility.waitTextDisplayed(driver, savedClient, generatedClientName);
		String actualClientName = generalutility.getElementText(savedClient);
		return actualClientName;
	}

	public boolean searchAndEditClientData(WebDriver driver, String searchElement) {
		boolean elementFound = false;
		int elementIndex = generalutility.findTableElement(driver, searchElement);
		if (elementIndex >= 0) { // view details only when element is present/found in search result list
			String locatorEye = "//table[@class='table table-striped table-bordered']//tbody//tr[" + (elementIndex + 1)
					+ "]//td[6]//span[@class='glyphicon glyphicon-eye-open']";
			WebElement eyeElement = driver.findElement(By.xpath(locatorEye));
			navigateToViewClient(driver, eyeElement);
			elementFound = true;
		}
		return elementFound;
	}

	public void navigateToViewClient(WebDriver driver, WebElement element) {
		waitutility.waitElementClickable(driver, element);
		element.click();
	}

	public String getViewPageTitle(WebDriver driver, String clientName) {
		waitutility.waitTextDisplayed(driver, savedClient, clientName);
		String titleClientName = savedClient.getText();
		return titleClientName;
	}

}
