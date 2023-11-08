package elementRepository;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utilities.ExcelUtilities;
import utilities.GeneralUtilities;
import utilities.WaitUtilities;

public class Clients {

	WebDriver driver;
	GeneralUtilities gu = new GeneralUtilities();
	WaitUtilities wu = new WaitUtilities();
	ExcelUtilities eu = new ExcelUtilities();
	static String inputClientName;
	static int inputClientId;
	static int inputRefNo;
	static int inputInvoiceContract;
	static String inputDeliveryDropdown;
	static String inputPhoneNo;
	static String inputClientAddress;
	static int inputFax;
	static int inputSettlementDays;
	static String inputEmail;
	static String inputVatRateDropdown;
	static String inputPostCode;
	static int inputCompanyReg;
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

	public String getClientName(String fileName, String sheetName) {
		try {
			inputClientName = eu.getStringData(fileName, sheetName, 0, 1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return inputClientName;
	}

	public String getClientId(String fileName, String sheetName) {
		try {
			inputClientId = eu.getIntegerData(fileName, sheetName, 1, 1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return String.valueOf(inputClientId);
	}

	public void enterClientId(String clientId) {
		searchClientId.sendKeys(clientId);
	}

	public void clickSearch() {
		searchBtn.click();
	}

	public String generateClientName(String clientName) {
		String generatedClientName = clientName + gu.generateCurrentDateAndTime();
		return generatedClientName;
	}

	public void getClientDetailsFromExcel(String fileName, String sheetName) {
		try {
			inputClientName = eu.getStringData(fileName, sheetName, 0, 1);
			inputClientId = eu.getIntegerData(fileName, sheetName, 1, 1);
			inputRefNo = eu.getIntegerData(fileName, sheetName, 2, 1);
			inputInvoiceContract = eu.getIntegerData(fileName, sheetName, 3, 1);
			inputDeliveryDropdown = eu.getStringData(fileName, sheetName, 4, 1);
			inputPhoneNo = eu.getStringData(fileName, sheetName, 5, 1);
			inputClientAddress = eu.getStringData(fileName, sheetName, 6, 1);
			inputFax = eu.getIntegerData(fileName, sheetName, 7, 1);
			inputSettlementDays = eu.getIntegerData(fileName, sheetName, 8, 1);
			inputEmail = eu.getStringData(fileName, sheetName, 9, 1);
			inputVatRateDropdown = eu.getStringData(fileName, sheetName, 10, 1);
			inputPostCode = eu.getStringData(fileName, sheetName, 11, 1);
			inputCompanyReg = eu.getIntegerData(fileName, sheetName, 12, 1);
			inputInvoiceOrderDropdown = eu.getStringData(fileName, sheetName, 13, 1);
		} catch (Exception e) {
			System.out.println("An Exception Occurred!!!" + e);
		}

	}

	public String enterClientDetails() {
		gu.selectDropdownByIndex(branchDropdown, 1);
		gu.enterText(refNo, String.valueOf(inputRefNo)); // Convert int to String and pass it to enterText Function
		gu.selectDropdownByValue(invoiceOrderDropdown, inputInvoiceOrderDropdown);
		gu.selectDropdownByIndex(divisionDropdown, 1);
		gu.enterText(invoiceContract, String.valueOf(inputInvoiceContract));
		gu.selectDropdownByVisibleText(deliveryDropdown, inputDeliveryDropdown);
		String generatedClientName = generateClientName(inputClientName);
		gu.enterText(clientName, generatedClientName);
		gu.enterText(phoneNo, inputPhoneNo);
		gu.selectDropdownByIndex(masterDocumentDropdown, 1);
		gu.enterText(clientAddress, inputClientAddress);
		gu.enterText(fax, String.valueOf(inputFax));
		gu.enterText(settlementDays, String.valueOf(inputSettlementDays));
		gu.scrollDown(driver);
		gu.enterText(email, inputEmail);
		gu.selectDropdownByValue(vatrateDropdown, inputVatRateDropdown);
		gu.enterText(postCode, inputPostCode);
		gu.enterText(companyReg, String.valueOf(inputCompanyReg));
		wu = new WaitUtilities();
		wu.waitElementClickable(driver, directClient);
		directClient.click();
		return generatedClientName;
	}

	public void saveClient() {
		saveBtn.click();
	}

	public String getSavedClientName(WebDriver driver, String generatedClientName) {
		wu.waitTextDisplayed(driver, savedClient, generatedClientName);
		String actualClientName = gu.getElementText(savedClient);
		return actualClientName;
	}

	public int searchClientData(WebDriver driver, String searchElement) {
		return gu.findTableElement(driver, searchElement);
	}

	public void navigateToViewClient(WebDriver driver, WebElement element) {
		wu.waitElementClickable(driver, element);
		element.click();
	}

	public String getViewPageTitle(WebDriver driver, String clientName) {
		wu.waitTextDisplayed(driver, savedClient, clientName);
		String titleClientName = savedClient.getText();
		return titleClientName;
	}

}
