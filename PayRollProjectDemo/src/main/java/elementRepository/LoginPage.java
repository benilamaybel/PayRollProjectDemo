package elementRepository;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


import utilities.ExcelUtilities;
import utilities.GeneralUtilities;

public class LoginPage{
	WebDriver driver;
	GeneralUtilities gu = new GeneralUtilities();
	ExcelUtilities eu = new ExcelUtilities();

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "loginform-username")
	WebElement userNameField;
	@FindBy(id = "loginform-password")
	WebElement passWordField;
	@FindBy(name = "login-button")
	WebElement loginBtn;
	@FindBy(xpath = "//div[@class='form-group field-loginform-password required has-error']//p[@class='help-block help-block-error']")
	WebElement validationError;
	@FindBy (xpath = "//div[@class='col-sm-4 form-area inner']//h1")
	WebElement loginPageText;

	public String[] getUserDetailsFromExcel(String fileName, String sheetName) {
		String[] userCredentials = new String[2];
		try {
			userCredentials[0] = eu.getStringData(fileName,sheetName, 1, 0);
			userCredentials[1] = eu.getStringData(fileName, sheetName, 1, 1);

		} catch (Exception e) {
			System.out.println("An Exception Occurred!!!" + e);
		}
		return userCredentials;
	}

	public void performlogin(String userName, String passWord) {
		userNameField.sendKeys(userName);
		passWordField.sendKeys(passWord);
		loginBtn.click();
	}
	
	public void performloginUsingExcelInput(String fileName, String sheetName) {
		String[] userData =  getUserDetailsFromExcel(fileName,sheetName); //calling another function getUserDetailsFromExcel()
		String userName = userData[0];
		String passWord = userData[1]; 
		performlogin(userName, passWord);
	}

	public String getValidationError() {
		return gu.getElementText(validationError);
	}
	
	public String getLoginPageText() {
		return gu.getElementText(loginPageText);
	}

}
